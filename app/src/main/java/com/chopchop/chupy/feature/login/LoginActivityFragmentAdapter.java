package com.chopchop.chupy.feature.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class LoginActivityFragmentAdapter extends FragmentStatePagerAdapter {
    private Fragment fragment;
    private String[] nMenu;

    public LoginActivityFragmentAdapter(FragmentManager fm, String[] nMenu) {
        super(fm);
        this.nMenu = nMenu;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                fragment = new LoginFragment();
                break;
            case 1:
                fragment = new RegisterFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return nMenu.length;
    }
}
