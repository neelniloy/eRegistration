package com.sarker.ereg;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.sarker.ereg.adapter.StudentAdapter;
import com.sarker.ereg.database.DBManager;

public class ViewStudentList extends AppCompatActivity {



    private String current_user_id;
    private TextView empty;
    private ImageView back;
    private EditText search;
    private Handler handler = new Handler();

    private DBManager dbManager;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_view_student_list);

        dbManager = new DBManager(this);
        dbManager.open();


        back = findViewById(R.id.back);
        search = findViewById(R.id.search_field);
        empty  = findViewById(R.id.empty);
        recyclerView = findViewById(R.id.student_list_rv);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this, dbManager.fetch());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                if(search.getText().toString().length()>0){

                    String str = search.getText().toString();

                    adapter = new StudentAdapter(ViewStudentList.this, dbManager.SearchData(str));
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                    if (adapter.getItemCount() == 0)
                    {
                        empty.setVisibility(View.VISIBLE);
                    }else {
                        empty.setVisibility(View.GONE);
                    }


                }else {

                    empty.setVisibility(View.GONE);

                    adapter = new StudentAdapter(ViewStudentList.this, dbManager.fetch());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                }


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        if (adapter.getItemCount() == 0)
        {
            empty.setVisibility(View.VISIBLE);
            search.setVisibility(View.GONE);
        }else {
            empty.setVisibility(View.GONE);
            search.setVisibility(View.VISIBLE);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        dbManager.delete((long) viewHolder.itemView.getTag());
                        adapter.swapCursor(dbManager.fetch());
                        adapter.notifyDataSetChanged();

                    }
                };
                handler.postDelayed(runnable,3000);


                Snackbar snackbar = Snackbar
                        .make(recyclerView, "Item is deleted", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.WHITE)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                handler.removeCallbacks(runnable);
                                adapter.swapCursor(dbManager.fetch());
                                Snackbar snackbar1 = Snackbar.make(recyclerView, "Item is restored!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });

                snackbar.show();


            }
        }).attachToRecyclerView(recyclerView);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });





    }


}