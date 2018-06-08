package com.hebehan.dbutil.bean;

import com.hebehan.dbutil.annotation.Table;

/**
 * @author Hebe Han
 * @date 2018/6/8 14:59
 */
@Table("student")
public class Student extends Person {
    private String banji;

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public String getBanji() {
        return banji;
    }
}
