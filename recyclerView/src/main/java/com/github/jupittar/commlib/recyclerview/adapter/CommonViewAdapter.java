package com.github.jupittar.commlib.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.recyclerview.BaseViewHolder;

import java.util.List;

public abstract class CommonViewAdapter<T> extends BaseViewAdapter<T> {

    private static final int ITEM_TYPE_HEADER = 0x111;
    private static final int ITEM_TYPE_FOOTER = 0x222;

    private View mHeaderView;
    private View mFooterView;

    public CommonViewAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    public void setHeaderView(@NonNull View view) {
        this.mHeaderView = view;
        notifyItemInserted(0);
    }

    public void setFooterView(@NonNull View view) {
        this.mFooterView = view;
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder.getItemViewType() != ITEM_TYPE_HEADER
                && holder.getItemViewType() != ITEM_TYPE_FOOTER) {
            if (mHeaderView != null) {
                position--;
            }
            T item = getItem(position);
            convertView(holder, item);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        if (mHeaderView != null && viewType == ITEM_TYPE_HEADER) {
            holder = new BaseViewHolder(mHeaderView);
        } else if (mFooterView != null && viewType == ITEM_TYPE_FOOTER) {
            holder = new BaseViewHolder(mFooterView);
        } else {
            holder = super.onCreateViewHolder(parent, viewType);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader() && position == 0) {
            return ITEM_TYPE_HEADER;
        }

        if (hasFooter() && position == getItemCount() - 1) {
            return ITEM_TYPE_FOOTER;
        }

        return addItemViewType(hasHeader() ? position - 1 : position);
    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        if (hasHeader()) {
            count++;
        }
        if (hasFooter()) {
            count++;
        }
        return count;
    }

    public int addItemViewType(int position) {
        return -1;
    }

    private void add(T item) {
        mData.add(item);
        notifyItemInserted(hasHeader() ? mData.size() : mData.size() - 1);
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
            notifyItemRemoved(hasHeader() ? position + 1 : position);
        }
    }

    public void clear() {
        while (mData.size() > 0) {
            remove(mData.get(hasHeader() ? 1 : 0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public boolean hasHeader() {
        return mHeaderView != null;
    }

    public boolean hasFooter() {
        return mFooterView != null;
    }
}
