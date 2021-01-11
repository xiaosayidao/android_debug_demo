package com.xiaosayidao.debugdemo.anr;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;

import androidx.core.app.NotificationCompat.Builder;

import com.xiaosayidao.debugdemo.MainActivity;
import com.xiaosayidao.debugdemo.R;

public class AnrFgServiceTimeOutService extends Service {
    private static final String CHANNEL_ID = "anrdemo";
    private static final String NOTIFICATION_CHANNEL_ID = "AnrFgServiceTimeOutService";
    private static final int NOTIFICATION_ID = 42;
    public static final String TAG = "AnrFgServiceTimeOutService";

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra("keep_live", false)) {
            callStartForeground();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void callStartForeground() {
        createNotificationChannel();
        startForeground(1, new Builder(this, CHANNEL_ID).setContentTitle(
                "Foreground Service").setContentText("Anr demo test").setSmallIcon(
                R.drawable.ic_launcher_background).setContentIntent(
                PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),
                        0)).build());
    }

    private void createNotificationChannel() {
        if (VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService(
                    NotificationManager.class)).createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                            NotificationManager.IMPORTANCE_DEFAULT));
        }
    }
}
