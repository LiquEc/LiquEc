/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public class SoilUnitWeight {
    private Float aboveGHT;
    private Float belowGWT;

    public SoilUnitWeight() {
        // No argument constructor to allow use as standard Java Bean
    }

    public SoilUnitWeight(final Float aboveGHT, final Float belowGWT) {
        this.aboveGHT = aboveGHT;
        this.belowGWT = belowGWT;
    }

    public Float getAboveGHT() {
        return aboveGHT;
    }

    public void setAboveGHT(final Float aboveGHT) {
        this.aboveGHT = aboveGHT;
    }

    public Float getBelowGWT() {
        return belowGWT;
    }

    public void setBelowGWT(final Float belowGWT) {
        this.belowGWT = belowGWT;
    }
}
