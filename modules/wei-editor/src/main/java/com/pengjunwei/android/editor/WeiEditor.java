package com.pengjunwei.android.editor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.pengjunwei.android.editor.model.WeiEditorMenuItem;
import com.pengjunwei.android.editor.model.WeiEditorSpaceItem;
import com.pengjunwei.android.editor.model.WeiEditorVHStyle;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHDelegate;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHMenuItem;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHProvider;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHSpace;

/**
 * 本地化编辑器
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditor extends ConstraintLayout implements WeiEditorVHDelegate {
    /**
     * 标题
     */
    protected TextView                 title;
    /**
     * 编辑器功能菜单按钮
     */
    protected RecyclerView             menuRecyclerView;
    protected LinearLayoutManager      menuLayoutManager;
    protected WeiEditorTemplateAdapter menuAdapter;
    /**
     * 编辑器内容
     */
    protected RecyclerView             contentRecyclerView;
    protected LinearLayoutManager      contentLayoutManager;
    protected WeiEditorTemplateAdapter contentAdapter;


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

        menuAdapter = new WeiEditorTemplateAdapter(this);
        contentAdapter = new WeiEditorTemplateAdapter(this);

        WeiEditorVHProvider.getGlobalProvider().register(WeiEditorMenuItem.class
                , R.layout.wei_editor_menu_item_layout
                , R.layout.wei_editor_menu_item_layout
                , WeiEditorVHMenuItem.class
        );

        WeiEditorVHProvider.getGlobalProvider().register(WeiEditorSpaceItem.class
                , R.layout.wei_editor_space_item_layout
                , R.layout.wei_editor_space_item_layout
                , WeiEditorVHSpace.class
        );

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
        menuRecyclerView.setAdapter(menuAdapter);

        contentLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        contentRecyclerView.setLayoutManager(contentLayoutManager);
        contentRecyclerView.setAdapter(contentAdapter);

        initDefaultMenu();
    }

    public void initDefaultMenu() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //        int            marginLeft1    = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
        int spaceValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, displayMetrics);

        //        WeiEditorMenuItem item = new WeiEditorMenuItem("media", R.drawable.format_bar_button_media_selector);
        //        menuAdapter.add(new WeiEditorVHBaseData<WeiEditorMenuItem>(item, WeiEditorVHMenuItem.LAYOUT_RES_ID));
        //
        //        item = new WeiEditorMenuItem("title", R.drawable.format_bar_button_show_title_selector);
        //        menuAdapter.add(new WeiEditorVHBaseData<WeiEditorMenuItem>(item, WeiEditorVHMenuItem.LAYOUT_RES_ID));
        //
        //        item = new WeiEditorMenuItem("link", R.drawable.format_bar_button_link_selector);
        //        menuAdapter.add(new WeiEditorVHBaseData<WeiEditorMenuItem>(item, WeiEditorVHMenuItem.LAYOUT_RES_ID));
        //
        //        item = new WeiEditorMenuItem("bold", R.drawable.format_bar_button_bold_selector);
        //        menuAdapter.add(new WeiEditorVHBaseData<WeiEditorMenuItem>(item, WeiEditorVHMenuItem.LAYOUT_RES_ID));
        //
        //        menuAdapter.add(new WeiEditorMenuItem("quote", R.drawable.format_bar_button_quote_selector));
        //        menuAdapter.add(new WeiEditorMenuItem("ul", R.drawable.format_bar_button_ul_selector));
        //
        //        item = new WeiEditorMenuItem("h3", R.drawable.format_bar_button_heading_selector);
        //        menuAdapter.add(item);
        //
        //        menuAdapter.notifyDataSetChanged();

        ///////////////////////////////////////////////////////////////////////////
        // 空间距离
        ///////////////////////////////////////////////////////////////////////////
        WeiEditorVHStyle style = new WeiEditorVHStyle();
        style.setLayoutParams(spaceValue, LayoutParams.MATCH_PARENT);
        menuAdapter.addData(new WeiEditorSpaceItem(), style);

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////
        WeiEditorMenuItem item = new WeiEditorMenuItem("media", R.drawable.format_bar_button_media_selector);
        menuAdapter.addData(item);

        item = new WeiEditorMenuItem("title", R.drawable.format_bar_button_show_title_selector);
        menuAdapter.addData(item);

        item = new WeiEditorMenuItem("link", R.drawable.format_bar_button_link_selector);
        menuAdapter.addData(item);

        item = new WeiEditorMenuItem("bold", R.drawable.format_bar_button_bold_selector);
        menuAdapter.addData(item);

        menuAdapter.addData(new WeiEditorMenuItem("quote", R.drawable.format_bar_button_quote_selector));
        menuAdapter.addData(new WeiEditorMenuItem("ul", R.drawable.format_bar_button_ul_selector));

        item = new WeiEditorMenuItem("h3", R.drawable.format_bar_button_heading_selector);
        menuAdapter.addData(item);

        ///////////////////////////////////////////////////////////////////////////
        // 空间距离
        ///////////////////////////////////////////////////////////////////////////
        menuAdapter.addData(new WeiEditorSpaceItem(), style);

        menuAdapter.notifyDataSetChanged();
    }
}
