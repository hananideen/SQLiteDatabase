package com.sql.sqliteexample;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by Hananideen on 12/8/2015.
 */
public class MyListActivity extends ListActivity {

    private String TAG = "dbase";
    private Cursor cursor;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//get the cursor to load into the list
        cursor = readDatabase();
//      prepare the parameters for the list
        String[] columns = {MyDbContractConstants.MyConstants.NAME, MyDbContractConstants.MyConstants.EMAIL};
        int[] to = {android.R.id.text1, android.R.id.text2};
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, columns, to, 0);
        setListAdapter(listAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //close database here?
        Log.i(TAG, "in MyListActivity onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "in MyListActivity onPause()");
//        close the cursor when the activity closes so we don't leak memory
        cursor.close();
    }

    /*gets all the records out of the database and puts them into a cursor*/
    private Cursor readDatabase() {
//        get the database
        MyDbOpenHelper dbHelper = new MyDbOpenHelper(this,
                MyDbContractConstants.MyConstants.DATABASE_NAME,
                null,MyDbContractConstants.MyConstants.DATABASE_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//prepare the parameters
        String[] projection = {MyDbContractConstants.MyConstants.KEY_ID
                , MyDbContractConstants.MyConstants.NAME
                , MyDbContractConstants.MyConstants.EMAIL
                , MyDbContractConstants.MyConstants.PHONE};
        String selection = null;
        String[] selectionArguments = null;
        String groupBy = null;
        String having = null;
        String sortOrder = null;
//        do the query
        Cursor cursor = db.query(MyDbContractConstants.MyConstants.DATABASE_TABLE,
                projection, selection, selectionArguments, groupBy, having, sortOrder);

        return cursor;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, "Selected item ID: " + id, Toast.LENGTH_SHORT).show();
    }
}
