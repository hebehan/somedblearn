package com.hebehan.dbutil.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Hebe Han
 * @date 2018/6/8 15:53
 */
@Entity
public class GreenDaoPerson {

    @Id(autoincrement = true)
    private Long zhujian;
    private String name;
    private Float height;
    private Integer age;
    private Date date;
    private boolean isAdult;
    @Generated(hash = 209104907)
    public GreenDaoPerson(Long zhujian, String name, Float height, Integer age,
            Date date, boolean isAdult) {
        this.zhujian = zhujian;
        this.name = name;
        this.height = height;
        this.age = age;
        this.date = date;
        this.isAdult = isAdult;
    }
    @Generated(hash = 403472575)
    public GreenDaoPerson() {
    }
    public Long getZhujian() {
        return this.zhujian;
    }
    public void setZhujian(Long zhujian) {
        this.zhujian = zhujian;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Float getHeight() {
        return this.height;
    }
    public void setHeight(Float height) {
        this.height = height;
    }
    public Integer getAge() {
        return this.age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public boolean getIsAdult() {
        return this.isAdult;
    }
    public void setIsAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

    
}
