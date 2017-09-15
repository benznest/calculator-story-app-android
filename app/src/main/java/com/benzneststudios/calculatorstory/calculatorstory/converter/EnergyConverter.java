package com.benzneststudios.calculatorstory.calculatorstory.converter;

import com.benzneststudios.calculatorstory.calculatorstory.MyContextor;
import com.benzneststudios.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class EnergyConverter {

    private static int[] resEnergyName = {
            R.string.electron_volts,
            R.string.joules,
            R.string.kilojoules,
            R.string.food_calories
    };

    // BASE ON JOULES.
    private static double[] factorPerJoules = {
            6.241509E18,
            1,
            0.001,
            0.000239
    };

    public static String[] getEnergyName() {
        int length = resEnergyName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resEnergyName[i]);
        }
        return str;
    }

    public static double getFactorPerJoules(int unitIndex) {
        return factorPerJoules[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = EnergyConverter.getFactorPerJoules(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = EnergyConverter.getFactorPerJoules(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
