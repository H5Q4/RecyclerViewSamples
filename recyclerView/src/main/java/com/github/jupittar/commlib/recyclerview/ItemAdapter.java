package com.github.jupittar.commlib.recyclerview;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.github.jupittar.commlib.recyclerview.listener.OnItemChangedListener;
import com.github.jupittar.commlib.recyclerview.listener.OnItemClickedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Base adapter class for displaying a collection of items. Provides functionality for handling changing items,
 * persistent item state, item click events and reusable item views.
 * <p>
 * @see <a href="https://android.googlesource.com/platform/packages/apps/DeskClock/+/master/src/com/android/deskclock
 * /ItemAdapter.java">Original code</a>
 */
public class ItemAdapter<T extends ItemHolder> extends RecyclerView.Adapter<ItemViewHolder> {

    /**
     * List of {@link ItemHolder} this adapter represented exclude headers and footers
     */
    private List<T> mItemHolders;

    /**
     * List of {@link ItemHolder} for the headers this adapter represented
     */
    private List<ItemHolder> mHeaderHolders;

    /**
     * List of {@link ItemHolder} for the footers this adapter represented
     */
    private List<ItemHolder> mFooterHolders;

    public List<ItemHolder> getHeaderHolders() {
        return mHeaderHolders == null ? new ArrayList<ItemHolder>() : mHeaderHolders;
    }

    public List<ItemHolder> getFooterHolders() {
        return mFooterHolders == null ? new ArrayList<ItemHolder>() : mFooterHolders;
    }


    /**
     * Invoked when any item changed
     */
    private OnItemChangedListener mOnItemChangedListener;

    /**
     * Factories for creating new {@link ItemViewHolder}
     */
    private final SparseArray<ItemViewHolder.Factory> mFactoriesByViewType = new SparseArray<>();

    /**
     * Click listeners to invoke in {@link #mOnItemClickedListener}
     */
    private final SparseArray<OnItemClickedListener> mClickListenersByViewType = new SparseArray<>();

    /**
     * Finds the position of the changed item holder and invoke {@link #notifyItemChanged(int)} or
     * {@link #notifyItemChanged(int, Object)} if payloads are present(in order to do in-place animation)
     */
    private final OnItemChangedListener mItemChangedNotifier = new OnItemChangedListener() {
        @Override
        public void onItemChanged(ItemHolder<?> itemHolder) {
            if (mOnItemChangedListener != null) {
                mOnItemChangedListener.onItemChanged(itemHolder);
            }
            int position = mItemHolders.indexOf(itemHolder);
            position += getHeaderCount();
            if (position != RecyclerView.NO_POSITION) {
                notifyItemChanged(position);
            }
        }

        @Override
        public void onItemChanged(ItemHolder<?> itemHolder, Object payload) {
            if (mOnItemChangedListener != null) {
                mOnItemChangedListener.onItemChanged(itemHolder, payload);
            }
            int position = mItemHolders.indexOf(itemHolder);
            position += getHeaderCount();
            if (position != RecyclerView.NO_POSITION) {
                notifyItemChanged(position, payload);
            }
        }
    };

    /**
     * Invokes the {@link OnItemClickedListener} in {@link #mClickListenersByViewType} corresponding to
     * {@link ItemViewHolder#getItemViewType()}
     */
    private final OnItemClickedListener mOnItemClickedListener = new OnItemClickedListener() {
        @Override
        public void onItemClicked(ItemViewHolder<?> viewHolder, int id) {
            final OnItemClickedListener listener = mClickListenersByViewType.get(viewHolder.getItemViewType());
            if (listener != null) {
                listener.onItemClicked(viewHolder, id);
            }
        }
    };

    /**
     * Convenience for calling {@link #setHasStableIds(boolean)} with {@code true}
     *
     * @return this object, allowing calls to methods in this class to be chained
     */
    public ItemAdapter setHasStableIds() {
        setHasStableIds(true);
        return this;
    }

    /**
     * Sets the {@link ItemViewHolder.Factory} used to create
     * new item view holders in {@link #onCreateViewHolder(ViewGroup, int)}.
     *
     * @param factory   the {@link ItemViewHolder.Factory} used to create new item view holders
     * @param viewTypes the unique identifier for the view types to be created
     * @return this object, allowing calls to methods in this class to be chained
     */
    public ItemAdapter withViewTypes(ItemViewHolder.Factory factory, int... viewTypes) {
        for (int viewType :
                viewTypes) {
            mFactoriesByViewType.put(viewType, factory);
        }
        return this;
    }

    /**
     * Sets the {@link OnItemClickedListener}
     *
     * @param listener  the {@link OnItemClickedListener} invoked by {@link #mOnItemClickedListener}
     * @param viewTypes the unique identifier for the view types to be created
     * @return this object, allowing calls to methods in this class to be chained
     */
    public ItemAdapter click(OnItemClickedListener listener, int... viewTypes) {
        for (int viewType :
                viewTypes) {
            mClickListenersByViewType.put(viewType, listener);
        }
        return this;
    }

    /**
     * @return the count of the headers this adapter represented
     */
    private int getHeaderCount() {
        return getHeaderHolders().size();
    }

    /**
     * @return the count of the footers this adapter represented
     */
    private int getFooterCount() {
        return getFooterHolders().size();
    }

    /**
     * Returns {@code true} if the item holder in the specified position is header
     *
     * @param position in which the item holder to judge
     * @return {@code true} if the item holder in the specified position is header
     */
    private boolean isPositionForHeader(int position) {
        return position < getHeaderCount();
    }

    /**
     * Returns {@code true} if the item holder in the specified position is footer
     *
     * @param position in which the item holder to judge
     * @return {@code true} if the item holder in the specified position is footer
     */
    private boolean isPositionForFooter(int position) {
        return position >= getHeaderCount() + mItemHolders.size();
    }

    /**
     * Inserts the specified {@link ItemHolder}s as a header and
     * invokes {@link #notifyItemInserted(int)} to update the UI.
     *
     * @param header the item holder to add
     * @return this object, allowing calls of methods in this class to be chained
     */
    public ItemAdapter addHeader(ItemHolder... header) {
        for (ItemHolder itemHolder :
                header) {
            getHeaderHolders().add(itemHolder);
            notifyItemInserted(getHeaderCount() - 1);
        }
        return this;
    }
/**
     * Inserts the specified {@link ItemHolder}s as a footer and
     * invokes {@link #notifyItemInserted(int)} to update the UI.
     *
     * @param footer the item holder to add
     * @return this object, allowing calls of methods in this class to be chained
     */
    public ItemAdapter addFooter(ItemHolder... footer) {
        for (ItemHolder itemHolder :
                footer) {
            getFooterHolders().add(itemHolder);
            notifyItemInserted(getFooterCount() - 1);
        }
        return this;
    }

    /**
     * Sets the list of item holders serve as the dataset for this adapter and invokes
     * {@link #notifyDataSetChanged()} to update the UI.
     * <p/>
     * if {@link #hasStableIds()} returns {@code true}, then the instance state will be preserved
     * between new and old holders that matching {@link ItemHolder#mItemId} values.
     *
     * @param itemHolders the new list of item holders
     * @return this object, allowing calls to methods in this class to be chained
     */
    public ItemAdapter setItems(List<T> itemHolders) {
        List<T> oldItemHolders = mItemHolders;
        if (oldItemHolders != itemHolders) {
            if (oldItemHolders != null) {
                // remove the OnItemChangedListener from the old item holders
                for (T oldItemHolder :
                        oldItemHolders) {
                    oldItemHolder.removeOnItemChangedListener(mItemChangedNotifier);
                }
            }

            if (oldItemHolders != null && itemHolders != null && hasStableIds()) {
                // transfer instance state from old to new item holders based on item id.
                // here use a simple O(N^2) implementation since we assume the number of items
                // is relatively small and generating a temporary map would be more expensive.
                final Bundle bundle = new Bundle();
                for (ItemHolder newItemHolder :
                        itemHolders) {
                    for (ItemHolder oldItemHolder :
                            oldItemHolders) {
                        if (newItemHolder.mItemId == oldItemHolder.mItemId
                                && newItemHolder != oldItemHolder) {
                            // clear any exiting state from bundle
                            bundle.clear();

                            // transfer instance state from old to new item holders
                            oldItemHolder.onSaveInstanceState(bundle);
                            newItemHolder.onRestoreInstanceState(bundle);

                            break;
                        }
                    }
                }
            }

            if (itemHolders != null) {
                // add the item change listener to the new item holders
                for (ItemHolder itemHolder :
                        itemHolders) {
                    itemHolder.addOnItemChangedListener(mItemChangedNotifier);
                }
            }

            // finally update the current list of item holders and inform the RecyclerView to update the UI
            mItemHolders = itemHolders;
            notifyDataSetChanged();
        }
        return this;
    }

    /**
     * Inserts the specified item holder in the specified position. Invokes {@link #notifyItemInserted(int)}
     * to update the UI.
     *
     * @param position   the index to which to add the item holder
     * @param itemHolder the item holder to add
     * @return this object, allowing calls to methods in this class to be chained
     */
    public ItemAdapter addItem(int position, @NonNull T itemHolder) {
        if (isPositionForHeader(position)) {
            throw new IllegalArgumentException("Can't add a normal item in header position!");
        }
        itemHolder.addOnItemChangedListener(mItemChangedNotifier);
        position = Math.min(position, mItemHolders.size() + getHeaderCount());
        mItemHolders.add(position, itemHolder);
        notifyItemInserted(position);
        return this;
    }

    /**
     * Removes the first occurrence of the specified item holder in the list, if it is present
     * (optional operation). If the list does not contain this item holder, it is unchanged. Invokes
     * {@link #notifyItemRemoved(int)} to update the UI.
     *
     * @param itemHolder the item holder to remove
     * @return this object, allowing calls to methods in this class to be chained
     */
    public ItemAdapter removeItem(@NonNull T itemHolder) {
        final int index = mItemHolders.indexOf(itemHolder);
        if (index >= 0) {
            itemHolder = mItemHolders.remove(index);
            itemHolder.removeOnItemChangedListener(mItemChangedNotifier);
            notifyItemRemoved(index + getHeaderCount());
        }
        return this;
    }

    /**
     * Sets the listener to be invoked whenever any item changes.
     *
     * @param listener the {@link OnItemChangedListener} to set
     */
    public void setOnItemChangedListener(OnItemChangedListener listener) {
        mOnItemChangedListener = listener;
    }

    /**
     * @return the current list of item holders represented by this adapter excluding headers and footers
     */
    public final List<T> getItems() {
        return mItemHolders;
    }

    /**
     * Finds the item holder associating with the specified identifier.
     *
     * @param id the identifier by which to find
     * @return the item holder associating with the specified identifier
     */
    public T findItemById(long id) {
        for (T holder :
                mItemHolders) {
            if (holder.mItemId == id) {
                return holder;
            }
        }
        return null;
    }

    @NonNull @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final ItemViewHolder.Factory factory = mFactoriesByViewType.get(viewType);
        if (factory != null) {
            return factory.createViewHolder(parent, viewType);
        }
        throw new IllegalArgumentException("Unsupported view type: " + viewType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // suppress any unchecked warnings since it is up to the subclass to guarantee
        // compatibility of their view holders with the item holder corresponding position
        if (isPositionForHeader(position)) {
            holder.bindItemView(mHeaderHolders.get(position));
        } else if (isPositionForFooter(position)) {
            holder.bindItemView(mFooterHolders.get(position));
        } else {
            holder.bindItemView(mItemHolders.get(position));
        }
        holder.setOnItemClickedListener(mOnItemClickedListener);
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionForHeader(position)) {
            return getHeaderHolders().get(position).getItemViewType();
        }
        if (isPositionForFooter(position)) {
            return getFooterHolders().get(position).getItemViewType();
        }
        return mItemHolders.get(position).getItemViewType();
    }

    @Override
    public long getItemId(int position) {
        if (hasStableIds() || isPositionForHeader(position) || isPositionForFooter(position)){
            return RecyclerView.NO_ID;
        } else {
            return mItemHolders.get(position).mItemId;
        }
    }

    @Override
    public void onViewRecycled(@NonNull ItemViewHolder holder) {
        holder.setOnItemClickedListener(null);
        holder.recycleItemView();
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + getHeaderCount() + getFooterCount();
    }

    /**
     *
     * @return the count of items excluding headers and footers
     */
    public int getContentItemCount() {
        return mItemHolders == null ? 0 : mItemHolders.size();
    }
}
