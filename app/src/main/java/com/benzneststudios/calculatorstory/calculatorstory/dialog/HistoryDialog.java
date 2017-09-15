package com.benzneststudios.calculatorstory.calculatorstory.dialog;

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

import com.benzneststudios.calculatorstory.calculatorstory.MyHistory;
import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.adapter.MyHistoryAdapter;
import com.benzneststudios.calculatorstory.calculatorstory.dao.HistoryDao;
import com.benzneststudios.calculatorstory.calculatorstory.view.MyRowHistoryView;

import java.util.ArrayList;

/**
 * Created by benznest on 26-Jun-17.
 */

public class HistoryDialog extends Dialog {

    private TextView tvNoData;
    private ListView listView;
    private Button btnBack;
    private MyHistoryAdapter adapter;
    private OnHistorySelectedListener mOnHistorySelectedListener;
    private ArrayList<HistoryDao> listHistory;

    public HistoryDialog(Context context) {
        super(context);
        init();
    }

    public HistoryDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected HistoryDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.dialog_history);
        this.setCancelable(true);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        tvNoData = (TextView) findViewById(R.id.tv_no_data);
        listView = (ListView) findViewById(R.id.listView);

        adapter = new MyHistoryAdapter();
        adapter.setOnHistoryListener(new MyRowHistoryView.OnHistoryListener() {
            @Override
            public void onRemoveSelected(HistoryDao historyDao) {
                MyHistory.remove(getContext(), historyDao.getId());
                update();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mOnHistorySelectedListener != null) {
                    mOnHistorySelectedListener.onSelected(listHistory.get(i));
                }

                dismiss();
            }
        });
        listView.setAdapter(adapter);

        update();

        btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void update() {
        listHistory = MyHistory.getHistoryList(getContext());
        adapter.setData(listHistory);
        adapter.notifyDataSetChanged();

        if (listHistory.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }
    }

    public void setOnHistorySelectedListener(OnHistorySelectedListener onHistorySelectedListener) {
        mOnHistorySelectedListener = onHistorySelectedListener;
    }

    public interface OnHistorySelectedListener {
        void onSelected(HistoryDao historyDao);
    }

}
