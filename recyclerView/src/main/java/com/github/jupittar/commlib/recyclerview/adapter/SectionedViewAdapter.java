package com.github.jupittar.commlib.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.recyclerview.BaseViewHolder;
import com.github.jupittar.commlib.recyclerview.model.SectionedItem;

public abstract class SectionedViewAdapter<T extends SectionedItem> extends CommonViewAdapter<T> {

    private static final int ITEM_TYPE_SECTION = 0x333;
    @LayoutRes
    private int mSectionLayoutId;

    public SectionedViewAdapter(Context context, @LayoutRes int layoutId, @LayoutRes int sectionLayoutId) {
        super(context, layoutId);
        this.mSectionLayoutId = sectionLayoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_SECTION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(mSectionLayoutId, parent, false);
            return new BaseViewHolder(view);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void convertView(BaseViewHolder holder, T item) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_SECTION:
                convertSectionHead(holder, item);
                break;
            default:
                convertSectionItem(holder, item);
                break;
        }
    }

    @Override
    public int addItemViewType(int position) {
        T item = getItem(position);
        return item.isHead() ? ITEM_TYPE_SECTION : 0;
    }

    public abstract void convertSectionHead(BaseViewHolder holder, T item);

    public abstract void convertSectionItem(BaseViewHolder holder, T item);
}
