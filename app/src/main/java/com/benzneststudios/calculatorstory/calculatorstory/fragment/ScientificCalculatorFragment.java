package com.benzneststudios.calculatorstory.calculatorstory.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.benzneststudios.calculatorstory.calculatorstory.CalculateFactorial;
import com.benzneststudios.calculatorstory.calculatorstory.DBHelper;
import com.benzneststudios.calculatorstory.calculatorstory.ExtendedDoubleEvaluator;
import com.benzneststudios.calculatorstory.calculatorstory.MyApplication;
import com.benzneststudios.calculatorstory.calculatorstory.MyCalculatorOptionManager;
import com.benzneststudios.calculatorstory.calculatorstory.MyConstantApp;
import com.benzneststudios.calculatorstory.calculatorstory.MyHistory;
import com.benzneststudios.calculatorstory.calculatorstory.MyOperatorOptionManager;
import com.benzneststudios.calculatorstory.calculatorstory.MyUtil;
import com.benzneststudios.calculatorstory.calculatorstory.R;
import com.benzneststudios.calculatorstory.calculatorstory.adapter.MyHistoryAdapter;
import com.benzneststudios.calculatorstory.calculatorstory.dao.ConstantDao;
import com.benzneststudios.calculatorstory.calculatorstory.dao.EquationDao;
import com.benzneststudios.calculatorstory.calculatorstory.dao.HistoryDao;
import com.benzneststudios.calculatorstory.calculatorstory.dialog.HistoryDialog;
import com.benzneststudios.calculatorstory.calculatorstory.dialog.constant.ChooseConstantDialog;
import com.benzneststudios.calculatorstory.calculatorstory.dialog.constant.CreateConstantDialog;
import com.benzneststudios.calculatorstory.calculatorstory.dialog.equation.ChooseEquationDialog;
import com.benzneststudios.calculatorstory.calculatorstory.dialog.equation.CreateEquationDialog;
import com.benzneststudios.calculatorstory.calculatorstory.view.MyRowHistoryView;

import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScientificCalculatorFragment extends Fragment {

    private TextView tvResult;
    private TextView tvRecent;
    private int count = 0;
    private String expression = "";
    private String text = "";
    private Double result = 0.0;
    private DBHelper dbHelper;
    private Button mode, btnToggle, square, xpowy, log, sin, cos, tan, sqrt, fact;
    private Button btnClear, btnBackspace, btnEqual, btnDot, btnPosneg, btnPi;
    private Button btnOpenBracket, btnCloseBracket;
    private Button[] btnNumber;
    private Button[] btnOperator;
    private TextView tvMore;
    private int toggleMode = 1;
    private int angleMode = 1;

    // For tablet.
    private TextView tvNoData;
    private ListView listViewHistory;
    private MyHistoryAdapter historyAdapter;
    private String recentEquation = "";
    private boolean showHistory = true;
    private ImageView imgToggleHistory;
    private FrameLayout flHistoryContainer;

    public static ScientificCalculatorFragment newInstance() {
        Bundle args = new Bundle();

        ScientificCalculatorFragment fragment = new ScientificCalculatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ScientificCalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scienctific_calculator, container, false);
        initInstance(v);
        initInstanceForTablet(v);
        getActivity().setTitle(R.string.sciCal);
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

            toggleMode = savedInstanceState.getInt(MyConstantApp.KEY_TOGGLE_MODE);
            btnToggle.setTag(toggleMode);

            angleMode = savedInstanceState.getInt(MyConstantApp.KEY_ANGLE_MODE);
            mode.setTag(angleMode);

            processToggle();
            processMode();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MyConstantApp.KEY_RESULT_1, tvResult.getText().toString());
        outState.putInt(MyConstantApp.KEY_TOGGLE_MODE, toggleMode);
        outState.putInt(MyConstantApp.KEY_ANGLE_MODE, angleMode);
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

        btnOperator = new Button[4];
        btnOperator[0] = (Button) v.findViewById(R.id.plus);
        btnOperator[1] = (Button) v.findViewById(R.id.minus);
        btnOperator[2] = (Button) v.findViewById(R.id.multiply);
        btnOperator[3] = (Button) v.findViewById(R.id.divide);

        for (int i = 0; i < btnOperator.length; i++) {
            final int finalI = i;
            btnOperator[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyUtil.vibrate();
                    operationClicked(btnOperator[finalI].getText().toString());
                }
            });
        }


        btnToggle = (Button) v.findViewById(R.id.toggle);
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyUtil.vibrate();
                processToggle();
            }
        });


        mode = (Button) v.findViewById(R.id.mode);
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyUtil.vibrate();
                processMode();
            }
        });


        square = (Button) v.findViewById(R.id.square);
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processSquare();
            }
        });

        xpowy = (Button) v.findViewById(R.id.xpowy);
        xpowy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processXpowy();
            }

        });

        log = (Button) v.findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processLog();
            }
        });

        sin = (Button) v.findViewById(R.id.sin);
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processSin(sin.getText().toString());
            }
        });

        cos = (Button) v.findViewById(R.id.cos);
        cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processCos(cos.getText().toString());
            }
        });

        tan = (Button) v.findViewById(R.id.tan);
        tan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processTan(tan.getText().toString());
            }
        });

        sqrt = (Button) v.findViewById(R.id.sqrt);
        sqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processSqrt();
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

        btnPosneg = (Button) v.findViewById(R.id.posneg);
        btnPosneg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processPosneg();
            }
        });

        btnPi = (Button) v.findViewById(R.id.pi);
        btnPi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processPi();
            }
        });

        fact = (Button) v.findViewById(R.id.factorial);
        fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processFactorial();
            }
        });

        btnBackspace = (Button) v.findViewById(R.id.backSpace);
        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processBackspace();
            }
        });

        btnEqual = (Button) v.findViewById(R.id.equal);
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.vibrate();
                processEqual();
            }
        });

        dbHelper = new DBHelper(getContext());


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
        setResult("");

        //tags to change the mode from degree to radian and vice versa
        mode.setTag(1);
        //tags to change the names of the buttons performing different operations
        btnToggle.setTag(1);

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
                    openSaveConstant();
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

    private void openSaveConstant() {
        CreateConstantDialog dialog = new CreateConstantDialog(getContext());
        dialog.setOnConstantListener(new CreateConstantDialog.OnConstantListener() {
            @Override
            public void onConstantCreated(ConstantDao c) {
                MyUtil.showSnackBarSuccess(getContext(), tvMore, R.string.done);
            }
        });
        dialog.show();
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

    private void processPi() {
        setResult(tvResult.getText() + "π");
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

    private void openOptionDialog(final String value, final boolean isBeforeEvaluation) {
        String[] str = MyOperatorOptionManager.getCustomOptionName(value);
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
                text = tvResult.getText().toString();
                if (choice[0] == MyOperatorOptionManager.OPTION_CUSTOM_BRING_ALL) {
                    if (isBeforeEvaluation) {
                        setResult(value + "(" + text + ")");
                    } else {
                        setResult("(" + text + ")" + value);
                    }
                } else {
                    setResult(text + value);
                }
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    private void processPosneg() {
        if (tvResult.length() != 0) {
            String s = tvResult.getText().toString();
            char arr[] = s.toCharArray();
            if (arr[0] == '-')
                setResult(s.substring(1, s.length()));
            else
                setResult("-" + s);
        }
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

    private void processEqual() {
                    /*for more knowledge on DoubleEvaluator and its tutorial go to the below link
                http://javaluator.sourceforge.net/en/home/*/
        if (tvResult.length() > 0) {
            expression = tvResult.getText().toString();
            tvRecent.setText(expression);

            if (expression.contains("π")) {
                expression = addOperatorBeforePi(expression);
            }

            expression = expression.replace("÷", "/");
            expression = expression.replace("×", "*");
            expression = expression.replace("π", "(pi)");

            if (angleMode == 1) {
                expression = expression.replace("sin(", "sind(");
                expression = expression.replace("cos(", "cosd(");
                expression = expression.replace("tan(", "tand(");

                expression = expression.replace("sin\u207B\u00B9(", "asind(");
                expression = expression.replace("cos\u207B\u00B9(", "acosd(");
                expression = expression.replace("tan\u207B\u00B9(", "atand(");
            } else {
                expression = expression.replace("sin\u207B\u00B9(", "asin(");
                expression = expression.replace("cos\u207B\u00B9(", "acos(");
                expression = expression.replace("tan\u207B\u00B9(", "atan(");
            }

            Log.d("benznest log", "expression = " + expression);

        }

        if (expression.length() == 0) {
            expression = "0.0";
        }
        String equation = tvResult.getText().toString();
        String ans = "";

        try {
            //evaluate the expression
            result = new ExtendedDoubleEvaluator().evaluate(expression);
            //insert expression and result in sqlite database if expression is valid and not 0.0
            if (String.valueOf(result).equals("6.123233995736766E-17")) {
                result = 0.0;
                setResult(result + "");
                ans = result.toString();
            } else if (String.valueOf(result).equals("1.633123935319537E16")) {
                MyUtil.showSnackBarFail(getContext(), btnEqual, "The answer is infinity.");
                String inf = DecimalFormatSymbols.getInstance().getInfinity();
                setResult(inf);
                ans = inf;
            } else {
                setResult("" + result.toString());
                ans = result.toString();
            }

            Log.d("benznest log ", "Equation = " + equation);
            Log.d("benznest log ", "recentEquation = " + recentEquation);

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

    private String addOperatorBeforePi(String expression) {
        if (expression.length() == 0) {
            return "";
        }
        char[] str = expression.toCharArray();
        String result = "";
        for (int i = 1; i < str.length; i++) {
            result = result + str[i - 1];
            if (str[i] == 'π') {
                if (Character.isDigit(str[i - 1]) || str[i - 1] == ')') {
                    result = result + "*";
                } else {
                    //
                }
            }
        }
        result = result + str[str.length - 1];
        return result;
    }


    private void processBackspace() {
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
            //if tvResult edit text contains only - sign or sqrt or any other text functions
            // at last then clear the edit text tvResult
            if (newText.equals("-") || newText.endsWith("sqrt") || newText.endsWith("log") || newText.endsWith("ln")
                    || newText.endsWith("sin") || newText.endsWith("asin") || newText.endsWith("asind") || newText.endsWith("sinh")
                    || newText.endsWith("cos") || newText.endsWith("acos") || newText.endsWith("acosd") || newText.endsWith("cosh")
                    || newText.endsWith("tan") || newText.endsWith("atan") || newText.endsWith("atand") || newText.endsWith("tanh")
                    || newText.endsWith("cbrt")) {
                newText = "";
            }
            //if pow sign is left at the last or divide sign
            else if (newText.endsWith("^") || newText.endsWith("/"))
                newText = newText.substring(0, newText.length() - 1);
            else if (newText.endsWith("pi") || newText.endsWith("e^"))
                newText = newText.substring(0, newText.length() - 2);
            setResult(newText);
        }
    }

    private void processFactorial() {
        updateToggleMode();
        updateAngleMode();

        if (tvResult.length() != 0) {
            text = tvResult.getText().toString();
            if (toggleMode == 2) {
                setResult("(" + text + ")%");
            } else {
                String res = "";
                try {
                    CalculateFactorial cf = new CalculateFactorial();
                    int[] arr = cf.factorial((int) Double.parseDouble(String.valueOf(new ExtendedDoubleEvaluator().evaluate(text))));
                    int res_size = cf.getRes();
                    if (res_size > 20) {
                        for (int i = res_size - 1; i >= res_size - 20; i--) {
                            if (i == res_size - 2)
                                res += ".";
                            res += arr[i];
                        }
                        res += "E" + (res_size - 1);
                    } else {
                        for (int i = res_size - 1; i >= 0; i--) {
                            res += arr[i];
                        }
                    }
                    setResult(res);
                } catch (Exception e) {
                    if (e.toString().contains("ArrayIndexOutOfBoundsException")) {
                        MyUtil.showSnackBarFail(getContext(), btnEqual, "Result too big.");
                    } else
                        MyUtil.showSnackBarFail(getContext(), btnEqual, "Invalid expression.");
                    e.printStackTrace();
                }
            }
        }
    }

    private void processSqrt() {

        updateAngleMode();
        updateToggleMode();

        if (toggleMode == 1) {
            openOptionDialog("sqrt", true);
        } else if (toggleMode == 2) {
            openOptionDialog("cbrt", true);
        } else {
            openOptionDialog("1/", true);
        }
    }

    private void setResult(String text) {
        tvResult.setText(text);
    }

    private void processTan(String valueShow) {
        updateAngleMode();
        updateToggleMode();

        if (toggleMode == 1) {
            openOptionDialog("tan", true);
        } else if (toggleMode == 2) {
            openOptionDialog(valueShow, true);
        } else {
            openOptionDialog("tanh", true);
        }

    }

    private void processCos(String valueShow) {
        updateAngleMode();
        updateToggleMode();

        if (toggleMode == 1) {
            openOptionDialog("cos", true);
        } else if (toggleMode == 2) {
            openOptionDialog(valueShow, true);
        } else {
            openOptionDialog("cosh", true);
        }

    }

    private void processSin(String valueShow) {
        updateAngleMode();
        updateToggleMode();

        if (toggleMode == 1) {
            openOptionDialog("sin", true);
        } else if (toggleMode == 2) {
            openOptionDialog(valueShow, true);
        } else {
            openOptionDialog("sinh", true);
        }
    }

    private void processLog() {
        updateToggleMode();
        updateAngleMode();

        if (toggleMode == 2) {
            openOptionDialog("ln", true);
        } else {
            openOptionDialog("log", true);
        }
    }

    private void processXpowy() {
        updateToggleMode();
        updateAngleMode();

        if (toggleMode == 1) {
            openOptionDialog("^", false);
        } else if (toggleMode == 2) {
            openOptionDialog("10^", true);
        } else {
            openOptionDialog("e^", true);
        }
    }

    private void processSquare() {
        updateToggleMode();
        updateAngleMode();

        if (toggleMode == 2) {
            openOptionDialog("^3", false);
        } else {
            openOptionDialog("^2", false);
        }
    }

    private void processMode() {
        updateToggleMode();
        updateAngleMode();

        if (angleMode == 1) {
            mode.setTag(2);
            mode.setText(R.string.mode2);
        } else {
            mode.setTag(1);
            mode.setText(R.string.mode1);
        }
    }

    private void processToggle() {
        updateToggleMode();
        updateAngleMode();

        if (toggleMode == 1) {
            btnToggle.setTag(2);
            square.setText(R.string.cube);
            xpowy.setText(R.string.tenpow);
            log.setText(R.string.naturalLog);
            sin.setText(R.string.sininv);
            cos.setText(R.string.cosinv);
            tan.setText(R.string.taninv);
            sqrt.setText(R.string.cuberoot);
            fact.setText(R.string.Mod);
        } else if (toggleMode == 2) {
            btnToggle.setTag(3);
            square.setText(R.string.square);
            xpowy.setText(R.string.epown);
            log.setText(R.string.log);
            sin.setText(R.string.hyperbolicSine);
            cos.setText(R.string.hyperbolicCosine);
            tan.setText(R.string.hyperbolicTan);
            sqrt.setText(R.string.inverse);
            fact.setText(R.string.factorial);
        } else if (toggleMode == 3) {
            btnToggle.setTag(1);
            sin.setText(R.string.sin);
            cos.setText(R.string.cos);
            tan.setText(R.string.tan);
            sqrt.setText(R.string.sqrt);
            xpowy.setText(R.string.xpown);
        }
    }

    private void updateAngleMode() {
        angleMode = ((int) mode.getTag());
    }

    private void updateToggleMode() {
        toggleMode = (int) btnToggle.getTag();
    }

    private void operationClicked(String op) {
        setResult(tvResult.getText() + op);
    }

}
