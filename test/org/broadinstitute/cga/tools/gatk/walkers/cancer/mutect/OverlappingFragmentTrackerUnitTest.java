package org.broadinstitute.cga.tools.gatk.walkers.cancer.mutect;

import net.sf.samtools.SAMFileHeader;
import org.broadinstitute.sting.utils.GenomeLocParser;
import org.broadinstitute.sting.utils.sam.ArtificialSAMUtils;
import org.broadinstitute.sting.utils.sam.GATKSAMRecord;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class OverlappingFragmentTrackerUnitTest {
    private final SAMFileHeader header = ArtificialSAMUtils.createArtificialSamHeader(2, 1, 100000);
    private final GenomeLocParser genomeLocParser = new GenomeLocParser(header.getSequenceDictionary());

    private GATKSAMRecord read(final String name, final int contigIndex, final int start, final int length) {
        return ArtificialSAMUtils.createArtificialRead(header, name, contigIndex, start, length);
    }

    // visits a locus the way the overlapping-fragment filter does, with the reads that cover it
    private int visit(final OverlappingFragmentTracker tracker, final String contig, final int position, final List<GATKSAMRecord> reads) {
        tracker.startLocus(genomeLocParser.createGenomeLoc(contig, position));
        int covering = 0;
        for (final GATKSAMRecord read : reads) {
            if (read.getAlignmentStart() <= position && position <= read.getAlignmentEnd()) {
                tracker.groupFor(read);
                covering++;
            }
        }
        return covering;
    }

    /**
     * The tracker must not hold on to reads that have ended: at every locus it knows exactly the reads that
     * cover the locus, no matter how deep the pile is or how long the traversal has been going.
     */
    @Test
    public void holdsOnlyTheReadsCoveringTheLocus() {
        final int readLength = 50;
        final int readsStartingPerPosition = 20;
        final int lastStart = 2000;

        final List<GATKSAMRecord> reads = new ArrayList<GATKSAMRecord>();
        for (int start = 1; start <= lastStart; start++) {
            for (int i = 0; i < readsStartingPerPosition; i++) {
                // a few shorter reads, so that reads do not end in the order they start
                final int length = (i % 5 == 0) ? readLength - 17 : readLength;
                reads.add(read("read" + start + "_" + i, 0, start, length));
            }
        }

        final OverlappingFragmentTracker tracker = new OverlappingFragmentTracker();
        int deepest = 0;
        for (int position = 1; position <= lastStart + readLength; position++) {
            // only hand the tracker the reads near this position, as a pileup would
            final int from = Math.max(0, (position - readLength) * readsStartingPerPosition);
            final int to = Math.min(reads.size(), position * readsStartingPerPosition);
            final int covering = visit(tracker, "chr1", position, reads.subList(from, to));
            Assert.assertEquals(tracker.openReadCount(), covering, "reads held at position " + position);
            deepest = Math.max(deepest, covering);
        }
        Assert.assertTrue(deepest >= 900, "the test pile should be deep, was " + deepest);
    }

    @Test
    public void forgetsReadsAcrossAGapAndOnANewContig() {
        final List<GATKSAMRecord> reads = new ArrayList<GATKSAMRecord>();
        reads.add(read("a", 0, 100, 50));
        reads.add(read("b", 0, 120, 50));
        reads.add(read("c", 0, 5000, 50));
        reads.add(read("d", 1, 5000, 50));

        final OverlappingFragmentTracker tracker = new OverlappingFragmentTracker();
        final List<GATKSAMRecord> onChr1 = reads.subList(0, 3);
        Assert.assertEquals(visit(tracker, "chr1", 130, onChr1), 2);
        Assert.assertEquals(tracker.openReadCount(), 2);

        // the traversal skips positions without coverage (and the gaps between intervals)
        Assert.assertEquals(visit(tracker, "chr1", 5010, onChr1), 1);
        Assert.assertEquals(tracker.openReadCount(), 1);

        Assert.assertEquals(visit(tracker, "chr2", 5010, reads.subList(3, 4)), 1);
        Assert.assertEquals(tracker.openReadCount(), 1);
    }

    @Test
    public void readsSharingANameShareAGroupWhileTheyAreOpen() {
        final GATKSAMRecord first = read("pair", 0, 100, 50);    // covers 100-149
        final GATKSAMRecord mate = read("pair", 0, 130, 50);     // covers 130-179
        final GATKSAMRecord other = read("other", 0, 130, 50);
        final GATKSAMRecord sameNameLater = read("pair", 0, 300, 50);

        final OverlappingFragmentTracker tracker = new OverlappingFragmentTracker();

        tracker.startLocus(genomeLocParser.createGenomeLoc("chr1", 140));
        final OverlappingFragmentTracker.Group group = tracker.groupFor(first);
        Assert.assertSame(tracker.groupFor(mate), group);
        Assert.assertSame(tracker.groupFor(first), group);
        Assert.assertNotSame(tracker.groupFor(other), group);
        Assert.assertEquals(group.members, 2);

        // a read is still known at its last base ...
        tracker.startLocus(genomeLocParser.createGenomeLoc("chr1", 149));
        Assert.assertSame(tracker.groupFor(first), group);
        Assert.assertEquals(tracker.openReadCount(), 3);

        // ... and forgotten right after it, while its mate keeps the group
        tracker.startLocus(genomeLocParser.createGenomeLoc("chr1", 150));
        Assert.assertEquals(tracker.openReadCount(), 2);
        Assert.assertSame(tracker.groupFor(mate), group);
        Assert.assertEquals(group.members, 1);

        // once every read of the name has ended, the name starts afresh
        tracker.startLocus(genomeLocParser.createGenomeLoc("chr1", 310));
        Assert.assertEquals(tracker.openReadCount(), 0);
        final OverlappingFragmentTracker.Group later = tracker.groupFor(sameNameLater);
        Assert.assertNotSame(later, group);
        Assert.assertEquals(later.members, 1);
    }
}
