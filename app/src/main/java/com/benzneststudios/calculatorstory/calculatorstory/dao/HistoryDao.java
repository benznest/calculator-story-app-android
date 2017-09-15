package com.benzneststudios.calculatorstory.calculatorstory.dao;

import java.util.Date;

/**
 * Created by benznest on 26-Jun-17.
 */

public class HistoryDao {
    private long id;
    private String equation;
    private String answer;

    public HistoryDao(String equation, String answer) {
        this.id = new Date().getTime();
        this.equation = equation;
        this.answer = answer;
    }

    public HistoryDao() {
        this.id = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
