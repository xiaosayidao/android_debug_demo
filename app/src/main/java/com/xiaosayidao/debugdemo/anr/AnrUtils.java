package com.xiaosayidao.debugdemo.anr;

import static android.os.Environment.DIRECTORY_PICTURES;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class AnrUtils {
    public static final String ACTION_START_BG_SERVICE = "anrdemo.intent.action.START_BG_SERVICE";
    public static final int BROADCAST_BG_TIMEOUT = 60000;
    public static final int BROADCAST_FG_TIMEOUT = 10000;
    public static final long DEFAULT_INPUT_DISPATCHING_TIMEOUT_NANOS = 5000;
    public static final String EXTRA_BR_TYPE = "EXTRA_BR_TYPE";
    public static final int SERVICE_BACKGROUND_TIMEOUT = 200000;
    public static final int SERVICE_START_FOREGROUND_TIMEOUT = 10000;
    public static final int SERVICE_TIMEOUT = 20000;
    public static final String TAG = "AnrUtils";

    public static void waitTime(long lastTime, String reason) {
        long end;
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        StringBuilder sb = new StringBuilder();
        sb.append(element.getFileName());
        sb.append("#");
        sb.append(element.getMethodName());
        sb.append(":");
        sb.append(element.getLineNumber());
        String trace = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(trace);
        sb2.append(" ");
        sb2.append(TextUtils.isEmpty(reason) ? "" : reason);
        sb2.append(" wait start ");
        String sb3 = sb2.toString();
        String str = TAG;
        Log.d(str, sb3);
        long start = System.currentTimeMillis();
        do {
            end = System.currentTimeMillis() - start;
        } while (end < lastTime);
        StringBuilder sb4 = new StringBuilder();
        sb4.append(trace);
        sb4.append(" wait last  ");
        sb4.append(end);
        sb4.append(" ms");
        Log.d(str, sb4.toString());
    }

    public static void skipHiddenApi() {
        if (VERSION.SDK_INT >= 28) {
            try {
                Method forName = Class.class.getDeclaredMethod("forName",
                        new Class[]{String.class});
                Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod",
                        new Class[]{String.class, Class[].class});
                Class<?> vmRuntimeClass = (Class) forName.invoke(null,
                        new Object[]{"dalvik.system.VMRuntime"});
                ((Method) getDeclaredMethod.invoke(vmRuntimeClass,
                        new Object[]{"setHiddenApiExemptions",
                                new Class[]{String[].class}})).invoke(
                        ((Method) getDeclaredMethod.invoke(vmRuntimeClass,
                                new Object[]{"getRuntime", null})).invoke(null, new Object[0]),
                        new Object[]{new String[]{"L"}});
            } catch (Throwable e) {
                Log.e("[error]", "reflect bootstrap failed:", e);
            }
        }
    }

    public static void sort(Context context) {
        File SDCardFile = context.getExternalFilesDir(DIRECTORY_PICTURES);
        File file = new File(SDCardFile, "data.txt");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            //Log.d(TAG, "performItemClicked: " + i);
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                String data =
                        "AnrTestdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd";
                fos.write(data.getBytes());
                fos.flush();
            }

            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
