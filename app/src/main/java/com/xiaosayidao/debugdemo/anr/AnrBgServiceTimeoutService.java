package com.xiaosayidao.debugdemo.anr;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AnrBgServiceTimeoutService extends Service {
    public static final String EXTRA_TYPE = "extra_start_type";
    public static final String TAG = "AnrBgService";
    public static final int TYPE_BG_START = 1;
    public static final int TYPE_FG_START = 2;
    private ActivityManager mAM;

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");
        this.mAM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        StringBuilder sb = new StringBuilder();
        sb.append("onStartCommand() called with: intent = [");
        sb.append(intent);
        sb.append("], flags = [");
        sb.append(flags);
        sb.append("], startId = [");
        sb.append(startId);
        sb.append("]");
        String sb2 = sb.toString();
        String str = TAG;
        Log.d(str, sb2);
        if (intent == null || intent.getIntExtra(EXTRA_TYPE, -1) != 2) {
            Log.d(str, "onStartCommand() start from bg ");
            AnrUtils.waitTime(200100, "bg service");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onDestroy() {
        super.onDestroy();
        String str = TAG;
        Log.d(str, "onDestroy() start persist service ");
        Log.d(str, "onDestroy: kill current service");
    }
}
