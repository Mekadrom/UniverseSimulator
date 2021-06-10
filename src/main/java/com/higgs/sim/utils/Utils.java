package com.higgs.sim.utils;

import org.apache.commons.math3.util.Pair;

public class Utils {
    private static final String workingDir = System.getProperty("user.dir");

    public static String getWorkingDir() {
        return Utils.workingDir;
    }

    public static double dist(final double x1, final double y1, final double x2, final double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static Pair<Double, Double> getCartesianVelocity(final double length, final double angle) {
        return Pair.create(length * StrictMath.cos(angle), length * StrictMath.sin(angle));
    }

    public static int randomColor() {
        return (int) (Math.random() * 0xffffffff);
    }
}
