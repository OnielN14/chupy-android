package com.chopchop.chupy.feature.search.read;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SearchReadFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment fragment;
    private int nMenu;

    private String[] pageTitle = {"News","Article","Tips & Trick"};
//    private int readMaterialCategory[] = {1, 2, 3};

    public SearchReadFragmentPagerAdapter(FragmentManager supportFragmentManager, int nMenus) {
        super(supportFragmentManager);
        this.nMenu = nMenus;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                fragment = new ReadNewsFragment();
                break;
            case 1 :
                fragment = new ReadArticleFragment();
                break;
            case 2 :
                fragment = new ReadTipsFragment();
                break;

        }

        return fragment;
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
