package com.sarker.ereg;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddStudent extends AppCompatActivity {

    private static final int PICK_FROM_GALLERY = 1 ;
    private ImageView back,edit;
    private CircleImageView stuPic;
    private TextInputEditText Name, roll;
    private TextInputLayout semesterTextLayout,nameEditTextLayout, rollEditTextLayout;
    private Button btnAdd;
    private ProgressDialog progressDialog;
    private String user_id,status="",semester = " ", section= " ",takenCourse = " ";
    private LinearLayout linearLayout;
    private RadioButton inProgress, completed;
    private DBManager dbManager;
    private ArrayList<String> course= new ArrayList<String>();
    private Bitmap bitmap = null;
    private byte[] img = null;

    Uri contentURI,resultUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_add_student);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnAdd = findViewById(R.id.btn_add_student);
        edit = findViewById(R.id.edit_stu_pic);
        stuPic = findViewById(R.id.student_pic);


        Name = findViewById(R.id.et_student_name);
        roll = findViewById(R.id.et_roll);
        inProgress = findViewById(R.id.inProgress);
        completed = findViewById(R.id.completed);
        linearLayout = findViewById(R.id.add_check_box);

        semesterTextLayout = findViewById(R.id.text_input_layout);
        nameEditTextLayout = findViewById(R.id.editTextNameLayout);
        rollEditTextLayout = findViewById(R.id.editTextRollLayout);


        dbManager = new DBManager(this);
        dbManager.open();



        String[] Dept = new String[] {"Spring 2021", "Summer 2021", "Fall 2021"};

        final AutoCompleteTextView dropdown = findViewById(R.id.semesterDropdown);

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(
                        this,
                        R.layout.support_simple_spinner_dropdown_item,
                        Dept);
        dropdown.setAdapter(adapter);


        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                semester = adapter.getItem(position);
                
                String[] S = {""};
                int len = 0;

                linearLayout.removeAllViews();

                if (semester.equals("Spring 2021")){
                    S = new String[] {"CSE233:Object Oriented Programming II","CSE234:Object Oriented Programming II Lab","CSE231:Microprocessor, Embedded Systems and IoT", "CSE232:Microprocessor, Embedded Systems and IoT Lab", "CSE235:Numerical Methods","CSE236:Math for Computer Science","CSE237:Software Project IV"};
                     len = S.length;
                }
                else if (semester.equals("Summer 2021")){
//                    S = new String[] {"CSE233:Object Oriented Programming II","CSE234:Object Oriented Programming II Lab","CSE231:Microprocessor, Embedded Systems and IoT", "CSE232:Microprocessor, Embedded Systems and IoT Lab", "CSE235:Numerical Methods","CSE236:Math for Computer Science","CSE237:Software Project IV"};
//                    len = S.length;
                }
                else if (semester.equals("Fall 2021")){
//                    S = new String[] {"CSE233:Object Oriented Programming II","CSE234:Object Oriented Programming II Lab","CSE231:Microprocessor, Embedded Systems and IoT", "CSE232:Microprocessor, Embedded Systems and IoT Lab", "CSE235:Numerical Methods","CSE236:Math for Computer Science","CSE237:Software Project IV"};
//                    len = S.length;
                }

                for (int i = 0; i < len; i++) {

                    final CheckBox checkBox = new CheckBox(getApplicationContext());
                    checkBox.setText(S[i]);
                    checkBox.setTextSize(16);
                    checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#1C485C")));
                    final int finalI = i;
                    final String[] finalS = S;
                    
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            if (checkBox.isChecked()){

                                course.add(finalS[finalI]);

                            }else{

                                course.remove(finalS[finalI]);

                            }
                        }

                    });

                    linearLayout.addView(checkBox);
                }


            }
        });


        String[] Sec = new String[] {"O-1", "O-2", "O-3","O-4", "O-5", "O-6","O-7", "O-8", "O-9","O-10", "O-11", "O-12","O-13", "O-14", "O-15"};

        final AutoCompleteTextView dropdown1 = findViewById(R.id.sectionDropdown);

        final ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>(
                        this,
                        R.layout.support_simple_spinner_dropdown_item,
                        Sec);
        dropdown1.setAdapter(adapter1);


        dropdown1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                section = adapter1.getItem(position);

            }
        });




        edit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

              selectImage(AddStudent.this);

            }
        });




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = Name.getText().toString();
                String id = roll.getText().toString();


                if (semester.equals(" ")) {
                    semesterTextLayout.setError("*Semester required");
                    dropdown.requestFocus();

                }else if (name.isEmpty()) {
                    nameEditTextLayout.setError("*Name required");
                    Name.requestFocus();

                }

                else if (id.isEmpty()) {
                    rollEditTextLayout.setError("*ID required");
                    roll.requestFocus();
                }
                else if (!(inProgress.isChecked() || completed.isChecked() )) {

                    Toast.makeText(AddStudent.this, "Select Registration Status", Toast.LENGTH_SHORT).show();

                }
                else if (!(name.isEmpty() && id.isEmpty() && !(inProgress.isChecked() || completed.isChecked() ) )) {

                    progressDialog = new ProgressDialog(AddStudent.this);
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Registering...");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progressDialog.dismiss();

                            sendUserData();

                        }
                    },1500);




                } else {
                    progressDialog.dismiss();
                    Toast.makeText(AddStudent.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void sendUserData() {


        String name = Name.getText().toString();
        String id = roll.getText().toString();
        String saveCurrentDate, saveCurrentTime;

        if (inProgress.isChecked() ){

            status = "In Progress";

        }else if ( completed.isChecked()){
            status = "Completed";
        }

        for (String s : course)
        {
            takenCourse += s + "\n";
        }


        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calFordDate.getTime());


        if (bitmap!=null){

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            img= stream.toByteArray();

        }



        dbManager.insert(semester,name, id,section,takenCourse,status,saveCurrentDate,saveCurrentTime,img);


        progressDialog.dismiss();
        Toast.makeText(AddStudent.this, "Details Successfully Added", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(AddStudent.this, MainActivity.class));
        finish();


    }

    public static long getDateInMillis(String srcDate) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat(
                "hh:mm a");

        long dateInMillis = 0;
        try {
            Date date = desiredFormat.parse(srcDate);
            dateInMillis = date.getTime();
            return dateInMillis;
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }


    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PICK_FROM_GALLERY);

                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode==0){

                if (resultCode == RESULT_OK && data != null) {

                    bitmap = (Bitmap) data.getExtras().get("data");
                    stuPic.setImageBitmap(bitmap);


                }



            }else {

                if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && data.getData() != null) {
                    contentURI = data.getData();

                    CropImage.activity(contentURI)
                            .setAspectRatio(1,1)
                            .start(AddStudent.this);

                }

                if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK) {
                        resultUri = result.getUri();

                        Picasso.get().load(resultUri).fit().centerInside().into(stuPic);

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        Exception error = result.getError();
                    }
                }

        }
    }

}