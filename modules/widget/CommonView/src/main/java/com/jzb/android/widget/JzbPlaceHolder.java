package com.jzb.android.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by pengjunwei on 2018/1/5.
 */

public class JzbPlaceHolder extends AppCompatImageView {
    protected int     onMeasureCount;
    protected boolean isSetPlaceHolderSize;


    public JzbPlaceHolder(Context context) {
        this(context, null);
    }

    public JzbPlaceHolder(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JzbPlaceHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onMeasureCount++;
        //        LogTool.getInstance().saveLog(" 测量次数         onMeasureCount===>", onMeasureCount);

        int measuredWidth  = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        updatePlaceHolderSize(measuredWidth, measuredHeight);
    }

    protected void updatePlaceHolderSize(int parentWidth, int parentHeight) {
        if (isSetPlaceHolderSize) {
            return;
        }

        ViewGroup.LayoutParams layoutParams = getLayoutParams();

        int size = parentWidth;
        if (parentWidth > parentHeight) {
            //宽图为1/2.5 长图图为1/1.5
            size = (size * 10) / 25;
        } else {
            size = (size * 10) / 15;
        }

        layoutParams.width = size;
        layoutParams.height = ((size * 100 / 561) * 164) / 100;

        //        LogTool.getInstance().saveLog("jzbFocus debug ===> 默认图 宽度       :" + layoutParams.width
        //                , "         默认高度===>", layoutParams.height
        //                , "         onMeasureCount===>", onMeasureCount
        //                , "         parentWidth===>", parentWidth
        //        );

        isSetPlaceHolderSize = true;
        setMeasuredDimension(size, layoutParams.height);
        setScaleType(ScaleType.FIT_CENTER);
    }
}
