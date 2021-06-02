package com.higgs.sim.utils;

public class Utils {
    private static final String workingDir = System.getProperty("user.dir");

    public static String getWorkingDir() {
        return Utils.workingDir;
    }

    public static double dist(final Vector u, final Vector v) {
        return Utils.dist(u.x, u.y, u.z, v.x, v.y, v.z);
    }

    public static double dist(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));// + Math.pow((z2 - z1), 2));
    }
}
