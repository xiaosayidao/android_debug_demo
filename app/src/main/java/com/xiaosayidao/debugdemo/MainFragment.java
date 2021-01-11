package com.xiaosayidao.debugdemo;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

public class MainFragment extends BaseFragment {

    private int[] mNavigationFragments = {
            R.id.action_FirstFragment_to_CrashFragment,
            R.id.action_FirstFragment_to_AnrFragment,
            R.id.action_FirstFragment_to_CrashFragment};
    public static final boolean DEBUG_PERFORMANCE = false;

    @Override
    protected void initDataOnCreate() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (DEBUG_PERFORMANCE) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base;
    }

    @Override
    protected String[] getAdapterArrays() {
        return getResources().getStringArray(R.array.debug_type);
    }

    @Override
    protected String[] getAdapterSummaryArrays() {
        return getResources().getStringArray(R.array.debug_type_summary);
    }

    @Override
    public void onItemClick(View v, int position) {
        Toast.makeText(v.getContext(), getAdapterArrays()[position], Toast.LENGTH_SHORT).show();
        NavHostFragment.findNavController(MainFragment.this)
                .navigate(mNavigationFragments[position]);
    }
}