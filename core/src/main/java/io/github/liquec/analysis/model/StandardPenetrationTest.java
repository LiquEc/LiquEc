/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public class StandardPenetrationTest {
    private Float depth;
    private Integer sptBlowCounts;

    public StandardPenetrationTest() {
        // No argument constructor to allow use as standard Java Bean
    }

    public StandardPenetrationTest(final Float depth, final Integer sptBlowCounts) {
        this.depth = depth;
        this.sptBlowCounts = sptBlowCounts;
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
}
