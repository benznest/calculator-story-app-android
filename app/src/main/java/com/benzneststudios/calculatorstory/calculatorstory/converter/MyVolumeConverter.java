package com.benzneststudios.calculatorstory.calculatorstory.converter;

import com.benzneststudios.calculatorstory.calculatorstory.MyContextor;
import com.benzneststudios.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class MyVolumeConverter {

    private static int[] resVolumeName = {
            R.string.milliliters,
            R.string.cubic_centimeters,
            R.string.liters,
            R.string.cubic_meters,
            R.string.teaspoons_us,
            R.string.tablespoons_us,
            R.string.fluid_ounces_us,
            R.string.cups_us,
            R.string.pints_us,
            R.string.quarts_us,
            R.string.gallons_us,
            R.string.cubic_inches,
            R.string.cubic_feet,
            R.string.teaspoons_uk,
            R.string.tablespoons_uk,
            R.string.fluid_ounces_uk,
            R.string.pints_uk,
            R.string.quarts_uk,
            R.string.gallons_uk
    };

    // BASE ON GRAM.
    private static double[] factorPerCubicMeters = {
            1000000,
            1000000,
            1000,
            1,
            202884.1,
            67628.04,
            33814.02,
            4226.753,
            2113.376,
            1056.688,
            264.1721,
            61023.74,
            35.31467,
            168936.4,
            56312.13,
            35195.08,
            1759.754,
            879.877,
            219.9692
    };

    public static String[] getVolumeName() {
        int length = resVolumeName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resVolumeName[i]);
        }
        return str;
    }

    public static double getFactorPerCubicMeter(int unitIndex) {
        return factorPerCubicMeters[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = MyVolumeConverter.getFactorPerCubicMeter(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = MyVolumeConverter.getFactorPerCubicMeter(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
