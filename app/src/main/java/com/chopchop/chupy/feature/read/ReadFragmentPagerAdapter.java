package com.chopchop.chupy.feature.read;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.chopchop.chupy.FragmentRead;
import com.chopchop.chupy.model.ReadMaterial;

import java.util.ArrayList;
import java.util.List;

public class ReadFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment fragment;
    private String[] nMenus;

//    private String[] pageTitle = {"News","Article","Tips & Trick"};

    public ReadFragmentPagerAdapter(FragmentManager supportFragmentManager, String[] nMenus) {
        super(supportFragmentManager);
        this.nMenus = nMenus;

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

    public static List<ReadMaterial> categorizedReadMaterial(List<ReadMaterial> readMaterialList, int readMaterialCategory) {
        List<ReadMaterial> tempList = new ArrayList<>();
        for (ReadMaterial item : readMaterialList){
            if (item.getCategoryId() == readMaterialCategory){
                tempList.add(item);
            }
        }

        return tempList;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return nMenus[position];
    }

    @Override
    public int getCount() {
        return nMenus.length;
    }


}
