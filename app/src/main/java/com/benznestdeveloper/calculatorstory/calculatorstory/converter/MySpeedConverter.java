package com.benznestdeveloper.calculatorstory.calculatorstory.converter;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyContextor;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class MySpeedConverter {

    private static int[] resSpeedName = {
            R.string.centimeters_per_second,
            R.string.meters_per_second,
            R.string.kilometers_per_hour,
            R.string.feet_per_second,
            R.string.miles_per_hour,
            R.string.knots,
            R.string.mach
    };

    // BASE ON METERS / S.
    private static double[] factorPerMeterPerSecond = {
            100,
            1,
            3.6,
            3.2808399,
            2.23693629,
            1.94384449,
            0.0029
    };

    public static String[] getSpeedName() {
        int length = resSpeedName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resSpeedName[i]);
        }
        return str;
    }

    public static double getFactorPerMeter(int unitIndex) {
        return factorPerMeterPerSecond[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = MySpeedConverter.getFactorPerMeter(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = MySpeedConverter.getFactorPerMeter(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
