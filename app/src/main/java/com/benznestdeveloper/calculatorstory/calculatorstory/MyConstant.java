package com.benznestdeveloper.calculatorstory.calculatorstory;

import android.content.Context;
import android.content.SharedPreferences;

import com.benznestdeveloper.calculatorstory.calculatorstory.dao.ConstantDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by benznest on 26-Jun-17.
 */

public class MyConstant {
    private static final String SHARED_PREFERENCES_NAME = "CALCULATOR_STORY_CONSTANT";

    private static SharedPreferences sp;

    private static void setContext(Context context) {
        if (context == null) {
            context = MyContextor.getInstance();
        }
        sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static final String KEY_CONSTANT_LIST = "KEY_CONSTANT_LIST";

    public static void addConstant(Context context, ConstantDao constantDao) {
        setContext(context);

        ArrayList<ConstantDao> listConst = getConstantList(context);
        listConst.add(0, constantDao);

        saveConstantList(context, listConst);
    }

    public static void update(Context context, ConstantDao constantDao) {
        setContext(context);

        ArrayList<ConstantDao> listConst = getConstantList(context);
        for (int i = 0; i < listConst.size(); i++) {
            if (listConst.get(i).getId() == constantDao.getId()) {
                listConst.set(i, constantDao);
            }
        }

        saveConstantList(context, listConst);
    }

    public static void saveConstantList(Context context, ArrayList<ConstantDao> listConst) {
        setContext(context);
        String json = new Gson().toJson(listConst);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_CONSTANT_LIST, json);
        editor.commit();
    }

    public static ArrayList<ConstantDao> getConstantList(Context context) {
        setContext(context);
        String json = sp.getString(KEY_CONSTANT_LIST, "");

        ArrayList<ConstantDao> listConst =
                new Gson().fromJson(json, new TypeToken<ArrayList<ConstantDao>>() {
                }.getType());

        if (listConst == null) {
            listConst = new ArrayList<>();
        }
        return listConst;
    }

    public static void remove(Context context, long id) {
        setContext(context);

        ArrayList<ConstantDao> listConst = getConstantList(context);
        for (int i = 0; i < listConst.size(); i++) {
            if (listConst.get(i).getId() == id) {
                listConst.remove(i);
            }
        }

        saveConstantList(context, listConst);
    }

    public static void removeAll(Context context) {
        setContext(context);

        ArrayList<ConstantDao> listConst = getConstantList(context);
        listConst.clear();

        saveConstantList(context, listConst);
    }
}

