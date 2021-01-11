package com.xiaosayidao.debugdemo.anr;

import android.os.Bundle;
import android.view.View;
import com.xiaosayidao.debugdemo.R;
public class InputDispatchTimeoutActivity extends BaseRecyclerViewActivity implements OnItemClickListener {
    public static final int input_dispatch_key_event = 5;
    public static final int input_dispatch_touch_event = 6;
    public static final int window_not_focused_application_focused = 1;
    private int mPosition = -1;
    private final int window_paused = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new TestThread(InputDispatchTimeoutActivity.this).start();
                }
            }
        }).start();
    }

    public String[] getItemTitle() {
        return getResources().getStringArray(R.array.input_dispatch_timeout_title_array);
    }

    public String[] getItemSummary() {
        return getResources().getStringArray(R.array.input_dispatch_timeout_summary);
    }

    public void onStart() {
        super.onStart();
    }

    public void onRestart() {
        super.onRestart();
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        if (this.mPosition == 1) {
            AnrUtils.waitTime(10000, null);
        }
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void performItemClicked(View view, int position) {
        this.mPosition = position;
        if (position == 5 || position == 6) {
            finish();
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void finish() {
        int i = this.mPosition;
        if (i == 5 || i == 6) {
            AnrUtils.waitTime(10000, null);
        }
        super.finish();
    }
}
