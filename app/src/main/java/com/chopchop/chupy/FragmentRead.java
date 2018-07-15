package com.chopchop.chupy;


import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chopchop.chupy.feature.read.ReadFragmentPagerAdapter;
import com.chopchop.chupy.feature.read.adapter.TagItemClickListener;
import com.chopchop.chupy.feature.read.adapter.TagSearchAdapter;
import com.chopchop.chupy.model.Tag;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRead extends Fragment {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private ReadFragmentPagerAdapter readFragmentPagerAdapter;
    private Resources res;

    private RecyclerView tagRecyclerView;
    private RecyclerView.Adapter tagAdapter;
    private RecyclerView.LayoutManager tagLayoutManager;

    private View searchPanel;
    private EditText searchBar;
    private ImageView searchButton;
    private RelativeLayout searchArea;

    private String[] tabMenu;
    private String[] dummyTags = {"Big Tits", "School Girl", "Vanilla", "Lolicon", "Milf", "Uncensored", "Romance", "Ahegao", "Maid", "Facial", "Fantasy", "Virgin"};
    private List<Tag> tagList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_read, container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar_read);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        searchPanel = (View) rootView.findViewById(R.id.linear_layout_search_tags);
        searchPanel.setVisibility(View.GONE);

        mTabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout_read);

        tagRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycled_view_tag_search);
        tagLayoutManager = new FlowLayoutManager();
        tagRecyclerView.setLayoutManager(tagLayoutManager);
        tagAdapter = new TagSearchAdapter(dummyTags);
        tagRecyclerView.setAdapter(tagAdapter);

        for (String item: dummyTags) {
            tagList.add(new Tag(item));
        }

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

        tagRecyclerView.addOnItemTouchListener(new TagItemClickListener(getActivity(), tagRecyclerView, new TagItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Toast.makeText(getActivity(), dummyTags[position], Toast.LENGTH_SHORT).show();
                TextView textHolder = v.findViewById(R.id.text_view_tag_name);
                int t = textHolder.getPaddingTop();
                int b = textHolder.getPaddingBottom();
                int r = textHolder.getPaddingRight();
                int l = textHolder.getPaddingLeft();
                if (tagList.get(position).getTagStatus() == 0){
                    tagList.get(position).setTagStatus(1);
                    textHolder.setBackground(getActivity().getDrawable(R.drawable.component_rounded_tag_active));
                    textHolder.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                }
                else{
                    tagList.get(position).setTagStatus(0);
                    textHolder.setBackground(getActivity().getDrawable(R.drawable.component_rounded_tag));
                    textHolder.setTextColor(Color.parseColor("#ffffff"));
                }

                textHolder.setPadding(l,t,r,b);
            }

            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        }));

        searchArea = rootView.findViewById(R.id.relLayout);
        searchBar = rootView.findViewById(R.id.input_search);
        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) searchArea.getLayoutParams();
                final float scale = getActivity().getResources().getDisplayMetrics().density;

                if (hasFocus){

                    searchArea.setBackground(getActivity().getDrawable(R.drawable.component_not_rounded_search_bar));

                    viewPager.setVisibility(View.GONE);
                    mTabLayout.setVisibility(View.GONE);
                    searchPanel.setVisibility(View.VISIBLE);
                    marginLayoutParams.setMargins(0,0,0,0);

                    v.requestLayout();
                }
                else{
//                     convert the DP into pixel
                    int l =  (int)(4 * scale + 0.5f);
                    int r =  (int)(4 * scale + 0.5f);
                    int t =  (int)(4 * scale + 0.5f);
                    int b =  (int)(4 * scale + 0.5f);

                    searchArea.setBackground(getActivity().getDrawable(R.drawable.component_rounded_search_bar));

                    viewPager.setVisibility(View.VISIBLE);
                    mTabLayout.setVisibility(View.VISIBLE);
                    searchPanel.setVisibility(View.GONE);

                    marginLayoutParams.setMargins(t,l,r,b);
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
