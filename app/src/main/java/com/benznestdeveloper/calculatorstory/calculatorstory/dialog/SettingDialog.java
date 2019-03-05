package com.benznestdeveloper.calculatorstory.calculatorstory.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyCache;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyCalculatorStartup;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyContextor;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyLanguages;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyUtil;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 26-Jun-17.
 */

public class SettingDialog extends Dialog {

    private Button btnChangeLanguage;
    private SwitchCompat mSwitchVibrate;
    private Button btnStartup;
    private Button btnSave;

    private OnSettingUpdateListener mOnSettingUpdateListener;

    public SettingDialog(Context context) {
        super(context);
        init();
    }

    public SettingDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected SettingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_setting);
        this.setCancelable(true);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btnChangeLanguage = (Button) findViewById(R.id.btn_language);
        btnChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangeLanguageDialog();
            }
        });
        btnChangeLanguage.setText(MyLanguages.getUserLanguageName(getContext()));

        mSwitchVibrate = (SwitchCompat) findViewById(R.id.switch_vibrate);
        mSwitchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyUtil.vibrate();
                MyCache.setUserVibrate(getContext(), b);
            }
        });

        btnStartup = (Button) findViewById(R.id.btn_startup);
        btnStartup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                openChangeStartupDialog();
            }
        });
        btnStartup.setText(MyCalculatorStartup.getCurrentStartupName());

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                dismiss();
            }
        });
    }

    private void openChangeStartupDialog() {
        final String[] str = MyCalculatorStartup.getName();
        int current = MyCache.getStartupCalculator(getContext());

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setSingleChoiceItems(str, current, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyUtil.vibrate();
                MyCache.setStartupCalculator(MyContextor.getInstance(), which);
                dialog.dismiss();
                dismiss();
            }
        });
        alert.show();
    }

    private void openChangeLanguageDialog() {
        final String[] languageName = MyLanguages.LANGUAGE_NAME;
        int currentLanguage = MyCache.getUserLanguage(getContext());

        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setSingleChoiceItems(languageName, currentLanguage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyUtil.vibrate();
                MyCache.setUserLanguage(MyContextor.getInstance(), which);
                MyLanguages.changeLanguage(MyContextor.getInstance(), which);
                dialog.dismiss();
                dismiss();

                if (mOnSettingUpdateListener != null) {
                    mOnSettingUpdateListener.onLanguageChanged();
                }
            }
        });
        alert.show();
    }

    public void setOnSettingUpdateListener(OnSettingUpdateListener onSettingUpdateListener) {
        mOnSettingUpdateListener = onSettingUpdateListener;
    }

    public interface OnSettingUpdateListener {
        void onLanguageChanged();
    }
}
