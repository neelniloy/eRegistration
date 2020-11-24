package com.sarker.ereg.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {


    // Table Name
    public static final String TABLE_NAME = "StudentDetails";

    // Table columns
    public static final String _ID = "_id";
    public static final String SEMESTER = "semester";
    public static final String DEPARTMENT = "department";
    public static final String NAME = "name";
    public static final String SID = "sID";
    public static final String SECTION = "section";
    public static final String COURSE = "course";
    public static final String STATUS = "status";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String IMAGE = "image";


    // Database Information
    static final String DB_NAME = "EREG.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SEMESTER + " TEXT, " + DEPARTMENT + " TEXT , " + NAME+ " TEXT,  " + SID
            + " TEXT,  " + SECTION + " TEXT,  " + COURSE + " TEXT,  " + STATUS + " TEXT,  " + DATE + " TEXT,  " + TIME + " TEXT,  " + IMAGE + " BLOB);";

    private Context context;


    public DatabaseHelper(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            //Toast.makeText(context, "On Create is called ", Toast.LENGTH_SHORT).show();
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: "+e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        try {
            //Toast.makeText(context, "On Upgrade is called ", Toast.LENGTH_SHORT).show();

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: "+e, Toast.LENGTH_SHORT).show();
        }
    }
}
