package com.xiaosayidao.debugdemo.anr;

import static android.os.Environment.DIRECTORY_PICTURES;

import static com.xiaosayidao.debugdemo.anr.AnrBgServiceTimeoutService.TYPE_FG_START;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xiaosayidao.debugdemo.BaseFragment;
import com.xiaosayidao.debugdemo.R;

import java.io.File;
import java.io.FileOutputStream;

public class AnrFragment extends BaseFragment {
    public static final int bg_broadcast_timeout = 5;
    public static final int bg_service_timeout = 3;
    public static final int bind_service_timeout = 2;
    public static final int content_provider_anr = 0;
    public static final int fg_broadcast_timeout = 6;
    public static final int fg_service_timeout = 4;
    public static final int input_dispatch_timeout = 7;
    public static final int start_service_timeout = 1;
    private static final int io_wait_high = 8;
    private static final int synchronized_block = 9;
    private static final int binder_blocked = 10;

    private static final String TAG = "AnrFragment";
    private Handler mHandler = new Handler();

    @Override
    protected String[] getAdapterArrays() {
        return getResources().getStringArray(R.array.anr_type_array);
    }

    @Override
    protected String[] getAdapterSummaryArrays() {
        return getResources().getStringArray(R.array.anr_type_summary);
    }

    @Override
    public void onItemClick(View v, int position) {
        switch (position) {
            case content_provider_anr:
                new DownloadFilesTask(mActivityContext).execute(new Object[0]);
                return;
            case start_service_timeout:
                mActivityContext.startService(new Intent(mActivityContext,
                        AnrServiceTimeOutService.class));
                return;
            case bind_service_timeout:
                mActivityContext.bindService(new Intent(mActivityContext,
                                AnrServiceTimeOutService.class),
                        new ServiceConnection() {
                            public void onServiceConnected(ComponentName name,
                                    IBinder service) {
                            }

                            public void onServiceDisconnected(ComponentName name) {
                            }
                        }, Context.BIND_AUTO_CREATE);
                return;
            case bg_service_timeout:
                Intent intent = new Intent();
                intent.setClass(mActivityContext, AnrBgServiceTimeoutService.class);
                intent.putExtra(AnrBgServiceTimeoutService.EXTRA_TYPE, TYPE_FG_START);
                mActivityContext.startService(intent);
                return;
            case fg_service_timeout:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mActivityContext.startForegroundService(
                            new Intent(mActivityContext, AnrFgServiceTimeOutService.class));
                }
                return;
            case bg_broadcast_timeout:
                Intent intent2 = new Intent(mActivityContext, AnrBroadCastReceiver.class);
                mActivityContext.sendBroadcast(intent2);
                return;
            case fg_broadcast_timeout:
                Intent intent1 = new Intent(mActivityContext, AnrBroadCastReceiver.class);
                intent1.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                mActivityContext.sendBroadcast(intent1);
                return;
            case input_dispatch_timeout:
                Intent intent3 = new Intent(mActivityContext, InputDispatchTimeoutActivity.class);
                intent3.setPackage(mActivityContext.getPackageName());
                startActivity(intent3);
                return;
            case io_wait_high:
                triggerIoHigh();
                return;
            case synchronized_block:
                synchronizedBlock();
                return;
            case binder_blocked:
                binderBlocked();
                return;
            default:
                return;
        }
    }

    private void binderBlocked() {
        ActivityManager manager =
                (ActivityManager) mActivityContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager.getAppTasks();
        }
    }

    private void longTimeCount() {
        try {
            for (int i = 0; i < 500000; i++) {
                File SDCardFile = getContext().getExternalFilesDir(DIRECTORY_PICTURES);
                File file = new File(SDCardFile, "data.txt");
                FileOutputStream fos;
                fos = new FileOutputStream(file);
                //Log.d(TAG, "performItemClicked: " + i);
                String data = "AnrTest";
                fos.write(data.getBytes());
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Object mObject = new Object();

    void triggerIoHigh() {
        AnrUtils.sort(mActivityContext);
    }


    void synchronizedBlock() {
        new LockTask().execute();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                synchronized (mObject) {
                    Toast.makeText(mActivityContext,
                            mActivityContext.getResources().getText(R.string.lock_dev),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, 1000);


    }

    public class LockTask extends AsyncTask<Integer[], Integer, Long> {
        @Override
        protected Long doInBackground(Integer[]... integers) {
            synchronized (mObject) {
                Log.d(TAG, "synchronizedBlock: task wait");
                // This is a long-running operation, which makes
                // the lock last for a long time
                AnrUtils.waitTime(AnrUtils.DEFAULT_INPUT_DISPATCHING_TIMEOUT_NANOS * 2,
                        "synchronized ");
            }
            return null;
        }
    }
}

