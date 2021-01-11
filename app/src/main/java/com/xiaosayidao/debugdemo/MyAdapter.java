package com.xiaosayidao.debugdemo;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


public class MyAdapter extends Adapter<MyAdapter.MyViewHolder> {
    public ClickListener mOnItemClickListerner;
    private final String[] mSummary;
    private String[] mTitle;

    public static class MyViewHolder extends ViewHolder {
        public TextView summary;
        public TextView title;

        public MyViewHolder(LinearLayout v) {
            super(v);
            this.title = (TextView) v.findViewById(R.id.title);
            this.summary = (TextView) v.findViewById(R.id.summary);
        }
    }

    void setOnItemClickListener(ClickListener onItemClickListener) {
        mOnItemClickListerner = onItemClickListener;
    }

    public MyAdapter(String[] title, String[] summary) {
        this.mTitle = title;
        this.mSummary = summary;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder((LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.my_text_view, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(this.mTitle[position]);
        String[] strArr = this.mSummary;
        if (strArr.length > position && !TextUtils.isEmpty(strArr[position])) {
            holder.summary.setText(this.mSummary[position]);
        } else {
            holder.summary.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (MyAdapter.this.mOnItemClickListerner != null) {
                MyAdapter.this.mOnItemClickListerner.onItemClick(v, position);
            }
        });
    }

    public int getItemCount() {
        return this.mTitle.length;
    }


    public interface ClickListener {
        void onItemClick(View v, int position);

        void onItemLongClick(View v, int position);
    }

}