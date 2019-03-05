package com.benznestdeveloper.calculatorstory.calculatorstory;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by benznest on 23-Jun-17.
 */

public class MyUtil {

    public static Snackbar showSnackBarSuccess(Context context, View view, int res_message) {
        String message = context.getString(res_message);
        return showSnackBarSuccess(context, view, message);
    }

    public static Snackbar showSnackBarSuccess(Context context, View view, String message) {
        try {
            Snackbar snackbar = Snackbar.make(view, " " + message, Snackbar.LENGTH_LONG)
                    .setAction("Redo", null);

            View snackBarLayout = snackbar.getView();
            snackBarLayout.setBackgroundResource(R.color.colorSuccess);


            TextView textView = (TextView) snackBarLayout.findViewById(android.support.design.R.id.snackbar_text);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_checked, 0, 0, 0);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(5, 0, 0, 0);
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary));
            snackbar.show();
            return snackbar;
        } catch (NullPointerException e) {
            //
        }
        return null;
    }

    public static Snackbar showSnackBarWarning(Context context, View view, int res_message) {
        String message = context.getString(res_message);
        return showSnackBarWarning(context, view, message);
    }

    public static Snackbar showSnackBarWarning(Context context, View view, String message) {
        try {
            Snackbar snackbar = Snackbar.make(view, " " + message, Snackbar.LENGTH_LONG)
                    .setAction("Redo", null);

            View snackBarLayout = snackbar.getView();
            snackBarLayout.setBackgroundResource(R.color.colorWarning);


            TextView textView = (TextView) snackBarLayout.findViewById(android.support.design.R.id.snackbar_text);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info, 0, 0, 0);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(5, 0, 0, 0);
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary));
            snackbar.show();
            return snackbar;
        } catch (NullPointerException e) {

        }
        return null;
    }

    public static Snackbar showSnackBarFail(Context context, View view, int res_message) {
        if (context == null) {
            context = MyContextor.getInstance();
        }

        String message = context.getString(res_message);
        return showSnackBarFail(context, view, message);
    }

    public static Snackbar showSnackBarFail(Context context, View view, String message) {
        try {
            Snackbar snackbar = Snackbar.make(view, " " + message, Snackbar.LENGTH_SHORT)
                    .setAction("Redo", null);

            View snackBarLayout = snackbar.getView();
            snackBarLayout.setBackgroundResource(R.color.colorError);


            TextView textView = (TextView) snackBarLayout.findViewById(android.support.design.R.id.snackbar_text);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(5, 0, 0, 0);
            textView.setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary));
            snackbar.show();
            return snackbar;
        } catch (NullPointerException e) {

        }
        return null;
    }

    public static void copyToClipBoard(String value) {
        ClipboardManager clipboard = (ClipboardManager) MyContextor.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(value, value);
        clipboard.setPrimaryClip(clip);
    }

    public static String getTextFromClipBoard() {
        try {
            ClipboardManager clipboard = (ClipboardManager) MyContextor.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            return item.getText().toString();
        } catch (Exception e) {

        }
        return null;
    }

    public static void vibrate(int time) {
        boolean vibrate = MyCache.isUserVibrate(MyContextor.getInstance());
        if (vibrate) {
            Vibrator v = (Vibrator) MyContextor.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(time);
        }
    }

    public static void vibrate() {
        vibrate(50);
    }

    public static void openPlayStore(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
