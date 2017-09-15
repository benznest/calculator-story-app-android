package com.benzneststudios.calculatorstory.calculatorstory.dialog.constant;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.benzneststudios.calculatorstory.calculatorstory.MyConstant;
import com.benzneststudios.calculatorstory.calculatorstory.MyUtil;
import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.dao.ConstantDao;

/**
 * Created by benznest on 26-Jun-17.
 */

public class CreateConstantDialog extends Dialog {

    private EditText edtConstantName;
    private EditText edtValue;
    private EditText edtUnit;
    private Button btnSave;
    private OnConstantListener mOnConstantListener;

    public CreateConstantDialog(Context context) {
        super(context);
        init();
    }

    public CreateConstantDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected CreateConstantDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_create_constant);
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

        ConstantDao c = new ConstantDao();
        c.setName(name);
        c.setValue(value);
        c.setUnit(unit);
        MyConstant.addConstant(getContext(), c);

        if (mOnConstantListener != null) {
            mOnConstantListener.onConstantCreated(c);
        }

        dismiss();
    }

    public void setValue(String value){
        edtValue.setText(value);
    }

    public void setUnit(String unit){
        edtUnit.setText(unit);
    }

    public void setOnConstantListener(OnConstantListener onConstantListener) {
        mOnConstantListener = onConstantListener;
    }

    public interface OnConstantListener {
        void onConstantCreated(ConstantDao c);
    }
}
