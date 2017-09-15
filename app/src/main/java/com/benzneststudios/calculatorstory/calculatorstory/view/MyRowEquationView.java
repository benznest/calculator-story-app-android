package com.benzneststudios.calculatorstory.calculatorstory.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.dao.EquationDao;

/**
 * Created by benznest on 26-Jun-17.
 */

public class MyRowEquationView extends FrameLayout {

    private TextView tvEquationName;
    private TextView tvEquation;
    private ImageView imgEdit;
    private ImageView imgRemove;
    private boolean simpleMode;
    private OnEquationMenuSelectedListener mOnEquationMenuSelectedListener;

    private EquationDao mEquationDao;

    public MyRowEquationView(Context context) {
        super(context);
        init();
    }

    public MyRowEquationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRowEquationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyRowEquationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.row_equation, this);

        tvEquationName = (TextView) findViewById(R.id.tv_equation_name);
        tvEquation = (TextView) findViewById(R.id.tv_equation);

        imgEdit = (ImageView) findViewById(R.id.img_edit);
        imgEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnEquationMenuSelectedListener != null) {
                    mOnEquationMenuSelectedListener.onEditSelected(mEquationDao);
                }
            }
        });

        imgRemove = (ImageView) findViewById(R.id.img_delete);
        imgRemove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnEquationMenuSelectedListener != null) {
                    mOnEquationMenuSelectedListener.onRemoveSelected(mEquationDao);
                }
            }
        });
    }

    public void setEquation(EquationDao equationDao) {
        mEquationDao = equationDao;
        setEquationName(mEquationDao.getName());
        setEquation(mEquationDao.getEquation());
    }

    private void setEquationName(String name) {
        tvEquationName.setText(name);
    }

    private void setEquation(String eq) {
        tvEquation.setText(eq);
    }

    public void setOnEquationMenuSelectedListener(OnEquationMenuSelectedListener onEquationMenuSelectedListener) {
        mOnEquationMenuSelectedListener = onEquationMenuSelectedListener;
    }

    public void setSimpleMode(boolean simpleMode){
        this.simpleMode = simpleMode;
        if(simpleMode){
            imgEdit.setVisibility(GONE);
            imgRemove.setVisibility(GONE);
        }else{
            imgEdit.setVisibility(VISIBLE);
            imgRemove.setVisibility(VISIBLE);
        }
    }
    public interface OnEquationMenuSelectedListener {
        void onEditSelected(EquationDao eq);

        void onRemoveSelected(EquationDao eq);
    }
}
