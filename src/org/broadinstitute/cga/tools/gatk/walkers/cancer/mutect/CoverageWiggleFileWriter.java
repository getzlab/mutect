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

import net.sf.picard.util.FormatUtil;
import org.broadinstitute.sting.gatk.contexts.AlignmentContext;

import java.io.PrintStream;

/**
 * Handles writing of wiggle format coverage data to an underlying PrintStream from GATK
 *
 * Output is accumulated as bytes in a buffer and handed to the stream in large blocks, rather than one
 * PrintStream.print per locus (each of which runs through the charset encoder and a flush).  The bytes
 * written are the same as before.  flush() must be called once traversal is complete so that the tail of
 * the buffer reaches the stream before it is closed.
 */
public class CoverageWiggleFileWriter {
    protected PrintStream ps;

    private int lastContigIndex = -1;
    private long lastPosition = -1;

    private final FormatUtil fmt = new FormatUtil();

    private static final int BUFFER_SIZE = 1 << 16;
    private final byte[] buffer;
    private int bufferLength = 0;


    public CoverageWiggleFileWriter(PrintStream ps) {
        this(ps, "SomaticCoverage");
    }

    public CoverageWiggleFileWriter(PrintStream ps, String trackName) {
        this.ps = ps;
        this.buffer = hasPrintStream() ? new byte[BUFFER_SIZE] : null;

        append("track type=wiggle_0 name=" + trackName + "\n");
    }

    private boolean hasPrintStream() {
        return (ps != null);
    }

    /**
     * Writes any buffered output to the underlying stream.  Must be called when traversal is complete.
     */
    public void flush() {
        if (hasPrintStream() && bufferLength > 0) {
            ps.write(buffer, 0, bufferLength);
            bufferLength = 0;
        }
    }

    private void append(final String s) {
        if (!hasPrintStream()) { return; }

        // the output is ASCII; anything else is encoded exactly as PrintStream would have (platform default charset)
        final int length = s.length();
        for (int i = 0; i < length; i++) {
            if (s.charAt(i) >= 128) {
                append(s.getBytes());
                return;
            }
        }

        if (length > buffer.length - bufferLength) { flush(); }
        if (length > buffer.length) {
            ps.write(s.getBytes(), 0, length);
            return;
        }
        for (int i = 0; i < length; i++) {
            buffer[bufferLength++] = (byte) s.charAt(i);
        }
    }

    private void append(final byte[] bytes) {
        if (bytes.length > buffer.length - bufferLength) { flush(); }
        if (bytes.length > buffer.length) {
            ps.write(bytes, 0, bytes.length);
            return;
        }
        System.arraycopy(bytes, 0, buffer, bufferLength, bytes.length);
        bufferLength += bytes.length;
    }

    // same characters as StringBuilder.append(long)
    private void append(final long value) {
        if (!hasPrintStream()) { return; }
        if (value < 0) {
            append(Long.toString(value));
            return;
        }

        int digits = 1;
        for (long v = value; v >= 10; v /= 10) { digits++; }

        if (digits > buffer.length - bufferLength) { flush(); }
        int position = bufferLength + digits;
        long v = value;
        do {
            buffer[--position] = (byte) ('0' + (v % 10));
            v /= 10;
        } while (v > 0);
        bufferLength += digits;
    }

    public void writeCoverage(final AlignmentContext context, final boolean isBaseCovered) {
        writeCoverage(context, isBaseCovered?1:0);
    }

    private void possiblyGenerateHeader(final AlignmentContext context) {
        if (this.lastContigIndex != context.getLocation().getContigIndex() ||
            this.lastPosition + 1 != context.getPosition()) {
                this.lastContigIndex = context.getLocation().getContigIndex();
                append("fixedStep chrom=");
                append(context.getContig());
                append(" start=");
                append(context.getPosition());
                append(" step=1\n");
        }
        this.lastPosition = context.getPosition();
    }

    public void writeCoverage(final AlignmentContext context, final int depth) {
        if (hasPrintStream()) {
            possiblyGenerateHeader(context);
            append(depth);
            append("\n");
        }
    }

    public void writeCoverage(final AlignmentContext context, final double power) {
        if (hasPrintStream()) {
            possiblyGenerateHeader(context);
            append(fmt.format(power));
            append("\n");
        }
    }

}
