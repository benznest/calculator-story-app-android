package com.benznestdeveloper.calculatorstory.calculatorstory.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyEquation;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyUtil;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.adapter.MyEquationAdapter;
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.EquationDao;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.equation.EditEquationDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.view.MyRowEquationView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquationFragment extends Fragment {

    private TextView tvNoData;
    private ListView listView;
    private MyEquationAdapter adapter;
    private ArrayList<EquationDao> listEquation;

    public static EquationFragment newInstance() {

        Bundle args = new Bundle();

        EquationFragment fragment = new EquationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EquationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_equation, container, false);
        initInstance(v);
        return v;
    }

    private void initInstance(View v) {
        tvNoData = (TextView) v.findViewById(R.id.tv_no_data);
        listEquation = MyEquation.getEquationList(getContext());
        listView = (ListView) v.findViewById(R.id.listView);
        adapter = new MyEquationAdapter();
        adapter.setData(listEquation);
        adapter.setOnEquationMenuSelectedListener(new MyRowEquationView.OnEquationMenuSelectedListener() {
            @Override
            public void onEditSelected(EquationDao eq) {
                openEditEquationDialog(eq);
            }

            @Override
            public void onRemoveSelected(EquationDao eq) {
                openConfirmRemoveDialog(eq);
            }
        });
        listView.setAdapter(adapter);

        update();
    }

    private void openEditEquationDialog(final EquationDao eq) {
        final EditEquationDialog dialog = new EditEquationDialog(getContext(), eq);
        dialog.setOnEquationListener(new EditEquationDialog.OnEquationListener() {
            @Override
            public void onEquationUpdated(EquationDao eq) {
                MyUtil.showSnackBarSuccess(getContext(), listView, getString(R.string.done));
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void openConfirmRemoveDialog(final EquationDao eq) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(R.string.are_you_sure_delete);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.remove,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyEquation.remove(getContext(), eq.getId());
                        MyUtil.showSnackBarSuccess(getContext(), listView, getString(R.string.done));
                        update();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void update() {
        listEquation = MyEquation.getEquationList(getContext());
        adapter.setData(listEquation);
        adapter.notifyDataSetChanged();

        if (listEquation.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }


        getActivity().setTitle(getString(R.string.equation)+" ("+listEquation.size()+")");
    }

}
