package com.hebehan.dbutil.dbutils;

import java.util.List;
import java.util.Map;

/**
 * @author Hebe Han
 * @date 2018/6/7 13:54
 */
public class SqlMaker {

    public static String getCreatTableSql(Class<?> clazz){
        StringBuffer sqlbuild = new StringBuffer();
        sqlbuild.append("CREATE TABLE IF NOT EXISTS ");
        sqlbuild.append(Utils.getTableName(clazz));
        sqlbuild.append(" ( ");
        sqlbuild.append(Utils.getPrimaryKey(clazz));
        sqlbuild.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        Map<String,String> attrNameMap = Utils.getTMapAttrNameAndType(clazz);
        for (String key:attrNameMap.keySet()){
            if (key.endsWith(Utils.getPrimaryKey(clazz)))
                continue;
            String type = attrNameMap.get(key);
            switch (type){
                case ("class java.lang.String"):
                    sqlbuild.append(key+" TEXT,");
                    break;
                case("int"):
                case ("class java.lang.Integer"):
                case("long"):
                case ("class java.lang.Long"):
                    sqlbuild.append(key+" INTEGER,");
                    break;
                case ("float"):
                case ("class java.lang.Float"):
                case ("double"):
                case ("class java.lang.Double"):
                    sqlbuild.append(key+" REAL,");
                    break;
                case ("boolean"):
                case ("class java.lang.Boolean"):
                    sqlbuild.append(key+" NUMERIC,");
                    break;
                case ("java.util.Date"):
                case ("java.sql.Date"):
                    sqlbuild.append(key+" NUMERIC,");
                    break;
            }
        }
        sqlbuild.deleteCharAt(sqlbuild.length() - 1);
        sqlbuild.append(" )");
        LogUtil.sql(sqlbuild.toString());
        return sqlbuild.toString();
    }

    public static String getWhereClause(String clause){
        return clause + "=?";
    }

    public static String[] getWhereArgs(Object args){
        return new String[]{String.valueOf(args)};
    }
}
