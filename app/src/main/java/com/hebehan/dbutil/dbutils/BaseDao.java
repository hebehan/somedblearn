package com.hebehan.dbutil.dbutils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.tomkey.commons.tools.DevUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T>{
    public static SQLiteDatabase read;
    public static SQLiteDatabase write;
    public String tableName;

    protected void init(String tableName){
        if (read == null)
            read = BdToolDBHelper.getInstance().getReadableDatabase();

        if (write == null)
            write = BdToolDBHelper.getInstance().getWritableDatabase();

        this.tableName = tableName;
    }


    public void insert(T t){
        write.insert(tableName,null, Utils.getTValues(t));
    }

    /**
     * 插入列表数据
     * @param lists
     */
    public void insert(List<T> lists){
        if (lists != null && lists.size() > 0){
            for (int i = 0; i < lists.size(); i++) {
                insert(lists.get(i));
            }
        }
    }

    /**
     * 清空表
     */
    public void cleanall(){
        write.delete(tableName,null,null);
    }

    /**
     * 删除多少条数据
     */
    public void clean(int count, String orderCol, String ASCORDESC){
        //默认用主键排序
        if (TextUtils.isEmpty(ASCORDESC) || !"ascdesc".contains(ASCORDESC.toLowerCase()))
            ASCORDESC = "ASC";
        if (TextUtils.isEmpty(orderCol))
            orderCol = "id";

        DevUtil.d("trackdb","DELETE FROM "+tableName+" WHERE id IN (SELECT id FROM "+tableName+" ORDER BY "+orderCol+" "+ASCORDESC+" LIMIT "+count+");");
        write.execSQL("DELETE FROM "+tableName+" WHERE id IN (SELECT id FROM "+tableName+" ORDER BY "+orderCol+" "+ASCORDESC+" LIMIT "+count+");");
    }

    /**
     * 属性名必须跟数据字段名一致
     * @param clasz
     * @return
     */
    public List<T> getList(Class<T> clasz){
        List<T> lists = new LinkedList<>();
        Cursor cursor = null;
        try {
            cursor = read.rawQuery("select * from "+tableName,null);
            List<Map<String,String>> nts = Utils.getTListAttrNameAndType(clasz);
            if (cursor.moveToFirst()){
                while (cursor.moveToNext()){
                    T bean = clasz.newInstance();
                    for (Map<String,String> map:nts){
                        for(String name:map.keySet()){
                            switch (map.get(name)){
                                case ("class java.lang.String"):
                                    Utils.setInvokeValue(bean,name,cursor.getString(cursor.getColumnIndex(name)));
                                    break;
                                case("int"):
                                case ("class java.lang.Integer"):
                                    Utils.setInvokeValue(bean,name,cursor.getInt(cursor.getColumnIndex(name)));
                                    break;
                                case("long"):
                                case ("class java.lang.Long"):
                                    Utils.setInvokeValue(bean,name,cursor.getLong(cursor.getColumnIndex(name)));
                                    break;
                                case("double"):
                                case ("class java.lang.Double"):
                                    Utils.setInvokeValue(bean,name,cursor.getDouble(cursor.getColumnIndex(name)));
                                    break;
                                case("float"):
                                case ("class java.lang.Float"):
                                    Utils.setInvokeValue(bean,name,cursor.getFloat(cursor.getColumnIndex(name)));
                                    break;
                            }
                        }
                    }
                    lists.add(bean);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
        }

        return lists;
    }


}
