package com.jzb.android.widget.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by wikipeng on 2017/5/22.
 */

public abstract class SmartViewPagerAdapter extends FragmentStatePagerAdapter {
    protected SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public SmartViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        if (registeredFragments.size() >= position && position < 0) {
            return null;
        }
        return registeredFragments.get(position);
    }
}
