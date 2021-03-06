package com.bryanphang.sutd_digital_lock;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Random;

public class AddAccessActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

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

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAccessActivity.this,AddAccessActivity.this,
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAccessActivity.this,AddAccessActivity.this,
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
                String password = random();
                sqliteHelper.addAccess(new Access(null, Name, Locknum, Datetimefrom, Datetimeto, password));
                Toast.makeText(AddAccessActivity.this, "New Access Added", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddAccessActivity.this, AccessRVActivity.class);
                startActivity(intent);

            }
        });
    }

    //random String generator of 10 characters
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddAccessActivity.this, AddAccessActivity.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        showTime(hourOfDay, minute, year, month+1, dayOfMonth);

    }

    private void showTime(int hour, int min, int year, int month, int day){
        if(change==true) {
            datetimeFrom.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year).append(" ").append(hour).append(":")
                    .append(min));
        }if(change==false){
            datetimeTo.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year).append(" ").append(hour).append(":")
                    .append(min));
        }
    }

}
