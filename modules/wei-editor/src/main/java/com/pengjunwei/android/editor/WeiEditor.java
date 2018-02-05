package com.pengjunwei.android.editor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jzb.common.LogTool;
import com.pengjunwei.android.editor.component.WeiEditorVHEditText;
import com.pengjunwei.android.editor.model.WeiEditorMenuItem;
import com.pengjunwei.android.editor.model.WeiEditorSpaceItem;
import com.pengjunwei.android.editor.model.WeiEditorText;
import com.pengjunwei.android.editor.model.WeiEditorVHBaseData;
import com.pengjunwei.android.editor.model.WeiEditorVHStyle;
import com.pengjunwei.android.editor.view.WeiEditorEditText;
import com.pengjunwei.android.editor.view.WeiEditorLayoutManager;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHBase;
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
    protected WeiEditorLayoutManager   menuLayoutManager;
    protected WeiEditorTemplateAdapter menuAdapter;
    /**
     * 编辑器内容
     */
    protected RecyclerView             contentRecyclerView;
    protected LinearLayoutManager      contentLayoutManager;
    protected WeiEditorContentAdapter  contentAdapter;

    /**
     * 临时过度焦点的视图
     */
    protected WeiEditorEditText tempEditText;

    /**
     * 当前获取焦点的viewHolder
     */
    protected WeiEditorVHBase currentFocusViewHolder;
    protected int             currentFocusViewPosition;

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
        contentAdapter = new WeiEditorContentAdapter(this);

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

        WeiEditorVHProvider.getGlobalProvider().register(WeiEditorText.class
                , R.layout.wei_editor_edittext_item_layout
                , R.layout.wei_editor_edittext_item_layout
                , WeiEditorVHEditText.class
        );
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        title = findViewById(R.id.editorTitle);
        menuRecyclerView = findViewById(R.id.editorMenuRecyclerView);
        contentRecyclerView = findViewById(R.id.editorRecyclerView);
        tempEditText = findViewById(R.id.tempEditText);

        Context context = getContext();
        menuLayoutManager = new WeiEditorLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        menuRecyclerView.setLayoutManager(menuLayoutManager);
        menuRecyclerView.setAdapter(menuAdapter);

        contentLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        contentRecyclerView.setLayoutManager(contentLayoutManager);
        contentRecyclerView.setAdapter(contentAdapter);

        initDefaultMenu();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        LogTool.getInstance().saveLog("dispatchKeyEvent  ===> ", event);
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        LogTool.getInstance().saveLog("dispatchKeyEventPreIme  ===> ", event);
        return super.dispatchKeyEventPreIme(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        LogTool.getInstance().saveLog("onKeyUp ===> ", keyCode
                , " event===> ", event
        );
        return super.onKeyUp(keyCode, event);
    }

    public void updateFocusViewHolder(WeiEditorVHBase viewHolder, int position) {
        this.currentFocusViewHolder = viewHolder;
        this.currentFocusViewPosition = position;
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

    public void startEdit() {
        WeiEditorText viewData = new WeiEditorText();
        viewData.buildFirstHint();
        contentAdapter.addData(viewData);
        contentAdapter.notifyDataSetChanged();
    }

    public void insertEditText(final int position, String newText) {
        WeiEditorText viewData = new WeiEditorText();
        viewData.isRequestFocus = true;
        viewData.text = newText;
        WeiEditorVHBaseData tempData  = contentAdapter.create(viewData, null);
        int                 itemCount = contentAdapter.getItemCount();
        if (position >= itemCount) {
            contentAdapter.add(itemCount, tempData);
        } else {
            contentAdapter.add(position, tempData);
        }
        //        LogTool.getInstance().saveLog("insertEditText ===> ", position
        //                , " text===> ", newText
        //        );

        //        contentRecyclerView.postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        contentAdapter.notifyItemInserted(position);
        //            }
        //        }, 0);
    }

    public void scrollToPosition(int position) {
        //        contentLayoutManager.scrollToPositionWithOffset(position, 0);
        contentRecyclerView.scrollToPosition(position);
    }
}

//Process: com.pengjunwei.android.custom.demo, PID: 339
//        java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder{17ccca7d position=2 id=-1, oldPos=1, pLpos:1 scrap [attachedScrap] tmpDetached no parent} android.support.v7.widget.RecyclerView{3e8c1e40 VFED.... ......I. 0,0-1080,1526 #7f080044 app:id/editorRecyclerView}, adapter:com.pengjunwei.android.editor.WeiEditorTemplateAdapter@38609179, layout:android.support.v7.widget.LinearLayoutManager@2a06febe, context:com.pengjunwei.android.custom.demo.editor.TestEditorActivity@16f1b925
//        at android.support.v7.widget.RecyclerView$Recycler.validateViewHolderForOffsetPosition(RecyclerView.java:5421)
//        at android.support.v7.widget.RecyclerView$Recycler.tryGetViewHolderForPositionByDeadline(RecyclerView.java:5603)
//        at android.support.v7.widget.RecyclerView$Recycler.getViewForPosition(RecyclerView.java:5563)
//        at android.support.v7.widget.RecyclerView$Recycler.getViewForPosition(RecyclerView.java:5559)
//        at android.support.v7.widget.LinearLayoutManager$LayoutState.next(LinearLayoutManager.java:2229)
//        at android.support.v7.widget.LinearLayoutManager.layoutChunk(LinearLayoutManager.java:1556)
//        at android.support.v7.widget.LinearLayoutManager.fill(LinearLayoutManager.java:1516)
//        at android.support.v7.widget.LinearLayoutManager.onLayoutChildren(LinearLayoutManager.java:608)
//        at android.support.v7.widget.RecyclerView.dispatchLayoutStep1(RecyclerView.java:3644)
//        at android.support.v7.widget.RecyclerView.dispatchLayout(RecyclerView.java:3408)
//        at android.support.v7.widget.RecyclerView.onLayout(RecyclerView.java:3962)
//        at android.view.View.layout(View.java:16695)
//        at android.view.ViewGroup.layout(ViewGroup.java:5328)
//        at android.support.constraint.ConstraintLayout.onLayout(ConstraintLayout.java:1197)
//        at android.view.View.layout(View.java:16695)
//        at android.view.ViewGroup.layout(ViewGroup.java:5328)
//        at android.support.constraint.ConstraintLayout.onLayout(ConstraintLayout.java:1197)
//        at android.view.View.layout(View.java:16695)
//        at android.view.ViewGroup.layout(ViewGroup.java:5328)
//        at android.support.constraint.ConstraintLayout.onLayout(ConstraintLayout.java:1197)
//        at android.view.View.layout(View.java:16695)
//        at android.view.ViewGroup.layout(ViewGroup.java:5328)
//        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:573)
//        at android.widget.FrameLayout.onLayout(FrameLayout.java:508)
//        at android.view.View.layout(View.java:16695)
//        at android.view.ViewGroup.layout(ViewGroup.java:5328)
//        at android.support.v7.widget.ActionBarOverlayLayout.onLayout(ActionBarOverlayLayout.java:443)
//        at android.view.View.layout(View.java:16695)
//        at android.view.ViewGroup.layout(ViewGroup.java:5328)
//        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:573)
//        at android.widget.FrameLayout.onLayout(FrameLayout.java:508)
//        at android.view.View.layout(View.java:16695)
//        at android.view.ViewGroup.layout(ViewGroup.java:5328)
//        at android.widget.LinearLayout.setChildFrame(LinearLayout.java:1702)
//        at android.widget.LinearLayout.layoutVertical(LinearLayout.java:1556)
//        at android.widget.LinearLayout.onLayout(LinearLayout.java:1465)
//        at android.view.View.layout(View.java:16695)
//        at android.view.ViewGroup.layout(ViewGroup.java:5328)
//        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:573)
//        at android.widget.FrameLayout.onLayout(FrameLayout.java:508)
//        at android.view.View.layout(View.java:16695)
//        at android.view.ViewGroup.layout(ViewGroup.java:5328)
//        at android.view.ViewRootImpl.performLayout(ViewRootImpl.java:2319)
//        at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:2032)
//        at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:1191)
//        at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:6642)
//        at android.view.Choreographer$CallbackRecord.run(Choreographer.java:777)
//        at android.view.Choreographer.doCallbacks(Choreographer.java:590)
//        at android.view.Choreographer.doFrame(Choreographer.java:560)
//        at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.ja
