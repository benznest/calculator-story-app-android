package com.benznestdeveloper.calculatorstory.calculatorstory.converter;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyContextor;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class MyTemperatureConverter {

    public static final int INDEX_CELSIUS = 0;
    public static final int INDEX_FAHRENHEIT = 1;
    public static final int INDEX_KELVIN = 2;
    public static final int INDEX_REAUMUR = 3;


    private static int[] resTemperatureName = {
            R.string.celsius,
            R.string.fahrenheit,
            R.string.kelvin,
            R.string.reaumur};

    private static int[] resTemperatureNameSmall = {
            R.string.celsius_small,
            R.string.fahrenheit_small,
            R.string.kelvin_small,
            R.string.reaumur_small};

    private static double[] factorPerMeter = {
            1,
            1000000,
            274.15
    };

    public static String[] getName() {
        int length = resTemperatureName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resTemperatureName[i]);
        }
        return str;
    }

    public static String[] getNameSmall() {
        int length = resTemperatureNameSmall.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resTemperatureNameSmall[i]);
        }
        return str;
    }

    public static double convert(int unitFrom, int unitTo, double valueUnitFrom) {
        if (unitFrom == INDEX_CELSIUS) {
            if (unitTo == INDEX_FAHRENHEIT) {
                return (1.8 * valueUnitFrom) + 32;
            } else if (unitTo == INDEX_KELVIN) {
                return valueUnitFrom + 273.15;
            } else if (unitTo == INDEX_REAUMUR) {
                return valueUnitFrom * (4 / 5);
            }
        }

        if (unitFrom == INDEX_FAHRENHEIT) {
            if (unitTo == INDEX_CELSIUS) {
                return (valueUnitFrom - 32) / 1.8;
            } else if (unitTo == INDEX_KELVIN) {
                return (valueUnitFrom + 459.67) / (1.8);
            } else if (unitTo == INDEX_REAUMUR) {
                return (valueUnitFrom - 32) * (4 / 9);
            }
        }

        if (unitFrom == INDEX_KELVIN) {
            if (unitTo == INDEX_CELSIUS) {
                return valueUnitFrom - 273.15;
            } else if (unitTo == INDEX_FAHRENHEIT) {
                return (valueUnitFrom * 1.8) - 459.67;
            } else if (unitTo == INDEX_REAUMUR) {
                return (valueUnitFrom - 273.15) * (4 / 5);
            }
        }

        if (unitFrom == INDEX_REAUMUR) {
            if (unitTo == INDEX_CELSIUS) {
                return valueUnitFrom * (5 / 4);
            } else if (unitTo == INDEX_FAHRENHEIT) {
                return valueUnitFrom * (9 / 4) + 32;
            } else if (unitTo == INDEX_KELVIN) {
                return valueUnitFrom * (5 / 4) + 273.15;
            }
        }

        return valueUnitFrom;
    }
}
