package com.chopchop.chupy;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopchop.chupy.feature.read.ReadFragmentPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRead extends Fragment {

    private ActionBar actionBar;
    private ViewPager viewPager;
    private ReadFragmentPagerAdapter readFragmentPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_read, container, false);

        actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        readFragmentPagerAdapter = new ReadFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager_read);
        viewPager.setAdapter(readFragmentPagerAdapter);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        for (int i = 0; i<readFragmentPagerAdapter.getCount(); i++){
            actionBar.addTab(actionBar.newTab().setText(readFragmentPagerAdapter.getPageTitle(i)).setTabListener(tabListener));
        }

        return rootView;
    }

}
