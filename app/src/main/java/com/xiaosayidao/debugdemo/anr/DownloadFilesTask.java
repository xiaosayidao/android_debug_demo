package com.xiaosayidao.debugdemo.anr;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.RemoteException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class DownloadFilesTask extends AsyncTask {
    private Context mContext;

    DownloadFilesTask(Context context) {
        this.mContext = context;
    }

    public Object doInBackground(Object[] objects) {
        Uri uri = Uri.parse("content://com.xiaosayidao.debugdemo.anr/anr");
        ContentProviderClient client =
                this.mContext.getContentResolver().acquireContentProviderClient(uri);
        Method methodBook = null;
        try {
            methodBook = client.getClass().getDeclaredMethod("setDetectNotResponding",
                    new Class[]{Long.TYPE});
            methodBook.setAccessible(true);
            methodBook.invoke(client, new Object[]{Integer.valueOf(AnrContentProvider.TIME_OUT)});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            Method method = methodBook;
        }
        Method method2 = methodBook;
        try {
            client.query(uri, null, null, null, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                client.close();
            }
        } catch (RemoteException e4) {
            e4.printStackTrace();
        }
        return null;
    }
}
