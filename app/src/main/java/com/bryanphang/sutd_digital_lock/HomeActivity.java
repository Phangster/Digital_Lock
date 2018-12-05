package com.bryanphang.sutd_digital_lock;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity{

    Button addAccess, btn_viewall;
    protected BottomNavigationView bottomNavigationView;
    private SqliteHelper sqliteHelper;

    RecyclerView recyclerView;
    SQLiteListAdapter sqLiteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.appbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.access:
                        return true;
                    case R.id.keys:
                        return true;
                }
                return false;
            }
        });
        */

        //TODO 9.7 The standard code to fill the recyclerview with data
        recyclerView = findViewById(R.id.charaRecyclerView);
        //create instance of data source object
        sqliteHelper = SqliteHelper.createSqliteHelper(this);
        //link adapter and data source
        sqLiteListAdapter = new SQLiteListAdapter(this, sqliteHelper);
        //set recyclerview adapter
        recyclerView.setAdapter(sqLiteListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sqliteHelper = new SqliteHelper(this);

    }
    //Viewing items stored in database
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setMessage(Message);
        builder.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.go_add_access) {
            Intent intent = new Intent(this, LockAccessActivity.class);
            startActivity(intent);
        }

        if (id == R.id.view_all) {
            Cursor result = sqliteHelper.getAllData();
            if(result.getCount() == 0){
                //show message
                showMessage("Error", "Nothing found");
            }else{
                StringBuffer buffer = new StringBuffer();
                while (result.moveToNext()){
                    buffer.append("Id: "+result.getString(0) + "\n");
                    buffer.append("Locknumber: "+result.getString(1) + "\n");
                    buffer.append("Name: "+result.getString(2) + "\n");
                    buffer.append("Timefrom: "+result.getString(3) + "\n");
                    buffer.append("Timeto: "+result.getString(4) + "\n");
                }
                System.out.println(buffer.toString());

                //show all data
                showMessage("Data", buffer.toString());
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
