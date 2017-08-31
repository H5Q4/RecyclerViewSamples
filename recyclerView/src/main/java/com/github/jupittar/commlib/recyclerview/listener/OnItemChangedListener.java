package com.github.jupittar.commlib.recyclerview.listener;


import com.github.jupittar.commlib.recyclerview.ItemHolder;

/**
 * Callback interface for when an item changes and should be re-bound.
 */
public interface OnItemChangedListener {
    /**
     * Invoked by {@link ItemHolder#notifyItemChanged()}.
     *
     * @param itemHolder the item holder that has changed
     */
    void onItemChanged(ItemHolder<?> itemHolder);


    /**
     * Invoked by {@link ItemHolder#notifyItemChanged(Object payload)}.
     *
     * @param itemHolder the item holder that has changed
     * @param payload the payload object
     */
    void onItemChanged(ItemHolder<?> itemHolder, Object payload);
}
