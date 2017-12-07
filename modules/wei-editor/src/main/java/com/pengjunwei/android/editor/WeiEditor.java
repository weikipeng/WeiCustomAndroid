package com.pengjunwei.android.editor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.pengjunwei.android.editor.model.WeiEditorMenuItem;

/**
 * 本地化编辑器
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditor extends ConstraintLayout {
    /**
     * 标题
     */
    protected TextView                title;
    /**
     * 编辑器功能菜单按钮
     */
    protected RecyclerView            menuRecyclerView;
    protected LinearLayoutManager     menuLayoutManager;
    protected WeiEditorMenuAdapter    menuAdapter;
    /**
     * 编辑器内容
     */
    protected RecyclerView            contentRecyclerView;
    protected LinearLayoutManager     contentLayoutManager;
    protected WeiEditorContentAdapter contentAdapter;


    public WeiEditor(Context context) {
        this(context, null);
    }

    public WeiEditor(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeiEditor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //    public WeiEditor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    //        super(context, attrs, defStyleAttr, defStyleRes);
    //        init(context, attrs, defStyleAttr);
    //    }

    protected void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.wei_editor_layout, this);

        menuAdapter = new WeiEditorMenuAdapter();
        contentAdapter = new WeiEditorContentAdapter();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        title = findViewById(R.id.editorTitle);
        menuRecyclerView = findViewById(R.id.editorMenuRecyclerView);
        contentRecyclerView = findViewById(R.id.editorRecyclerView);

        Context context = getContext();
        menuLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        menuRecyclerView.setLayoutManager(menuLayoutManager);
        menuAdapter = new WeiEditorMenuAdapter();
        menuRecyclerView.setAdapter(menuAdapter);

        contentLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        contentRecyclerView.setLayoutManager(contentLayoutManager);
        contentAdapter = new WeiEditorContentAdapter();
        contentRecyclerView.setAdapter(contentAdapter);

        initDefaultMenu();
    }

    public void initDefaultMenu() {
        menuAdapter.add(new WeiEditorMenuItem("media", R.drawable.format_bar_button_media_selector));
        menuAdapter.add(new WeiEditorMenuItem("title", R.drawable.format_bar_button_show_title_selector));
        menuAdapter.add(new WeiEditorMenuItem("link", R.drawable.format_bar_button_link_selector));
        menuAdapter.add(new WeiEditorMenuItem("bold", R.drawable.format_bar_button_bold_selector));

        menuAdapter.notifyDataSetChanged();
    }
}
