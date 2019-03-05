package com.benznestdeveloper.calculatorstory.calculatorstory.converter;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyContextor;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 29-Jun-17.
 */

public class MyDataConverter {

    private static int[] resDataName = {
            R.string.bits,
            R.string.bytes,
            R.string.kilobits,
            R.string.kilobytes,
            R.string.megabits,
            R.string.megabytes,
            R.string.gigabits,
            R.string.gigabytes,
            R.string.terabits,
            R.string.terabytes,
            R.string.petabits,
            R.string.petabytes,
            R.string.exabits,
            R.string.exabytes,
            R.string.zetabits,
            R.string.zetabytes,
            R.string.yottabits,
            R.string.yottabytes
    };

    private static int[] resDataNameSmall = {
            R.string.bits_small,
            R.string.bytes_small,
            R.string.kilobits_small,
            R.string.kilobytes_small,
            R.string.megabits_small,
            R.string.megabytes_small,
            R.string.gigabits_small,
            R.string.gigabytes_small,
            R.string.terabits_small,
            R.string.terabytes_small,
            R.string.petabits_small,
            R.string.petabytes_small,
            R.string.exabits_small,
            R.string.exabytes_small,
            R.string.zetabits_small,
            R.string.zetabytes_small,
            R.string.yottabits_small,
            R.string.yottabytes_small
    };

    // BASE ON BYTE.
    private static double[] factorPerByte = {
            8,
            1,
            0.008,
            0.001,
            0.000008,
            0.000001,
            0.000000008,
            0.000000001,
            0.000000000008,
            0.000000000001,
            0.000000000000008,
            0.000000000000001,
            0.000000000000000008,
            0.000000000000000001,
            0.000000000000000000008,
            0.000000000000000000001,
            0.000000000000000000000008,
            0.000000000000000000000001,
    };

    public static String[] getDataName() {
        int length = resDataName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resDataName[i]);
        }
        return str;
    }

    public static String[] getDataNameSmall() {
        int length = resDataNameSmall.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resDataNameSmall[i]);
        }
        return str;
    }

    public static double getFactorPerByte(int unitIndex) {
        return factorPerByte[unitIndex];
    }

    public static double convert(int indexUnitFrom, int indexUnitTo, double valueFrom) {
        double factorUnitSelected = MyDataConverter.getFactorPerByte(indexUnitFrom);
        double valueSelectedMeter = valueFrom / factorUnitSelected;

        double factorUnitDeselected = MyDataConverter.getFactorPerByte(indexUnitTo);
        double valueDeselectedMeter = valueSelectedMeter * factorUnitDeselected;
        return valueDeselectedMeter;
    }

}
