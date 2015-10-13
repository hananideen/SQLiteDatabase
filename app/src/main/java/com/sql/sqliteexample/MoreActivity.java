package com.sql.sqliteexample;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Hananideen on 12/8/2015.
 */
public class MoreActivity extends Activity {
    private String TAG = "dbase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        Button buttonFindRecord = (Button) findViewById(R.id.buttonFind);
        buttonFindRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findRecord();
            }
        });

        Button buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRecord();
            }
        });

        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecord();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "in MoreActivity onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //close database here?
        Log.i(TAG, "in MoreActivity onDestroy()");
    }

    /*deletes a record matching a ID*/
    private void deleteRecord() {
//        first load a record so we know the record exists
        long rowId = insertRecord();
//        get the database
        MyDbOpenHelper dbHelper = new MyDbOpenHelper(this,
                MyDbContractConstants.MyConstants.DATABASE_NAME, null,
                MyDbContractConstants.MyConstants.DATABASE_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //      prepare the where and where argument strings
        String where = MyDbContractConstants.MyConstants.KEY_ID + " = ? ";
        String[] whereArguments = {String.valueOf(rowId)};
        //        delete the record matching the ID of the one we inserted
        int deletedRows = db.delete(
                MyDbContractConstants.MyConstants.DATABASE_TABLE
                , where, whereArguments);
//        display number of rows deleted in the log
        Log.i(TAG, "Number rows deleted: " + deletedRows);
    }

    /*updates a record matching a given name*/
    private void updateRecord() {
//        insert a record so we know it exists
        long rowId = insertRecord();
//      get the database
        MyDbOpenHelper dbHelper = new MyDbOpenHelper(this,
                MyDbContractConstants.MyConstants.DATABASE_NAME, null,
                MyDbContractConstants.MyConstants.DATABASE_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//create the content values to replace existing values
        ContentValues values = new ContentValues();
        values.put(MyDbContractConstants.MyConstants.NAME, "Roland");
        //      create the where and where arguments
        String where = MyDbContractConstants.MyConstants.NAME + " = ?";
        String[] whereArgs = {"Reginald"};
        //        do the update
        int numberRowsUpdated = db.update(
                MyDbContractConstants.MyConstants.DATABASE_TABLE,
                values, where, whereArgs);
//        display the number of updated rows in the log
        Log.i(TAG, "Number records updated: " + numberRowsUpdated);
    }

    /*finds a record matching a given name*/
    private void findRecord() {
//        insert a record so we know it exists
        long rowId = insertRecord();
        Log.i(TAG, "Row id of inserted record: " + rowId);
//      get the database
        MyDbOpenHelper dbHelper = new MyDbOpenHelper(this,
                MyDbContractConstants.MyConstants.DATABASE_NAME, null,
                MyDbContractConstants.MyConstants.DATABASE_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //set up the column values you want returned
        String[] projection = {MyDbContractConstants.MyConstants.KEY_ID
                , MyDbContractConstants.MyConstants.NAME
                , MyDbContractConstants.MyConstants.EMAIL
                , MyDbContractConstants.MyConstants.PHONE};
        //        set up the selection criteria
        String selection = MyDbContractConstants.MyConstants.NAME + " = ? ";
        String[] selectionArguments = {"Reginald"};
        //        set up remaining parameters
        String groupBy = null;
        String having = null;
        String sortOrder = null;
        //        do the query and get a cursor
        Cursor cursor = db.query(MyDbContractConstants.MyConstants.DATABASE_TABLE,
                projection, selection, selectionArguments, groupBy, having, sortOrder);

        //process the result and show the returned values in the log
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            String key = cursor.getString(0);
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String phone = cursor.getString(3);
            Log.i(TAG, key + " " + name + " " + email + " " + phone);
            cursor.moveToNext();
        }


    }

    /*inserts a dummy record so we know that there is an existing record when we query or transact the database*/
    private long insertRecord() {
        MyDbOpenHelper dbHelper = new MyDbOpenHelper(this,
                MyDbContractConstants.MyConstants.DATABASE_NAME, null,
                MyDbContractConstants.MyConstants.DATABASE_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MyDbContractConstants.MyConstants.NAME, "Reginald");
        values.put(MyDbContractConstants.MyConstants.EMAIL, "reggy@overthere.com");
        values.put(MyDbContractConstants.MyConstants.PHONE, "65219064");
//        return the row ID of the inserted record
        return db.insert(MyDbContractConstants.MyConstants.DATABASE_TABLE, null, values);
    }
}
