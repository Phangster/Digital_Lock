package com.bryanphang.sutd_digital_lock;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SQLiteListAdapter sqLiteListAdapter;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        //STRATEGY DESIGN PATTERN: BEHAVIOUR TO REGULATE HOW WIDGET FUNCTIONS IS DELEGATED TO LINEARLAYOUT MANAGER
        //TODO 9.7 The standard code to fill the recyclerview with data
        recyclerView = findViewById(R.id.charaRecyclerView);
        //create instance of data source object
        sqliteHelper = SqliteHelper.createSqliteHelper(this);
        //link adapter and data source
        sqLiteListAdapter = new SQLiteListAdapter(this, sqliteHelper);
        //set recyclerview adapter
        recyclerView.setAdapter(sqLiteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //TODO 9.8 Put in code to allow each recyclerview item to be deleted when swiped




        //TODO 9.9 attach the recyclerView to helper


    }
}
