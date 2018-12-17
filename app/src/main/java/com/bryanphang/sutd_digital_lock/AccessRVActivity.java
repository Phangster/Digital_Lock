package com.bryanphang.sutd_digital_lock;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class AccessRVActivity extends AppCompatActivity{

    Button addAccess, btn_viewall,btn_delete;
    protected BottomNavigationView bottomNavigationView;
    private SqliteHelper sqliteHelper;

    RecyclerView recyclerView;
    AccessAdapter sqLiteListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO 9.7 The standard code to fill the recyclerview with data
        recyclerView = findViewById(R.id.charaRecyclerView);
        //create instance of data source object
        sqliteHelper = SqliteHelper.createSqliteHelper(this);
        //link adapter and data source
        sqLiteListAdapter = new AccessAdapter(this, sqliteHelper);
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
            Intent intent = new Intent(this, BluetoothActivity.class);
            startActivity(intent);
        }

        if (id == R.id.go_add_access) {
            Intent intent = new Intent(this, AddAccessActivity.class);
            startActivity(intent);
        }

        if (id == R.id.my_locks) {
            Intent intent = new Intent(this, LockRVActivity.class);
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
                    buffer.append("Lock Code: "+result.getString(2) + "\n");
                    buffer.append("Name: "+result.getString(1) + "\n");
                    buffer.append("Time from: "+result.getString(3) + "\n");
                    buffer.append("Time to: "+result.getString(4) + "\n");
                }
                System.out.println(buffer.toString());

                //show all data
                showMessage("Data", buffer.toString());
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
