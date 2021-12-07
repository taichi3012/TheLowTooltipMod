package com.github.taichi3012.thelowtooltipmod.util;

import java.util.Arrays;

public class JavaUtil {
    public static boolean[] generateFillArray(int size, boolean value) {
        boolean[] b = new boolean[size];
        Arrays.fill(b, value);
        return b;
    }

    public static double digitRound(double value, double digit) {
        return Math.round(value * Math.pow(10.0d, digit)) / Math.pow(10.0d, digit);
    }
}
