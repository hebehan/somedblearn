package com.hebehan.dbutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hebehan.dbutil.bean.GreenDaoPerson;
import com.hebehan.dbutil.bean.Person;
import com.hebehan.dbutil.bean.SugarPerson;
import com.hebehan.dbutil.dbutils.BaseDao;
import com.hebehan.dbutil.dbutils.LogUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Hebe Han
 * @date 2018/6/8 15:28
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Person person = new Person(0,"张三",1.74f,18,new Date(),true);
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            BaseDao.getInstance().save(person);
        }
        LogUtil.d("performence","插入1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");
        time = System.currentTimeMillis();
        List<Person> personList = BaseDao.getInstance().findAll(Person.class);
        LogUtil.d("performence","查找1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");
        time = System.currentTimeMillis();
        for (Person p:personList){
            BaseDao.getInstance().delete(p);
        }
        LogUtil.d("performence","删除1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");

        time = System.currentTimeMillis();
        SugarPerson sugarPerson = new SugarPerson(0,"张三",1.74f,18,new Date(),true);
        for (int i = 0; i < 1000; i++) {
            sugarPerson.save();
        }
        LogUtil.d("performence","sugar添加1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");

        time = System.currentTimeMillis();
        List<SugarPerson> sugarPersonList = SugarPerson.find(SugarPerson.class,null,null);
        LogUtil.d("performence","sugar查询1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");

        time = System.currentTimeMillis();
        for (SugarPerson sugarPerson1:sugarPersonList){
            sugarPerson1.delete();
        }
        LogUtil.d("performence","sugar删除1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");

        time = System.currentTimeMillis();
        GreenDaoPerson greenDaoPerson = new GreenDaoPerson(null,"张三",1.74f,18,new Date(),true);
        for (int i = 0; i < 1000; i++) {
            DbUtilApplication.getDaoSession().getGreenDaoPersonDao().insert(greenDaoPerson);
        }
        LogUtil.d("performence","green插入1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");

        time = System.currentTimeMillis();
        List<GreenDaoPerson> greenDaoPersonList = DbUtilApplication.getDaoSession().getGreenDaoPersonDao().loadAll();
        LogUtil.d("performence","green查找1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");

        time = System.currentTimeMillis();
        for (GreenDaoPerson greenDaoPerson1:greenDaoPersonList){
            DbUtilApplication.getDaoSession().getGreenDaoPersonDao().delete(greenDaoPerson1);
        }
        LogUtil.d("performence","green删除1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");

    }
}
