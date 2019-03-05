package com.benznestdeveloper.calculatorstory.calculatorstory.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.HistoryDao;

/**
 * Created by benznest on 26-Jun-17.
 */

public class MyRowHistoryView extends FrameLayout {

    private TextView tvEquation;
    private TextView tvAnswer;
    private ImageView imgRemove;

    private HistoryDao mHistoryDao;
    private OnHistoryListener mOnHistoryListener;

    public MyRowHistoryView(Context context) {
        super(context);
        init();
    }

    public MyRowHistoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRowHistoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyRowHistoryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.row_history, this);

        tvEquation = (TextView) findViewById(R.id.tv_equation);
        tvAnswer = (TextView) findViewById(R.id.tv_answer);

        imgRemove = (ImageView) findViewById(R.id.img_delete);
        imgRemove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnHistoryListener != null) {
                    mOnHistoryListener.onRemoveSelected(mHistoryDao);
                }
            }
        });
    }

    public void setHistory(HistoryDao historyDao) {
        this.mHistoryDao = historyDao;
        setEquation(historyDao.getEquation());
        setAnswer(historyDao.getAnswer());
    }

    private void setEquation(String eq) {
        tvEquation.setText(eq);
    }

    private void setAnswer(String ans) {
        tvAnswer.setText("= "+ans);
    }

    public void setOnHistoryListener(OnHistoryListener onHistoryListener) {
        mOnHistoryListener = onHistoryListener;
    }

    public interface OnHistoryListener {
        void onRemoveSelected(HistoryDao historyDao);
    }
}
