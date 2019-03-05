package com.benznestdeveloper.calculatorstory.calculatorstory.converter;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyContextor;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class PressureConverter {

    private static int[] resPressureName = {
            R.string.atmospheres,
            R.string.bars,
            R.string.kilopascals,
            R.string.millimeters_of_mercury,
            R.string.pascals,
            R.string.pound_per_square_inch
    };

    // BASE ON PASCALS.
    private static double[] factorPerPascal = {
            0.00001,
            0.00001,
            0.001,
            0.007502,
            1,
            0.000145,
    };

    public static String[] getPressureName() {
        int length = resPressureName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resPressureName[i]);
        }
        return str;
    }

    public static double getFactorPerPascal(int unitIndex) {
        return factorPerPascal[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = PressureConverter.getFactorPerPascal(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = PressureConverter.getFactorPerPascal(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
