package com.benznestdeveloper.calculatorstory.calculatorstory;

/**
 * Created by benznest on 23-Jun-17.
 */

public class MyCalculatorOptionManager {

    public static final int OPTION_SAVE_EQUATION = 0;
    public static final int OPTION_LOAD_EQUATION = 1;
    public static final int OPTION_SAVE_CONSTANT = 2;
    public static final int OPTION_INSERT_CONSTANT = 3;
    public static final int OPTION_COPY_TO_CLIPBOARD = 4;
    public static final int OPTION_RESULT_PASTE = 5;
    public static final int OPTION_RESULT_PASTE_END = 6;
    public static final int OPTION_HISTORY = 7;

    public static final int OPTION_LOAD_EQUATION_REPLACE = 0;
    public static final int OPTION_LOAD_EQUATION_INSERT_LAST = 1;

    private static int[] resCalculatorOption = {
            R.string.save_equation,
            R.string.load_equation,
            R.string.save_constant,
            R.string.insert_constant,
            R.string.copy_to_clipboard,
            R.string.paste,
            R.string.paste_last,
            R.string.history};
    private static int[] resLoadEquationOption = {R.string.replace_result, R.string.insert_last};

    public static String[] getCalculatorOptionName() {
        int length = resCalculatorOption.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resCalculatorOption[i]);
            if (i == OPTION_HISTORY) {
                str[i] += " (" + MyHistory.getHistoryList(MyContextor.getInstance()).size() + ")";
            }
        }
        return str;
    }

    public static String[] getLoadEquationName() {
        int length = resLoadEquationOption.length;
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = MyContextor.getInstance().getString(resLoadEquationOption[i]);
        }
        return str;
    }
}
