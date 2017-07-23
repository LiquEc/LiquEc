/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.chart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private static final Logger LOG = LoggerFactory.getLogger(Line.class);

    private static final float DISTANCE = 0.04f;

    public Point p0;
    public Point p1;

    public Line(final Point p0, final Point p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    public List<Point> getPoints() {

        final float totalDistance = this.getTotalDistance();
        final List<Point> points = new ArrayList<>();

        if (totalDistance == 0) {
            return points;
        }

        for (float i = DISTANCE; i < totalDistance; i = i + DISTANCE) {
            final float t = i / totalDistance;
            points.add(new Point(this.calculateCoordinateX(t), this.calculateCoordinateY(t)));
        }

        return points;
    }

    private float getTotalDistance() {
        return (float) Math.sqrt(Math.pow((this.p1.getX() - this.p0.getX()), 2) + Math.pow((this.p1.getY() - this.p0.getY()), 2));
    }

    private float calculateCoordinateX(final float t) {
        return ((1 - t) * this.p0.getX()) + (t * this.p1.getX());
    }

    private float calculateCoordinateY(final float t) {
        return ((1 - t) * this.p0.getY()) + (t * this.p1.getY());
    }
}
