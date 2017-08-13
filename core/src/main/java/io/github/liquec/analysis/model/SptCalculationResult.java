/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public class SptCalculationResult {
    private Float depth;
    private Integer sptBlowCounts;
    private Float energyRatio;
    private Float totalTension;
    private Float interstitialPressure;
    private Float effectivePressure;
    private Float sptCorrected;
    private Float cycleStressRatio;
    private Float cycleResistanceRatio;
    private Float factorSafety;

    public SptCalculationResult(final StandardPenetrationTest standardPenetrationTest) {
        this.depth = standardPenetrationTest.getDepth();
        this.sptBlowCounts = standardPenetrationTest.getSptBlowCounts();
        this.energyRatio = standardPenetrationTest.getEnergyRatio();
    }

    public Float getDepth() {
        return depth;
    }

    public void setDepth(final Float depth) {
        this.depth = depth;
    }

    public Integer getSptBlowCounts() {
        return sptBlowCounts;
    }

    public void setSptBlowCounts(final Integer sptBlowCounts) {
        this.sptBlowCounts = sptBlowCounts;
    }

    public Float getEnergyRatio() {
        return energyRatio;
    }

    public void setEnergyRatio(final Float energyRatio) {
        this.energyRatio = energyRatio;
    }

    public Float getTotalTension() {
        return totalTension;
    }

    public void setTotalTension(final Float totalTension) {
        this.totalTension = totalTension;
    }

    public Float getInterstitialPressure() {
        return interstitialPressure;
    }

    public void setInterstitialPressure(final Float interstitialPressure) {
        this.interstitialPressure = interstitialPressure;
    }

    public Float getEffectivePressure() {
        return effectivePressure;
    }

    public void setEffectivePressure(final Float effectivePressure) {
        this.effectivePressure = effectivePressure;
    }

    public Float getSptCorrected() {
        return sptCorrected;
    }

    public void setSptCorrected(final Float sptCorrected) {
        this.sptCorrected = sptCorrected;
    }

    public Float getCycleStressRatio() {
        return cycleStressRatio;
    }

    public void setCycleStressRatio(final Float cycleStressRatio) {
        this.cycleStressRatio = cycleStressRatio;
    }

    public Float getCycleResistanceRatio() {
        return cycleResistanceRatio;
    }

    public void setCycleResistanceRatio(final Float cycleResistanceRatio) {
        this.cycleResistanceRatio = cycleResistanceRatio;
    }

    public Float getFactorSafety() {
        return factorSafety;
    }

    public void setFactorSafety(final Float factorSafety) {
        this.factorSafety = factorSafety;
    }
}
