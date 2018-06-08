package com.hebehan.dbutil.bean;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * @author Hebe Han
 * @date 2018/6/8 15:29
 */
public class SugarPerson extends SugarRecord {

    private Integer _id;
    private String name;
    private Float height;
    private Integer age;
    private Date date;
    private boolean isAdult;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public SugarPerson(Integer _id, String name, Float height, Integer age, Date date, boolean isAdult) {
        this._id = _id;
        this.name = name;
        this.height = height;
        this.age = age;
        this.date = date;
        this.isAdult = isAdult;
    }
}
