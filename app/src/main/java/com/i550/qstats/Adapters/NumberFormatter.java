package com.i550.qstats.Adapters;

import java.util.Locale;

public class NumberFormatter {
    private static Locale l = Locale.getDefault();

    static String formatNum(int num) {
        String number;
        if (num < 1_000) number = String.valueOf(num);
        else if (num < 10_000) {
            double d = (double) num / 1000;
            number = String.format(l, "%.2f K", d);
        } else if (num < 100_000) {
            double d = (double) num / 1000;
            number = String.format(l, "%.1f K", d);
        } else if (num < 1_000_000) {
            double d = (double) num / 1000;
            number = String.format(l, "%.0f K", d);
        } else {
            double d = (double) num / 1000000;
            number = String.format(l, "%.1f M", d);
        }
        return number;
    }

}
