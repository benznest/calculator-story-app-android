package com.benzneststudios.calculatorstory.calculatorstory;

import android.content.Context;
import android.content.SharedPreferences;

import com.benzneststudios.calculatorstory.calculatorstory.dao.EquationDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by benznest on 26-Jun-17.
 */

public class MyEquation {
    private static final String SHARED_PREFERENCES_NAME = "CALCULATOR_STORY_EQUATION";

    private static SharedPreferences sp;

    private static void setContext(Context context) {
        if (context == null) {
            context = MyContextor.getInstance();
        }
        sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static final String KEY_EQUATION_LIST = "KEY_EQUATION_LIST";

    public static void addEquation(Context context, EquationDao eq) {
        setContext(context);

        ArrayList<EquationDao> listDebt = getEquationList(context);
        listDebt.add(0, eq);

        saveEquationList(context, listDebt);
    }

    public static void updateEquation(Context context, EquationDao eq) {
        setContext(context);

        ArrayList<EquationDao> listEquation = getEquationList(context);
        for (int i = 0; i < listEquation.size(); i++) {
            if (listEquation.get(i).getId() == eq.getId()) {
                listEquation.set(i, eq);
            }
        }

        saveEquationList(context, listEquation);
    }

    public static void saveEquationList(Context context, ArrayList<EquationDao> listEquation) {
        setContext(context);
        String json = new Gson().toJson(listEquation);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_EQUATION_LIST, json);
        editor.commit();
    }

    public static ArrayList<EquationDao> getEquationList(Context context) {
        setContext(context);
        String json = sp.getString(KEY_EQUATION_LIST, "");

        ArrayList<EquationDao> listEquation =
                new Gson().fromJson(json, new TypeToken<ArrayList<EquationDao>>() {
                }.getType());

        if (listEquation == null) {
            listEquation = new ArrayList<>();
        }
        return listEquation;
    }

    public static void remove(Context context, long id) {
        setContext(context);

        ArrayList<EquationDao> listEquation = getEquationList(context);
        for (int i = 0; i < listEquation.size(); i++) {
            if (listEquation.get(i).getId() == id) {
                listEquation.remove(i);
            }
        }

        saveEquationList(context, listEquation);
    }

    public static void removeAll(Context context) {
        setContext(context);

        ArrayList<EquationDao> listEquation = getEquationList(context);
        listEquation.clear();

        saveEquationList(context, listEquation);
    }
}

