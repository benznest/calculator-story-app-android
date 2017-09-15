package com.benzneststudios.calculatorstory.calculatorstory;

/**
 * Created by benznest on 25-Jun-17.
 */

public class MyCalendarUtils {
    private static final String[] MONTH_SMALL = {
            "JAN",
            "FEB",
            "MAR",
            "APR",
            "MAY",
            "JUN",
            "JUL",
            "AUG",
            "SEP",
            "OCT",
            "NOV",
            "DEV"
    };

    public static String getMonthSmallName(int month) {
        return MONTH_SMALL[month - 1];
    }
}
