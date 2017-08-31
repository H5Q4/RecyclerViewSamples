package com.github.jupittar.commlib.recyclerview;


import android.os.Bundle;

import com.github.jupittar.commlib.recyclerview.listener.OnItemChangedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for wrapping an item for compatibility with an {@link ItemHolder}.
 * <p>
 *     An {@link ItemHolder} serves as a bridge between the model and view layer;
 *     subclasses should implement properties that fall beyond the scope of their model layer
 *     but are necessary for the view layer. Properties that should be persisted across data set changes
 *     can be preserved via the {@link #onSaveInstanceState(Bundle)} and {@link #onRestoreInstanceState(Bundle)}
 *     methods.
 * </p>
 * Note: An {@link ItemHolder} can be used by multiple {@link ItemHolder}s and any state changes should
 * simultaneously be reflected in both UIs.
 *
 * @param <T> the item type wrapped by the holder.
 */
public abstract class ItemHolder<T> {

    /**
     * The item held by this holder
     */
    public final T mItem;

    /**
     * Unique id corresponding to the item
     */
    public final long mItemId;

    /**
     * Listeners to be invoked by {@link #notifyItemChanged()}
     */
    private final List<OnItemChangedListener> mOnItemChangedListeners = new ArrayList<>();

    public ItemHolder(T item, long itemId) {
        mItem = item;
        mItemId = itemId;
    }

    /**
     * Adds the {@link OnItemChangedListener} to the current list of {@link OnItemChangedListener}s if
     * it is not registered.
     *
     * @param listener the {@link OnItemChangedListener} to be added
     */
    public final void addOnItemChangedListener(OnItemChangedListener listener) {
        if (!mOnItemChangedListeners.contains(listener)) {
            mOnItemChangedListeners.add(listener);
        }
    }

    /**
     * Removes the {@link OnItemChangedListener} from the current list of {@link OnItemChangedListener}s
     *
     * @param listener the {@link OnItemChangedListener} to be removed
     */
    public final void removeOnItemChangedListener(OnItemChangedListener listener) {
        mOnItemChangedListeners.remove(listener);
    }

    /**
     * Invokes {@link OnItemChangedListener#onItemChanged(ItemHolder)} for all added via
     * {@link #addOnItemChangedListener(OnItemChangedListener)}
     */
    public final void notifyItemChanged() {
        for (OnItemChangedListener listener :
                mOnItemChangedListeners) {
            listener.onItemChanged(this);
        }
    }

    /**
     * Invokes {@link OnItemChangedListener#onItemChanged(ItemHolder, Object)} for all added via
     * {@link #addOnItemChangedListener(OnItemChangedListener)}
     */
    public final void notifyItemChanged(Object payload) {
        for (OnItemChangedListener listener :
                mOnItemChangedListeners) {
            listener.onItemChanged(this, payload);
        }
    }

    /**
     * Called to retrieve per-instance state when the item may disappear or change so that state
     * can be restored in {@link #onRestoreInstanceState(Bundle)}
     * <p>
     *     Note: Subclasses must not maintain a reference to the {@link Bundle} as it may be reused by
     *     other items in the {@link ItemHolder}.
     * </p>
     *
     * @param bundle the {@link Bundle} in which to place saved state
     */
    public void onSaveInstanceState(Bundle bundle) {
        // for subclasses
    }


    /**
     * Called to restore any per-instance state which was previously saved in {@link #onSaveInstanceState(Bundle)}
     * for an item with a matching {@link #mItemId}
     * <p>
     *     Note: Subclasses must not maintain a reference to the {@link Bundle} as it may be reused by
     *     other items in the {@link ItemHolder}.
     * </p>
     *
     * @param bundle the {@link Bundle} in which to retrieve saved state
     */
    public void onRestoreInstanceState(Bundle bundle) {
        // for subclasses
    }

    /**
     * @return the unique identifier for the view that should be used to present the item.
     * e.g. the layout resource id
     */
    public abstract int getItemViewType();

}
