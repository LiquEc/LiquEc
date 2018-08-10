/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public class SoilUnitWeight {
    private Float aboveGwt;
    private Float belowGwt;

    public SoilUnitWeight() {
        // No argument constructor to allow use as standard Java Bean
    }

    public SoilUnitWeight(final Float aboveGwt, final Float belowGwt) {
        this.aboveGwt = aboveGwt;
        this.belowGwt = belowGwt;
    }

    public Float getAboveGwt() {
        return aboveGwt;
    }

    public void setAboveGwt(final Float aboveGwt) {
        this.aboveGwt = aboveGwt;
    }

    public Float getBelowGwt() {
        return belowGwt;
    }

    public void setBelowGwt(final Float belowGwt) {
        this.belowGwt = belowGwt;
    }
}
