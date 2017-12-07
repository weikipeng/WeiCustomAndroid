package com.pengjunwei.android.editor.viewholder;

import android.view.View;

import com.pengjunwei.android.editor.R;
import com.pengjunwei.android.editor.RippleToggleButton;
import com.pengjunwei.android.editor.model.WeiEditorMenuItem;
import com.pengjunwei.android.editor.model.WeiEditorVHBaseData;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorVHMenuItem extends WeiEditorVHBase<WeiEditorMenuItem> {
    protected RippleToggleButton editorMenuButton;
    public static final int LAYOUT_RES_ID = R.layout.wei_editor_menu_item_layout;

    public WeiEditorVHMenuItem(View itemView, WeiEditorVHDelegate delegate) {
        super(itemView, delegate);

        editorMenuButton = itemView.findViewById(R.id.editorMenuButton);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editorMenuButton.setChecked(editorMenuButton.isChecked());
            }
        });
    }

    public void onBindViewHolder(int position, WeiEditorVHBaseData<WeiEditorMenuItem> data) {
        super.onBindViewHolder(position, data);
        if (data == null) {
            return;
        }

        WeiEditorMenuItem realData = data.data;
        editorMenuButton.setButtonDrawable(realData.iconRes);

//        if ((realData.marginArray != null) && realData.marginArray.length == 4) {
//            itemView.setPadding(realData.marginArray[0]
//                    , realData.marginArray[1]
//                    , realData.marginArray[2]
//                    , realData.marginArray[3]);
//        }

        //        Resources resources = itemView.getResources();
        //        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        //        float margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, displayMetrics);
    }
}
