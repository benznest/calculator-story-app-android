package com.benzneststudios.calculatorstory.calculatorstory;

/**
 * Created by benznest on 23-Jun-17.
 */

public class MyOperatorOptionManager {

    public static final int OPTION_SQRT_BRING_ALL = 0;
    public static final int OPTION_SQRT_PUT_LAST = 1;

    public static final int OPTION_SQUARE_BRING_ALL = 0;
    public static final int OPTION_SQUARE_PUT_LAST = 1;

    public static final int OPTION_CUSTOM_BRING_ALL = 0;
    public static final int OPTION_CUSTOM_PUT_LAST = 1;

    private static int[] resSqrtOption = {R.string.sqrt_option_1, R.string.sqrt_option_2};
    private static int[] resSqUAREOption = {R.string.square_option_1, R.string.square_option_2};
    private static int[] resCustomOption = {R.string.custom_option_1, R.string.custom_option_2};

    public static String[] getSqrtOptionName() {
        int length = resSqrtOption.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resSqrtOption[i]);
        }
        return str;

    }

    public static String[] getSquareOptionName() {
        int length = resSqUAREOption.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resSqUAREOption[i]);
        }
        return str;
    }

    public static String[] getCustomOptionName(String data) {
        int length = resCustomOption.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resCustomOption[i],data);
        }
        return str;
    }
}
