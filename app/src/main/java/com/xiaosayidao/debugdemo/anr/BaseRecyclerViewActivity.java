package com.xiaosayidao.debugdemo.anr;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaosayidao.debugdemo.R;

public abstract class BaseRecyclerViewActivity extends AppCompatActivity implements
        OnItemClickListener {
    private LinearLayoutManager layoutManager;
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String[] mSummary;
    private String[] mTitle;

    public abstract String[] getItemSummary();

    public abstract String[] getItemTitle();

    public abstract void performItemClicked(View view, int i);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_recyclerview_layout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        this.mRecyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.layoutManager = linearLayoutManager;
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this.mRecyclerView.getContext(),
                        this.layoutManager.getOrientation()));
        this.mTitle = getItemTitle();
        this.mSummary = getItemSummary();
        String[] strArr = this.mTitle;
        if (strArr == null || strArr.length == 0) {
            throw new RuntimeException("override getData()  ");
        }
        MyAdapter myAdapter = new MyAdapter(this.mTitle, this.mSummary, this);
        this.mAdapter = myAdapter;
        this.mRecyclerView.setAdapter(myAdapter);
    }

    public void onItemClicked(View view, int position) {
        Toast.makeText(this, this.mTitle[position], Toast.LENGTH_SHORT).show();
        performItemClicked(view, position);
    }
}
