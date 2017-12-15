package com.pengjunwei.android.custom.demo.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.pengjunwei.android.custom.demo.R;
import com.pengjunwei.android.custom.demo.recyclerview.adapter.SimpleStringAdapter;
import com.pengjunwei.android.custom.demo.recyclerview.decorator.DividerItemDecoration;

/**
 * Created by wikipeng on 2017/12/15.
 */
public class TestRecyclerViewActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new MyLayoutManager(this));
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
//                ConstraintLayout.LayoutParams.MATCH_PARENT));

        recyclerView.setAdapter(new SimpleStringAdapter(this, Cheeses.sCheeseStrings) {
            @Override
            public SimpleStringAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
                final SimpleStringAdapter.ViewHolder vh = super
                        .onCreateViewHolder(parent, viewType);
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

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    private static final int SCROLL_DISTANCE = 80; // dp

    /**
     * A basic ListView-style LayoutManager.
     */
    class MyLayoutManager extends RecyclerView.LayoutManager {
        private static final String TAG = "MyLayoutManager";
        private       int mFirstPosition;
        private final int mScrollDistance;

        public MyLayoutManager(Context c) {
            final DisplayMetrics dm = c.getResources().getDisplayMetrics();
            mScrollDistance = (int) (SCROLL_DISTANCE * dm.density + 0.5f);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            final int  parentBottom = getHeight() - getPaddingBottom();
            final View oldTopView   = getChildCount() > 0 ? getChildAt(0) : null;
            int        oldTop       = getPaddingTop();
            if (oldTopView != null) {
                oldTop = oldTopView.getTop();
            }
            detachAndScrapAttachedViews(recycler);
            int       top   = oldTop;
            int       bottom;
            final int left  = getPaddingLeft();
            final int right = getWidth() - getPaddingRight();
            final int count = state.getItemCount();
            for (int i = 0; mFirstPosition + i < count && top < parentBottom; i++, top = bottom) {
                View v = recycler.getViewForPosition(mFirstPosition + i);
                addView(v, i);
                measureChildWithMargins(v, 0, 0);
                bottom = top + getDecoratedMeasuredHeight(v);
                layoutDecorated(v, left, top, right, bottom);
            }
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public boolean canScrollVertically() {
            return true;
        }

        @Override
        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler,
                                      RecyclerView.State state) {
            if (getChildCount() == 0) {
                return 0;
            }
            int       scrolled = 0;
            final int left     = getPaddingLeft();
            final int right    = getWidth() - getPaddingRight();
            if (dy < 0) {
                while (scrolled > dy) {
                    final View topView    = getChildAt(0);
                    final int  hangingTop = Math.max(-getDecoratedTop(topView), 0);
                    final int  scrollBy   = Math.min(scrolled - dy, hangingTop);
                    scrolled -= scrollBy;
                    offsetChildrenVertical(scrollBy);
                    if (mFirstPosition > 0 && scrolled > dy) {
                        mFirstPosition--;
                        View v = recycler.getViewForPosition(mFirstPosition);
                        addView(v, 0);
                        measureChildWithMargins(v, 0, 0);
                        final int bottom = getDecoratedTop(topView);
                        final int top    = bottom - getDecoratedMeasuredHeight(v);
                        layoutDecorated(v, left, top, right, bottom);
                    } else {
                        break;
                    }
                }
            } else if (dy > 0) {
                final int parentHeight = getHeight();
                while (scrolled < dy) {
                    final View bottomView = getChildAt(getChildCount() - 1);
                    final int hangingBottom =
                            Math.max(getDecoratedBottom(bottomView) - parentHeight, 0);
                    final int scrollBy = -Math.min(dy - scrolled, hangingBottom);
                    scrolled -= scrollBy;
                    offsetChildrenVertical(scrollBy);
                    if (scrolled < dy && state.getItemCount() > mFirstPosition + getChildCount()) {
                        View      v   = recycler.getViewForPosition(mFirstPosition + getChildCount());
                        final int top = getDecoratedBottom(getChildAt(getChildCount() - 1));
                        addView(v);
                        measureChildWithMargins(v, 0, 0);
                        final int bottom = top + getDecoratedMeasuredHeight(v);
                        layoutDecorated(v, left, top, right, bottom);
                    } else {
                        break;
                    }
                }
            }
            recycleViewsOutOfBounds(recycler);
            return scrolled;
        }

        @Override
        public View onFocusSearchFailed(View focused, int direction,
                                        RecyclerView.Recycler recycler, RecyclerView.State state) {
            final int oldCount = getChildCount();
            if (oldCount == 0) {
                return null;
            }
            final int left           = getPaddingLeft();
            final int right          = getWidth() - getPaddingRight();
            View      toFocus        = null;
            int       newViewsHeight = 0;
            if (direction == View.FOCUS_UP || direction == View.FOCUS_BACKWARD) {
                while (mFirstPosition > 0 && newViewsHeight < mScrollDistance) {
                    mFirstPosition--;
                    View      v      = recycler.getViewForPosition(mFirstPosition);
                    final int bottom = getDecoratedTop(getChildAt(0));
                    addView(v, 0);
                    measureChildWithMargins(v, 0, 0);
                    final int top = bottom - getDecoratedMeasuredHeight(v);
                    layoutDecorated(v, left, top, right, bottom);
                    if (v.isFocusable()) {
                        toFocus = v;
                        break;
                    }
                }
            }
            if (direction == View.FOCUS_DOWN || direction == View.FOCUS_FORWARD) {
                while (mFirstPosition + getChildCount() < state.getItemCount() &&
                        newViewsHeight < mScrollDistance) {
                    View      v   = recycler.getViewForPosition(mFirstPosition + getChildCount());
                    final int top = getDecoratedBottom(getChildAt(getChildCount() - 1));
                    addView(v);
                    measureChildWithMargins(v, 0, 0);
                    final int bottom = top + getDecoratedMeasuredHeight(v);
                    layoutDecorated(v, left, top, right, bottom);
                    if (v.isFocusable()) {
                        toFocus = v;
                        break;
                    }
                }
            }
            return toFocus;
        }

        public void recycleViewsOutOfBounds(RecyclerView.Recycler recycler) {
            final int childCount   = getChildCount();
            final int parentWidth  = getWidth();
            final int parentHeight = getHeight();
            boolean   foundFirst   = false;
            int       first        = 0;
            int       last         = 0;
            for (int i = 0; i < childCount; i++) {
                final View v = getChildAt(i);
                if (v.hasFocus() || (getDecoratedRight(v) >= 0 &&
                        getDecoratedLeft(v) <= parentWidth &&
                        getDecoratedBottom(v) >= 0 &&
                        getDecoratedTop(v) <= parentHeight)) {
                    if (!foundFirst) {
                        first = i;
                        foundFirst = true;
                    }
                    last = i;
                }
            }
            for (int i = childCount - 1; i > last; i--) {
                removeAndRecycleViewAt(i, recycler);
            }
            for (int i = first - 1; i >= 0; i--) {
                removeAndRecycleViewAt(i, recycler);
            }
            if (getChildCount() == 0) {
                mFirstPosition = 0;
            } else {
                mFirstPosition += first;
            }
        }
    }
}
