/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

public enum Polynomial {

    // Eurocode
    EUROCODE_A1(+0000.0562712584, +0000.0235304846, -0000.0031087842, +0000.0003716392, -0000.0000196952, +0000.0000003475, +0000.0000000029),
    EUROCODE_A2(+0699.5094771763, +0154.6774955751, +0012.8221180239, +0000.4721399728, +0000.0065173735, +0000.0000000000, +0000.0000000000),
    EUROCODE_B1(-0000.1343385091, +0000.0974834289, +0000.0162815857, +0000.0014800712, -0000.0000645096, +0000.0000010911, +0000.0000000000),
    EUROCODE_B2(+0350.7533268770, -0065.7224752419, +0004.6129687346, -0000.1436660680, +0000.0016758777, +0000.0000000000, +0000.0000000000),
    EUROCODE_C1(+0000.0448296329, -0000.0094070222, +0000.0033050307, -0000.0002385061, +0000.0000079813, -0000.0000000987, +0000.0000000000),
    EUROCODE_C2(-8043.1432771184, +1489.9589620681, -0110.3230416596, +0004.0816050267, -0000.0754533111, +0000.0005575932, +0000.0000000000),

    // NCSE-02
    NCSE02_A(+0000.3795425287, -0000.2556070600, +0000.0865399943, -0000.0134605429, +0000.0010825978, -0000.0000433242, +0000.0000006829),
    NCSE02_B(+0000.7423315466, -0000.3968346345, +0000.0923208225, -0000.0104384920, +0000.0006297183, -0000.0000193490, +0000.0000002383),
    NCSE02_C(+0000.6808078098, -0000.3256813155, +0000.0642887905, -0000.0061087131, +0000.0003071981, -0000.0000078144, +0000.0000000792);

    private double position0;
    private double position1;
    private double position2;
    private double position3;
    private double position4;
    private double position5;
    private double position6;

    Polynomial(double position0, double position1, double position2, double position3, double position4, double position5, double position6) {
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

    public double getValue(final int position) {
        switch(position) {
            case 0: return position0;
            case 1: return position1;
            case 2: return position2;
            case 3: return position3;
            case 4: return position4;
            case 5: return position5;
            case 6: return position6;
            default:return 0;
        }
    }
}
