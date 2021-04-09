package com.example.messagingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class TimedMessageReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SmsManager mySmsManager = SmsManager.getDefault();
        String Number = intent.getStringExtra("number");
        String Message = intent.getStringExtra("message");

        Log.println(Log.ASSERT,"message","reciedfdfd");
        mySmsManager.sendTextMessage(Number,null,Message,null,null);
    }
}
