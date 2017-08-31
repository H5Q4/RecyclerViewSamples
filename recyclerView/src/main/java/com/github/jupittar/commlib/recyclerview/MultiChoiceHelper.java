package com.github.jupittar.commlib.recyclerview;


import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;

/**
 *
 * Helper class to reproduce ListView's modal MultiChoice mode with a RecyclerView.
 *
 * <p/>
 * Providing the following features:
 * <li>Track selections and deselections</li>
 * <li>Automatically correct the selections corresponding to dataset changes(items inserted, deleted or moved)</li>
 * <li>Provide a way to save and restore the selections when the container {@link AppCompatActivity} or
 * {@link android.support.v4.app.Fragment} gets recreated</li>
 * <li>Be layout-agnostic(compatible with lists, grids and others)</li>
 * <li>Allow predictive animations(not interfere with them)</li>
 * <li>Compatible with API 7+</li>
 *
 * Note: Declare and use this class from inside your Adapter.
 *
 * @author Christophe Beyls
 * Modified by: huangqiao
 *
 * link: https://medium.com/@BladeCoder/implementing-a-modal-selection-helper-for-recyclerview-1e888b4cd5b9
 */
public class MultiChoiceHelper {

    private AppCompatActivity mActivity;
    private RecyclerView.Adapter mAdapter;

    /**
     * positions of the selected items
     */
    private SparseBooleanArray mCheckStates;

    /**
     * count of selected items
     */
    private int mCheckedItemCount;

    /**
     * item IDs associated with current positions
     */
    private LongSparseArray<Integer> mCheckedIdStates;

    /**
     * Makes sure this constructor is called before setting the adapter on the RecyclerView
     * so that this class will be notified before the RecyclerView in case of dataset changes.
     *
     **/
    public MultiChoiceHelper(@NonNull AppCompatActivity activity, @NonNull RecyclerView.Adapter adapter) {
        mActivity = activity;
        mAdapter = adapter;

        mCheckStates = new SparseBooleanArray(0);
        if (adapter.hasStableIds()) {
            mCheckedIdStates = new LongSparseArray<>(0);
        }
    }

    /**
     * Returns the checked state of the specified position.
     *
     * @param position the item position whose checked state to return
     * @return the item's checked state
     */
    public boolean isItemChecked(int position) {
        return mCheckStates.get(position);
    }

    /**
     * Sets the checked state of the specified position with the specified state.
     *
     * @param position the position of the item whose checked state is to be changed
     * @param value the new checked state for the item
     */
    public void setItemChecked(int position, boolean value) {
        boolean oldValue = mCheckStates.get(position);
        mCheckStates.put(position, value);

        if (oldValue != value) {
            if (value) {
                ++mCheckedItemCount;
            } else {
                --mCheckedItemCount;
            }

            final long id = mAdapter.getItemId(position);
            if (mCheckedIdStates != null) {
                if (value) {
                    mCheckedIdStates.put(id, position);
                } else {
                    mCheckedIdStates.delete(id);
                }
            }
        }
    }



}
