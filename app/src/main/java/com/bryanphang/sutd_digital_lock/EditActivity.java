package com.bryanphang.sutd_digital_lock;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import java.util.Random;
import java.util.TimeZone;

public class EditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    Button btn_editdatetimePickerFrom;
    Button btn_editdatetimePickerTo;
    Button btn_editAccess;

    TextView originaldatetimeFrom;
    TextView originaldatetimeTo;
    TextInputEditText originalfindname;
    TextInputEditText originallocknum;

    int dayOfMonth,month,year,hour,minute;
    boolean change;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_edit);
        sqliteHelper = new SqliteHelper(this);
        btn_editdatetimePickerFrom = findViewById(R.id.btn_editdatetimePickerFrom);
        btn_editdatetimePickerTo = findViewById(R.id.btn_editdatetimePickerTo);
        btn_editAccess = findViewById(R.id.btn_editAccess);

        originaldatetimeFrom = findViewById(R.id.originaldatetimeFrom);
        originaldatetimeTo = findViewById(R.id.originaldatetimeTo);
        originalfindname = findViewById(R.id.textInputEditTextOriginalfindname);
        originallocknum = findViewById(R.id.textInputEditTextOriginallocknum);

        final String name = getIntent().getStringExtra(SQLiteListAdapter.name_entry);
        final String lockid = getIntent().getStringExtra(SQLiteListAdapter.lockid_entry);
        final String fromDate = getIntent().getStringExtra(SQLiteListAdapter.fromDate_entry);
        final String toDate = getIntent().getStringExtra(SQLiteListAdapter.toDate_entry);

        originalfindname.setHint(name);
        originallocknum.setHint(lockid);
        originaldatetimeFrom.setText(fromDate);
        originaldatetimeTo.setText(toDate);

        btn_editdatetimePickerFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change = true;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this,EditActivity.this,
                        year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        btn_editdatetimePickerTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change = false;
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditActivity.this,EditActivity.this,
                        year, month+1, dayOfMonth);
                datePickerDialog.show();
            }
        });

        btn_editAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOutput = originalfindname.getText().toString();
                System.out.println("Here goes the name: ");
                System.out.println(nameOutput);
                if (nameOutput.length() < 1) {
                    System.out.println("entered!");
                    nameOutput = name;
                }
                String locknumOutput = originallocknum.getText().toString();
                System.out.println("Here goes the code: ");
                System.out.println(locknumOutput);
                if (locknumOutput.length() < 1) {
                    System.out.println("entered!");
                    locknumOutput = lockid;
                }
                System.out.println(locknumOutput);
                String dateTimeFromOutput = originaldatetimeFrom.getText().toString();
                String dateTimeToOutput = originaldatetimeTo.getText().toString();
                String password = random();
                sqliteHelper.deleteOneRow(name);
                sqliteHelper.addAccess(new Access(null, nameOutput, locknumOutput, dateTimeFromOutput, dateTimeToOutput, password));

                Intent intent = new Intent(EditActivity.this, HomeActivity.class);
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

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this, EditActivity.this,
                hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        showTime(hourOfDay, minute, year, month+1, dayOfMonth);

    }

    private void showTime(int hour, int min, int year, int month, int day){
        if(change==true) {
            originaldatetimeFrom.setText(new StringBuilder().append("From: ").append(day).append("/")
                    .append(month).append("/").append(year).append(" ").append(hour).append(":")
                    .append(min));
        }if(change==false){
            originaldatetimeTo.setText(new StringBuilder().append("To: ").append(day).append("/")
                    .append(month).append("/").append(year).append(" ").append(hour).append(":")
                    .append(min));
        }
    }

}
