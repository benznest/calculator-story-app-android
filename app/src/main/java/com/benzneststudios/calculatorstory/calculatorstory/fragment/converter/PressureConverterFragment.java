package com.benzneststudios.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.converter.PressureConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PressureConverterFragment extends BaseConverterFragment {

    public static PressureConverterFragment newInstance() {

        Bundle args = new Bundle();
        PressureConverterFragment fragment = new PressureConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PressureConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.pressure);
        init(R.drawable.ic_pressure, R.string.pressure, PressureConverter.getPressureName(), PressureConverter.getPressureName());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = PressureConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
