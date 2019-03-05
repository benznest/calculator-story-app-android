package com.benznestdeveloper.calculatorstory.calculatorstory.fragment.converter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.benznestdeveloper.calculatorstory.calculatorstory.MyConstantApp;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyConverterOptionManager;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyUtil;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.ConstantDao;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.constant.CreateConstantDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.constant.ChooseConstantDialog;

/**
 * Created by benznest on 30-Jun-17.
 */

public class BaseConverterFragment extends Fragment {

    public static final int RESULT_SELECTED_1 = 0;
    public static final int RESULT_SELECTED_2 = 1;

    public static final int UNIT_1 = 1;
    public static final int UNIT_2 = 2;

    protected TextView tvResult1;
    protected TextView tvResult2;

    protected Button[] btnNumber;
    protected Button btnDot;
    protected Button btnClear;

    protected Button btnUnit1;
    protected Button btnUnit2;

    protected int indexResultSelected = 0;
    protected int indexUnit1Selected = 0;
    protected int indexUnit2Selected = 0;

    private Button btnBackSpace;
    private Button btnEqual;
    private ImageView imgSelect1;
    private ImageView imgSelect2;

    protected String[] unitNameFull;
    protected String[] unitNameShow;
    protected int resDrawableIcons;
    protected int resName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_converter, container, false);
        initInstance(v);
        setResultTextSelected("");
        onReStore(savedInstanceState);
        update();
        return v;
    }

    private void onReStore(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String result1 = savedInstanceState.getString(MyConstantApp.KEY_RESULT_1);
            String result2 = savedInstanceState.getString(MyConstantApp.KEY_RESULT_2);

            tvResult1.setText(result1);
            tvResult2.setText(result2);

            indexResultSelected = savedInstanceState.getInt(MyConstantApp.KEY_RESULT_SELECTED);
            indexUnit1Selected = savedInstanceState.getInt(MyConstantApp.KEY_INDEX_UNIT_1_SELECTED);
            indexUnit2Selected = savedInstanceState.getInt(MyConstantApp.KEY_INDEX_UNIT_2_SELECTED);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MyConstantApp.KEY_RESULT_1, tvResult1.getText().toString());
        outState.putString(MyConstantApp.KEY_RESULT_2, tvResult2.getText().toString());
        outState.putInt(MyConstantApp.KEY_RESULT_SELECTED, indexResultSelected);
        outState.putInt(MyConstantApp.KEY_INDEX_UNIT_1_SELECTED, indexUnit1Selected);
        outState.putInt(MyConstantApp.KEY_INDEX_UNIT_2_SELECTED, indexUnit2Selected);
    }


    private void initInstance(View v) {

        imgSelect1 = (ImageView) v.findViewById(R.id.img_checkbox_1);
        imgSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                setIndexResultSelected(RESULT_SELECTED_1);
            }
        });

        imgSelect2 = (ImageView) v.findViewById(R.id.img_checkbox_2);
        imgSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                setIndexResultSelected(RESULT_SELECTED_2);
            }
        });

        setIndexResultSelected(indexResultSelected);

        btnUnit1 = (Button) v.findViewById(R.id.btn_unit_1);
        btnUnit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                openChooseUnitDialog(UNIT_1, indexUnit1Selected);
            }
        });

        btnUnit2 = (Button) v.findViewById(R.id.btn_unit_2);
        btnUnit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                openChooseUnitDialog(UNIT_2, indexUnit2Selected);
            }
        });

        btnNumber = new Button[10];
        btnNumber[0] = (Button) v.findViewById(R.id.num0);
        btnNumber[1] = (Button) v.findViewById(R.id.num1);
        btnNumber[2] = (Button) v.findViewById(R.id.num2);
        btnNumber[3] = (Button) v.findViewById(R.id.num3);
        btnNumber[4] = (Button) v.findViewById(R.id.num4);
        btnNumber[5] = (Button) v.findViewById(R.id.num5);
        btnNumber[6] = (Button) v.findViewById(R.id.num6);
        btnNumber[7] = (Button) v.findViewById(R.id.num7);
        btnNumber[8] = (Button) v.findViewById(R.id.num8);
        btnNumber[9] = (Button) v.findViewById(R.id.num9);

        for (int i = 0; i < btnNumber.length; i++) {
            final int finalI = i;
            btnNumber[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyUtil.vibrate();
                    String text = getResultTextSelected();
                    setResultTextSelected(text + btnNumber[finalI].getText().toString());
                    calculate();
                }
            });
        }

        btnDot = (Button) v.findViewById(R.id.dot);
        btnDot.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View view) {
                                          MyUtil.vibrate();
                                          String text = getResultTextSelected();
                                          if (text.length() > 0 && isCanDot(text)) {
                                              setResultTextSelected(text + ".");
                                          }
                                      }
                                  }

        );

        btnClear = (Button) v.findViewById(R.id.clear);
        btnClear.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View view) {
                                            MyUtil.vibrate();
                                            setResultTextSelected("");
                                            calculate();
                                        }
                                    }

        );

        btnEqual = (Button) v.findViewById(R.id.equal);
        btnEqual.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View view) {
                                            MyUtil.vibrate();
                                            calculate();
                                        }
                                    }

        );

        btnBackSpace = (Button) v.findViewById(R.id.backSpace);
        btnBackSpace.setOnClickListener(new View.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(View view) {
                                                MyUtil.vibrate();
                                                backSpace();
                                                calculate();
                                            }
                                        }

        );


        tvResult1 = (TextView) v.findViewById(R.id.tv_result_1);
        tvResult1.setMovementMethod(new

                ScrollingMovementMethod()

        );
        tvResult1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openConverterOptionDialog(tvResult1, btnUnit1.getText().toString());
                return true;
            }
        });

        tvResult2 = (TextView) v.findViewById(R.id.tv_result_2);
        tvResult2.setMovementMethod(new
                ScrollingMovementMethod()
        );
        tvResult2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openConverterOptionDialog(tvResult2, btnUnit2.getText().toString());
                return true;
            }
        });
    }


    private void openConverterOptionDialog(final TextView tv, final String unit) {
        String[] str = MyConverterOptionManager.getConverterOptionName();
        final int[] choice = {0};
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.option);
        builder.setSingleChoiceItems(str, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyUtil.vibrate();
                choice[0] = which;
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyUtil.vibrate();
                if (choice[0] == MyConverterOptionManager.OPTION_SAVE_CONSTANT) {
                    openSaveConstantDialog(tv, unit);
                } else if (choice[0] == MyConverterOptionManager.OPTION_INSERT_CONSTANT) {
                    openChooseConstantDialog(tv);
                } else if (choice[0] == MyConverterOptionManager.OPTION_COPY_TO_CLIPBOARD) {
                    MyUtil.copyToClipBoard(tv.getText().toString());
                    MyUtil.showSnackBarSuccess(getContext(), tv, R.string.done);
                } else if (choice[0] == MyConverterOptionManager.OPTION_RESULT_PASTE) {
                    String paste = MyUtil.getTextFromClipBoard();
                    if (paste != null) {
                        tv.setText(paste);
                        MyUtil.showSnackBarSuccess(getContext(), tv, R.string.done);
                    } else {
                        MyUtil.showSnackBarFail(getContext(), tv, R.string.no_data);
                    }
                }
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    private void openChooseConstantDialog(final TextView tv) {
        ChooseConstantDialog dialog = new ChooseConstantDialog(getContext());
        dialog.setOnConstantSelectedListener(new ChooseConstantDialog.OnConstantSelectedListener() {
            @Override
            public void onSelected(ConstantDao c) {
                tv.setText(c.getValue());
                calculate();
            }
        });
        dialog.show();
    }

    private void openSaveConstantDialog(final TextView tv, String unit) {
        CreateConstantDialog dialog = new CreateConstantDialog(getContext());
        dialog.setValue(tv.getText().toString());
        dialog.setUnit(unit);
        dialog.setOnConstantListener(new CreateConstantDialog.OnConstantListener() {
            @Override
            public void onConstantCreated(ConstantDao c) {
                MyUtil.showSnackBarSuccess(getContext(), tv, R.string.done);
            }
        });
        dialog.show();
    }

    private boolean isCanDot(CharSequence text) {
        if (!text.toString().contains(".")) {
            return true;
        } else {
            return false;
        }
    }

    private void backSpace() {
        String text = getResultTextSelected();
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            setResultTextSelected(text);
            calculate();
        }
    }

    private void calculate() {

        if (indexUnit1Selected != indexUnit2Selected) {
            String strValueSelected = getResultTextSelected();
            double valueSelected = 0;
            try {
                valueSelected = Double.parseDouble(strValueSelected);
            } catch (Exception e) {

            }

            double valueDeselectedMeter = onCalculate(getIndexUnitSelected(),
                    getIndexUnitDeselected(),
                    valueSelected);

//            double valueDeselectedMeter = MyAreaConverter.convert(
//                    getIndexUnitSelected(),
//                    getIndexUnitDeselected(),
//                    valueSelected);

            setResultTextDeselected("" + valueDeselectedMeter);
        } else {
            setResultTextDeselected(getResultTextSelected());
        }
    }

    protected void setResultTextSelected(String text) {
        if (indexResultSelected == RESULT_SELECTED_1) {
            tvResult1.setText(text);
        } else {
            tvResult2.setText(text);
        }
    }

    private void setResultTextDeselected(String text) {

        if (text.endsWith(".0")) {
            text = text.replace(".0", "");
        }

        if (indexResultSelected == RESULT_SELECTED_1) {
            tvResult2.setText(text);
        } else {
            tvResult1.setText(text);
        }
    }

    public String getResultTextSelected() {
        String text = "";
        if (indexResultSelected == RESULT_SELECTED_1) {
            text = tvResult1.getText().toString();
        } else {
            text = tvResult2.getText().toString();
        }
        return text;
    }

    public void setIndexResultSelected(int indexResultSelected) {
        this.indexResultSelected = indexResultSelected;
        if (indexResultSelected == RESULT_SELECTED_1) {
            imgSelect1.setImageResource(R.drawable.ic_state_selected);
            imgSelect2.setImageResource(R.drawable.ic_state);
        } else {
            imgSelect2.setImageResource(R.drawable.ic_state_selected);
            imgSelect1.setImageResource(R.drawable.ic_state);
        }
    }

    public int getIndexUnitSelected() {
        if (indexResultSelected == RESULT_SELECTED_1) {
            return indexUnit1Selected;
        } else {
            return indexUnit2Selected;
        }
    }

    public int getIndexUnitDeselected() {
        if (indexResultSelected == RESULT_SELECTED_2) {
            return indexUnit1Selected;
        } else {
            return indexUnit2Selected;
        }
    }

    private void openChooseUnitDialog(final int indexUnit, int indexUnitSelected) {
        String[] str = unitNameFull;
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext());
        builder.setTitle(resName);
        builder.setIcon(resDrawableIcons);
        builder.setSingleChoiceItems(str, indexUnitSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (indexUnit == UNIT_1) {
                    indexUnit1Selected = which;
                } else {
                    indexUnit2Selected = which;
                }

                update();
                calculate();
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    protected void update() {
        try {
            String[] strUnit = unitNameShow;
            btnUnit1.setText(strUnit[indexUnit1Selected]);
            btnUnit2.setText(strUnit[indexUnit2Selected]);

            setIndexResultSelected(indexResultSelected);
        } catch (Exception e) {

        }
    }

    protected void init(int resDrawableIcons, int resName, String[] unitNameFull, String[] unitNameShow) {
        this.unitNameFull = unitNameFull;
        this.unitNameShow = unitNameShow;
        this.resDrawableIcons = resDrawableIcons;
        this.resName = resName;
        update();
    }

    protected double onCalculate(int indexUnitSelected, int indexUnitDeselected, double valueSelected) {
        return valueSelected;
    }

}
