package com.benznestdeveloper.calculatorstory.calculatorstory.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.benznestdeveloper.calculatorstory.calculatorstory.dao.ConstantDao;
import com.benznestdeveloper.calculatorstory.calculatorstory.view.MyRowConstantView;

import java.util.ArrayList;

/**
 * Created by benznest on 26-Jun-17.
 */

public class MyConstantAdapter extends BaseAdapter {
    private ArrayList<ConstantDao> listConstant;
    private MyRowConstantView.OnConstantMenuSelectedListener mOnConstantMenuSelectedListener;
    private boolean simpleMode = false;

    public void setData(ArrayList<ConstantDao> listConstant) {
        this.listConstant = listConstant;
    }

    public void setOnConstantMenuSelectedListener(MyRowConstantView.OnConstantMenuSelectedListener onConstantMenuSelectedListener) {
        mOnConstantMenuSelectedListener = onConstantMenuSelectedListener;
    }

    @Override
    public int getCount() {
        return listConstant.size() ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyRowConstantView row = null;
        if (view == null) {
            row = new MyRowConstantView(viewGroup.getContext());
        } else {
            row = (MyRowConstantView) view;
        }

        row.setConstant(listConstant.get(i));
        row.setOnConstantMenuSelectedListener(mOnConstantMenuSelectedListener);
        row.setSimpleMode(simpleMode);


        return row;
    }

    public void setSimpleMode(boolean simpleMode) {
        this.simpleMode = simpleMode;
    }
}
