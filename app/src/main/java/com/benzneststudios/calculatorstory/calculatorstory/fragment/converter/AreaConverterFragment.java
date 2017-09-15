package com.benzneststudios.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.converter.MyAreaConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AreaConverterFragment extends BaseConverterFragment {

    public static AreaConverterFragment newInstance() {

        Bundle args = new Bundle();
        AreaConverterFragment fragment = new AreaConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AreaConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.area);
        init(R.drawable.ic_area, R.string.area, MyAreaConverter.getAreaName(), MyAreaConverter.getAreaName());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = MyAreaConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
