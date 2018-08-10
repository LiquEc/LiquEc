/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public class SptCalculationResult {

    private Boolean result;
    private String errorMessage;

    private Float depth;
    private Integer sptBlowCounts;
    private Float energyRatio;
    private Double totalStress;
    private Double interstitialStress;
    private Double effectiveStress;
    private Double effectiveStressFactor;
    private Double earthquakeMagnitudeCorrection;
    private Double coefficientContributionCorrection;
    private Double sptCorrected; // chart
    private Double cycleStressRatio; // chart
    private Double depthFactor;
    private Double cycleResistanceRatio;
    private Double cycleResistanceRatioCorrected; // chart
    private Double safetyFactor; // chart

    public SptCalculationResult(final StandardPenetrationTest standardPenetrationTest) {
        this.result = true;
        this.depth = standardPenetrationTest.getDepth();
        this.sptBlowCounts = standardPenetrationTest.getSptBlowCounts();
        this.energyRatio = standardPenetrationTest.getEnergyRatio();
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(final Boolean result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
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

    public Double getTotalStress() {
        return totalStress;
    }

    public void setTotalStress(final Double totalStress) {
        this.totalStress = totalStress;
    }

    public Double getInterstitialStress() {
        return interstitialStress;
    }

    public void setInterstitialStress(final Double interstitialStress) {
        this.interstitialStress = interstitialStress;
    }

    public Double getEffectiveStress() {
        return effectiveStress;
    }

    public void setEffectiveStress(final Double effectiveStress) {
        this.effectiveStress = effectiveStress;
    }

    public Double getEffectiveStressFactor() {
        return effectiveStressFactor;
    }

    public void setEffectiveStressFactor(final Double effectiveStressFactor) {
        this.effectiveStressFactor = effectiveStressFactor;
    }

    public Double getEarthquakeMagnitudeCorrection() {
        return earthquakeMagnitudeCorrection;
    }

    public void setEarthquakeMagnitudeCorrection(final Double earthquakeMagnitudeCorrection) {
        this.earthquakeMagnitudeCorrection = earthquakeMagnitudeCorrection;
    }

    public Double getCoefficientContributionCorrection() {
        return coefficientContributionCorrection;
    }

    public void setCoefficientContributionCorrection(final Double coefficientContributionCorrection) {
        this.coefficientContributionCorrection = coefficientContributionCorrection;
    }

    public Double getSptCorrected() {
        return sptCorrected;
    }

    public void setSptCorrected(final Double sptCorrected) {
        this.sptCorrected = sptCorrected;
    }

    public Double getCycleStressRatio() {
        return cycleStressRatio;
    }

    public void setCycleStressRatio(final Double cycleStressRatio) {
        this.cycleStressRatio = cycleStressRatio;
    }

    public Double getDepthFactor() {
        return depthFactor;
    }

    public void setDepthFactor(final Double depthFactor) {
        this.depthFactor = depthFactor;
    }

    public Double getCycleResistanceRatio() {
        return cycleResistanceRatio;
    }

    public void setCycleResistanceRatio(final Double cycleResistanceRatio) {
        this.cycleResistanceRatio = cycleResistanceRatio;
    }

    public Double getCycleResistanceRatioCorrected() {
        return cycleResistanceRatioCorrected;
    }

    public void setCycleResistanceRatioCorrected(final Double cycleResistanceRatioCorrected) {
        this.cycleResistanceRatioCorrected = cycleResistanceRatioCorrected;
    }

    public Double getSafetyFactor() {
        return safetyFactor;
    }

    public void setSafetyFactor(final Double safetyFactor) {
        this.safetyFactor = safetyFactor;
    }
}
