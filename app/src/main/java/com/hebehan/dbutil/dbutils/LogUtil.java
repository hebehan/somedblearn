package com.hebehan.dbutil.dbutils;

import android.util.Log;

/**
 * @author Hebe Han
 * @date 2018/6/7 14:02
 */
public class LogUtil {

    public static boolean isDebug = true;

    public static void d(String tag,String message){
        if (isDebug)
            Log.d(tag,message);
    }

    public static void sql(String sql){
        d("showsql",sql);
    }
}
