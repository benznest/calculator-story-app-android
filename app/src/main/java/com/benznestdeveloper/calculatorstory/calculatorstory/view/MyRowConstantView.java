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
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.ConstantDao;

/**
 * Created by benznest on 26-Jun-17.
 */

public class MyRowConstantView extends FrameLayout {

    private TextView tvConstantName;
    private TextView tvValue;
    private ImageView imgEdit;
    private ImageView imgRemove;
    private boolean simpleMode;
    private OnConstantMenuSelectedListener mOnConstantMenuSelectedListener;

    private ConstantDao constantDao;

    public MyRowConstantView(Context context) {
        super(context);
        init();
    }

    public MyRowConstantView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRowConstantView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyRowConstantView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.row_constant, this);

        tvConstantName = (TextView) findViewById(R.id.tv_constant_name);
        tvValue = (TextView) findViewById(R.id.tv_value);

        imgEdit = (ImageView) findViewById(R.id.img_edit);
        imgEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnConstantMenuSelectedListener != null) {
                    mOnConstantMenuSelectedListener.onEditSelected(constantDao);
                }
            }
        });

        imgRemove = (ImageView) findViewById(R.id.img_delete);
        imgRemove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnConstantMenuSelectedListener != null) {
                    mOnConstantMenuSelectedListener.onRemoveSelected(constantDao);
                }
            }
        });
    }

    public void setConstant(ConstantDao constantDao) {
        this.constantDao = constantDao;
        setConstantName(constantDao.getName());
        setValue(constantDao.getValue(), constantDao.getUnit());
    }

    private void setConstantName(String name) {
        tvConstantName.setText(name);
    }

    private void setValue(String eq, String unit) {
        tvValue.setText(eq + " " + unit);
    }

    public void setOnConstantMenuSelectedListener(OnConstantMenuSelectedListener onConstantMenuSelectedListener) {
        mOnConstantMenuSelectedListener = onConstantMenuSelectedListener;
    }

    public void setSimpleMode(boolean simpleMode) {
        this.simpleMode = simpleMode;
        if (simpleMode) {
            imgEdit.setVisibility(GONE);
            imgRemove.setVisibility(GONE);
        } else {
            imgEdit.setVisibility(VISIBLE);
            imgRemove.setVisibility(VISIBLE);
        }
    }

    public interface OnConstantMenuSelectedListener {
        void onEditSelected(ConstantDao c);

        void onRemoveSelected(ConstantDao c);
    }
}
