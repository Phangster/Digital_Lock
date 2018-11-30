package com.bryanphang.sutd_digital_lock;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.TimeZone;

public class LockAccessActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    Button btn_datetimePickerFrom;
    Button btn_datetimePickerTo;
    Button btn_submitAccess;

    TextView datetimeFrom;
    TextView datetimeTo;
    TextInputEditText findname;
    TextInputEditText locknum;


    int dayOfMonth,month,year,hour,minute;
    boolean change;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_access);
        sqliteHelper = new SqliteHelper(this);
        btn_datetimePickerFrom = findViewById(R.id.btn_datetimePickerFrom);
        btn_datetimePickerTo = findViewById(R.id.btn_datetimePickerTo);
        btn_submitAccess = findViewById(R.id.btn_submitAccess);

        datetimeFrom = findViewById(R.id.datetimeFrom);
        datetimeTo = findViewById(R.id.datetimeTo);
        findname = findViewById(R.id.textInputEditTextfindname);
        locknum = findViewById(R.id.textInputEditTextlocknum);

        btn_datetimePickerFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change = true;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(LockAccessActivity.this,LockAccessActivity.this,
                        year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        btn_datetimePickerTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change = false;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(LockAccessActivity.this,LockAccessActivity.this,
                        year, month+1, dayOfMonth);
                datePickerDialog.show();
            }
        });

        btn_submitAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name= findname.getText().toString();
                String Locknum = locknum.getText().toString();
                String Datetimefrom = datetimeFrom.getText().toString();
                String Datetimeto = datetimeTo.getText().toString();
                sqliteHelper.addAccess(new Access(null, Name, Locknum, Datetimefrom, Datetimeto));

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(LockAccessActivity.this, LockAccessActivity.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        showTime(hourOfDay, minute, year, month, dayOfMonth);

    }

    private void showTime(int hour, int min, int year, int month, int day){
        if(change==true) {
            datetimeFrom.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year).append(" ").append(hour).append("H")
                    .append(min).append("min"));
        }if(change==false){
            datetimeTo.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year).append(" ").append(hour).append("H")
                    .append(min).append("min"));
        }
    }

}