package com.benznestdeveloper.calculatorstory.calculatorstory.dao;

import java.util.Date;

/**
 * Created by benznest on 26-Jun-17.
 */

public class ConstantDao {
    private long id;
    private String name;
    private String value;
    private String unit;

    public ConstantDao() {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        if(unit == null){
            unit = "";
        }
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
