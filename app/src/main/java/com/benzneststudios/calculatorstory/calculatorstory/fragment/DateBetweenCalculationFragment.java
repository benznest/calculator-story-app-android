package com.benzneststudios.calculatorstory.calculatorstory.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.benzneststudios.calculatorstory.calculatorstory.MyCalendarUtils;
import com.benzneststudios.calculatorstory.calculatorstory.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DateBetweenCalculationFragment extends Fragment {

    private Calendar cFrom, cTo;
    private TextView tvResult;
    private TextView tvDays;
    private Button btnFrom;
    private Button btnTo;

    public static DateBetweenCalculationFragment newInstance() {
        Bundle args = new Bundle();

        DateBetweenCalculationFragment fragment = new DateBetweenCalculationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DateBetweenCalculationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_between_date_calculation, container, false);
        initInstance(v);
        getActivity().setTitle(R.string.between_date_calculation);
        return v;
    }

    private void initInstance(View v) {
        cFrom = Calendar.getInstance();
        cTo = Calendar.getInstance();

        tvResult = (TextView) v.findViewById(R.id.tv_result);
        tvDays = (TextView) v.findViewById(R.id.tv_total_days);
        tvResult.setText(0 + " "+getResources().getQuantityString(R.plurals.day,0));
        tvDays.setText(0 + " "+getResources().getQuantityString(R.plurals.day,0));

        btnFrom = (Button) v.findViewById(R.id.btn_from);
        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(R.string.from, new OnDateSelectedListener() {
                    @Override
                    public void onSelected(Calendar calendar) {
                        cFrom = calendar;
                        setTextFromButton(calendar);
                        calculateDiffer();
                    }
                }, cFrom);
            }

        });

        btnTo = (Button) v.findViewById(R.id.btn_to);
        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(R.string.to, new OnDateSelectedListener() {
                    @Override
                    public void onSelected(Calendar calendar) {
                        cTo = calendar;
                        setTextToButton(calendar);
                        calculateDiffer();
                    }
                }, cTo);
            }
        });

        setTextFromButton(cFrom);
        setTextToButton(cTo);
    }

    private void calculateDiffer() {
        long diff = cFrom.getTimeInMillis() - cTo.getTimeInMillis();
        if (diff < 0) {
            diff = diff * -1;
        }

        long totalDays = diff / (24 * 60 * 60 * 1000);
        int year = (int) (totalDays / 365);
        int months = (int) ((totalDays % 365) / 12);
        int day = (int) ((totalDays % 365) % 12);


        String str = "";
        if (year > 0) {
            str = str + year + " "+ getResources().getQuantityString(R.plurals.year, year) + " ";
        }
        if (months > 0) {
            str = str + months + " "+ getResources().getQuantityString(R.plurals.month, months) + " ";
        }
        if (day > 0) {
            str = str + day +" "+  getResources().getQuantityString(R.plurals.day, day) + " ";
        }

        tvResult.setText(str);
        tvDays.setText("" + totalDays+" "+getResources().getQuantityString(R.plurals.day, day));
    }

    private void openDatePicker(int res_title, final OnDateSelectedListener onDateSelectedListener, final Calendar c) {
        DatePickerDialog dp = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                int mYear = year;
                int mMonth = monthOfYear;
                int mDay = dayOfMonth;

                c.set(Calendar.YEAR, mYear);
                c.set(Calendar.MONTH, mMonth);
                c.set(Calendar.DAY_OF_MONTH, mDay);

                onDateSelectedListener.onSelected(c);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dp.setIcon(R.drawable.ic_date_calculation);
        dp.setTitle(res_title);
        dp.show();

    }

    private void setTextFromButton(Calendar c) {
        btnFrom.setText("" + c.get(Calendar.DAY_OF_MONTH) + " " + MyCalendarUtils.getMonthSmallName(c.get(Calendar.MONTH) + 1) + " " + c.get(Calendar.YEAR));
    }

    private void setTextToButton(Calendar c) {
        btnTo.setText("" + c.get(Calendar.DAY_OF_MONTH) + " " +  MyCalendarUtils.getMonthSmallName(c.get(Calendar.MONTH) + 1) + " " + c.get(Calendar.YEAR));
    }

    public interface OnDateSelectedListener {
        void onSelected(Calendar calendar);
    }
}
