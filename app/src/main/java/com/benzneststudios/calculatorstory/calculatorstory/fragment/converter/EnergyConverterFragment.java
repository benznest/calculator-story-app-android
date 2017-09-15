package com.benzneststudios.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.converter.EnergyConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnergyConverterFragment extends BaseConverterFragment {

    public static EnergyConverterFragment newInstance() {

        Bundle args = new Bundle();
        EnergyConverterFragment fragment = new EnergyConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EnergyConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.energy);
        init(R.drawable.ic_energy, R.string.energy, EnergyConverter.getEnergyName(), EnergyConverter.getEnergyName());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = EnergyConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
