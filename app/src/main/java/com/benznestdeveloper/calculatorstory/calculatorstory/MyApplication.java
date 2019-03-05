package com.benznestdeveloper.calculatorstory.calculatorstory;

import android.app.Application;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by benznest on 23-Jun-17.
 */

public class MyApplication extends Application {

    private static boolean tablet = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        MyContextor.setApplication(this);
        MyContextor.setContext(getApplicationContext());
        tablet = getResources().getBoolean(R.bool.is_tablet);
        setupLanguage();

    }

    private void setupLanguage() {
        int language = MyCache.getUserLanguage(this);
        MyLanguages.changeLanguage(this, language);
    }

    public static boolean isTablet() {
        return tablet;
    }
}
