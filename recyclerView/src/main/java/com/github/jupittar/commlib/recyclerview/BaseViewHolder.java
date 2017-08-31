package com.github.jupittar.commlib.recyclerview;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    /**
     * container of caching views
     */
    private SparseArray<View> mViewSparseArray;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * get view by id
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        if (mViewSparseArray == null) {
            mViewSparseArray = new SparseArray<>();
        }
        T view = (T) mViewSparseArray.get(viewId);
        if (view == null) {
            view = (T) itemView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return view;
    }

}
