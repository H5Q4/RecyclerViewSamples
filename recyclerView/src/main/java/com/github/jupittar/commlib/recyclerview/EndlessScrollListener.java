package com.github.jupittar.commlib.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.jupittar.commlib.recyclerview.adapter.BaseViewAdapter;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int mPreviousTotal = 0; // the total number of items in the data set after the last loading.
    private int mVisibleThreshold = 3; // the min amount of items to have below current scroll position before loading more.
    private boolean mLoading = true; // true if still waiting for the last set of data to load

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int firstVisibleItem = 0;
        int visibleItemCount = 0;
        int totalItemCount = 0;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        visibleItemCount = layoutManager.getChildCount();

        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
            totalItemCount = gridLayoutManager.getItemCount();
        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            totalItemCount = linearLayoutManager.getItemCount();
        }

        if (adapter instanceof BaseViewAdapter && ((BaseViewAdapter) adapter).isEmpty()) {
            mPreviousTotal = 0;
        }

        if (mLoading) {
            if (adapter instanceof CommonViewAdapter) {
                if (((CommonViewAdapter) adapter).hasHeader()) {
                    mPreviousTotal++;
                }
                if (((CommonViewAdapter) adapter).hasFooter()) {
                    mPreviousTotal++;
                }
            }
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }

        if (!mLoading && totalItemCount - firstVisibleItem <=
                mVisibleThreshold + visibleItemCount) {
            onLoadMore();
            mLoading = true;
        }

    }


    public void setVisibleThreshold(int visibleThreshold) {
        mVisibleThreshold = visibleThreshold;
    }

    public abstract void onLoadMore();
}
