package com.benznestdeveloper.calculatorstory.calculatorstory.converter;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyContextor;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class MyAreaConverter {

    private static int[] resAreaName = {
            R.string.square_millimeters,
            R.string.square_centimeters,
            R.string.square_meters,
            R.string.square_kilometers,
            R.string.square_inches,
            R.string.square_feet,
            R.string.square_yards,
            R.string.square_mile,
            R.string.hectares,
            R.string.acres,
            R.string.rai,
            R.string.square_wa};

    // BASE ON SQUARE METERS.
    private static double[] factorPerSquareMeter = {
            1000000,
            10000,
            1,
            0.000001,
            1550.0031,
            10.7639104,
            1.19599005,
            0.000000386102159,
            0.0001,
            0.000247105381,
            0.000625,
            0.25
    };

    public static String[] getAreaName() {
        int length = resAreaName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resAreaName[i]);
        }
        return str;
    }

    public static double getFactorPerMeter(int unitIndex) {
        return factorPerSquareMeter[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = MyAreaConverter.getFactorPerMeter(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = MyAreaConverter.getFactorPerMeter(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
