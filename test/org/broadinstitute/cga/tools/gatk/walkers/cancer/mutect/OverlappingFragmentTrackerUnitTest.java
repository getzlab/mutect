package org.broadinstitute.cga.tools.gatk.walkers.cancer.mutect;

import net.sf.samtools.SAMFileHeader;
import org.broadinstitute.sting.utils.GenomeLocParser;
import org.broadinstitute.sting.utils.sam.ArtificialSAMUtils;
import org.broadinstitute.sting.utils.sam.GATKSAMRecord;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OverlappingFragmentTrackerUnitTest {
    // what the tracker may hold beyond the reads of the pile: reads it has not got round to forgetting
    private static final int SLACK = 64;

    private final SAMFileHeader header = ArtificialSAMUtils.createArtificialSamHeader(2, 1, 100000);
    private final GenomeLocParser genomeLocParser = new GenomeLocParser(header.getSequenceDictionary());

    private GATKSAMRecord read(final String name, final int start, final int length) {
        return ArtificialSAMUtils.createArtificialRead(header, name, 0, start, length);
    }

    private List<GATKSAMRecord> reads(final String namePrefix, final int count) {
        final List<GATKSAMRecord> reads = new ArrayList<GATKSAMRecord>();
        for (int i = 0; i < count; i++) {
            reads.add(read(namePrefix + i, 1, 50));
        }
        return reads;
    }

    // visits a locus the way the overlapping-fragment filter does, with the reads of its pile
    private void visit(final OverlappingFragmentTracker tracker, final String contig, final int position, final List<GATKSAMRecord> pile) {
        tracker.startLocus(genomeLocParser.createGenomeLoc(contig, position));
        for (final GATKSAMRecord read : pile) {
            tracker.groupFor(read);
        }
    }

    /**
     * Walks a deep pile in which every read stays for as many loci as the engine keeps it, and checks at each
     * locus that the tracker holds little more than the pile: the reads of the pile, those that left the pile
     * at this very locus, and its allowance of reads it has yet to forget.
     */
    private void checkTrackerStaysCloseToThePile(final int lociAReadStays, final int readsStartingPerPosition, final int minimumDepth) {
        final int lastStart = 2000;
        final List<List<GATKSAMRecord>> readsStartingAt = new ArrayList<List<GATKSAMRecord>>();
        for (int start = 0; start <= lastStart; start++) {
            final List<GATKSAMRecord> starting = new ArrayList<GATKSAMRecord>();
            for (int i = 0; i < readsStartingPerPosition; i++) {
                starting.add(read("read" + start + "_" + i, start + 1, 50));
            }
            readsStartingAt.add(starting);
        }

        final OverlappingFragmentTracker tracker = new OverlappingFragmentTracker();
        int deepest = 0;
        int previousPileSize = 0;
        for (int position = 0; position <= lastStart; position++) {
            final List<GATKSAMRecord> pile = new ArrayList<GATKSAMRecord>();
            for (int start = Math.max(0, position - lociAReadStays + 1); start <= position; start++) {
                final List<GATKSAMRecord> starting = readsStartingAt.get(start);
                // some reads leave a few loci early, so that reads do not leave in the order they came
                final int staying = (position - start >= lociAReadStays - 7) ? starting.size() * 4 / 5 : starting.size();
                pile.addAll(starting.subList(0, staying));
            }
            visit(tracker, "chr1", position + 1, pile);

            final int allowed = pile.size() + readsStartingPerPosition + SLACK + previousPileSize / 8;
            Assert.assertTrue(tracker.trackedReadCount() <= allowed,
                    "at position " + position + " the pile has " + pile.size() + " reads and the tracker holds " + tracker.trackedReadCount());
            Assert.assertTrue(tracker.trackedReadCount() >= pile.size());
            deepest = Math.max(deepest, pile.size());
            previousPileSize = pile.size();
        }
        Assert.assertTrue(deepest >= minimumDepth, "the test pile should be deep, was " + deepest);
    }

    @Test
    public void holdsLittleMoreThanThePileWhenReadsRunToTheirEnd() {
        checkTrackerStaysCloseToThePile(50, 20, 900);
    }

    /**
     * Where coverage is above the downsampling target the engine drops most reads long before they end.
     * The tracker must let go of them then, not when the traversal reaches their end.
     */
    @Test
    public void holdsLittleMoreThanThePileWhenTheEngineDropsReadsEarly() {
        checkTrackerStaysCloseToThePile(8, 120, 700);
    }

    @Test
    public void forgetsEverythingOnANewContig() {
        final OverlappingFragmentTracker tracker = new OverlappingFragmentTracker();
        visit(tracker, "chr1", 130, reads("a", 500));
        Assert.assertEquals(tracker.trackedReadCount(), 500);

        final GATKSAMRecord onChr2 = ArtificialSAMUtils.createArtificialRead(header, "b", 1, 130, 50);
        visit(tracker, "chr2", 130, Arrays.asList(onChr2));
        Assert.assertEquals(tracker.trackedReadCount(), 1);
    }

    @Test
    public void readsSharingANameShareAGroup() {
        final GATKSAMRecord first = read("pair", 100, 50);
        final GATKSAMRecord mate = read("pair", 130, 50);
        final GATKSAMRecord other = read("other", 130, 50);

        final OverlappingFragmentTracker tracker = new OverlappingFragmentTracker();
        tracker.startLocus(genomeLocParser.createGenomeLoc("chr1", 140));
        final OverlappingFragmentTracker.Group group = tracker.groupFor(first);
        Assert.assertSame(tracker.groupFor(mate), group);
        Assert.assertNotSame(tracker.groupFor(other), group);
        Assert.assertEquals(group.members, 2);

        // the same groups at the next locus
        tracker.startLocus(genomeLocParser.createGenomeLoc("chr1", 141));
        Assert.assertSame(tracker.groupFor(mate), group);
        Assert.assertSame(tracker.groupFor(first), group);
        Assert.assertEquals(group.members, 2);
    }

    /**
     * A read can be missing from the pile at a locus and come back (its base there is an N, or the locus is in
     * a skipped region of the read).  If it was forgotten in between, it must find its mate's group again.
     */
    @Test
    public void aForgottenReadThatComesBackRejoinsItsMate() {
        final GATKSAMRecord first = read("pair", 100, 50);
        final GATKSAMRecord mate = read("pair", 130, 50);

        final OverlappingFragmentTracker tracker = new OverlappingFragmentTracker();

        final List<GATKSAMRecord> pile = reads("filler", 200);
        pile.add(first);
        pile.add(mate);
        visit(tracker, "chr1", 140, pile);
        Assert.assertEquals(tracker.trackedReadCount(), 202);

        // only the mate is in the next pile; at the locus after, everything else has been forgotten
        visit(tracker, "chr1", 141, Arrays.asList(mate));
        tracker.startLocus(genomeLocParser.createGenomeLoc("chr1", 142));
        Assert.assertEquals(tracker.trackedReadCount(), 1);

        final OverlappingFragmentTracker.Group mateGroup = tracker.groupFor(mate);
        Assert.assertEquals(mateGroup.members, 1);
        Assert.assertSame(tracker.groupFor(first), mateGroup);
        Assert.assertEquals(mateGroup.members, 2);
    }

    @Test
    public void aNameStartsAfreshOnceAllItsReadsAreForgotten() {
        final GATKSAMRecord first = read("pair", 100, 50);
        final GATKSAMRecord mate = read("pair", 130, 50);

        final OverlappingFragmentTracker tracker = new OverlappingFragmentTracker();

        final List<GATKSAMRecord> pile = reads("filler", 200);
        pile.add(first);
        pile.add(mate);
        tracker.startLocus(genomeLocParser.createGenomeLoc("chr1", 140));
        for (final GATKSAMRecord read : pile) { tracker.groupFor(read); }
        final OverlappingFragmentTracker.Group oldGroup = tracker.groupFor(first);

        // neither read of the pair is in the next pile
        visit(tracker, "chr1", 141, reads("another", 1));
        tracker.startLocus(genomeLocParser.createGenomeLoc("chr1", 142));
        Assert.assertEquals(tracker.trackedReadCount(), 1);

        final OverlappingFragmentTracker.Group newGroup = tracker.groupFor(mate);
        Assert.assertNotSame(newGroup, oldGroup);
        Assert.assertSame(tracker.groupFor(first), newGroup);
        Assert.assertEquals(newGroup.members, 2);
    }
}
