package com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.converter.MyTemperatureConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureConverterFragment extends BaseConverterFragment {

    public static TemperatureConverterFragment newInstance() {

        Bundle args = new Bundle();
        TemperatureConverterFragment fragment = new TemperatureConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TemperatureConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.temperature);
        init(R.drawable.ic_temperature, R.string.temperature
                , MyTemperatureConverter.getName(), MyTemperatureConverter.getNameSmall());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = MyTemperatureConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
