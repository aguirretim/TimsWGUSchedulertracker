package com.example.timswguschedulertracker.classesforobjects;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.timswguschedulertracker.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //DEFINE WHAT HAPPENS WHEN THE ALARM GOES OFF
        Bundle extras = intent.getExtras();
        //these values will always be overwritten but we need default values in order to compile
        String asg = "*", date = "**";
        if (extras != null){
            //extract the data we passed in on our AssessmentCreate page
            asg = extras.getString("asg");
            date = extras.getString("date");
            //maybe convert the date to 12hr

        }
        //Build a notification, and assign it to the channel we created for this app
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "ScheduleTrackerChannelID")
                .setContentTitle("Assignment Tracker")
                .setContentText( asg + ": is due at " + date ) //this date will show in 24hr format
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.mipmap.ic_launcher_round);//If you want to use a different icon save it to your drawable folder and referece like this... R.drawable.*nameOfFile*);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //Right now, we arent using the ID (its arbitrary) but I think you'd use this to determine if you clicked on this notification
        notificationManager.notify(200, builder.build());


        Toast.makeText(context, "ALARM GOING", Toast.LENGTH_LONG).show();
        Toast.makeText(context, "Time Up... Now Vibrating !!!",
                Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(3000);
    }
}
