package com.benzneststudios.calculatorstory.calculatorstory;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

/**
 * Created by benznest on 30-Jun-17.
 */

public class MyLanguages {
    public static final int LANGUAGE_ENGLISH = 0;
    public static final int LANGUAGE_THAI = 1;

    public static final String[] LANGUAGE_NAME = {"English" , "ไทย"};
    private static final String[] LANGUAGE_NAME_SMALL = {"en" , "th"};

    public static void changeLanguage(Context context, int language){
        Configuration config = new Configuration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(LANGUAGE_NAME_SMALL[language]));
        }else{
            config.locale = new Locale(LANGUAGE_NAME_SMALL[language]);
        }
        context.getResources().updateConfiguration(config, null);
    }

    public static String getUserLanguageName(Context context){
        return MyLanguages.LANGUAGE_NAME[MyCache.getUserLanguage(context)];
    }
}
