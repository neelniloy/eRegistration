package com.sarker.ereg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sarker.ereg.database.DBManager;

public class MainActivity extends AppCompatActivity {

    private Button addStudent,viewStudent;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_main);

        addStudent = findViewById(R.id.add_student);
        viewStudent = findViewById(R.id.view_student);

        dbManager = new DBManager(this);
        dbManager.open();

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent n = new Intent(MainActivity.this,AddStudent.class);
                startActivity(n);


            }
        });



        viewStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent n = new Intent(MainActivity.this,ViewStudentList.class);
                startActivity(n);

            }
        });


    }
}