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

public class EditEquationDialog extends Dialog {

    private EquationDao eq;
    private EditText edtEquationName;
    private EditText edtEquation;
    private Button btnSave;
    private OnEquationListener mOnEquationListener;

    public EditEquationDialog(Context context, EquationDao equationDao) {
        super(context);
        this.eq = equationDao;
        init();
        setEquationName(eq.getName());
        setEquation(eq.getEquation());
    }

    public EditEquationDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected EditEquationDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_edit_equation);
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

        eq.setName(name);
        eq.setEquation(equation);
        MyEquation.updateEquation(getContext(), eq);

        if (mOnEquationListener != null) {
            mOnEquationListener.onEquationUpdated(eq);
        }

        dismiss();
    }

    public void setEquationName(String equationName) {
        edtEquationName.setText(equationName);
    }

    public void setEquation(String equation) {
        edtEquation.setText(equation);
    }

    public void setOnEquationListener(OnEquationListener onEquationListener) {
        mOnEquationListener = onEquationListener;
    }

    public interface OnEquationListener {
        void onEquationUpdated(EquationDao eq);
    }
}
