package com.example.annagujgiczer.leckefuzet;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.annagujgiczer.leckefuzet.ui.categories.CategoryActivity;

/**
 * Created by annagujgiczer on 2016/12/10.
 */

public class AlarmService extends Service {

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleStart(intent, startId);
        return START_NOT_STICKY;
    }

    void handleStart(Intent intent, int startId) {
        Intent notificationIntent = new Intent(this.getApplicationContext(),CategoryActivity.class);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(AlarmService.this);

        builder.setSmallIcon(R.drawable.notif_icon).setContentTitle("Határidő közeledik!").setContentIntent(pendingNotificationIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = builder.getNotification();


        notificationManager.notify(R.drawable.notif_icon, notification);
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
