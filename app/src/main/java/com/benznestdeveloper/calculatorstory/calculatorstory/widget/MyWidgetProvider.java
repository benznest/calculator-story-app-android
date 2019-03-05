package com.benznestdeveloper.calculatorstory.calculatorstory.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.benznestdeveloper.calculatorstory.calculatorstory.ExtendedDoubleEvaluator;
import com.benznestdeveloper.calculatorstory.calculatorstory.MyConstantApp;
import com.benznestdeveloper.calculatorstory.calculatorstory.R;
import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.text.DecimalFormatSymbols;

/**
 * Created by benznest on 02-Jul-17.
 */

public class MyWidgetProvider extends AppWidgetProvider {

    public static String WIDGET_BUTTON_NUMBER = "WIDGET_BUTTON_NUMBER";
    public static String WIDGET_VALUE_CLEAR = "WIDGET_VALUE_CLEAR";
    public static String WIDGET_VALUE_REMOVE = "WIDGET_VALUE_REMOVE";
    public static String WIDGET_VALUE_EQUAL = "WIDGET_VALUE_EQUAL";
    public static String WIDGET_VALUE_POSNEG = "WIDGET_VALUE_POSNEG";

    public static RemoteViews rv;
    public static String expression = "";
    public static int value = 0;

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // loop each App Widget.
        for (int i = 0; i < appWidgetIds.length; i++) {
            int rows = 0;
            int columns = 0;
            int appWidgetId = appWidgetIds[i];

            rv = new RemoteViews(context.getPackageName(), R.layout.widget_standard_calculator);

            setOnClickButtonOnWidget(context, rv, R.id.num0, WIDGET_BUTTON_NUMBER, "0");
            setOnClickButtonOnWidget(context, rv, R.id.num1, WIDGET_BUTTON_NUMBER, "1");
            setOnClickButtonOnWidget(context, rv, R.id.num2, WIDGET_BUTTON_NUMBER, "2");
            setOnClickButtonOnWidget(context, rv, R.id.num3, WIDGET_BUTTON_NUMBER, "3");
            setOnClickButtonOnWidget(context, rv, R.id.num4, WIDGET_BUTTON_NUMBER, "4");
            setOnClickButtonOnWidget(context, rv, R.id.num5, WIDGET_BUTTON_NUMBER, "5");
            setOnClickButtonOnWidget(context, rv, R.id.num6, WIDGET_BUTTON_NUMBER, "6");
            setOnClickButtonOnWidget(context, rv, R.id.num7, WIDGET_BUTTON_NUMBER, "7");
            setOnClickButtonOnWidget(context, rv, R.id.num8, WIDGET_BUTTON_NUMBER, "8");
            setOnClickButtonOnWidget(context, rv, R.id.num9, WIDGET_BUTTON_NUMBER, "9");
            setOnClickButtonOnWidget(context, rv, R.id.dot, WIDGET_BUTTON_NUMBER, ".");
            setOnClickButtonOnWidget(context, rv, R.id.posneg, WIDGET_BUTTON_NUMBER, WIDGET_VALUE_POSNEG);

            setOnClickButtonOnWidget(context, rv, R.id.plus, WIDGET_BUTTON_NUMBER, "+");
            setOnClickButtonOnWidget(context, rv, R.id.minus, WIDGET_BUTTON_NUMBER, "-");
            setOnClickButtonOnWidget(context, rv, R.id.multiply, WIDGET_BUTTON_NUMBER, "×");
            setOnClickButtonOnWidget(context, rv, R.id.divide, WIDGET_BUTTON_NUMBER, "÷");

            setOnClickButtonOnWidget(context, rv, R.id.clear, WIDGET_BUTTON_NUMBER, WIDGET_VALUE_CLEAR);
            setOnClickButtonOnWidget(context, rv, R.id.backSpace, WIDGET_BUTTON_NUMBER, WIDGET_VALUE_REMOVE);
            setOnClickButtonOnWidget(context, rv, R.id.equal, WIDGET_BUTTON_NUMBER, WIDGET_VALUE_EQUAL);

            // setData widget.
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }

    private void setOnClickButtonOnWidget(Context context, RemoteViews rv, int viewId, String action, String value) {
//        Intent intent = new Intent(WIDGET_BUTTON_NUMBER);
//        intent.putExtra(MyConstantApp.KEY_WIDGET_BUTTON, value);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        rv.setOnClickPendingIntent(viewId, pendingIntent);

//        MyWidgetProvider.value = value;

        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        intent.putExtra(MyConstantApp.KEY_WIDGET_BUTTON, String.valueOf(value));
        PendingIntent pdi = PendingIntent.getBroadcast(context, viewId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(viewId, pdi);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        int[] appWidgetIds = {appWidgetId};
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("benznest log", "onReceive = " + intent.getAction());
        if (intent.getAction().equals(WIDGET_BUTTON_NUMBER)) {
            Log.d("benznest log", "onReceive = equal");
            String value = intent.getExtras().getString(MyConstantApp.KEY_WIDGET_BUTTON);
            if (value.equals(WIDGET_VALUE_CLEAR)) {
                MyWidgetProvider.expression = "";
                rv.setTextViewText(R.id.tv_result, "" + MyWidgetProvider.expression);
            } else if (value.equals(WIDGET_VALUE_REMOVE)) {
                MyWidgetProvider.expression = backSpace(MyWidgetProvider.expression);
                rv.setTextViewText(R.id.tv_result, "" + MyWidgetProvider.expression);
            } else if (value.equals(WIDGET_VALUE_EQUAL)) {
                rv.setTextViewText(R.id.tv_recent, "" + MyWidgetProvider.expression);

                MyWidgetProvider.expression = calculate(MyWidgetProvider.expression);
                rv.setTextViewText(R.id.tv_result, "" + MyWidgetProvider.expression);
            } else if (value.equals(WIDGET_VALUE_POSNEG)) {
                MyWidgetProvider.expression = posneg(MyWidgetProvider.expression);
                rv.setTextViewText(R.id.tv_result, "" + MyWidgetProvider.expression);
            } else {
                MyWidgetProvider.expression += "" + intent.getExtras().getString(MyConstantApp.KEY_WIDGET_BUTTON);
                rv.setTextViewText(R.id.tv_result, "" + MyWidgetProvider.expression);
            }
            AppWidgetManager.getInstance(context).updateAppWidget(getProvider(context), rv);
        }
    }

    public ComponentName getProvider(Context context) {
        ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
        return thisWidget;
    }

    public String posneg(String s){
        if (s.length() != 0) {
            char arr[] = s.toCharArray();
            if (arr[0] == '-')
                return s.substring(1, s.length());
            else
                return ("-" + s);
        }
        return s;
    }

    public String calculate(String expression) {
        expression = expression.replace("÷", "/");
        expression = expression.replace("×", "*");

        DoubleEvaluator evaluator = new DoubleEvaluator();
        try {
            //evaluate the expression
            Double result = new ExtendedDoubleEvaluator().evaluate(expression);
            //insert expression and result in sqlite database if expression is valid and not 0.0

            if (result.isInfinite()) {
                return DecimalFormatSymbols.getInstance().getInfinity();
            } else {
                return result.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private String backSpace(String str) {
        if (str.length() <= 0) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }
}
