package com.pengjunwei.android.custom.demo.recyclerview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.pengjunwei.android.custom.demo.R;
import com.pengjunwei.android.custom.demo.recyclerview.adapter.SimpleStringAdapter;
import com.pengjunwei.android.custom.demo.recyclerview.decorator.HorizontalDividerItemDecoration;
import com.pengjunwei.android.custom.demo.recyclerview.manager.WeiLinearLayoutManager;

/**
 * Created by wikipeng on 2017/12/15.
 */
public class TestCustomRecyclerViewActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new WeiLinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(new SimpleStringAdapter(this, Cheeses.sCheeseStrings_2) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ViewHolder vh = super.onCreateViewHolder(parent, viewType);
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int pos = vh.getAdapterPosition();
                        if (pos == RecyclerView.NO_POSITION) {
                            return;
                        }
                        if (pos + 1 < getItemCount()) {
                            swap(pos, pos + 1);
                        }
                    }
                });
                return vh;
            }
        });

        //        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        //        recyclerView.addItemDecoration(new android.support.v7.widget.DividerItemDecoration(this
        //                , android.support.v7.widget.DividerItemDecoration.VERTICAL));

        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.RED)
                        .size(30)
                        .build());

    }
}
