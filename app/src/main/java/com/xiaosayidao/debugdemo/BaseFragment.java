package com.xiaosayidao.debugdemo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseFragment extends Fragment implements MyAdapter.ClickListener {

    private LinearLayoutManager mLayoutManager;
    protected Context mActivityContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataOnCreate();
        mActivityContext = getActivity();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(BaseFragment.this).navigateUp();
            }
        });
    }

    protected void initDataOnCreate() {

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutId(), container, false);
    }

    protected int getLayoutId() {
        return R.layout.fragment_base;
    }

    protected abstract String[] getAdapterArrays();

    protected String[] getAdapterSummaryArrays() {
        return new String[0];
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(view.getContext());
        RecyclerView recycler = view.findViewById(R.id.recycler_view);
        recycler.setLayoutManager(mLayoutManager);
        recycler.addItemDecoration(new DividerItemDecoration(view.getContext(),
                mLayoutManager.getOrientation()));
        MyAdapter adapter = new MyAdapter(getAdapterArrays(), getAdapterSummaryArrays());
        adapter.setOnItemClickListener(this);
        recycler.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View v, int position) {
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }
}