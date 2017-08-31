package com.github.jupittar.commlib.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.recyclerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    List<T> mData;
    @LayoutRes private int mLayoutId;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public BaseViewAdapter(Context context, @LayoutRes int layoutId) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        mData = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        final BaseViewHolder holder = new BaseViewHolder(view);
        if (mOnItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T item = getItem(position);
        convertView(holder, item);
    }

    private void add(T item) {
        mData.add(item);
        notifyItemInserted(mData.size() - 1);
    }

    public void addAll(List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    public void remove(T item) {
        int position = mData.indexOf(item);
        if (position > -1) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<T> getData() {
        return this.mData;
    }

    public abstract void convertView(BaseViewHolder holder, T item);

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
