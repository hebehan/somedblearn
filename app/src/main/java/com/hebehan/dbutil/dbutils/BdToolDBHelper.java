package com.hebehan.dbutil.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.imdada.bdtool.constants.BDToolDBConstants;
import com.tomkey.commons.tools.DevUtil;

/**
 * bd工具数据库帮助
 * @author Hebe Han
 * @date 2018/3/23 11:44
 */
public class BdToolDBHelper extends SQLiteOpenHelper {

    /**
     * 目前bd工具先支持一个库，多库的可以做instance LIST
     */
    private static BdToolDBHelper instance;
    public static void init(Context context){
        if(instance == null){
            synchronized (BdToolDBHelper.class){
                if(instance == null){
                    instance = new BdToolDBHelper(context, BDToolDBConstants.DBName.BDTOOL_DB, 100);
                }
            }
        }
    }
    public static BdToolDBHelper getInstance(){
        return instance;
    }

    private BdToolDBHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 创建track表
         */
        db.execSQL("create table if not exists "+BDToolDBConstants.TableName.TABLE_TRACK+" (id integer primary key autoincrement, " +
                "trackId integer, " +
                "trackStatus integer," +
                "trackDes text," +
                "trackLat double, " +
                "trackLng double ," +
                "createTime long)");


//        /**
//         * 创建visitmodule表
//         */
//        db.execSQL("create table if not exists "+BDToolDBConstants.TableName.TABLE_VISITMODULE+" (id integer primary key autoincrement, " +
//                "supplierId long, " +
//                "bdId integer, " +
//                "taskId long, " +
//                "moudles text, " +
//                "taskStartTime long, " +
//                "taskEndTime long ," +
//                "createTime long," +
//                "updateTime long)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DevUtil.e("onUpgrade","oldVersion = "+oldVersion+" newVersion = "+newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        super.onDowngrade(db, oldVersion, newVersion);
        DevUtil.e("onDowngrade","oldVersion = "+oldVersion+" newVersion = "+newVersion);
        db.setVersion(newVersion);
    }
}
