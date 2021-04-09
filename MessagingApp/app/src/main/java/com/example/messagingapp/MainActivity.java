package com.example.messagingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
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
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    EditText number, message;
    TextView date1, time1;
    Calendar calendar=Calendar.getInstance();
    String DATE,TIME;
    boolean clicked=false;
    Calendar calendar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = (EditText) findViewById(R.id.editTextNumber);
        message = (EditText) findViewById(R.id.editTextMessage);
        calendar1=Calendar.getInstance();
        calendar1.setTimeInMillis(System.currentTimeMillis());
        date1 = (TextView) findViewById(R.id.textView2);
        time1 = (TextView) findViewById(R.id.textView3);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS}, 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendMessage(View view) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this,TimedMessageReciever.class);
        i.putExtra("number",number.getText().toString());
        i.putExtra("message",message.getText().toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,i,PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pendingIntent);
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
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH,month);
                calendar1.set(Calendar.DAY_OF_MONTH,dayOfMonth);
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
                calendar1.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar1.set(Calendar.MINUTE,minute);
                calendar1.set(Calendar.SECOND,0);
                calendar1.set(Calendar.MILLISECOND,0);
                TIME=time;
                Log.println(Log.ASSERT,"time",calendar1.getTimeInMillis()+"");
            }
        },hours ,mins,false);
        timePickerDialog.show();
    }

}