package com.benznestdeveloper.calculatorstory.calculatorstory.converter;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyContextor;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class MyTimeConverter {

    private static int[] resTimeName = {
            R.string.microseconds,
            R.string.milliseconds,
            R.string.seconds,
            R.string.minutes,
            R.string.hours,
            R.string.days,
            R.string.weeks,
            R.string.month,
            R.string.years
    };

    // BASE ON DAYS.
    private static double[] factorPerSecond = {
            864E8,
            864E5,
            86400,
            1440,
            24,
            1,
            0.142,
            0.032258,
            0.002738
    };

    public static String[] getTimeName() {
        int length = resTimeName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resTimeName[i]);
        }
        return str;
    }

    public static double getFactorPerSecond(int unitIndex) {
        return factorPerSecond[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = MyTimeConverter.getFactorPerSecond(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = MyTimeConverter.getFactorPerSecond(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
