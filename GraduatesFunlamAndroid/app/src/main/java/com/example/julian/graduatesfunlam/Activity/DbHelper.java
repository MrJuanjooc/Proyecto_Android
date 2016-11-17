package com.example.julian.graduatesfunlam.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JULIAN on 7/10/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = DbHelper.class.getSimpleName();
    public  static  final  String DB_NAME = "myapp.db";
    public  static  final int DB_VERSION = 1;

    public  static  final  String GRADUATE_TABLE = "graduate";
    public  static  final  String COLUMN_ID = "_id";
    public  static  final  String  COLUMN_EMAIL = "email";
    public  static  final  String  COLUMN_PASS = "password";

    /*
    CREATE TABLE GRADUATES FUNLAM
     */
    public static final String CREATE_TABLE_GRADUATES = "CREATE TABLE " + GRADUATE_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT);";

    public DbHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GRADUATES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("DROP TABLE IF EXISTS" + GRADUATE_TABLE);
        onCreate(db);
    }

    public  void addGraduate(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, password);

        long id = db.insert(GRADUATE_TABLE, null, values);
        db.close();

        Log.d(TAG, "Graduate inserted" + id);
    }

    public  boolean getGraduate(String email, String password){
        String selectQuery = "select * from " + GRADUATE_TABLE + " where " +
                COLUMN_EMAIL + " = " + "'"+email+"'" + " and " + COLUMN_PASS + " = " + "'"+password+"'";

        SQLiteDatabase db =  this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount()> 0){
            return  true;
        }
        cursor.close();
        db.close();
        return false;
    }
}
