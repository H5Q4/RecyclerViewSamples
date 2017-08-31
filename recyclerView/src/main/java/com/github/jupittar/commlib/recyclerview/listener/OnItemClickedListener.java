package com.github.jupittar.commlib.recyclerview.listener;


import com.github.jupittar.commlib.recyclerview.ItemViewHolder;

/**
 * Callback interface for handling when an item is clicked.
 */
public interface OnItemClickedListener {

    /**
     * Invoked by {@link ItemViewHolder#notifyItemClicked(int)}
     *
     * @param viewHolder the {@link ItemViewHolder} containing the view that was clicked
     * @param id the unique identifier for the click action that has occurred
     */
    void onItemClicked(ItemViewHolder<?> viewHolder, int id);

}
