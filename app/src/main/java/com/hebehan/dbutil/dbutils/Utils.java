package com.hebehan.dbutil.dbutils;

import android.content.ContentValues;
import android.database.Cursor;

import com.hebehan.dbutil.annotation.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Hebe Han
 * @date 2018/6/6 21:21
 */
public class Utils {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 获取属性名+属性值
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> ContentValues getTValues(T bean){
        Field[] fields = bean.getClass().getDeclaredFields();
        ContentValues values = new ContentValues();
        for(int j=0 ; j<fields.length ; j++){//遍历所有属性
            fields[j].setAccessible(true);
            String name = fields[j].getName();    //获取属性的名字
//            if (("serialversionuid creator $change").contains(name.toLowerCase()))
//                continue;
            String type = fields[j].getGenericType().toString();    //获取属性的类型

            switch (type){
                case ("class java.lang.String"):
                    values.put(name, getInvokeValue(bean,name) == null?"":(String)getInvokeValue(bean,name));
                    break;
                case("int"):
                case ("class java.lang.Integer"):
                    values.put(name, getInvokeValue(bean,name) == null?0:(Integer) getInvokeValue(bean,name));
                    break;
                case("long"):
                case ("class java.lang.Long"):
                    values.put(name, getInvokeValue(bean,name) == null?0:(Long) getInvokeValue(bean,name));
                    break;
                case("float"):
                case ("class java.lang.Float"):
                    values.put(name, getInvokeValue(bean,name) == null?0:(Float) getInvokeValue(bean,name));
                    break;
                case("double"):
                case ("class java.lang.Double"):
                    values.put(name, getInvokeValue(bean,name) == null?0:(Double) getInvokeValue(bean,name));
                    break;
            }
        }
        return values;
    }

    /**
     * 获取属性名列表
     * @param clasz
     * @return
     */
    public static Map<String,String> getTMapAttrNameAndType(Class<?> clasz){
        Field[] fields = clasz.getDeclaredFields();
        Map<String,String> attrNameMap = new HashMap<>();
        for(int i=0 ; i<fields.length ; i++) {//遍历所有属性
            fields[i].setAccessible(true);
            String name = fields[i].getName();    //获取属性的名字
//            if (("serialversionuid creator $change").contains(name.toLowerCase()))
//                continue;
            String type = fields[i].getGenericType().toString();    //获取属性的类型
            attrNameMap.put(name,type);
        }
        return attrNameMap;
    }

    /**
     * 获取属性值
     * @param bean
     * @param name
     * @param <T>
     * @return
     */
    public static <T> Object getInvokeValue(T bean,String name){
        Method m = null;
        try {
            name = name.substring(0,1).toUpperCase()+name.substring(1); //将属性的首字符大写，方便构造get，set方法
            m = bean.getClass().getMethod("get"+name);
            return m.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置字段值
     * @param bean
     * @param name
     * @param value
     * @param <T>
     */
    public static <T> void setInvokeValue(T bean,String name,Object value){
        try {
            Field f = bean.getClass().getDeclaredField(name);
            f.setAccessible(true);
//            if (value instanceof Boolean){
//                f.set(bean,value);
//            }
//            else if (value instanceof java.util.Date || value instanceof java.sql.Date){
//                f.set(bean,((Date)value).getTime());
//            }
//            else {
//                f.set(bean,value);
//            }

            f.set(bean,value);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取表明(实体类的名字)
     * @param clazz
     * @return
     */
    public static String getTableName(Class<?> clazz){
        Table table = clazz.getAnnotation(Table.class);
        if(table == null || table.value().trim().length() == 0 ){
            //当没有注解的时候默认用类的名称作为表名,并把点（.）替换为下划线(_)
            return clazz.getName().replace('.', '_');
        }
        return table.value();
    }

    public static String getPrimaryKey(Class<?> clazz){
        return "id";
    }

    public static <T> List<T> getList(Cursor cursor,Class<T> clasz){
        List<T> lists = new LinkedList<>();
        if (cursor == null)
            return lists;
        try {
            Map<String,String> attrNameMap = getTMapAttrNameAndType(clasz);
            if (cursor.moveToFirst()){
                while (cursor.moveToNext()){
                    T bean = clasz.newInstance();
                    for (String name :attrNameMap.keySet()){
                        switch (attrNameMap.get(name)){
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
