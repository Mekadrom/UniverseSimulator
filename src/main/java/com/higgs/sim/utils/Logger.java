package com.higgs.sim.utils;

public class Logger {
    public static void log(String message) {
        System.out.println(message);
    }

    public static void log(int message) {
        log(String.valueOf(message));
    }

    public static void log(double message) {
        log(String.valueOf(message));
    }

    public static void log(boolean message) {
        log(String.valueOf(message));
    }

    public static void log(Object message) {
        log(String.valueOf(message));
    }
}
