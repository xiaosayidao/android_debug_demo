package com.xiaosayidao.debugdemo.anr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AnrBroadCastReceiver extends BroadcastReceiver {
    public static final int TYPE_BG = 1;
    public static final int TYPE_FG = 0;

    public void onReceive(Context context, Intent intent) {
        long timeout = 60000;
        String reason = "bg onReceive";
        if ((intent.getFlags() & Intent.FLAG_RECEIVER_FOREGROUND) != 0) {
            timeout = 10000;
            reason = "fg onReceive";
        }
        AnrUtils.waitTime(timeout, reason);
    }
}
