package com.sarker.ereg.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;


    public DBManager(Context c) {
        context = c;
    }


    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String semester,String name, String sID, String sec, String course, String status, String date,String time, byte[] img) {

        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.SEMESTER, semester);
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.SID, sID);
        contentValue.put(DatabaseHelper.SECTION, sec);
        contentValue.put(DatabaseHelper.COURSE, course);
        contentValue.put(DatabaseHelper.STATUS, status);
        contentValue.put(DatabaseHelper.DATE, date);
        contentValue.put(DatabaseHelper.TIME, time);
        contentValue.put(DatabaseHelper.IMAGE, img);

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);

    }

    public Cursor fetch() {

        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.SID,DatabaseHelper.DATE, DatabaseHelper.STATUS,DatabaseHelper.SEMESTER, DatabaseHelper.SECTION, DatabaseHelper.COURSE,DatabaseHelper.TIME,DatabaseHelper.IMAGE };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }

    public int update(long _id, String semester,String name, String sID, String sec, String course, String status, String date,String time,byte[] img) {

        ContentValues contentValue = new ContentValues();

        contentValue.put(DatabaseHelper.SEMESTER, semester);
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.SID, sID);
        contentValue.put(DatabaseHelper.SECTION, sec);
        contentValue.put(DatabaseHelper.COURSE, course);
        contentValue.put(DatabaseHelper.STATUS, status);
        contentValue.put(DatabaseHelper.DATE, date);
        contentValue.put(DatabaseHelper.TIME, time);
        contentValue.put(DatabaseHelper.IMAGE, img);

        int i = database.update(DatabaseHelper.TABLE_NAME, contentValue, DatabaseHelper._ID + " = " + _id, null);
        return i;

    }

    public void delete(long _id) {

        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);


    }

    public Cursor SearchData(String search){

        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.SID,DatabaseHelper.DATE, DatabaseHelper.STATUS,DatabaseHelper.SEMESTER, DatabaseHelper.SECTION, DatabaseHelper.COURSE,DatabaseHelper.TIME,DatabaseHelper.IMAGE };


        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns,
                DatabaseHelper.SID + " LIKE '%"+search+"%'" ,
                null, null, null,null );
        return cursor;

    }

}
