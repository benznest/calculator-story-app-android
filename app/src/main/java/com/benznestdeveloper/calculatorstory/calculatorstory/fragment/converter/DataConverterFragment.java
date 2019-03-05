package com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.converter.MyDataConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataConverterFragment extends BaseConverterFragment {

    public static DataConverterFragment newInstance() {

        Bundle args = new Bundle();
        DataConverterFragment fragment = new DataConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DataConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.data);
        init(R.drawable.ic_data, R.string.data, MyDataConverter.getDataName(), MyDataConverter.getDataNameSmall());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = MyDataConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
