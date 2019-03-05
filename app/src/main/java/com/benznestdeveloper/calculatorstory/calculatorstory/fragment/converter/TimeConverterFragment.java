package com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.converter.MyTimeConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeConverterFragment extends BaseConverterFragment {

    public static TimeConverterFragment newInstance() {

        Bundle args = new Bundle();
        TimeConverterFragment fragment = new TimeConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TimeConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.time);
        init(R.drawable.ic_time, R.string.time, MyTimeConverter.getTimeName(), MyTimeConverter.getTimeName());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = MyTimeConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return Math.round(valueDeselectedMeter);
    }
}
