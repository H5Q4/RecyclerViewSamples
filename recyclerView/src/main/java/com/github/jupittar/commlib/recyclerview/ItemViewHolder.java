package com.github.jupittar.commlib.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.jupittar.commlib.recyclerview.listener.OnItemClickedListener;


public class ItemViewHolder<T extends ItemHolder> extends RecyclerView.ViewHolder {

    /**
     * The current {@link ItemHolder} bound to this holder
     */
    private T mItemHolder;

    /**
     * The current {@link OnItemClickedListener} associated with this holder
     */
    private OnItemClickedListener mOnItemClickedListener;

    /**
     * Designated constructor
     *
     * @param itemView the item {@link View} to associate with this holder
     */
    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * @return the {@link ItemHolder} bound to this holder, or {@code null} if unbound
     */
    public final T getItemHolder() {
        return mItemHolder;
    }

    /**
     * Binds the holder's {@link #itemView} to a particular item.
     *
     * @param itemHolder the {@link ItemHolder} to bind
     */
    public final void bindItemView(T itemHolder) {
        mItemHolder = itemHolder;
        onBindItemView(itemHolder);
    }

    /**
     * Called when a new item is bound to the holder. Subclasses should override to
     * bind any relevant data to their {@link #itemView} in this method.
     *
     * @param itemHolder the {@link ItemHolder} to bind
     */
    protected void onBindItemView(T itemHolder) {
        // for subclasses
    }

    /**
     * Recycles the current item view, unbinding the current item holder and state.
     */
    public final void recycleItemView() {
        mItemHolder = null;
        mOnItemClickedListener = null;
        onRecycleItemView();
    }

    /**
     * Called when the current item view is recycled. Subclasses should override to release
     * any bound item state and prepare their {@link #itemView} for reusing
     */
    protected void onRecycleItemView() {
        // for subclasses
    }

    /**
     * Sets the current {@link OnItemClickedListener} to be invoked via {@link #notifyItemClicked(int)}.
     *
     * @param listener the new {@link OnItemClickedListener}, or {@code null} to clear
     */
    public final void setOnItemClickedListener(OnItemClickedListener listener) {
        mOnItemClickedListener = listener;
    }

    /**
     * Called by subclasses to invoke the current
     * {@link OnItemClickedListener} for a
     * particular click event so it can be handled in a high level.
     *
     * @param id the unique identifier for the click action that has occurred
     */
    public void notifyItemClicked(int id) {
        if (mOnItemClickedListener != null) {
            mOnItemClickedListener.onItemClicked(this, id);
        }
    }


    /**
     * Factory interface used by {@link ItemAdapter} for creating new {@link ItemViewHolder}.
     */
    public interface Factory {
        /**
         * Used by {@link ItemAdapter#createViewHolder(ViewGroup, int)} to make a new {@link ItemViewHolder}
         * for a given view type.
         *
         * @param parent the {@link ViewGroup} that the {@link ItemViewHolder#itemView} will be attached
         * @param ViewType the unique id of the item view to create
         * @return a new initialized {@link ItemViewHolder}
         */
        ItemViewHolder<?> createViewHolder(ViewGroup parent, int ViewType);
    }
}
