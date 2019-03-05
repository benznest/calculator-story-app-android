package com.benznestdeveloper.calculatorstory.calculatorstory.dialog.equation;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyEquation;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyUtil;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.EquationDao;

/**
 * Created by benznest on 26-Jun-17.
 */

public class CreateEquationDialog extends Dialog {

    private EditText edtEquationName;
    private EditText edtEquation;
    private Button btnSave;
    private OnEquationListener mOnEquationListener;


    public CreateEquationDialog(Context context) {
        super(context);
        init();
    }

    public CreateEquationDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected CreateEquationDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_create_equation);
        this.setCancelable(true);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edtEquationName = (EditText) findViewById(R.id.edt_equation_name);
        edtEquation = (EditText) findViewById(R.id.edt_equation);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        String name = edtEquationName.getText().toString().trim();
        String equation = edtEquation.getText().toString().trim();
        if (name.length() <= 0) {
            MyUtil.showSnackBarFail(getContext(), btnSave, R.string.error_name_empty);
            return;
        }

        if (equation.length() <= 0) {
            MyUtil.showSnackBarFail(getContext(), btnSave, R.string.error_equation_empty);
            return;
        }

        EquationDao eq = new EquationDao();
        eq.setName(name);
        eq.setEquation(equation);
        MyEquation.addEquation(getContext(), eq);

        if (mOnEquationListener != null) {
            mOnEquationListener.onEquationCreated(eq);
        }

        dismiss();
    }

    public void setEquation(String equation) {
        edtEquation.setText(equation);
    }

    public void setOnEquationListener(OnEquationListener onEquationListener) {
        mOnEquationListener = onEquationListener;
    }

    public interface OnEquationListener {
        void onEquationCreated(EquationDao eq);
    }
}
