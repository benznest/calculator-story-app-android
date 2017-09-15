package com.benzneststudios.calculatorstory.calculatorstory.dialog.constant;

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

import com.benzneststudios.calculatorstory.calculatorstory.MyConstant;
import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.adapter.MyConstantAdapter;
import com.benzneststudios.calculatorstory.calculatorstory.dao.ConstantDao;

import java.util.ArrayList;

/**
 * Created by benznest on 26-Jun-17.
 */

public class ChooseConstantDialog extends Dialog {

    private TextView tvNoData;
    private ListView listView;
    private Button btnBack;
    private MyConstantAdapter adapter;
    private OnConstantSelectedListener mOnConstantSelectedListener;
    private ArrayList<ConstantDao> listConstant;

    public ChooseConstantDialog(Context context) {
        super(context);
        init();
    }

    public ChooseConstantDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected ChooseConstantDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_choose_constant);
        this.setCancelable(true);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        listConstant = MyConstant.getConstantList(getContext());
        tvNoData = (TextView) findViewById(R.id.tv_no_data);
        if (listConstant.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }

        listView = (ListView) findViewById(R.id.listView);

        adapter = new MyConstantAdapter();
        adapter.setSimpleMode(true);
        adapter.setData(listConstant);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mOnConstantSelectedListener != null) {
                    mOnConstantSelectedListener.onSelected(listConstant.get(i));
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

    public void setOnConstantSelectedListener(OnConstantSelectedListener onConstantSelectedListener) {
        mOnConstantSelectedListener = onConstantSelectedListener;
    }

    public interface OnConstantSelectedListener {
        void onSelected(ConstantDao c);
    }

}
