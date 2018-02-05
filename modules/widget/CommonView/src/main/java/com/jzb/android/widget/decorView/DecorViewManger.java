package com.jzb.android.widget.decorView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wikipeng on 2017/6/16.
 */

public class DecorViewManger {
    private static DecorViewManger sDecorViewManger;
    protected      boolean         isDismissAll;
    protected      boolean         isTempDismissAll;
    private        Set<IDecorView> mDecorViewList;

    private DecorViewManger() {
        mDecorViewList = new HashSet<>();
    }

    public static DecorViewManger getInstance() {
        if (sDecorViewManger == null) {
            sDecorViewManger = new DecorViewManger();
        }
        return sDecorViewManger;
    }

    public void dismissAll() {
        if (isDismissAll) {
            return;
        }

        isDismissAll = true;
        Iterator<IDecorView> iterator = mDecorViewList.iterator();
        while (iterator.hasNext()) {
            iterator.next().dismiss(false);
        }
        mDecorViewList.clear();
        isDismissAll = false;
    }

    public void tempDismissAll() {
        if (isTempDismissAll) {
            return;
        }

        isTempDismissAll = true;
        Iterator<IDecorView> iterator = mDecorViewList.iterator();
        while (iterator.hasNext()) {
            iterator.next().tempDismiss();
        }
        mDecorViewList.clear();
        isTempDismissAll = false;
    }

    public int getCount() {
        return mDecorViewList.size();
    }

    public void add(IDecorView decorView) {
        mDecorViewList.add(decorView);
    }

    public void remove(IDecorView decorView) {
        mDecorViewList.remove(decorView);
    }
}
