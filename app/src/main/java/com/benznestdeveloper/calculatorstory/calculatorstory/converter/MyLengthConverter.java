package com.benznestdeveloper.calculatorstory.calculatorstory.converter;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyContextor;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class MyLengthConverter {

    private static int[] resLengthName = {
            R.string.nanometers,
            R.string.microns,
            R.string.millimeters,
            R.string.centimeters,
            R.string.meters,
            R.string.kilometers,
            R.string.inches,
            R.string.feet,
            R.string.yards,
            R.string.miles,
            R.string.nautical_miles};

    // BASE ON METERS.
    private static double[] factorPerMeter = {
            1000000000,
            1000000,
            1000,
            100,
            1,
            0.001,
            39.3700787,
            3.2808399,
            1.0936133,
            0.000621371192,
            0.000539956803
    };

    public static String[] getLengthName() {
        int length = resLengthName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resLengthName[i]);
        }
        return str;
    }

    public static double getFactorPerMeter(int unitIndex) {
        return factorPerMeter[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = MyLengthConverter.getFactorPerMeter(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = MyLengthConverter.getFactorPerMeter(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
