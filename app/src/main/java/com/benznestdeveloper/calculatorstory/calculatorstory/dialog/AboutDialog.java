package com.benznestdeveloper.calculatorstory.calculatorstory.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.benznestdeveloper.calculatorstory.calculatorstory.BuildConfig;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;

/**
 * Created by benznest on 30-Jun-17.
 */

public class AboutDialog  extends Dialog {
    private TextView tvVersion;
    private Button btnFinish;

    public AboutDialog(Context context) {
        super(context);
        init();
    }

    public AboutDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public AboutDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_about);
        this.setCancelable(true);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initInstance();
    }

    private void initInstance() {

        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText(getContext().getString(R.string.version) + " " + BuildConfig.VERSION_NAME);

        btnFinish = (Button) findViewById(R.id.btn_finish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
