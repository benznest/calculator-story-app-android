package com.benzneststudios.calculatorstory.calculatorstory.dao;

import java.util.Date;

/**
 * Created by benznest on 26-Jun-17.
 */

public class EquationDao {
    private long id;
    private String name;
    private String equation;

    public EquationDao() {
        this.id = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }
}
