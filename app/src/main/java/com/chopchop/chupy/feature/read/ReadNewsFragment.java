package com.chopchop.chupy.feature.read;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.read.adapter.ReadMaterialRecyclerViewAdapter;
import com.chopchop.chupy.model.ReadMaterial;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class ReadNewsFragment extends Fragment {

    private RecyclerView itemsRecyclerView;
    private ReadMaterialRecyclerViewAdapter readMaterialRecyclerViewAdapter;

    private Slider slider;
    private List<ReadMaterial> readMaterialList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_read_halaman_news, container, false);

        // dummy data
        dummyDataInitialization();

        itemsRecyclerView = rootView.findViewById(R.id.recycled_view_other_item);
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readMaterialRecyclerViewAdapter = new ReadMaterialRecyclerViewAdapter(readMaterialList);
        itemsRecyclerView.setAdapter(readMaterialRecyclerViewAdapter);

        slider = rootView.findViewById(R.id.slider_top_read_material);

        return rootView;
    }

    /*
        this method is used to populate dummy data
        remove if you feel this method is not necessary
     */
    public void dummyDataInitialization(){
        readMaterialList.add(new ReadMaterial(1,"Thyroid Tumor Surgery in Cats","This just a description. You shouldn't worry about this.","20 Jul 2018","catlovers.jpg"));
        readMaterialList.add(new ReadMaterial(2,"Do Dogs Dream?","This just a description. You shouldn't worry about this.","20 Jul 2018","sleeping-dog.jpg"));
        readMaterialList.add(new ReadMaterial(3,"How My Former Puppy Mill Dog Changed All of My Pet Health","This just a description. You shouldn't worry about this.","20 Jul 2018","cutepuppy.jpg"));
        readMaterialList.add(new ReadMaterial(4,"The Story of My Aroused Cat Girl","This just a description. You shouldn't worry about this.","20 Jul 2018","cutepuppy.jpg"));
    }
}
