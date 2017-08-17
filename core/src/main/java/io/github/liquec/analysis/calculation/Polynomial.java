/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

public enum Polynomial {

    // Eurocode - Fines 05
    EUROCODE_F05_LESS_OR_EQUAL(+0000.0480000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),
    EUROCODE_F05_BETWEEN_FIST_SECTION(+0000.0448296329, -0000.0094070222, +0000.0033050307, -0000.0002385061, +0000.0000079813, -0000.0000000987, +0000.0000000000),
    EUROCODE_F05_BETWEEN_SECOND_SECTION(-8043.1432771184, +1489.9589620681, -0110.3230416596, +0004.0816050267, -0000.0754533111, +0000.0005575932, +0000.0000000000),
    EUROCODE_F05_GREATER_OR_EQUAL(+0000.6000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),

    // Eurocode - Fines 15
    EUROCODE_F15_LESS_OR_EQUAL(+0000.0960000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),
    EUROCODE_F15_BETWEEN_FIST_SECTION(-0000.1343385091, +0000.0974834289, -0000.0162815857, +0000.0014800712, -0000.0000645096, +0000.0000010911, +0000.0000000000),
    EUROCODE_F15_BETWEEN_SECOND_SECTION(+0350.7533268770, -0065.7224752419, +0004.6129687346, -0000.1436660680, +0000.0016758777, +0000.0000000000, +0000.0000000000),
    EUROCODE_F15_GREATER_OR_EQUAL(+0000.6000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),

    // Eurocode - Fines 35
    EUROCODE_F35_LESS_OR_EQUAL(+0000.0960000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),
    EUROCODE_F35_BETWEEN_FIST_SECTION(+0000.0562712584, +0000.0235304846, -0000.0031087842, +0000.0003716392, -0000.0000196952, +0000.0000003475, +0000.0000000029),
    EUROCODE_F35_BETWEEN_SECOND_SECTION(+0699.5094771763, -0154.6774955751, +0012.8221180239, -0000.4721399728, +0000.0065173735, +0000.0000000000, +0000.0000000000),
    EUROCODE_F35_GREATER_OR_EQUAL(+0000.6000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),

    // NCSE-02 - Fines 05
    NCSE02_F05_LESS_OR_EQUAL(+0000.0600000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),
    NCSE02_F05_BETWEEN(+0000.6808078098, -0000.3256813155, +0000.0642887905, -0000.0061087131, +0000.0003071981, -0000.0000078144, +0000.0000000792),
    NCSE02_F05_GREATER_OR_EQUAL(+0000.6000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),

    // NCSE-02 - Fines 15
    NCSE02_F15_LESS_OR_EQUAL(+0000.0966000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),
    NCSE02_F15_BETWEEN(+0000.7423315466, -0000.3968346345, +0000.0923208225, -0000.0104384920, +0000.0006297183, -0000.0000193490, +0000.0000002383),
    NCSE02_F15_GREATER_OR_EQUAL(+0000.6000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),

    // NCSE-02 - Fines 35
    NCSE02_F35_LESS_OR_EQUAL(+0000.1050000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),
    NCSE02_F35_BETWEEN(+0000.3795425287, -0000.2556070600, +0000.0865399943, -0000.0134605429, +0000.0010825978, -0000.0000433242, +0000.0000006829),
    NCSE02_F35_GREATER_OR_EQUAL(+0000.6000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000, +0000.0000000000),

    // Earthquake Magnitude
    EARTHQUAKE_MAGNITUDE_CORRECTION(+0298.8700000000, -0219.4910000000, +0066.3450000000, -0010.1500000000, +0000.7800000000, -0000.0240000000, +0000.0000000000);

    private double position0;
    private double position1;
    private double position2;
    private double position3;
    private double position4;
    private double position5;
    private double position6;

    Polynomial(final double position0, final double position1, final double position2, final double position3, final double position4, final double position5, final double position6) {
        this.position0 = position0;
        this.position1 = position1;
        this.position2 = position2;
        this.position3 = position3;
        this.position4 = position4;
        this.position5 = position5;
        this.position6 = position6;
    }

    public double getPosition0() {
        return position0;
    }

    public double getPosition1() {
        return position1;
    }

    public double getPosition2() {
        return position2;
    }

    public double getPosition3() {
        return position3;
    }

    public double getPosition4() {
        return position4;
    }

    public double getPosition5() {
        return position5;
    }

    public double getPosition6() {
        return position6;
    }

    public double getValue(final double argument) {
        double value = 0;
        for (int i = 0; i <= 6; i++) {
            value += this.getConstant(i) * Math.pow(argument, i);
        }
        return value;
    }

    private double getConstant(final int position) {
        switch (position) {
            case 0: return this.getPosition0();
            case 1: return this.getPosition1();
            case 2: return this.getPosition2();
            case 3: return this.getPosition3();
            case 4: return this.getPosition4();
            case 5: return this.getPosition5();
            case 6: return this.getPosition6();
            default: return 0;
        }
    }

}
