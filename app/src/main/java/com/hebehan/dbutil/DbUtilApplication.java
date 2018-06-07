package com.hebehan.dbutil;

import android.app.Application;

import com.hebehan.dbutil.dbutils.BdToolDBHelper;

/**
 * @author Hebe Han
 * @date 2018/6/7 12:52
 */
public class DbUtilApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BdToolDBHelper.init(this,"person");
    }
}
