package com.pengjunwei.android.custom.demo.contextmenu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pengjunwei.android.custom.demo.R;
import com.pengjunwei.android.custom.demo.recyclerview.Cheeses;
import com.pengjunwei.android.custom.demo.recyclerview.TestRecyclerViewActivity;
import com.pengjunwei.android.custom.demo.recyclerview.adapter.SimpleStringAdapter;
import com.pengjunwei.android.custom.demo.recyclerview.decorator.DividerItemDecoration;

public class ContextMenuActivity extends AppCompatActivity {
    private TextView mButton;
    private RecyclerView mRecyclerView;
    private SimpleStringAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);
        mButton = findViewById(R.id.testButton);
        registerForContextMenu(mButton);

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new SimpleStringAdapter(this, Cheeses.sCheeseStrings_3) {
            @Override
            public SimpleStringAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final SimpleStringAdapter.ViewHolder vh = super.onCreateViewHolder(parent, viewType);
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int pos = vh.getAdapterPosition();
                        if (pos == RecyclerView.NO_POSITION) {
                            return;
                        }
//                        if (pos + 1 < getItemCount()) {
//                            swap(pos, pos + 1);
//                        }

                        showDeleteDialog(pos);
                    }
                });
                return vh;
            }
        };

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否删除"+position);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAdapter.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
        builder.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "删除");
    }

}
