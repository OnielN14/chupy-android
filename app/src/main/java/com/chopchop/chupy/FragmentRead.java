package com.chopchop.chupy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopchop.chupy.feature.read.ReadFragmentPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRead extends Fragment {

    private ViewPager viewPager;
    private ReadFragmentPagerAdapter readFragmentPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_read, container, false);

        readFragmentPagerAdapter = new ReadFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager = (ViewPager) container.findViewById(R.id.view_pager_read);


        return rootView;
    }

}
