package com.sarker.ereg;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDetails extends AppCompatActivity {


    private String semester,name,id,sec,course,status,date,time,key,dept;
    private ImageView back,edit;
    private TextView Semester,Name,Id,Sec,Course,Status,Date,Time,Dept;
    private CircleImageView Pic;
    private byte [] img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_student_details);

        semester = getIntent().getStringExtra("semester");
        dept = getIntent().getStringExtra("dept");
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        sec = getIntent().getStringExtra("sec");
        course = getIntent().getStringExtra("course");
        status = getIntent().getStringExtra("status");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        key = getIntent().getStringExtra("key");
        img = getIntent().getByteArrayExtra("img");


        Name = findViewById(R.id.stu_name);
        Id = findViewById(R.id.stu_id);
        back = findViewById(R.id.back);
        Semester = findViewById(R.id.stu_sem);
        Sec = findViewById(R.id.stu_sec);
        Time = findViewById(R.id.stu_time);
        Date = findViewById(R.id.stu_date);
        Status = findViewById(R.id.stu_status);
        Course = findViewById(R.id.stu_course);
        edit = findViewById(R.id.edit);
        Pic = findViewById(R.id.student_profile);
        Dept = findViewById(R.id.stu_dept);

        Semester.setText(semester);
        Dept.setText(dept);
        Name.setText(name);
        Id.setText(id);
        Sec.setText(sec);
        Course.setText(course.trim());
        Status.setText(status);
        Time.setText(time);
        Date.setText(date);

        if (img!=null){
            Pic.setImageBitmap(BitmapFactory.decodeByteArray(img,0,img.length));
        }



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StudentDetails.this,EditDetails.class);

                intent.putExtra("name",name);
                intent.putExtra("id",id);
                intent.putExtra("semester",semester);
                intent.putExtra("dept",dept);
                intent.putExtra("sec",sec);
                intent.putExtra("course",course);
                intent.putExtra("time",time);
                intent.putExtra("date",date);
                intent.putExtra("status",status);
                intent.putExtra("key",key);
                intent.putExtra("img",img);

                startActivity(intent);

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });





    }


}