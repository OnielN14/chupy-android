package com.chopchop.chupy;


import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chopchop.chupy.feature.read.ReadFragmentPagerAdapter;
import com.chopchop.chupy.feature.read.adapter.TagItemClickListener;
import com.chopchop.chupy.feature.read.adapter.TagSearchAdapter;
import com.chopchop.chupy.feature.read.utilities.OnItemClickListener;
import com.chopchop.chupy.feature.read.utilities.ReadMaterialController;
import com.chopchop.chupy.model.Photo;
import com.chopchop.chupy.model.ReadMaterial;
import com.chopchop.chupy.model.Tag;
import com.chopchop.chupy.utilities.ChupyService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRead extends Fragment {

    ReadMaterialController controller = new ReadMaterialController();

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
//    private String[] dummyTags = {"Big Tits", "School Girl", "Vanilla", "Lolicon", "Milf", "Uncensored", "Romance", "Ahegao", "Maid", "Facial", "Fantasy", "Virgin"};

    public static List<Tag> tagList = new ArrayList<>();
    public static List<ReadMaterial> readMaterialList = new ArrayList<>();


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



        res = getResources();
        tabMenu = res.getStringArray(R.array.read_tab_menu);
        for (String menu: tabMenu) {
            mTabLayout.addTab(mTabLayout.newTab().setText(menu));
        }
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        readFragmentPagerAdapter = new ReadFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mTabLayout.getTabCount());
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager_read);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


        fetchReadMaterialAndSetupAdapter();

        tagRecyclerView.addOnItemTouchListener(new TagItemClickListener(getActivity(), tagRecyclerView, new OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
//                Toast.makeText(getActivity(), dummyTags[position], Toast.LENGTH_SHORT).show();
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

    private void fetchReadMaterialAndSetupAdapter(){
        controller.fetchReadMaterialCall().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                readMaterialList = parseDataFromService(response.body());

                tagAdapter = new TagSearchAdapter(tagList);

                viewPager.setAdapter(readFragmentPagerAdapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private List<ReadMaterial> parseDataFromService(JsonObject response){

        List<ReadMaterial> tempReadMaterials = new ArrayList<>();
        List<Tag> contentTagList, tempGlobalTagList = new ArrayList<>();
        Photo tempPhoto;
        JsonArray data = response.getAsJsonArray("data");
        for (JsonElement item: data) {


            if (item.getAsJsonObject().get("foto").getAsJsonArray().size() != 0){
                tempPhoto = parsePhotoFromData(item.getAsJsonObject().get("foto").getAsJsonArray().get(0));
            }
            else{
                tempPhoto = null;
            }

            contentTagList = parseTagFromContent(item.getAsJsonObject().get("tag"));

            tempGlobalTagList.addAll(contentTagList);

            tempReadMaterials.add(new ReadMaterial(item.getAsJsonObject().get("id").getAsInt(), item.getAsJsonObject().get("judul").getAsString(), item.getAsJsonObject().get("deskripsi").getAsString(), item.getAsJsonObject().get("kategori").getAsString(), item.getAsJsonObject().get("idKategori").getAsInt(), "24 Juli 2018", tempPhoto, contentTagList));

        }

        initiateTagRecyclerView(tempGlobalTagList);

        return tempReadMaterials;
    }

    private void initiateTagRecyclerView(List<Tag> tempTagList) {

        tagList.clear();

        Set<String> title = new HashSet<>();
        for (Tag item : tempTagList){
            if (title.add(item.getTagName())){
                tagList.add(item);
            }
        }


        tagAdapter = new TagSearchAdapter(tagList);
        tagRecyclerView.setAdapter(tagAdapter);
    }

    private List<Tag> parseTagFromContent(JsonElement tag) {
        List<Tag> tempTag = new ArrayList<>();
        for (JsonElement item : tag.getAsJsonArray()){
            tempTag.add(new Tag(item.getAsJsonObject().get("id").getAsInt(), item.getAsJsonObject().get("tag").getAsString()));
        }

        return tempTag;
    }

    private Photo parsePhotoFromData(JsonElement foto){
        return new Photo(foto.getAsJsonObject().get("id").getAsInt(), foto.getAsJsonObject().get("foto").getAsString(), ChupyService.baseUrl);
    }


}
