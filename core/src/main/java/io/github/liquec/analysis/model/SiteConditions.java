/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public final class SiteConditions {
    private Float peakGroundAcceleration;
    private Float earthquakeMagnitude;
    private Float coefficientOfContribution;

    public SiteConditions() {
        // No argument constructor to allow use as standard Java Bean
    }

    public SiteConditions(final Float peakGroundAcceleration, final Float earthquakeMagnitude, final Float coefficientOfContribution) {
        this.peakGroundAcceleration = peakGroundAcceleration;
        this.earthquakeMagnitude = earthquakeMagnitude;
        this.coefficientOfContribution = coefficientOfContribution;
    }

    public Float getPeakGroundAcceleration() {
        return peakGroundAcceleration;
    }

    public void setPeakGroundAcceleration(final Float peakGroundAcceleration) {
        this.peakGroundAcceleration = peakGroundAcceleration;
    }

    public Float getEarthquakeMagnitude() {
        return earthquakeMagnitude;
    }

    public void setEarthquakeMagnitude(final Float earthquakeMagnitude) {
        this.earthquakeMagnitude = earthquakeMagnitude;
    }

    public Float getCoefficientOfContribution() {
        return coefficientOfContribution;
    }

    public void setCoefficientOfContribution(final Float coefficientOfContribution) {
        this.coefficientOfContribution = coefficientOfContribution;
    }
}
