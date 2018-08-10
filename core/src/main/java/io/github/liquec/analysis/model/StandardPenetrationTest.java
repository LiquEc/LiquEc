/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public class StandardPenetrationTest implements Comparable<StandardPenetrationTest> {
    private Float depth;
    private Integer sptBlowCounts;
    private Float energyRatio;

    public StandardPenetrationTest() {
        // No argument constructor to allow use as standard Java Bean
    }

    public StandardPenetrationTest(final Float depth, final Integer sptBlowCounts, final Float energyRatio) {
        this.depth = depth;
        this.sptBlowCounts = sptBlowCounts;
        this.energyRatio = energyRatio;
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

    public int compareTo(final StandardPenetrationTest standardPenetrationTest) {
        if (this.depth.equals(standardPenetrationTest.getDepth())) {
            return 0;
        }
        return this.depth < standardPenetrationTest.getDepth() ? -1 : 1;
    }
}
