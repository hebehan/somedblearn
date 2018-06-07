package com.hebehan.dbutil.bean;

import com.hebehan.dbutil.annotation.Table;

import java.util.Date;

/**
 * @author Hebe Han
 * @date 2018/6/7 12:11
 */
@Table("person")
public class Person {
    private Integer id;
    private String name;
    private Float height;
    private Integer age;
    private Date date;
    private boolean isAdult;

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



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Person(Integer id, String name, Float height, Integer age, Date date, boolean isAdult) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.age = age;
        this.date = date;
        this.isAdult = isAdult;
    }

    public Person(Integer id) {
        this.id = id;
    }
    public Person() {
    }
}
