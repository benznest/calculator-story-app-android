package com.benznestdeveloper.calculatorstory.calculatorstory.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyConstant;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyUtil;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.adapter.MyConstantAdapter;
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.ConstantDao;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.constant.CreateConstantDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.constant.EditConstantDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.view.MyRowConstantView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConstantFragment extends Fragment {

    private TextView tvNoData;
    private ListView listView;
    private MyConstantAdapter adapter;
    private ArrayList<ConstantDao> listConst;
    private FloatingActionButton mFloatingCreateConstant;

    public static ConstantFragment newInstance() {

        Bundle args = new Bundle();

        ConstantFragment fragment = new ConstantFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ConstantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_constant, container, false);
        initInstance(v);
        return v;
    }

    private void initInstance(View v) {
        tvNoData = (TextView) v.findViewById(R.id.tv_no_data);
        listConst = MyConstant.getConstantList(getContext());
        listView = (ListView) v.findViewById(R.id.listView);
        adapter = new MyConstantAdapter();
        adapter.setData(listConst);
        adapter.setOnConstantMenuSelectedListener(new MyRowConstantView.OnConstantMenuSelectedListener() {
            @Override
            public void onEditSelected(ConstantDao c) {
                openEditConstantDialog(c);
            }

            @Override
            public void onRemoveSelected(ConstantDao c) {
                openConfirmRemoveDialog(c);
            }
        });
        listView.setAdapter(adapter);

        mFloatingCreateConstant = (FloatingActionButton) v.findViewById(R.id.fab_create_constant);
        mFloatingCreateConstant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateConstantDialog();
            }
        });

        update();
    }

    private void openCreateConstantDialog() {
        CreateConstantDialog dialog = new CreateConstantDialog(getContext());
        dialog.setOnConstantListener(new CreateConstantDialog.OnConstantListener() {
            @Override
            public void onConstantCreated(ConstantDao c) {
                update();
            }
        });
        dialog.show();
    }

    private void openEditConstantDialog(final ConstantDao c) {
        final EditConstantDialog dialog = new EditConstantDialog(getContext(), c);
        dialog.setOnConstantListener(new EditConstantDialog.OnConstantListener() {
            @Override
            public void onUpdated(ConstantDao c) {
                update();
                MyUtil.showSnackBarSuccess(getContext(), listView, getString(R.string.done));
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void openConfirmRemoveDialog(final ConstantDao c) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage(R.string.are_you_sure_delete);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.remove,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyConstant.remove(getContext(), c.getId());
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
        listConst = MyConstant.getConstantList(getContext());
        adapter.setData(listConst);
        adapter.notifyDataSetChanged();

        if (listConst.size() <= 0) {
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.GONE);
        }


        getActivity().setTitle(getString(R.string.constant) + " (" + listConst.size() + ")");
    }

}
