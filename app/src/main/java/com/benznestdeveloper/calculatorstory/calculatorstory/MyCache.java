package com.benznestdeveloper.calculatorstory.calculatorstory;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by benznest on 30-Jun-17.
 */

public class MyCache {

    private static final String SHARED_PREFERENCES_NAME = "RANDOM_STORY";
    private static SharedPreferences sp;
    public static String KEY_USER_LANGUAGE = "KEY_USER_LANGUAGE";
    public static String KEY_USER_VIBRATE = "KEY_USER_VIBRATE";
    public static String KEY_STARTUP_CALCULATOR = "KEY_STARTUP_CALCULATOR";

    private static void setContext(Context context) {
        if (context == null) {
            context = MyContextor.getInstance();
        }
        sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setUserLanguage(Context context, int lang) {
        setContext(context);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY_USER_LANGUAGE, lang);
        editor.commit();
    }

    public static int getUserLanguage(Context context) {
        setContext(context);

        return sp.getInt(KEY_USER_LANGUAGE, MyLanguages.LANGUAGE_ENGLISH);
    }

    public static void setUserVibrate(Context context, boolean is) {
        setContext(context);

        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY_USER_VIBRATE, is);
        editor.commit();
    }

    public static boolean isUserVibrate(Context context) {
        setContext(context);

        return sp.getBoolean(KEY_USER_VIBRATE, true);
    }

    public static void setStartupCalculator(Context context, int index) {
        setContext(context);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY_STARTUP_CALCULATOR, index);
        editor.commit();
    }

    public static int getStartupCalculator(Context context) {
        setContext(context);

        return sp.getInt(KEY_STARTUP_CALCULATOR, MyCalculatorStartup.STANDARD_CALCULATOR);
    }

}
