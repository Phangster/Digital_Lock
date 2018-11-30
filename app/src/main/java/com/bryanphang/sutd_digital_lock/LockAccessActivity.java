package com.bryanphang.sutd_digital_lock;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

    TextView datetimeFrom;
    TextView datetimeTo;


    int dayOfMonth,month,year,hour,minute;
    boolean change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_access);
        btn_datetimePickerFrom = findViewById(R.id.btn_datetimePickerFrom);
        btn_datetimePickerTo = findViewById(R.id.btn_datetimePickerTo);

        datetimeFrom = findViewById(R.id.datetimeFrom);
        datetimeTo = findViewById(R.id.datetimeTo);

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
                        year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(LockAccessActivity.this, LockAccessActivity.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if(change==true){
            datetimeFrom.setText(dayOfMonth + "/" +
                    month + "/" +
                    year + "/" +"\n" +
                    "Time: " +
                    hourOfDay + "H " +
                    minute + "min");
        }if(change==false){
            datetimeTo.setText(dayOfMonth + "/" +
                    month + "/" +
                    year + "/" +"\n" +
                    "Time: " +
                    hourOfDay +"H " +
                    minute + "min");
        }

        System.out.println(String.valueOf(year));
        System.out.println(String.valueOf(month));
    }
}
