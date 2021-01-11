package com.xiaosayidao.debugdemo.anr;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.xiaosayidao.debugdemo.R;

public class MyAdapter extends Adapter<MyAdapter.MyViewHolder> {
    public final OnItemClickListener mOnItemClickListerner;
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

    public MyAdapter(String[] title, String[] summary, OnItemClickListener onItemClickListener) {
        this.mTitle = title;
        this.mSummary = summary;
        this.mOnItemClickListerner = onItemClickListener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder((LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.my_text_view, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(this.mTitle[position]);
        String[] strArr = this.mSummary;
        if (strArr.length >= position && !TextUtils.isEmpty(strArr[position])) {
            holder.summary.setText(this.mSummary[position]);
        } else {
            holder.summary.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MyAdapter.this.mOnItemClickListerner != null) {
                    MyAdapter.this.mOnItemClickListerner.onItemClicked(v, position);
                }
            }
        });
    }

    public int getItemCount() {
        return this.mTitle.length;
    }
}
