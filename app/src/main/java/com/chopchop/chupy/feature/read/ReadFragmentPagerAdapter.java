package com.chopchop.chupy.feature.read;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ReadFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment fragment;
    private int nMenu = 3;

    private String[] pageTitle = {"News","Article","Tips & Trick"};

    public ReadFragmentPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);

    }

    @Override
    public Fragment getItem(int position) {
//        switch (position){
//            case 0 :
//                fragment = new ReadNewsFragment();
//                break;
//        }
//        return fragment;
        return new ReadNewsFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return pageTitle[position];
    }

    @Override
    public int getCount() {
        return nMenu;
    }
}
