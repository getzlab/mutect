package org.broadinstitute.cga.tools.gatk.walkers.cancer.mutect;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.BinomialDistribution;
import org.apache.commons.math.distribution.BinomialDistributionImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for MuTect
 */
public class PowerCalculationUnitTest {
    @Test
    public void testTumor() throws MathException {
        double Q30_EPS = Math.pow(10, (-30/10)) / 3d;
        double LOD = 6.3d;
        double wiggle = 0.00001;

        // test log likelihood calculation
        Assert.assertEquals(TumorPowerCalculator.calculateLogLikelihood(50, 5, 0.001, 0.1), -7.059164, wiggle, "Failed!");
        Assert.assertEquals(TumorPowerCalculator.calculateLogLikelihood(40, 5, 0.001, 0.1), -6.597727, wiggle, "Failed!");
        Assert.assertEquals(TumorPowerCalculator.calculateLogLikelihood(40, 10, 0.001, 0.1), -11.34971, wiggle, "Failed!");
        Assert.assertEquals(TumorPowerCalculator.calculateLogLikelihood(40, 10, 0.01, 0.1), -11.15482, wiggle, "Failed!");
        Assert.assertEquals(TumorPowerCalculator.calculateLogLikelihood(40, 10, 0.01, 0.2), -9.866713, wiggle, "Failed!");


        Assert.assertEquals(testTumorCalculatePower(50, Q30_EPS, LOD, 0.15, 0), 0.975342, wiggle, "Failed!");

        Assert.assertEquals(testTumorCalculatePower(20, Q30_EPS, LOD, 0.15, 0), 0.6364833, wiggle, "Failed!");
        Assert.assertEquals(testTumorCalculatePower(10, Q30_EPS, LOD, 0.15, 0), 0.3167154, wiggle, "Failed!");
        Assert.assertEquals(testTumorCalculatePower(50, Q30_EPS, LOD, 0.35, 0), 0.9999994, wiggle, "Failed!");
        Assert.assertEquals(testTumorCalculatePower(50, Q30_EPS, LOD, 0.05, 0), 0.3893047, wiggle, "Failed!");
        Assert.assertEquals(testTumorCalculatePower(50, Q30_EPS, 4.3, 0.05, 0), 0.6068266, wiggle, "Failed!");
        Assert.assertEquals(testTumorCalculatePower(50, Q30_EPS, 4.3, 0.05, 0.02), 0.0039135, wiggle, "Failed!");

        // make an instance to test caching
        TumorPowerCalculator pc = new TumorPowerCalculator(Q30_EPS, LOD, 0);
        Assert.assertEquals(pc.cachingPowerCalculation(50, 0.15), 0.975342, wiggle, "Failed!");
        Assert.assertEquals(pc.cachingPowerCalculation(50, 0.15), 0.975342, wiggle, "Failed!");
        Assert.assertEquals(pc.cachingPowerCalculation(50, 0.15), 0.975342, wiggle, "Failed!");

    }

    private static double testTumorCalculatePower(int n, double eps, double fdr, double delta, double contam) throws MathException {
        double power = TumorPowerCalculator.calculatePower(n, eps, fdr, delta, contam, true);
        System.out.println("Depth: " + n + " EPS: " + eps + " FDR: " + fdr + " Delta: " + delta + " --> Power: " + power);
        return power;
    }



    @Test
    public void testNormal() throws MathException {
        double Q30_EPS = Math.pow(10, (-30/10)) / 3d;
        double LOD = 2.3d;
        double wiggle = 0.000001;

        Assert.assertEquals(testNormalCalculatePower(50, Q30_EPS, LOD), 1.0000000, wiggle, "Failed!");
        Assert.assertEquals(testNormalCalculatePower(10, Q30_EPS, LOD), 0.9973492, wiggle, "Failed!");
        Assert.assertEquals(testNormalCalculatePower( 8, Q30_EPS, LOD), 0.9974184, wiggle, "Failed!");
        Assert.assertEquals(testNormalCalculatePower( 5, Q30_EPS, LOD), 0.0000000, wiggle, "Failed!");
        Assert.assertEquals(testNormalCalculatePower(10, Math.pow(10, (-20/10)) / 3d, LOD), 0.9762534, wiggle, "Failed!");


        // make an instance to test caching
        NormalPowerCalculator pc = new NormalPowerCalculator(Q30_EPS, LOD, true);
        Assert.assertEquals(pc.cachingPowerCalculation(10), 0.9973492, wiggle, "Failed!");
        Assert.assertEquals(pc.cachingPowerCalculation(10), 0.9973492, wiggle, "Failed!");
        Assert.assertEquals(pc.cachingPowerCalculation(10), 0.9973492, wiggle, "Failed!");

    }

    private static double testNormalCalculatePower(int n, double eps, double threshold) throws MathException {
        double power = NormalPowerCalculator.calculatePower(n, eps, threshold, true);
        System.out.println("Depth: " + n + " EPS: " + eps + " Threshold: " + threshold + " --> Power: " + power);
        return power;
    }




    // ---------------------------------------------------------------------------------------------------
    // The power calculations only compute the LOD scores and probabilities they use.  These tests hold them,
    // bit for bit, to the calculations as they were originally written (kept below), which tabulate the
    // score and the probability of every configuration.
    // ---------------------------------------------------------------------------------------------------

    private static final double Q30 = Math.pow(10, -1 * (30/10));   // the error rate MuTect passes in
    private static final int[] LARGE_DEPTHS = {2000, 5000, 12345, 40000, 99999, 150001, 190123, 288000};

    private static void assertSameBits(double actual, double expected, String what) {
        Assert.assertEquals(Double.doubleToRawLongBits(actual), Double.doubleToRawLongBits(expected),
                what + ": " + actual + " instead of " + expected);
    }

    private static void checkTumor(int depth, double eps, double lod, double delta, double contam, boolean smoothing) throws MathException {
        assertSameBits(TumorPowerCalculator.calculatePower(depth, eps, lod, delta, contam, smoothing),
                originalTumorPower(depth, eps, lod, delta, contam, smoothing),
                "tumor power, depth " + depth + " eps " + eps + " lod " + lod + " delta " + delta + " contam " + contam + " smoothing " + smoothing);
    }

    private static void checkNormal(int depth, double eps, double lod, boolean smoothing) throws MathException {
        assertSameBits(NormalPowerCalculator.calculatePower(depth, eps, lod, smoothing),
                originalNormalPower(depth, eps, lod, smoothing),
                "normal power, depth " + depth + " eps " + eps + " lod " + lod + " smoothing " + smoothing);
    }

    @Test
    public void tumorPowerIsUnchangedAtEveryModestDepth() throws MathException {
        final double[] deltas = {0.0, 0.001, 0.05, 0.15, 0.3f, 0.5, 0.97, 1.0};
        for (int depth = 0; depth <= 1200; depth++) {
            // the tumor power MuTect reports (fraction_contamination 0.01 or 0.02, and none)
            checkTumor(depth, Q30, 6.3f, 0.3f, 0.02f, true);
            checkTumor(depth, Q30, 6.3f, 0.3f, 0.01f, true);
            checkTumor(depth, Q30, 6.3f, 0.3f, 0, true);
            checkTumor(depth, Q30, 6.3f, 0.3f, 0.02f, false);
            // the strand artifact power, at whatever allele fraction the candidate has
            if (depth % 3 == 0) {
                for (final double delta : deltas) {
                    checkTumor(depth, Q30, 2.0f, delta, 0.0f, true);
                }
            }
        }
    }

    @Test
    public void tumorPowerIsUnchangedAtLargeDepths() throws MathException {
        for (final int depth : LARGE_DEPTHS) {
            checkTumor(depth, Q30, 6.3f, 0.3f, 0.02f, true);
            checkTumor(depth, Q30, 6.3f, 0.3f, 0.01f, true);
            checkTumor(depth, Q30, 6.3f, 0.3f, 0, true);
            checkTumor(depth, Q30, 6.3f, 0.3f, 0.01f, false);
            checkTumor(depth, Q30, 2.0f, 0.0, 0.0f, true);
            checkTumor(depth, Q30, 2.0f, 0.004, 0.0f, true);
            checkTumor(depth, Q30, 2.0f, 0.05, 0.0f, true);
            checkTumor(depth, Q30, 2.0f, 0.5, 0.0f, true);
            checkTumor(depth, Q30, 2.0f, 0.999, 0.0f, true);
            checkTumor(depth, Q30, 2.0f, 1.0, 0.0f, true);
            // a threshold no configuration reaches, and one every configuration reaches
            checkTumor(depth, Q30, 1e12, 0.3f, 0.02f, true);
            checkTumor(depth, Q30, -1e12, 0.3f, 0.02f, true);
        }
    }

    @Test
    public void normalPowerIsUnchangedAtEveryModestDepth() throws MathException {
        for (int depth = 0; depth <= 1200; depth++) {
            checkNormal(depth, Q30, 2.3f, true);     // novel sites
            checkNormal(depth, Q30, 5.5f, true);     // dbSNP sites
            checkNormal(depth, Q30, 2.3f, false);
            checkNormal(depth, Math.pow(10, (-20/10)) / 3d, 2.3d, true);
        }
    }

    @Test
    public void normalPowerIsUnchangedAtLargeDepths() throws MathException {
        for (final int depth : LARGE_DEPTHS) {
            checkNormal(depth, Q30, 2.3f, true);
            checkNormal(depth, Q30, 5.5f, true);
            checkNormal(depth, Q30, 5.5f, false);
            checkNormal(depth, 0.2, 2.3f, true);
            checkNormal(depth, Q30, 1e12, true);
            checkNormal(depth, Q30, -1e12, true);
        }
    }

    // TumorPowerCalculator.calculatePower as originally written
    private static double originalTumorPower(int depth, double eps, double lodThreshold, double delta, double contam, boolean enableSmoothing) throws MathException {
        if (depth==0) return 0;

        double p_alt_given_e_delta = delta*(1d-eps) + (1d-delta)*eps;
        BinomialDistribution binom = new BinomialDistributionImpl(depth, p_alt_given_e_delta);
        double[] p = new double[depth+1];
        for(int i=0; i<p.length; i++) {
            p[i] = binom.probability(i);
        }

        double[] lod = new double[depth+1];
        for(int i=0; i<lod.length; i++) {
            lod[i] = TumorPowerCalculator.calculateTumorLod(depth, i, eps, contam);
        }

        int k = -1;
        for(int i=0; i<lod.length; i++) {
            if (lod[i] >= lodThreshold) {
                k = i;
                break;
            }
        }

        if (k == -1) {
            return 0;
        }

        double power = 0;

        if ( enableSmoothing && k > 0 ) {
            double x = 1d - (lodThreshold - lod[k-1]) / (lod[k] - lod[k-1]);
            power = x*p[k-1];
        }

        for(int i=k; i<p.length; i++) {
            power += p[i];
        }

        return(power);
    }

    // NormalPowerCalculator.calculatePower as originally written
    private static double originalNormalPower(int depth, double eps, double lodThreshold, boolean enableSmoothing) throws MathException {
        if (depth==0) return 0;

        BinomialDistribution binom = new BinomialDistributionImpl(depth, eps);
        double[] p = new double[depth+1];
        for(int i=0; i<p.length; i++) {
            p[i] = binom.probability(depth - i);
        }

        double[] lod = new double[depth+1];
        for(int i=0; i<lod.length; i++) {
            lod[i] = NormalPowerCalculator.calculateNormalLod(depth, depth-i, eps);
        }

        int k = -1;
        for(int i=0; i<lod.length; i++) {
            if (lod[i] >= lodThreshold) {
                k = i;
                break;
            }
        }

        if (k == -1) {
            return 0;
        }

        double power = 0;

        if ( enableSmoothing && k > 0 ) {
            double x = 1d - (lodThreshold - lod[k-1]) / (lod[k] - lod[k-1]);
            power = x*p[k-1];
        }

        for(int i=k; i<p.length; i++) {
            power += p[i];
        }

        return(power);
    }
}
