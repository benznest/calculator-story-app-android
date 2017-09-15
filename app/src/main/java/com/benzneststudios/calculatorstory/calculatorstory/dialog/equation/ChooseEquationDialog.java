package com.benzneststudios.calculatorstory.calculatorstory.dialog.equation;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.benzneststudios.calculatorstory.calculatorstory.MyEquation;
import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.adapter.MyEquationAdapter;
import com.benzneststudios.calculatorstory.calculatorstory.dao.EquationDao;

import java.util.ArrayList;

/**
 * Created by benznest on 26-Jun-17.
 */

public class ChooseEquationDialog extends Dialog {

    private TextView tvNoData;
    private ListView listView;
    private Button btnBack;
    private MyEquationAdapter adapter;
    private OnEquationSelectedListener mOnEquationSelectedListener;
    private ArrayList<EquationDao> listEquation;

    public ChooseEquationDialog(Context context) {
        super(context);
        init();
    }

    public ChooseEquationDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected ChooseEquationDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_choose_equation);
        this.setCancelable(true);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        listEquation = MyEquation.getEquationList(getContext());
        tvNoData = (TextView) findViewById(R.id.tv_no_data);
        if (listEquation.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }

        listView = (ListView) findViewById(R.id.listView);

        adapter = new MyEquationAdapter();
        adapter.setSimpleMode(true);
        adapter.setData(listEquation);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mOnEquationSelectedListener != null) {
                    mOnEquationSelectedListener.onSelected(listEquation.get(i));
                }

                dismiss();
            }
        });
        listView.setAdapter(adapter);

//
        btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setOnEquationSelectedListener(OnEquationSelectedListener onEquationSelectedListener) {
        mOnEquationSelectedListener = onEquationSelectedListener;
    }

    public interface OnEquationSelectedListener {
        void onSelected(EquationDao eq);
    }

}
