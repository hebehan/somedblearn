package com.hebehan.dbutil;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hebehan.dbutil.bean.Person;
import com.hebehan.dbutil.bean.Student;
import com.hebehan.dbutil.dbutils.BaseDao;
import com.hebehan.dbutil.dbutils.LogUtil;

import org.w3c.dom.ls.LSInput;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {
    public static final SimpleDateFormat SDF = new SimpleDateFormat("MM-dd HH:mm:ss");
    Button add;
    Button delete;
    Button update;
    Button query;
    Button all;
    RecyclerView recyclerView;
    DecimalFormat df = new DecimalFormat(".00");
    List<Person> list = new ArrayList<>();
    DbAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        query= findViewById(R.id.query);
        all= findViewById(R.id.all);
        recyclerView = findViewById(R.id.personrecycleview);

        adapter = new DbAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
                findAll();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(list.size() == 0?0:list.get(0).getId());
                findAll();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(list.size() == 0?0:list.get(0).getId());
                findAll();
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findOne(list.size() == 0?0:list.get(0).getId());
            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAll();
            }
        });

        all.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this,TestActivity.class));
                return true;
            }
        });

        findAll();

//        Student student = new Student();
//        student.setBanji("12");
//        BaseDao.getInstance().save(student);

//        Person person = getRandomPerson(0);
//        long time = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
//            BaseDao.getInstance().save(person);
//        }
//        LogUtil.d("performence","插入1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");
//        time = System.currentTimeMillis();
//        List<Person> personList = BaseDao.getInstance().findAll(Person.class);
//        LogUtil.d("performence","查找1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");
//        time = System.currentTimeMillis();
//        for (Person p:personList){
//            BaseDao.getInstance().delete(p);
//        }
//        LogUtil.d("performence","删除1000条数据耗时:"+(System.currentTimeMillis()-time)+"ms");

    }

    public void add(){
        long result = BaseDao.getInstance().save(getRandomPerson(0));
        LogUtil.d("test add",result>0?"add success id = "+result:"add fail");
    }

    public void delete(Integer id){
        LogUtil.d("test delete",BaseDao.getInstance().delete(new Person(id))>0?"delete id = "+id+" success":"delete fail");
    }

    public void update(Integer id){

        LogUtil.d("test delete",BaseDao.getInstance().update(getRandomPerson(id))>0?"update id = "+id+"success":"add fail");
    }

    public void findOne(Integer id){
        list.clear();
        Person person = BaseDao.getInstance().findById(new Person(id),Person.class);
        if (person != null)
            list.add(person);
        adapter.notifyDataSetChanged();
    }

    public void findAll(){
        list.clear();
        list.addAll(BaseDao.getInstance().findAll(Person.class));
        adapter.notifyDataSetChanged();
    }

    public Person getRandomPerson(Integer id){
       return new Person(id,"test"+new Random().nextInt(100),Float.valueOf(df.format(new Random().nextDouble()+1)),new Random().nextInt(60),new Date(),new Random().nextInt(100)%2==0);
    }

    public class DbAdapter extends RecyclerView.Adapter<PersonHolder>{
        List<Person> personList = new ArrayList<>();

        public DbAdapter(List<Person> personList){
            this.personList = personList;
        }


        @NonNull
        @Override
        public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            PersonHolder holder = new PersonHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.personitem,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
            Person person = personList.get(position);
            holder.id.setText(person.getId()+"");
            holder.name.setText(person.getName());
            holder.height.setText(person.getHeight()+"");
            holder.age.setText(person.getAge()+"");
            holder.isAudlt.setText(person.isAdult()+"");
            holder.date.setText(SDF.format(person.getDate()));
        }

        @Override
        public int getItemCount() {
            return personList==null?0:personList.size();
        }
    }

    public class PersonHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView name;
        TextView height;
        TextView age;
        TextView isAudlt;
        TextView date;
        public PersonHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            height = itemView.findViewById(R.id.height);
            age = itemView.findViewById(R.id.age);
            isAudlt = itemView.findViewById(R.id.isAudlt);
            date = itemView.findViewById(R.id.date);
        }
    }
}
