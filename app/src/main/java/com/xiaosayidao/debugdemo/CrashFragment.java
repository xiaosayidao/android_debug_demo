package com.xiaosayidao.debugdemo;

import android.content.Intent;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

public class CrashFragment extends BaseFragment {

    private int[] mNavigationFragments = {R.id.action_FirstFragment_to_CrashFragment};

    @Override
    protected void initDataOnCreate() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base;
    }

    @Override
    protected String[] getAdapterArrays() {
        return new String[]{"FC", "Index", "permission"};
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onItemClick(View v, int position) {
        if (position == 0) {
            v = null;
            v.findFocus();
        } else if (position == 1) {
            int result = mNavigationFragments[2];
        } else if (position == 2) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SHUTDOWN);
            v.getContext().sendBroadcast(intent);
        }
    }
}