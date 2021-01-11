package com.xiaosayidao.debugdemo.anr;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;

public class AnrServiceTimeOutService extends Service {
    public static final String TAG = "AnrService";
    private Messenger mMessenger = new Messenger(new Handler());

    public void onCreate() {
        super.onCreate();
        AnrUtils.waitTime(40000, null);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }
}
