package com.bryanphang.sutd_digital_lock;

import android.content.Intent;
import android.database.Cursor;
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

public class HomeActivity extends AppCompatActivity {

    Button addAccess, btn_viewall;
    private SqliteHelper sqliteHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqliteHelper = new SqliteHelper(this);

        addAccess = findViewById(R.id.addAccess);
        addAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LockAccessActivity.class);
                startActivity(intent);
            }
        });
        btn_viewall = findViewById(R.id.btn_viewall);
        btn_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = sqliteHelper.getAllData();
                if(result.getCount() == 0){
                    //show message
                    showMessage("Error", "Nothing found");
                    return;
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
        });

    }
    //Viewing items stored in database
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setMessage(Message);
        builder.show();
    }
}
