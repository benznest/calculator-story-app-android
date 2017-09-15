package com.benzneststudios.calculatorstory.calculatorstory.converter;

import com.benzneststudios.calculatorstory.calculatorstory.MyContextor;
import com.benzneststudios.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class MyWeightConverter {

    private static int[] resWeightName = {
            R.string.carats,
            R.string.milligrams,
            R.string.grams,
            R.string.kilograms,
            R.string.metric_tonnes,
            R.string.ounces,
            R.string.pounds,
            R.string.short_tons_us,
            R.string.long_tons_uk
    };

    // BASE ON GRAM.
    private static double[] factorPerGram = {
            5,
            1000,
            1,
            0.001,
            0.000001,
            0.035274,
            0.002205,
            0.000001,
            0.00000098420653
    };

    public static String[] getWeightName() {
        int length = resWeightName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resWeightName[i]);
        }
        return str;
    }

    public static double getFactorPerGram(int unitIndex) {
        return factorPerGram[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = MyWeightConverter.getFactorPerGram(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = MyWeightConverter.getFactorPerGram(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
