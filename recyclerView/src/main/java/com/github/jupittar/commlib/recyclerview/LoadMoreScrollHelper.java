package com.github.jupittar.commlib.recyclerview;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


public class LoadMoreScrollHelper {
    /**
     * the min amount of items to have below current scroll position before loading more.
     */
    private int mLoadMoreThreshold = 3;

    private RecyclerView mRecyclerView;
    private OnLoadMoreListener mOnLoadMoreListener;

    private LoadMoreScrollHelper(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public static LoadMoreScrollHelper addTo(RecyclerView recyclerView) {
        return new LoadMoreScrollHelper(recyclerView);
    }

    public LoadMoreScrollHelper onLoadMoreListener(OnLoadMoreListener listener) {
        this.mOnLoadMoreListener = listener;
        setUpScrollListener();
        return this;
    }

    public LoadMoreScrollHelper threshold(int threshold) {
        mLoadMoreThreshold = threshold;
        return this;
    }

    private void setUpScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * the total number of items in the data set after the last loading.
             */
            private int mPreviousTotalCount = 0;

            /**
             * true if still waiting for the last set of data to load
             */
            private boolean mLoading = true;


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy <= 0) {
                    return;
                }

                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPos = 0;
                int totalItemCount = adapter.getItemCount();

                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    lastVisibleItemPos = gridLayoutManager.findLastVisibleItemPosition();
                    mLoadMoreThreshold *= gridLayoutManager.getSpanCount();
                } else if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    lastVisibleItemPos = linearLayoutManager.findFirstVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    final int[] lastVisibleItemPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
                    for (int i = 0; i < lastVisibleItemPositions.length; i++) {
                        if (i == 0) {
                            lastVisibleItemPos = lastVisibleItemPositions[i];
                        } else if (lastVisibleItemPositions[i] > lastVisibleItemPos) {
                            lastVisibleItemPos = lastVisibleItemPositions[i];
                        }
                    }
                    mLoadMoreThreshold *= staggeredGridLayoutManager.getSpanCount();
                }

                // if the total item count is zero and the previous is not, assume the list
                // is invalidate and should be reset to initial state
                if (totalItemCount < mPreviousTotalCount) {
                    mPreviousTotalCount = totalItemCount;
                    if (totalItemCount == 0) {
                        mLoading = true;
                    }
                }

                // more data loaded
                if (mLoading && totalItemCount > mPreviousTotalCount) {
                    mLoading = false;
                    mPreviousTotalCount = totalItemCount;
                }


                if (!mLoading && totalItemCount - lastVisibleItemPos <= mLoadMoreThreshold) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    mLoading = true;
                }

            }
        });
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}
