package com.hebehan.dbutil.dbutils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseDao{
    private static SQLiteDatabase read;
    private static SQLiteDatabase write;


    Map<String,Boolean> isTable = new HashMap<>();

    static BaseDao instace;
    public static BaseDao getInstance(){
        if (instace == null){
            instace = new BaseDao();
        }
        return instace;
    }
    static {
        if (read == null)
            read = BdToolDBHelper.getInstance().getReadableDatabase();

        if (write == null)
            write = BdToolDBHelper.getInstance().getWritableDatabase();
    }

    //===========================↓===↓===↓====↓=↓=============数据库方法==========↓======↓=====↓=========↓====================//

    public long save(Object entity) {
        checkTalbleExist(entity.getClass());
        return write.insert(Utils.getTableName(entity.getClass()),null,Utils.getTValues(entity));
    }

    /**
     * 根据id删除一条数据 实体主键不能为空
     * @param entity
     * @return
     */
    public int delete(Object entity) {
        checkTalbleExist(entity.getClass());
        return write.delete(Utils.getTableName(entity.getClass()),SqlMaker.getWhereClause(Utils.getPrimaryKey(entity.getClass())),SqlMaker.getWhereArgs(Utils.getInvokeValue(entity,Utils.getPrimaryKey(entity.getClass()))));
    }

    /**
     * 根据id更新一条数据 实体主键不能为空
     * @param entity
     * @return
     */
    public int update(Object entity){
        checkTalbleExist(entity.getClass());
        return write.update(Utils.getTableName(entity.getClass()),Utils.getTValues(entity),SqlMaker.getWhereClause(Utils.getPrimaryKey(entity.getClass())),SqlMaker.getWhereArgs(Utils.getInvokeValue(entity,Utils.getPrimaryKey(entity.getClass()))));
    }

    public <T> T findById(Object entity,Class<T> clazz){
        checkTalbleExist(clazz);
//        Cursor cursor = write.query(Utils.getTableName(clazz),new String[]{Utils.getPrimaryKey(clazz)},SqlMaker.getWhereClause(Utils.getPrimaryKey(clazz)),SqlMaker.getWhereArgs(Utils.getInvokeValue(entity,Utils.getPrimaryKey(clazz))),null,null,null);
        Cursor cursor = read.rawQuery("select * from "+Utils.getTableName(clazz)+" where "+ SqlMaker.getWhereClause(Utils.getPrimaryKey(clazz)),SqlMaker.getWhereArgs(Utils.getInvokeValue(entity,Utils.getPrimaryKey(entity.getClass()))));
        try {
            List<T> lists = Utils.getList(cursor,clazz);
            return lists.size()>0?lists.get(0):null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return null;
    }

    public <T> List<T> findAll(Class<T> clazz){
        checkTalbleExist(clazz);
        Cursor cursor = read.rawQuery("select * from "+Utils.getTableName(clazz),null);
        try {
            return Utils.getList(cursor,clazz);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return null;
    }









    //========================↑==↑=====↑===↑===↑======↑====↑====数据库方法=========↑=======↑==========↑======↑=======↑===========//










    //===================================================其他方法==============================================================//
    public boolean checkTalbleExist(Class<?> clazz){
        String tableName = Utils.getTableName(clazz);
        if (isTable.get(tableName) == null){
            Cursor cursor = null;
            try {
                String sql = "SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='"
                        + tableName + "' ";
                LogUtil.sql(sql);
                cursor = read.rawQuery(sql, null);
                if (cursor != null && cursor.moveToNext()) {
                    int count = cursor.getInt(0);
                    if (count > 0) {
                        isTable.put(tableName,true);
                        return true;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null)
                    cursor.close();
                cursor = null;
            }
            //表不存在 创建表

            try {
                write.execSQL(SqlMaker.getCreatTableSql(clazz));
            }catch (Exception e){
                e.printStackTrace();
            }


            return false;
        }else {
            return true;
        }
    }
}
