package com.benznestdeveloper.calculatorstory.calculatorstory.converter;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyContextor;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class MyPowerConverter {

    private static int[] resPowerName = {
            R.string.watts,
            R.string.kilowatts,
            R.string.megawatts,
            R.string.horsepower,
            R.string.foot_pound_per_minute,
            R.string.btu_minute
    };

    // BASE ON watts.
    private static double[] factorPerWatts = {
            1,
            0.001,
            0.000001,
            0.001341,
            44.2537,
            0.056869
    };

    public static String[] getPowerName() {
        int length = resPowerName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resPowerName[i]);
        }
        return str;
    }

    public static double getFactorPerSecond(int unitIndex) {
        return factorPerWatts[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = MyPowerConverter.getFactorPerSecond(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = MyPowerConverter.getFactorPerSecond(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
