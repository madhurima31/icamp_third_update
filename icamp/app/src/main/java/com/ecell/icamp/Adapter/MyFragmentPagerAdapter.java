package com.ecell.icamp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by MadhuRima on 17-05-2017.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> listfragments;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> listFragments) {
        super(fm);
        this.listfragments= listFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return listfragments.get(position);
    }

    @Override
    public int getCount() {
        return listfragments.size();
    }
}
