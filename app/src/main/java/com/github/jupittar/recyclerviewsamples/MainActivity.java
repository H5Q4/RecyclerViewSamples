package com.github.jupittar.recyclerviewsamples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.jupittar.commlib.recyclerview.ItemAdapter;
import com.github.jupittar.recyclerviewsamples.entity.BookHolder;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ItemAdapter<BookHolder> mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //noinspection unchecked
        mItemAdapter = new ItemAdapter<>()
                .withViewTypes(new BookViewHolder.Factory(), R.layout.item_book)
                .setItems(DataFactory.generateBooks(9));
        mRecyclerView.setAdapter(mItemAdapter);
    }
}
