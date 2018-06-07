package com.hebehan.dbutil;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hebehan.dbutil.bean.Person;
import com.hebehan.dbutil.dbutils.BaseDao;
import com.hebehan.dbutil.dbutils.LogUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {

    Button add;
    Button delete;
    Button update;
    Button query;
    RecyclerView recyclerView;
    DecimalFormat df = new DecimalFormat(".00");
    Map<Long,Long> idmap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        query= findViewById(R.id.query);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void add(){
        long result = BaseDao.getInstance().save(getRandomPerson(0));
        idmap.put(result,result);
        LogUtil.d("test add",result>0?"add success id = "+result:"add fail");
    }

    public void delete(){
        Long key=0l;
        for (Long tmp:idmap.keySet()){
            key = tmp;
            break;
        }
        idmap.remove(key);
        LogUtil.d("test delete",BaseDao.getInstance().delete(new Person(key.intValue()))>0?"delete id = "+key+" success":"delete fail");
    }

    public void update(){
        Long key=0l;
        for (Long tmp:idmap.keySet()){
            key = tmp;
            break;
        }
        LogUtil.d("test delete",BaseDao.getInstance().update(getRandomPerson(key.intValue()))>0?"update id = "+key+"success":"add fail");
    }

    public void findAll(){

    }

    public Person getRandomPerson(Integer id){
       return new Person(id,"test"+new Random().nextInt(100),Float.valueOf(df.format(new Random().nextDouble()+1)),new Random().nextInt(60),new Date(),new Random().nextInt(100)%2==0);
    }

    public class DbAdapter extends RecyclerView.Adapter<PersonHolder>{
        List<Person> personList = new ArrayList<>();

        public void DbAdapter(List<Person> personList){
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
            holder.id.setText(person.getId());
            holder.name.setText(person.getName());
            holder.height.setText(person.getHeight()+"");
            holder.age.setText(person.getAge());
            holder.isAudlt.setText(person.isAdult()+"");
            holder.date.setText(person.getDate()+"");

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
