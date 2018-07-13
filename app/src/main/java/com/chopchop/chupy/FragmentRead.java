package com.chopchop.chupy;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopchop.chupy.feature.read.ReadFragmentPagerAdapter;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRead extends Fragment {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private SearchView searchView;
    private ReadFragmentPagerAdapter readFragmentPagerAdapter;
    private Resources res;
    private String[] tabMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_read, container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar_read);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        mTabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout_read);

        res = getResources();
        tabMenu = res.getStringArray(R.array.read_tab_menu);
        for (String menu: tabMenu) {
            mTabLayout.addTab(mTabLayout.newTab().setText(menu));
        }
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        readFragmentPagerAdapter = new ReadFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mTabLayout.getTabCount());
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager_read);
        viewPager.setAdapter(readFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        searchView = (SearchView) rootView.findViewById(R.id.search_view_read);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                final float scale = getActivity().getResources().getDisplayMetrics().density;

                int l;
                int r;
                int t;
                int b;


                if (hasFocus){
//                     convert the DP into pixel
                    l =  (int)(0 * scale + 0.5f);
                    r =  (int)(0 * scale + 0.5f);
                    t =  (int)(0 * scale + 0.5f);
                    b =  (int)(0 * scale + 0.5f);

                    v.setBackground(getActivity().getDrawable(R.drawable.component_not_rounded_search_bar));
                    marginLayoutParams.setMargins(l, t, r, b);

                    viewPager.setVisibility(View.GONE);
                    mTabLayout.setVisibility(View.GONE);

                    v.requestLayout();
                }
                else{
                    l =  (int)(8 * scale + 0.5f);
                    r =  (int)(8 * scale + 0.5f);
                    t =  (int)(8 * scale + 0.5f);
                    b =  (int)(8 * scale + 0.5f);

                    v.setBackground(getActivity().getDrawable(R.drawable.component_rounded_search_bar));
                    marginLayoutParams.setMargins(l, t, r, b);

                    viewPager.setVisibility(View.VISIBLE);
                    mTabLayout.setVisibility(View.VISIBLE);

                    v.requestLayout();
                }
            }
        });



        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

}
