package com.pengjunwei.android.custom.demo.editor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pengjunwei.android.custom.demo.R;
import com.pengjunwei.android.editor.WeiEditor;

/**
 * 编辑器测试Activity
 * Created by wikipeng on 2017/12/7.
 */
public class TestEditorActivity extends AppCompatActivity {
    protected WeiEditor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_editor);
        editor = findViewById(R.id.weiEditor);

        editor.startEdit();
    }
}
