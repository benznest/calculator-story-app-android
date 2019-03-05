package com.benznestdeveloper.calculatorstory.calculatorstory;

/**
 * Created by benznest on 23-Jun-17.
 */

public class MyConverterOptionManager {

    public static final int OPTION_SAVE_CONSTANT= 0;
    public static final int OPTION_INSERT_CONSTANT = 1;
    public static final int OPTION_COPY_TO_CLIPBOARD = 2;
    public static final int OPTION_RESULT_PASTE = 3;

    private static int[] resConverterOption = {
            R.string.save_constant,
            R.string.insert_constant,
            R.string.copy_to_clipboard,
            R.string.paste};

    public static String[] getConverterOptionName() {
        int length = resConverterOption.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resConverterOption[i]);
        }
        return str;
    }

}
