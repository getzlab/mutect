/*
 * By downloading the PROGRAM you agree to the following terms of use:
 *
 * BROAD INSTITUTE SOFTWARE LICENSE AGREEMENT
 * FOR ACADEMIC NON-COMMERCIAL RESEARCH PURPOSES ONLY
 *
 * This Agreement is made between the Broad Institute, Inc. with a principal address at 7 Cambridge Center, Cambridge, MA 02142 ("BROAD") and the LICENSEE and is effective at the date the downloading is completed ("EFFECTIVE DATE").
 * WHEREAS, LICENSEE desires to license the PROGRAM, as defined hereinafter, and BROAD wishes to have this PROGRAM utilized in the public interest, subject only to the royalty-free, nonexclusive, nontransferable license rights of the United States Government pursuant to 48 CFR 52.227-14; and
 * WHEREAS, LICENSEE desires to license the PROGRAM and BROAD desires to grant a license on the following terms and conditions.
 * NOW, THEREFORE, in consideration of the promises and covenants made herein, the parties hereto agree as follows:
 *
 * 1. DEFINITIONS
 * 1.1	"PROGRAM" shall mean copyright in the object code and source code known as MuTect and related documentation, if any, as they exist on the EFFECTIVE DATE and can be downloaded from http://www.broadinstitute.or/cancer/cga/mutect on the EFFECTIVE DATE.
 *
 * 2. LICENSE
 * 2.1   Grant. Subject to the terms of this Agreement, BROAD hereby grants to LICENSEE, solely for academic non-commercial research purposes, a non-exclusive, non-transferable license to: (a) download, execute and display the PROGRAM and (b) create bug fixes and modify the PROGRAM.
 * LICENSEE hereby automatically grants to BROAD a non-exclusive, royalty-free, irrevocable license to any LICENSEE bug fixes or modifications to the PROGRAM with unlimited rights to sublicense and/or distribute.  LICENSEE agrees to provide any such modifications and bug fixes to BROAD promptly upon their creation.
 * The LICENSEE may apply the PROGRAM in a pipeline to data owned by users other than the LICENSEE and provide these users the results of the PROGRAM provided LICENSEE does so for academic non-commercial purposes only.  For clarification purposes, academic sponsored research is not a commercial use under the terms of this Agreement.
 * 2.2  No Sublicensing or Additional Rights. LICENSEE shall not sublicense or distribute the PROGRAM, in whole or in part, without prior written permission from BROAD.  LICENSEE shall ensure that all of its users agree to the terms of this Agreement.  LICENSEE further agrees that it shall not put the PROGRAM on a network, server, or other similar technology that may be accessed by anyone other than the LICENSEE and its employees and users who have agreed to the terms of this agreement.
 * 2.3  License Limitations. Nothing in this Agreement shall be construed to confer any rights upon LICENSEE by implication, estoppel, or otherwise to any computer software, trademark, intellectual property, or patent rights of BROAD, or of any other entity, except as expressly granted herein. LICENSEE agrees that the PROGRAM, in whole or part, shall not be used for any commercial purpose, including without limitation, as the basis of a commercial software or hardware product or to provide services. LICENSEE further agrees that the PROGRAM shall not be copied or otherwise adapted in order to circumvent the need for obtaining a license for use of the PROGRAM.
 *
 * 3. OWNERSHIP OF INTELLECTUAL PROPERTY
 * LICENSEE acknowledges that title to the PROGRAM shall remain with BROAD. The PROGRAM is marked with the following BROAD copyright notice and notice of attribution to contributors. LICENSEE shall retain such notice on all copies.  LICENSEE agrees to include appropriate attribution if any results obtained from use of the PROGRAM are included in any publication.
 *
 * Copyright 2012 Broad Institute, Inc.
 * Notice of attribution:  The MuTect program was made available through the generosity of the Cancer Genome Analysis group at the Broad Institute, Inc. and is published at doi: 10.1038/nbt.2514.
 *
 * LICENSEE shall not use any trademark or trade name of BROAD, or any variation, adaptation, or abbreviation, of such marks or trade names, or any names of officers, faculty, students, employees, or agents of BROAD except as states above for attribution purposes.
 *
 * 4. INDEMNIFICATION
 * LICENSEE shall indemnify, defend, and hold harmless BROAD, and their respective officers, faculty, students, employees, associated investigators and agents, and their respective successors, heirs and assigns, ("Indemnitees"), against any liability, damage, loss, or expense (including reasonable attorneys fees and expenses) incurred by or imposed upon any of the Indemnitees in connection with any claims, suits, actions, demands or judgments arising out of any theory of liability (including, without limitation, actions in the form of tort, warranty, or strict liability and regardless of whether such action has any factual basis) pursuant to any right or license granted under this Agreement.
 *
 * 5. NO REPRESENTATIONS OR WARRANTIES
 * THE PROGRAM IS DELIVERED "AS IS."  BROAD MAKES NO REPRESENTATIONS OR WARRANTIES OF ANY KIND CONCERNING THE PROGRAM OR THE COPYRIGHT, EXPRESS OR IMPLIED, INCLUDING, WITHOUT LIMITATION, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, NONINFRINGEMENT, OR THE ABSENCE OF LATENT OR OTHER DEFECTS, WHETHER OR NOT DISCOVERABLE. BROAD EXTENDS NO WARRANTIES OF ANY KIND AS TO PROGRAM CONFORMITY WITH WHATEVER USER MANUALS OR OTHER LITERATURE MAY BE ISSUED FROM TIME TO TIME.
 * IN NO EVENT SHALL BROAD OR ITS RESPECTIVE DIRECTORS, OFFICERS, EMPLOYEES, AFFILIATED INVESTIGATORS AND AFFILIATES BE LIABLE FOR INCIDENTAL OR CONSEQUENTIAL DAMAGES OF ANY KIND, INCLUDING, WITHOUT LIMITATION, ECONOMIC DAMAGES OR INJURY TO PROPERTY AND LOST PROFITS, REGARDLESS OF WHETHER BROAD SHALL BE ADVISED, SHALL HAVE OTHER REASON TO KNOW, OR IN FACT SHALL KNOW OF THE POSSIBILITY OF THE FOREGOING.
 *
 * 6. ASSIGNMENT
 * This Agreement is personal to LICENSEE and any rights or obligations assigned by LICENSEE without the prior written consent of BROAD shall be null and void.
 *
 * 7. MISCELLANEOUS
 * 7.1 Export Control. LICENSEE gives assurance that it will comply with all United States export control laws and regulations controlling the export of the PROGRAM, including, without limitation, all Export Administration Regulations of the United States Department of Commerce. Among other things, these laws and regulations prohibit, or require a license for, the export of certain types of software to specified countries.
 * 7.2 Termination. LICENSEE shall have the right to terminate this Agreement for any reason upon prior written notice to BROAD. If LICENSEE breaches any provision hereunder, and fails to cure such breach within thirty (30) days, BROAD may terminate this Agreement immediately. Upon termination, LICENSEE shall provide BROAD with written assurance that the original and all copies of the PROGRAM have been destroyed, except that, upon prior written authorization from BROAD, LICENSEE may retain a copy for archive purposes.
 * 7.3 Survival. The following provisions shall survive the expiration or termination of this Agreement: Articles 1, 3, 4, 5 and Sections 2.2, 2.3, 7.3, and 7.4.
 * 7.4 Notice. Any notices under this Agreement shall be in writing, shall specifically refer to this Agreement, and shall be sent by hand, recognized national overnight courier, confirmed facsimile transmission, confirmed electronic mail, or registered or certified mail, postage prepaid, return receipt requested.  All notices under this Agreement shall be deemed effective upon receipt.
 * 7.5 Amendment and Waiver; Entire Agreement. This Agreement may be amended, supplemented, or otherwise modified only by means of a written instrument signed by all parties. Any waiver of any rights or failure to act in a specific instance shall relate only to such instance and shall not be construed as an agreement to waive any rights or fail to act in any other instance, whether or not similar. This Agreement constitutes the entire agreement among the parties with respect to its subject matter and supersedes prior agreements or understandings between the parties relating to its subject matter.
 * 7.6 Binding Effect; Headings. This Agreement shall be binding upon and inure to the benefit of the parties and their respective permitted successors and assigns. All headings are for convenience only and shall not affect the meaning of any provision of this Agreement.
 * 7.7 Governing Law. This Agreement shall be construed, governed, interpreted and applied in accordance with the internal laws of the Commonwealth of Massachusetts, U.S.A., without regard to conflict of laws principles.
 */

package org.broadinstitute.cga.tools.gatk.walkers.cancer.mutect;

import org.broadinstitute.sting.utils.GenomeLoc;
import org.broadinstitute.sting.utils.sam.GATKSAMRecord;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Bookkeeping for the overlapping-fragment filter that avoids hashing every read name at every locus.
 *
 * The engine presents the same read object at every locus the read covers, so each read is looked up by name
 * once, when it is first seen, and linked to the group of tracked reads that share its name (normally just its
 * mate).  At a locus, the reads of a group go through the same sequential comparison the per-locus version of
 * the filter applies, in the same pileup order, so the result is identical.
 *
 * Reads are forgotten soon after they stop turning up in the pile, whether they have ended or the engine has
 * dropped them early (where coverage is above the downsampling target it replaces most reads long before they
 * end): the tracker never holds more than the reads of the last pile plus an eighth.  Holding reads for longer
 * keeps their bases and qualities in memory, hundreds of thousands of reads where the coverage is extreme.
 * A read that turns up again after being forgotten (it can be missing from the pile at a locus where its base
 * is an N, or inside a skipped region) is simply looked up by name again, so when a read is forgotten never
 * changes the result.
 *
 * A tracker belongs to one kind of pile (tumor or normal), since names are only compared within a kind, and it
 * must be used for exactly one filter call per locus.  Not thread safe.
 */
final class OverlappingFragmentTracker {

    /** The tracked reads sharing one name, plus the state of the comparison among them at the current locus */
    static final class Group {
        int members = 0;

        // valid only when locusSerial matches the tracker's current locus
        long locusSerial = -1;
        boolean hasWinner = false;
        int winnerIndex = -1;
    }

    /** A tracked read's group, and the last locus at which the read was in the pile */
    private static final class TrackedRead {
        final Group group;
        long lastSeenAtLocus;

        TrackedRead(final Group group) { this.group = group; }
    }

    // reads that were not in the last pile are tolerated up to this many plus an eighth of that pile
    private static final int UNSEEN_READS_ALLOWED = 64;

    private final Map<GATKSAMRecord, TrackedRead> trackedReads = new IdentityHashMap<GATKSAMRecord, TrackedRead>();
    private final Map<String, Group> groupByName = new HashMap<String, Group>();

    private long locusSerial = 0;
    private int contigIndex = -1;
    private int readsSeenAtThisLocus = 0;

    /**
     * Must be called once at the start of each locus, before groupFor() is called for its reads
     */
    void startLocus(final GenomeLoc locus) {
        if (locus.getContigIndex() != contigIndex) {
            // nothing from another contig can be in a pileup on this one
            trackedReads.clear();
            groupByName.clear();
            contigIndex = locus.getContigIndex();
        } else {
            final int unseen = trackedReads.size() - readsSeenAtThisLocus;
            if (unseen > UNSEEN_READS_ALLOWED + readsSeenAtThisLocus / 8) {
                forgetReadsNotSeenAt(locusSerial);
            }
        }

        locusSerial++;
        readsSeenAtThisLocus = 0;
    }

    long currentLocusSerial() {
        return locusSerial;
    }

    /**
     * @return the group of tracked reads sharing this read's name, creating it (and linking the read to it) on
     *         the read's first appearance.  To be called once per locus for each read of the pile.
     */
    Group groupFor(final GATKSAMRecord read) {
        TrackedRead tracked = trackedReads.get(read);
        if (tracked == null) {
            final String readName = read.getReadName();
            Group group = groupByName.get(readName);
            if (group == null) {
                group = new Group();
                groupByName.put(readName, group);
            }
            group.members++;
            tracked = new TrackedRead(group);
            trackedReads.put(read, tracked);
        }
        tracked.lastSeenAtLocus = locusSerial;
        readsSeenAtThisLocus++;
        return tracked.group;
    }

    private void forgetReadsNotSeenAt(final long locus) {
        final Iterator<Map.Entry<GATKSAMRecord, TrackedRead>> entries = trackedReads.entrySet().iterator();
        while (entries.hasNext()) {
            final Map.Entry<GATKSAMRecord, TrackedRead> entry = entries.next();
            final TrackedRead tracked = entry.getValue();
            if (tracked.lastSeenAtLocus != locus) {
                if (--tracked.group.members == 0) {
                    groupByName.remove(entry.getKey().getReadName());
                }
                entries.remove();
            }
        }
    }

    int trackedReadCount() {
        return trackedReads.size();
    }
}
