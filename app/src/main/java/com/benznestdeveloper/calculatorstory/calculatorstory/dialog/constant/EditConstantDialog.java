package com.benznestdeveloper.calculatorstory.calculatorstory.dialog.constant;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyConstant;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyUtil;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.ConstantDao;

/**
 * Created by benznest on 26-Jun-17.
 */

public class EditConstantDialog extends Dialog {

    private ConstantDao c;
    private EditText edtConstantName;
    private EditText edtValue;
    private EditText edtUnit;
    private Button btnSave;
    private OnConstantListener mOnConstantListener;

    public EditConstantDialog(Context context, ConstantDao c) {
        super(context);
        this.c = c;
        init();
        setConstantName(c.getName());
        setValue(c.getValue());
        setUnit(c.getUnit());
    }

    public EditConstantDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected EditConstantDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_edit_constant);
        this.setCancelable(true);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edtConstantName = (EditText) findViewById(R.id.edt_constant_name);
        edtValue = (EditText) findViewById(R.id.edt_value);
        edtUnit = (EditText) findViewById(R.id.edt_unit);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        String name = edtConstantName.getText().toString().trim();
        String value = edtValue.getText().toString().trim();
        String unit = edtUnit.getText().toString().trim();

        if (name.length() <= 0) {
            MyUtil.showSnackBarFail(getContext(), btnSave, R.string.error_name_empty);
            return;
        }

        if (value.length() <= 0) {
            MyUtil.showSnackBarFail(getContext(), btnSave, R.string.error_constant_empty);
            return;
        }

        c.setName(name);
        c.setValue(value);
        c.setUnit(unit);
        MyConstant.update(getContext(), c);

        if (mOnConstantListener != null) {
            mOnConstantListener.onUpdated(c);
        }

        dismiss();
    }

    public void setConstantName(String equationName) {
        edtConstantName.setText(equationName);
    }

    public void setValue(String equation) {
        edtValue.setText(equation);
    }

    public void setUnit(String unit) {
        edtUnit.setText(unit);
    }

    public void setOnConstantListener(OnConstantListener onConstantListener) {
        mOnConstantListener = onConstantListener;
    }

    public interface OnConstantListener {
        void onUpdated(ConstantDao c);
    }
}
