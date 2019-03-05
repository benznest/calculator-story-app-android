package com.benznestdeveloper.calculatorstory.calculatorstory.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.benznestdeveloper.calculatorstory.calculatorstory.dao.EquationDao;
import com.benznestdeveloper.calculatorstory.calculatorstory.view.MyRowEquationView;

import java.util.ArrayList;

/**
 * Created by benznest on 26-Jun-17.
 */

public class MyEquationAdapter extends BaseAdapter {
    private ArrayList<EquationDao> listEquation;
    private MyRowEquationView.OnEquationMenuSelectedListener mOnEquationMenuSelectedListener;
    private boolean simpleMode = false;

    public void setData(ArrayList<EquationDao> listEquation) {
        this.listEquation = listEquation;
    }

    public void setOnEquationMenuSelectedListener(MyRowEquationView.OnEquationMenuSelectedListener onEquationMenuSelectedListener) {
        mOnEquationMenuSelectedListener = onEquationMenuSelectedListener;
    }

    @Override
    public int getCount() {
        return listEquation.size();
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
        MyRowEquationView row;
        if (view == null) {
            row = new MyRowEquationView(viewGroup.getContext());
        } else {
            row = (MyRowEquationView) view;
        }

        row.setEquation(listEquation.get(i));
        row.setOnEquationMenuSelectedListener(mOnEquationMenuSelectedListener);
        row.setSimpleMode(simpleMode);

        return row;
    }

    public void setSimpleMode(boolean simpleMode) {
        this.simpleMode = simpleMode;
    }
}
