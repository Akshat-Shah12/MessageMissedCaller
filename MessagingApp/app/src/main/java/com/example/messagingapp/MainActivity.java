package com.example.messagingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    EditText number, message;
    TextView date1, time1;
    Calendar calendar=Calendar.getInstance();
    String DATE,TIME;
    boolean clicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = (EditText) findViewById(R.id.editTextNumber);
        message = (EditText) findViewById(R.id.editTextMessage);
        date1 = (TextView) findViewById(R.id.textView2);
        time1 = (TextView) findViewById(R.id.textView3);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
    }

    public void sendMessage(View view) {
        SmsManager mySmsManager = SmsManager.getDefault();
        String Number = number.getText().toString();
        String Message = message.getText().toString();
        final Handler handler;
        Toast.makeText(this,DATE+" "+TIME,Toast.LENGTH_LONG).show();
        handler = new Handler();
        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mySmsManager.sendTextMessage(Number, null, Message, null, null);
            }
        }, 10000);*/
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int year1=calendar.get(Calendar.YEAR);
                int month1=calendar.get(Calendar.MONTH);
                int day1=calendar.get(Calendar.DAY_OF_MONTH);
                String current_date = day1+"-" + (month1+1)+"-"+year1;
                SimpleDateFormat format= new SimpleDateFormat("k:mm a");
                String current_time = format.format(calendar.getTime());
                if(current_date==DATE && current_time==TIME){
                    mySmsManager.sendTextMessage(Number,null,Message,null,null);
                    clicked=false;
                }
            }
        },0,1000);
    }



    public void handleDateButton(View view){

        int year1=calendar.get(Calendar.YEAR);
        int month1=calendar.get(Calendar.MONTH);
        int day1=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date1.setText(dayOfMonth+"-" + (month+1)+"-"+year);
                DATE=dayOfMonth+"-" + (month+1)+"-"+year;
            }
        },year1,month1,day1);
        datePickerDialog.show();
    }

    public void handleTimeButton(View view) {
        int hours=calendar.get(Calendar.HOUR_OF_DAY);
        int mins=calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog= new TimePickerDialog(MainActivity.this, R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c=Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                c.set(Calendar.MINUTE,minute);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format= new SimpleDateFormat("k:mm a");
                String time = format.format(c.getTime());
                time1.setText(time);
                TIME=time;
            }
        },hours ,mins,false);
        timePickerDialog.show();
    }
}