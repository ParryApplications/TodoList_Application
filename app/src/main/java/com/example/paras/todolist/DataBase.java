package com.example.paras.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBase extends SQLiteOpenHelper
{

    private Context ctx;

    public DataBase(Context context) {
        super(context,Constants.DB_NAME ,null,Constants.DB_VERSION);
        this.ctx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String TODO_LIST = " CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY ,"
                + Constants.KEY_TITLE + " TEXT ," + Constants.KEY_STATUS + " TEXT ," + Constants.KEY_DES + " TEXT ," + Constants.KEY_DATE + " LONG );";
        db.execSQL(TODO_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(" DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    //add title

    public void addtitle(TODO todo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TITLE,todo.getTodo_title());
        values.put(Constants.KEY_STATUS,todo.getTodo_status() );
        values.put(Constants.KEY_DES , todo.getDescription());
        values.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,values);

    }

    //get all list of todo

    public List<TODO> getalllist()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        List<TODO> todoList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID, Constants.KEY_TITLE, Constants.KEY_STATUS, Constants.KEY_DES,
                Constants.KEY_DATE},null,null,null,null,Constants.KEY_DATE + " DESC");

        if(cursor.moveToFirst())
        {
            do {
               TODO todo = new TODO();
                todo.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                todo.setTodo_title(cursor.getString(cursor.getColumnIndex(Constants.KEY_TITLE)));
                todo.setTodo_status(cursor.getString(cursor.getColumnIndex(Constants.KEY_STATUS)));
                todo.setDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_DES)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String df = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());
                todo.setDate(df);

                todoList.add(todo);

            }while (cursor.moveToNext());
        }

        return todoList;
    }

    //update list

    public int updatelist(TODO todo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TITLE,todo.getTodo_title());
        values.put(Constants.KEY_STATUS,todo.getTodo_status());
        values.put(Constants.KEY_DES,todo.getDescription());
        values.put(Constants.KEY_DATE,java.lang.System.currentTimeMillis());

        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID +"=?",new String[]{String.valueOf(todo.getId())});
    }

    //delete list

    public void deletelist(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID + "=?",new String[]{String.valueOf(id)});
        db.close();
    }

    //list count
    public int countlist()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String countquerry = "SELECT * FROM " + Constants.TABLE_NAME;

        Cursor cursor = db.rawQuery(countquerry,null);

        return cursor.getCount();
    }
}
