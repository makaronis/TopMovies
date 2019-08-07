package com.makaroni.topmovies.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.makaroni.topmovies.R;

public class NotificationHelper extends ContextWrapper {

    public static final String channelID = "channelID";
    public static final String channelName = "Top Movies";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getNotification(String movie) {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Top Movies reminder")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("You wanted to watch " + movie))
                .setContentText("You wanted to watch " + movie)
                .setSmallIcon(R.drawable.ic_notification);
    }
}