package com.benznestdeveloper.calculatorstory.calculatorstory.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.benznestdeveloper.calculatorstory.calculatorstory.DBHelper;
import com.benznestdeveloper.calculatorstory.calculatorstory.ExtendedDoubleEvaluator;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyApplication;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyCalculatorOptionManager;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyConstantApp;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyHistory;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyOperatorOptionManager;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyUtil;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.benznestdeveloper.calculatorstory.calculatorstory.adapter.MyHistoryAdapter;
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.ConstantDao;
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.EquationDao;
import com.benznestdeveloper.calculatorstory.calculatorstory.dao.HistoryDao;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.HistoryDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.constant.ChooseConstantDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.constant.CreateConstantDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.equation.ChooseEquationDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.dialog.equation.CreateEquationDialog;
import com.benznestdeveloper.calculatorstory.calculatorstory.view.MyRowHistoryView;
import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StandardCalculatorFragment extends Fragment {

    private TextView tvResult;
    private DBHelper dbHelper;
    private int count = 0;

    private Button[] btnNumber;
    private Button btnDot;
    private Button btnClear;

    private String expression = "";
    private String text = "";
    private Double result = 0.0;
    private Button btnBackSpace;

    private Button[] btnOperation;
    private Button btnEqual;
    private Button btnSquare;
    private Button btnSqrt;

    private Button btnOpenBracket;
    private Button btnCloseBracket;
    private Button btnPosneg;
    private TextView tvMore;
    private TextView tvRecent;

    // For tablet.
    private TextView tvNoData;
    private ListView listViewHistory;
    private MyHistoryAdapter historyAdapter;
    private String recentEquation = "";
    private boolean showHistory = true;
    private ImageView imgToggleHistory;
    private FrameLayout flHistoryContainer;

    public static StandardCalculatorFragment newInstance() {

        Bundle args = new Bundle();
        StandardCalculatorFragment fragment = new StandardCalculatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public StandardCalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_standard_calulator, container, false);
        initInstance(v);
        initInstanceForTablet(v);
        getActivity().setTitle(R.string.stdCal);

        onRestore(savedInstanceState);

        return v;
    }

    private void initInstanceForTablet(View v) {
        if (MyApplication.isTablet()) {
            tvNoData = (TextView) v.findViewById(R.id.tv_no_data);
            listViewHistory = (ListView) v.findViewById(R.id.listView_history);
            historyAdapter = new MyHistoryAdapter();

            listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    MyUtil.vibrate();
                    setResult(historyAdapter.getListHistory().get(i).getEquation());
                }
            });

            historyAdapter.setOnHistoryListener(new MyRowHistoryView.OnHistoryListener() {
                @Override
                public void onRemoveSelected(HistoryDao historyDao) {
                    MyUtil.vibrate();
                    MyHistory.remove(getContext(), historyDao.getId());
                    updateHistory();
                }
            });
            listViewHistory.setAdapter(historyAdapter);
            updateHistory();

            flHistoryContainer = (FrameLayout) v.findViewById(R.id.fl_history_container);

            imgToggleHistory = (ImageView) v.findViewById(R.id.img_toggle_history);
            imgToggleHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyUtil.vibrate();
                    if (showHistory) {
                        flHistoryContainer.setVisibility(View.GONE);
                    } else {
                        flHistoryContainer.setVisibility(View.VISIBLE);
                    }
                    showHistory = !showHistory;
                }
            });
        }
    }

    private void updateHistory() {
        if (MyApplication.isTablet()) {
            ArrayList<HistoryDao> listHistory = MyHistory.getHistoryList(getContext());
            historyAdapter.setData(listHistory);
            historyAdapter.notifyDataSetChanged();

            if (listHistory.size() > 0) {
                tvNoData.setVisibility(View.GONE);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    private void onRestore(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String result = savedInstanceState.getString(MyConstantApp.KEY_RESULT_1);
            tvResult.setText(result);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MyConstantApp.KEY_RESULT_1, tvResult.getText().toString());
    }

    private void initInstance(View v) {

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
                    setResult(tvResult.getText() + btnNumber[finalI].getText().toString());
                }
            });
        }

        btnOperation = new Button[4];
        btnOperation[0] = (Button) v.findViewById(R.id.plus);
        btnOperation[1] = (Button) v.findViewById(R.id.minus);
        btnOperation[2] = (Button) v.findViewById(R.id.multiply);
        btnOperation[3] = (Button) v.findViewById(R.id.divide);

        for (int i = 0; i < btnOperation.length; i++) {
            final int finalI = i;
            btnOperation[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyUtil.vibrate();
                    operationClicked(btnOperation[finalI].getText().toString());
                }
            });
        }

        btnDot = (Button) v.findViewById(R.id.dot);
        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                String text = tvResult.getText().toString();
                if (text.length() > 0 && isCanDot(text)) {
                    setResult(text + ".");
                }
            }
        });

        btnClear = (Button) v.findViewById(R.id.clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                setResult("");
                count = 0;
                expression = "";
            }
        });

        btnEqual = (Button) v.findViewById(R.id.equal);
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                /*for more knowledge on DoubleEvaluator and its tutorial go to the below link
                http://javaluator.sourceforge.net/en/home/*/
                calculateExpression();
            }
        });

        btnBackSpace = (Button) v.findViewById(R.id.backSpace);
        btnBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                backSpace();
            }
        });

        btnSquare = (Button) v.findViewById(R.id.square);
        btnSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                openSquareOptionDialog();
            }
        });

        btnSqrt = (Button) v.findViewById(R.id.sqrt);
        btnSqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                openSqrtOptionDialog();
            }
        });

        btnOpenBracket = (Button) v.findViewById(R.id.openBracket);
        btnOpenBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();

                setResult(tvResult.getText() + "(");
            }
        });

        btnCloseBracket = (Button) v.findViewById(R.id.closeBracket);
        btnCloseBracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();

                setResult(tvResult.getText() + ")");
            }
        });

        btnPosneg = (Button) v.findViewById(R.id.posneg);
        btnPosneg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();

                if (tvResult.length() != 0) {
                    String s = tvResult.getText().toString();
                    char arr[] = s.toCharArray();
                    if (arr[0] == '-')
                        setResult(s.substring(1, s.length()));
                    else
                        setResult("-" + s);
                }
            }
        });

        tvRecent = (TextView) v.findViewById(R.id.tv_recent);
        tvRecent.setText("");

        tvResult = (TextView) v.findViewById(R.id.tv_result);
        tvResult.setMovementMethod(new ScrollingMovementMethod());
        tvResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MyUtil.vibrate();
                openMoreDialog();
                return true;
            }
        });
        dbHelper = new DBHelper(getContext());
        setResult("");

        tvMore = (TextView) v.findViewById(R.id.tv_more);
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                openMoreDialog();
            }
        });
    }

    private void openMoreDialog() {
        String[] str = MyCalculatorOptionManager.getCalculatorOptionName();
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

                text = tvResult.getText().toString();
                if (choice[0] == MyCalculatorOptionManager.OPTION_LOAD_EQUATION) {
                    openChooseEquationDialog();
                } else if (choice[0] == MyCalculatorOptionManager.OPTION_SAVE_EQUATION) {
                    openCreateEquationDialog();
                } else if (choice[0] == MyCalculatorOptionManager.OPTION_SAVE_CONSTANT) {
                    openCreateConstant();
                } else if (choice[0] == MyCalculatorOptionManager.OPTION_INSERT_CONSTANT) {
                    openInsertConstant();
                } else if (choice[0] == MyCalculatorOptionManager.OPTION_COPY_TO_CLIPBOARD) {
                    MyUtil.copyToClipBoard(tvResult.getText().toString());
                    MyUtil.showSnackBarSuccess(getContext(), tvResult, R.string.done);
                } else if (choice[0] == MyCalculatorOptionManager.OPTION_RESULT_PASTE) {
                    String paste = MyUtil.getTextFromClipBoard();
                    if (paste != null) {
                        setResult(paste);
                        MyUtil.showSnackBarSuccess(getContext(), tvResult, R.string.done);
                    } else {
                        MyUtil.showSnackBarWarning(getContext(), tvResult, R.string.no_data);
                    }
                } else if (choice[0] == MyCalculatorOptionManager.OPTION_RESULT_PASTE_END) {
                    String paste = MyUtil.getTextFromClipBoard();
                    if (paste != null) {
                        setResult(tvResult.getText().toString() + "" + paste);
                        MyUtil.showSnackBarSuccess(getContext(), tvResult, R.string.done);
                    } else {
                        MyUtil.showSnackBarWarning(getContext(), tvResult, R.string.no_data);
                    }
                } else if (choice[0] == MyCalculatorOptionManager.OPTION_HISTORY) {
                    openHistoryDialog();
                }
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    private void openHistoryDialog() {
        HistoryDialog historyDialog = new HistoryDialog(getContext());
        historyDialog.setOnHistorySelectedListener(new HistoryDialog.OnHistorySelectedListener() {
            @Override
            public void onSelected(HistoryDao historyDao) {
                setResult(historyDao.getEquation());
            }
        });
        historyDialog.show();
    }


    private void openChooseEquationDialog() {
        ChooseEquationDialog chooseEquationDialog = new ChooseEquationDialog(getContext());
        chooseEquationDialog.setOnEquationSelectedListener(new ChooseEquationDialog.OnEquationSelectedListener() {
            @Override
            public void onSelected(EquationDao eq) {
                openLoadEquationOptionDialog(eq.getEquation());
            }
        });
        chooseEquationDialog.show();
    }

    private void openCreateConstant() {
        CreateConstantDialog dialog = new CreateConstantDialog(getContext());
        dialog.setOnConstantListener(new CreateConstantDialog.OnConstantListener() {
            @Override
            public void onConstantCreated(ConstantDao c) {
                MyUtil.showSnackBarSuccess(getContext(), tvMore, R.string.done);
            }
        });
        dialog.show();
    }

    private void openCreateEquationDialog() {
        CreateEquationDialog createEquationDialog = new CreateEquationDialog(getContext());
        createEquationDialog.setEquation(tvResult.getText().toString());
        createEquationDialog.setOnEquationListener(new CreateEquationDialog.OnEquationListener() {
            @Override
            public void onEquationCreated(EquationDao eq) {
                MyUtil.showSnackBarSuccess(getContext(), tvMore, R.string.equation_saved);
            }
        });
        createEquationDialog.show();
    }

    private void openInsertConstant() {
        ChooseConstantDialog chooseConstantDialog = new ChooseConstantDialog(getContext());
        chooseConstantDialog.setOnConstantSelectedListener(new ChooseConstantDialog.OnConstantSelectedListener() {
            @Override
            public void onSelected(ConstantDao c) {
                text = tvResult.getText().toString();
                setResult(text + c.getValue());
            }
        });
        chooseConstantDialog.show();
    }

    private void openLoadEquationOptionDialog(final String value) {
        String[] str = MyCalculatorOptionManager.getLoadEquationName();
        final int[] choice = {0};
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext());
        builder.setTitle(value);
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

                text = tvResult.getText().toString();
                if (choice[0] == MyCalculatorOptionManager.OPTION_LOAD_EQUATION_REPLACE) {
                    setResult(value);
                } else {
                    setResult(text + value);
                }
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    private void openSqrtOptionDialog() {

        String[] str = MyOperatorOptionManager.getSqrtOptionName();
        final int[] choice = {0};
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext());
        builder.setTitle("Sqrt");
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

                text = tvResult.getText().toString();
                if (choice[0] == MyOperatorOptionManager.OPTION_SQRT_BRING_ALL) {
                    setResult("sqrt(" + text + ")");
                } else {
                    setResult(text + "sqrt(");
                }
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    private void openSquareOptionDialog() {
        String[] str = MyOperatorOptionManager.getSquareOptionName();
        final int[] choice = {0};
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext());
        builder.setTitle("Sqrt");
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
                text = tvResult.getText().toString();
                if (choice[0] == MyOperatorOptionManager.OPTION_SQUARE_BRING_ALL) {
                    setResult("(" + text + ")^2");
                } else {
                    setResult(text + "^2");
                }
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    private boolean isCanDot(CharSequence text) {
        int length = text.length();
        boolean flag = false;
        for (int i = length - 1; i >= 0; i--) {
            char c = text.charAt(i);
            if (Character.isDigit(c)) {
                flag = true;
            } else {
                if (c == '.') {
                    return false;
                } else if (flag) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private void backSpace() {
        text = tvResult.getText().toString();
        if (text.length() > 0) {
            if (text.endsWith(".")) {
                count = 0;
            }
            String newText = text.substring(0, text.length() - 1);
            //to delete the data contained in the brackets at once
            if (text.endsWith(")")) {
                char[] a = text.toCharArray();
                int pos = a.length - 2;
                int counter = 1;
                //to find the opening bracket position
                for (int i = a.length - 2; i >= 0; i--) {
                    if (a[i] == ')') {
                        counter++;
                    } else if (a[i] == '(') {
                        counter--;
                    }
                    //if decimal is deleted b/w brackets then count should be zero
                    else if (a[i] == '.') {
                        count = 0;
                    }
                    //if opening bracket pair for the last bracket is found
                    if (counter == 0) {
                        pos = i;
                        break;
                    }
                }
                newText = text.substring(0, pos);
            }
            //if tvResult2 edit text contains only - sign or sqrt at last then clear the edit text tvResult2
            if (newText.equals("-") || newText.endsWith("sqrt")) {
                newText = "";
            }
            //if pow sign is left at the last
            else if (newText.endsWith("^"))
                newText = newText.substring(0, newText.length() - 1);

            setResult(newText);
        }
    }

    private void calculateExpression() {
        if (tvResult.length() > 0) {
            expression = tvResult.getText().toString();
        }

        if (expression.length() == 0) {
            expression = "0.0";
        }

        tvRecent.setText(expression);
        String equation = tvResult.getText().toString();

        String ans = "";

        expression = expression.replace("÷", "/");
        expression = expression.replace("×", "*");

        DoubleEvaluator evaluator = new DoubleEvaluator();
        try {
            //evaluate the expression
            result = new ExtendedDoubleEvaluator().evaluate(expression);
            //insert expression and result in sqlite database if expression is valid and not 0.0

            if (result.isInfinite()) {
                MyUtil.showSnackBarFail(getContext(), btnEqual, "The answer is infinity.");
                String inf = DecimalFormatSymbols.getInstance().getInfinity();
                setResult(inf);
                ans = inf;
            } else {
                setResult("" + result);
                ans = result.toString();
            }

            if (!equation.equals(recentEquation)) {
                recentEquation = equation;
                MyHistory.add(getContext(), new HistoryDao(equation, ans));
                updateHistory();
            }

        } catch (Exception e) {
            MyUtil.showSnackBarFail(getContext(), btnEqual, "Invalid expression.");
            e.printStackTrace();
        }
    }


    private void setResult(String text) {
        tvResult.setText(text);
    }

    private void operationClicked(String op) {

        if (tvResult.length() > 0) {
            String text = tvResult.getText().toString();
            setResult(text + op);
            count = 0;
        }
    }
}
