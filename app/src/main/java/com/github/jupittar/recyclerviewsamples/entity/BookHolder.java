package com.github.jupittar.recyclerviewsamples.entity;

import com.github.jupittar.commlib.recyclerview.ItemHolder;
import com.github.jupittar.recyclerviewsamples.R;


public class BookHolder extends ItemHolder<Book> {

    public BookHolder(Book item, long itemId) {
        super(item, itemId);
    }

    @Override
    public int getItemViewType() {
        return R.layout.item_book;
    }
}
