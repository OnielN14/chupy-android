package com.chopchop.chupy.feature.search.read;

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
import com.chopchop.chupy.feature.read.ReadDetail;
import com.chopchop.chupy.feature.read.adapter.ReadMaterialItemClickListener;
import com.chopchop.chupy.feature.read.adapter.ReadMaterialRecyclerViewAdapter;
import com.chopchop.chupy.feature.read.utilities.OnItemClickListener;
import com.chopchop.chupy.models.ReadMaterial;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.chopchop.chupy.feature.read.ReadFragmentPagerAdapter.categorizedReadMaterial;

public class ReadNewsFragment extends Fragment {

    private RecyclerView itemsRecyclerView;
    private ReadMaterialRecyclerViewAdapter readMaterialRecyclerViewAdapter;

    private List<ReadMaterial> readMaterialList = new ArrayList<>();
    private int readMaterialCategory = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_read_halaman_non_slider, container, false);

        readMaterialList = categorizedReadMaterial(FragmentRead.searchReadMaterialList, readMaterialCategory);

        itemsRecyclerView = rootView.findViewById(R.id.recycled_view_other_item);
        itemsRecyclerView.setHasFixedSize(true);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        readMaterialRecyclerViewAdapter = new ReadMaterialRecyclerViewAdapter(readMaterialList);
        itemsRecyclerView.setAdapter(readMaterialRecyclerViewAdapter);


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
