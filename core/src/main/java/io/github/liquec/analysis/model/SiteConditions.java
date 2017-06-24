/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public final class SiteConditions {
    private Float peakGroundAceleration;
    private Float earthquakeMagnitude;

    public SiteConditions() {
        // No argument constructor to allow use as standard Java Bean
    }

    public SiteConditions(final Float peakGroundAceleration, final Float earthquakeMagnitude) {
        this.peakGroundAceleration = peakGroundAceleration;
        this.earthquakeMagnitude = earthquakeMagnitude;
    }

    public Float getPeakGroundAceleration() {
        return peakGroundAceleration;
    }

    public void setPeakGroundAceleration(final Float peakGroundAceleration) {
        this.peakGroundAceleration = peakGroundAceleration;
    }

    public Float getEarthquakeMagnitude() {
        return earthquakeMagnitude;
    }

    public void setEarthquakeMagnitude(final Float earthquakeMagnitude) {
        this.earthquakeMagnitude = earthquakeMagnitude;
    }
}
