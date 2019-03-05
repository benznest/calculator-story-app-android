package com.benznestdeveloper.calculatorstory.calculatorstory;

/**
 * Created by benznest on 30-Jun-17.
 */

public class MyCalculatorStartup {

    public static final int STANDARD_CALCULATOR = 0;
    public static final int SCIENTIFIC_CALCULATOR = 1;
    public static final int BETWEEN_DATE_CALCULATION = 2;


    private static int[] resName = {
            R.string.stdCal,
            R.string.sciCal,
            R.string.between_date_calculation};

    public static String[] getName() {
        int length = resName.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resName[i]);
        }
        return str;
    }

    public static String getCurrentStartupName() {
        String[] str = getName();
        return str[MyCache.getStartupCalculator(MyContextor.getInstance())];
    }
}
