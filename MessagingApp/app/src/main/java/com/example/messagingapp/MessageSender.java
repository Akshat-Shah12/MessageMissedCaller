package com.example.messagingapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Timer;
import java.util.TimerTask;

public class MessageSender extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            Toast.makeText(context,"Call started..",Toast.LENGTH_SHORT).show();
            check = false;
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
            Toast.makeText(context,"Call ended..",Toast.LENGTH_SHORT).show();
            if(check) {
                if (count == 0) {
                    sendMessage(context);
                }
            }
            count++;
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            Toast.makeText(context,"Incoming call..",Toast.LENGTH_SHORT).show();
            count=0;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendMessage(Context context) {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Toast.makeText(context,"Waiting to send message",Toast.LENGTH_SHORT).show();
            }
        },2000);
        String number ="1234";
        Uri uriCallLogs = Uri.parse("content://call_log/calls");
        Cursor cursorCallLogs = context.getContentResolver().query(uriCallLogs, null,null,null);
        cursorCallLogs.moveToFirst();
        number = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.NUMBER));
        String message = "Call you back in 15 mins";
        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(number,null,message,null,null);
    }

    private static boolean check = true;
    private static int count = 0;
}
