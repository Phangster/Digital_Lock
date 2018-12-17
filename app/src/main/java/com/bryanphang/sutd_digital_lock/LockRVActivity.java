package com.bryanphang.sutd_digital_lock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class LockRVActivity extends AppCompatActivity {
    Button btn_delete;
    protected BottomNavigationView bottomNavigationView;
    private SqliteHelper sqliteHelper;

    RecyclerView recyclerView;
    LockAdapter lockTableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_owner_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO 9.7 The standard code to fill the recyclerview with data
        recyclerView = findViewById(R.id.charaRecyclerView);
        //create instance of data source object
        sqliteHelper = SqliteHelper.createSqliteHelper(this);
        //link adapter and data source
        lockTableAdapter = new LockAdapter(this, sqliteHelper);
        //set recyclerview adapter
        recyclerView.setAdapter(lockTableAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sqliteHelper = new SqliteHelper(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lock_owner_display, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.go_unlock_page) {
            Intent intent = new Intent(this, AccessRVActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
