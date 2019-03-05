package com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.converter.MyPowerConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PowerConverterFragment extends BaseConverterFragment {

    public static PowerConverterFragment newInstance() {

        Bundle args = new Bundle();
        PowerConverterFragment fragment = new PowerConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PowerConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.power);
        init(R.drawable.ic_power, R.string.power, MyPowerConverter.getPowerName(), MyPowerConverter.getPowerName());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = MyPowerConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
