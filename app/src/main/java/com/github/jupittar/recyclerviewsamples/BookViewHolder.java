package com.github.jupittar.recyclerviewsamples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jupittar.commlib.recyclerview.ItemViewHolder;
import com.github.jupittar.recyclerviewsamples.entity.BookHolder;


public class BookViewHolder extends ItemViewHolder<BookHolder> {

    private TextView mTitleTv;
    private TextView mAuthorTv;

    /**
     * Designated constructor
     *
     * @param itemView the item {@link View} to associate with this holder
     */
    public BookViewHolder(View itemView) {
        super(itemView);
        mTitleTv = (TextView) itemView.findViewById(R.id.tv_book_title);
        mAuthorTv = (TextView) itemView.findViewById(R.id.tv_book_author);
    }

    @Override
    protected void onBindItemView(BookHolder itemHolder) {
        mTitleTv.setText(itemHolder.mItem.title);
        mAuthorTv.setText(itemHolder.mItem.author);
    }

    public static class Factory implements ItemViewHolder.Factory {

        @Override
        public ItemViewHolder<?> createViewHolder(ViewGroup parent, int ViewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new BookViewHolder(inflater.inflate(ViewType, parent, false));
        }
    }
}
