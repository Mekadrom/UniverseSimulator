package com.higgs.sim.utils;

public class Logger {
    public static void log(final String message) {
        System.out.println(message);
    }

    public static void log(final int message) {
        Logger.log(String.valueOf(message));
    }

    public static void log(final double message) {
        Logger.log(String.valueOf(message));
    }

    public static void log(final boolean message) {
        Logger.log(String.valueOf(message));
    }

    public static void log(final Object message) {
        Logger.log(String.valueOf(message));
    }
}
