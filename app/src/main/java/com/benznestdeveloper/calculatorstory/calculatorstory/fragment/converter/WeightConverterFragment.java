package com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.converter.MyWeightConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeightConverterFragment extends BaseConverterFragment {

    public static WeightConverterFragment newInstance() {

        Bundle args = new Bundle();
        WeightConverterFragment fragment = new WeightConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public WeightConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.weight);
        init(R.drawable.ic_weight, R.string.weight
                , MyWeightConverter.getWeightName(), MyWeightConverter.getWeightName());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = MyWeightConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
