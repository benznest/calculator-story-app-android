package com.benznestdeveloper.calculatorstory.calculatorstory;

import android.os.Bundle;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;

/**
 * Created by benznest on 23-Jun-17.
 */

public class MyAdmob {
    private static String DEVICE_ID_HTC_DESIRE_EYE = "B9154FFA0B3F372EB8ADFF611800A138";
    private static String DEVICE_ID_EMULATOR_TABLET = "8A02482AA601E10933A98768ED6DD53A";
    private static String DEVICE_ID_SAMSUNG_TAB_S_8_4 = "BB20390D481DB961B68ECD4B00259F2A";

    public static AdRequest getAdsDefault() {
        AdRequest.Builder adBuilder = new AdRequest.Builder();

        adBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice(DEVICE_ID_HTC_DESIRE_EYE) // HTC Desire EYE.
                .addTestDevice(DEVICE_ID_SAMSUNG_TAB_S_8_4) // Tab S
                .addTestDevice(DEVICE_ID_EMULATOR_TABLET);

        Bundle bundle = new Bundle();
        bundle.putString("color_bg", "e7e7e7");
        bundle.putString("color_bg_top", "e7e7e7");
        bundle.putString("color_border", "e7e7e7");
        bundle.putString("color_link", "34495e");
        bundle.putString("color_text", "34495e");
        bundle.putString("color_url", "1abc9c");

        //AdMobExtras extras = new AdMobExtras(bundle);
        adBuilder.addNetworkExtrasBundle(AdMobAdapter.class, bundle);

        return adBuilder.build();
    }
}
