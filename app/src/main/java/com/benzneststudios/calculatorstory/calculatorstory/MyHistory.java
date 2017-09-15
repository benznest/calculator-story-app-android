package com.benzneststudios.calculatorstory.calculatorstory;

import android.content.Context;
import android.content.SharedPreferences;

import com.benzneststudios.calculatorstory.calculatorstory.dao.HistoryDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by benznest on 26-Jun-17.
 */

public class MyHistory {
    private static final String SHARED_PREFERENCES_NAME = "CALCULATOR_STORY_HISTORY";

    private static SharedPreferences sp;

    private static void setContext(Context context) {
        if (context == null) {
            context = MyContextor.getInstance();
        }
        sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static final String KEY_HISTORY_LIST = "KEY_HISTORY_LIST";

    public static void add(Context context, HistoryDao eq) {
        setContext(context);

        ArrayList<HistoryDao> list = getHistoryList(context);
        list.add(0, eq);

        saveHistoryList(context, list);
    }

    public static void update(Context context, HistoryDao eq) {
        setContext(context);

        ArrayList<HistoryDao> list = getHistoryList(context);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == eq.getId()) {
                list.set(i, eq);
            }
        }

        saveHistoryList(context, list);
    }

    public static void saveHistoryList(Context context, ArrayList<HistoryDao> list) {
        setContext(context);
        String json = new Gson().toJson(list);

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_HISTORY_LIST, json);
        editor.commit();
    }

    public static ArrayList<HistoryDao> getHistoryList(Context context) {
        setContext(context);
        String json = sp.getString(KEY_HISTORY_LIST, "");

        ArrayList<HistoryDao> list =
                new Gson().fromJson(json, new TypeToken<ArrayList<HistoryDao>>() {
                }.getType());

        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public static void remove(Context context, long id) {
        setContext(context);

        ArrayList<HistoryDao> list = getHistoryList(context);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                list.remove(i);
            }
        }

        saveHistoryList(context, list);
    }

    public static void removeAll(Context context) {
        setContext(context);

        ArrayList<HistoryDao> list = getHistoryList(context);
        list.clear();

        saveHistoryList(context, list);
    }
}

