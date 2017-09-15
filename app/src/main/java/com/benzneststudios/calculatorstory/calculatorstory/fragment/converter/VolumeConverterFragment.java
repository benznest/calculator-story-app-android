package com.benzneststudios.calculatorstory.calculatorstory.fragment.converter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.converter.MyVolumeConverter;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeConverterFragment extends BaseConverterFragment {


    public static VolumeConverterFragment newInstance() {

        Bundle args = new Bundle();
        VolumeConverterFragment fragment = new VolumeConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public VolumeConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.volume);
        init(R.drawable.ic_volume, R.string.volume
                , MyVolumeConverter.getVolumeName(), MyVolumeConverter.getVolumeName());
        return v;
    }

    @Override
    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        super.onCalculate(indexUnitSelected, indexUnitDeselected, valueSelected);
        double valueDeselectedMeter = MyVolumeConverter.convert(
                getIndexUnitSelected(),
                getIndexUnitDeselected(),
                valueSelected);
        return valueDeselectedMeter;
    }
}
