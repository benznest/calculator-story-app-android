package com.benzneststudios.calculatorstory.calculatorstory.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.benzneststudios.calculatorstory.calculatorstory.dao.HistoryDao;
import com.benzneststudios.calculatorstory.calculatorstory.view.MyRowHistoryView;

import java.util.ArrayList;

/**
 * Created by benznest on 26-Jun-17.
 */

public class MyHistoryAdapter extends BaseAdapter {

    private ArrayList<HistoryDao> listHistory = new ArrayList<>();
    private MyRowHistoryView.OnHistoryListener mOnHistoryListener;

    public void setData(ArrayList<HistoryDao> listHistory) {
        this.listHistory = listHistory;
    }

    public ArrayList<HistoryDao> getListHistory() {
        return listHistory;
    }

    @Override
    public int getCount() {
        return listHistory.size();
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
        MyRowHistoryView row = null;
        if (view == null) {
            row = new MyRowHistoryView(viewGroup.getContext());
        } else {
            row = (MyRowHistoryView) view;
        }

        row.setHistory(listHistory.get(i));
        row.setOnHistoryListener(mOnHistoryListener);

        return row;
    }

    public void setOnHistoryListener(MyRowHistoryView.OnHistoryListener onHistoryListener) {
        mOnHistoryListener = onHistoryListener;
    }
}
