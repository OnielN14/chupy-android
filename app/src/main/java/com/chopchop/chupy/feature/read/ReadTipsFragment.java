package com.chopchop.chupy.feature.read;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopchop.chupy.FragmentRead;
import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.read.adapter.ReadMaterialItemClickListener;
import com.chopchop.chupy.feature.read.adapter.ReadMaterialRecyclerViewAdapter;
import com.chopchop.chupy.feature.read.adapter.ReadMaterialSliderAdapter;
import com.chopchop.chupy.feature.read.utilities.OnItemClickListener;
import com.chopchop.chupy.feature.read.utilities.PicassoImageLoadingService;
import com.chopchop.chupy.model.ReadMaterial;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

import static com.chopchop.chupy.feature.read.ReadFragmentPagerAdapter.categorizedReadMaterial;

public class ReadTipsFragment extends Fragment {

    private RecyclerView itemsRecyclerView;
    private ReadMaterialRecyclerViewAdapter readMaterialRecyclerViewAdapter;

    private Slider slider;
    private ReadMaterialSliderAdapter readMaterialSliderAdapter;

    private List<ReadMaterial> readMaterialList = new ArrayList<>();
    private int readMaterialCategory = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_read_halaman, container, false);

        readMaterialList = categorizedReadMaterial(FragmentRead.readMaterialList, readMaterialCategory);

        itemsRecyclerView = rootView.findViewById(R.id.recycled_view_other_item);
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readMaterialRecyclerViewAdapter = new ReadMaterialRecyclerViewAdapter(readMaterialList);
        itemsRecyclerView.setAdapter(readMaterialRecyclerViewAdapter);

        Slider.init(new PicassoImageLoadingService(getActivity()));
        slider = rootView.findViewById(R.id.slider_top_read_material);
        readMaterialSliderAdapter = new ReadMaterialSliderAdapter(readMaterialList);
        slider.setAdapter(readMaterialSliderAdapter);

        itemsRecyclerView.addOnItemTouchListener(new ReadMaterialItemClickListener(getContext(), itemsRecyclerView, new OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                openReadMaterialDetail(readMaterialList.get(position));

            }

            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        }));


        return rootView;
    }

    private void openReadMaterialDetail(ReadMaterial readMaterial) {

        Intent intent = new Intent(getActivity(), ReadDetail.class);
        intent.putExtra("readObject", new Gson().toJson(readMaterial));
        startActivity(intent);

    }
}
