package com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.converter.MyLengthConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LengthConverterFragment extends BaseConverterFragment {

    public static LengthConverterFragment newInstance() {

        Bundle args = new Bundle();
        LengthConverterFragment fragment = new LengthConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LengthConverterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.length);
        init(R.drawable.ic_length, R.string.length, MyLengthConverter.getLengthName(), MyLengthConverter.getLengthName());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = MyLengthConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
