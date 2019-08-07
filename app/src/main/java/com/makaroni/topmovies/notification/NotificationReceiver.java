package com.makaroni.topmovies.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String movieTitle = "MOVIE";
    public static int NOTIFICATION_ID = 0 ;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getNotification(intent.getStringExtra(movieTitle));
        notificationHelper.getManager().notify(NOTIFICATION_ID, nb.build());
        NOTIFICATION_ID++;
    }
}
