package com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.converter.MySpeedConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpeedConverterFragment extends BaseConverterFragment {

    public static SpeedConverterFragment newInstance() {

        Bundle args = new Bundle();
        SpeedConverterFragment fragment = new SpeedConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SpeedConverterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.speed);
        init(R.drawable.ic_speed, R.string.speed, MySpeedConverter.getSpeedName(), MySpeedConverter.getSpeedName());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = MySpeedConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
