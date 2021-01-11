package com.xiaosayidao.debugdemo.anr;

import android.content.Context;

/* compiled from: InputDispatchTimeoutActivity */
class TestThread extends Thread {
    private Context context;

    public TestThread(Context context2) {
        this.context = context2;
    }

    public void run() {
        super.run();
        while (true) {
            long total = 0;
            for (int i = 0; ((long) i) < Long.MAX_VALUE; i++) {
                total += (long) i;
            }
        }
    }
}
