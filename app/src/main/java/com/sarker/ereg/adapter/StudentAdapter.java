package com.sarker.ereg.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sarker.ereg.R;
import com.sarker.ereg.StudentDetails;
import com.sarker.ereg.database.DatabaseHelper;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private Context sContext;
    private Cursor sCursor;

    public StudentAdapter(Context context, Cursor cursor) {
        sContext = context;
        sCursor = cursor;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Name,ID,Date,Status;
        public ImageView Pic;

        public ViewHolder(View itemView) {

            super(itemView);

            Name = itemView.findViewById(R.id.full_name);
            ID = itemView.findViewById(R.id.sID);
            Date = itemView.findViewById(R.id.date);
            Status = itemView.findViewById(R.id.status);
            Pic = itemView.findViewById(R.id.stu_pic);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(sContext);
        View view = inflater.inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!sCursor.moveToPosition(position)) {
            return;
        }
        final String name = sCursor.getString(sCursor.getColumnIndex(DatabaseHelper.NAME));
        final String id = sCursor.getString(sCursor.getColumnIndex(DatabaseHelper.SID));
        final String sec = sCursor.getString(sCursor.getColumnIndex(DatabaseHelper.SECTION));
        final String course = sCursor.getString(sCursor.getColumnIndex(DatabaseHelper.COURSE));
        final String sem = sCursor.getString(sCursor.getColumnIndex(DatabaseHelper.SEMESTER));
        final String dept = sCursor.getString(sCursor.getColumnIndex(DatabaseHelper.DEPARTMENT));
        final String time = sCursor.getString(sCursor.getColumnIndex(DatabaseHelper.TIME));
        final String date = sCursor.getString(sCursor.getColumnIndex(DatabaseHelper.DATE));
        final String status = sCursor.getString(sCursor.getColumnIndex(DatabaseHelper.STATUS));
        final long key = sCursor.getLong(sCursor.getColumnIndex(DatabaseHelper._ID));
        final byte[] img = sCursor.getBlob(sCursor.getColumnIndex(DatabaseHelper.IMAGE));



        if (img!=null){
            holder.Pic.setImageBitmap(BitmapFactory.decodeByteArray(img,0,img.length));
        }

        if (name.length()>20){
            holder.Name.setText(name.substring(0,19)+".");
        }else {
            holder.Name.setText(name);
        }


        holder.ID.setText(id);
        holder.Date.setText(date);

        holder.itemView.setTag(key);

        if (status.equals("Completed")){
            holder.Status.setText("● "+status);
            holder.Status.setTextColor(Color.parseColor("#19A119"));
        }else {
            holder.Status.setText("● "+status);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(sContext, StudentDetails.class);

                intent.putExtra("name",name);
                intent.putExtra("id",id);
                intent.putExtra("semester",sem);
                intent.putExtra("dept",dept);
                intent.putExtra("sec",sec);
                intent.putExtra("course",course);
                intent.putExtra("time",time);
                intent.putExtra("date",date);
                intent.putExtra("status",status);
                intent.putExtra("key",""+key);
                intent.putExtra("img",img);

                sContext.startActivity(intent);

            }
        });


    }
    @Override
    public int getItemCount() {
        return sCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (sCursor != null) {
            sCursor.close();
        }
        sCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

}
