package com.sql.sqliteexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String[][] recordsArray = {{"Jack", "jack@sprat.com", "012345678"},
            {"Mary", "mary@here.com", "09876543"},
            {"Lucy", "lucy@there.com", "76512098"},
            {"Harry", "harry@sally.com", "76091264"},
            {"Lizzy", "lizzy@dizzy.com", "76108329"}};

    private String TAG = "dbase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAddData = (Button) findViewById(R.id.buttonAddData);
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                populateDatabase();
            }
        });

        Button buttonReadData = (Button) findViewById(R.id.buttonReadData);
        buttonReadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                starts the activity to display the list
                Intent intent = new Intent(MainActivity.this, MyListActivity.class);
                startActivity(intent);
            }
        });

        Button buttonMore = (Button) findViewById(R.id.buttonMore);
        buttonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "in MainActivity onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //close database here?
        Log.i(TAG, "in MainActivity onDestroy()");
    }

    /*loads the records from the recordsArray into the database - creates the dbase if
        necessary and updates it if version is higher*/
    private void populateDatabase() {

        MyDbOpenHelper dbHelper = new MyDbOpenHelper(this,
                MyDbContractConstants.MyConstants.DATABASE_NAME, null,
                MyDbContractConstants.MyConstants.DATABASE_VERSION);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //load 5 records from recordsArray
        for (int row = 0; row < recordsArray.length; row++) {
            ContentValues values = new ContentValues();
            for (int col = 0; col < recordsArray[row].length; col++) {
                values.put(MyDbContractConstants.MyConstants.NAME, recordsArray[row][0]);
                values.put(MyDbContractConstants.MyConstants.EMAIL, recordsArray[row][1]);
                values.put(MyDbContractConstants.MyConstants.PHONE, recordsArray[row][2]);
            }
            long insertedRowId = db.insert(MyDbContractConstants.MyConstants.DATABASE_TABLE, null, values);
            Log.i(TAG, "Row ID of record added: " + String.valueOf(insertedRowId));
        }
    }
}
