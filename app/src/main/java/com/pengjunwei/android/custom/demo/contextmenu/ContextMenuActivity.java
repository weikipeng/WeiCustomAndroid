package com.pengjunwei.android.custom.demo.contextmenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.pengjunwei.android.custom.demo.R;

public class ContextMenuActivity extends AppCompatActivity{
    private TextView mButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);
        mButton = findViewById(R.id.testButton);
        registerForContextMenu(mButton);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "删除");
    }
}
