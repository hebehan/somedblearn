package com.hebehan.dbutil.dbutils;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Hebe Han
 * @date 2018/6/6 21:21
 */
public class Utils {

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
            if (("serialversionuid creator $change").contains(name.toLowerCase()))
                continue;
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
    public static <T> List<Map<String,String>> getTListAttrNameAndType(Class<T> clasz){
        Field[] fields = clasz.getDeclaredFields();
        List<Map<String,String>> attrNameList = new LinkedList<>();
        for(int i=0 ; i<fields.length ; i++) {//遍历所有属性
            fields[i].setAccessible(true);
            String name = fields[i].getName();    //获取属性的名字
            if (("serialversionuid creator $change").contains(name.toLowerCase()))
                continue;
            Map<String,String> attrNames = new HashMap<>();
            String type = fields[i].getGenericType().toString();    //获取属性的类型
            attrNames.put(name,type);
            attrNameList.add(attrNames);
        }
        return attrNameList;
    }

    /**
     * 获取属性值
     * @param bean
     * @param name
     * @param <T>
     * @return
     */
    private static <T> Object getInvokeValue(T bean,String name){
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
            f.set(bean,value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
