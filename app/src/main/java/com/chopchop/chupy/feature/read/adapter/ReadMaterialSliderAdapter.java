package com.chopchop.chupy.feature.read.adapter;

import com.chopchop.chupy.R;
import com.chopchop.chupy.model.ReadMaterial;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ReadMaterialSliderAdapter extends SliderAdapter{
    private List<ReadMaterial> readMaterialList;

    public ReadMaterialSliderAdapter(List<ReadMaterial> readMaterialList) {
        this.readMaterialList = readMaterialList;
    }

    @Override
    public int getItemCount() {
        return readMaterialList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {
        switch (position){
            case 1:
            case 3:
                imageSlideViewHolder.bindImageSlide(R.drawable.chupy_box);
                break;

            default:
            case 2:
            case 4:
                imageSlideViewHolder.bindImageSlide(R.drawable.chupy_box_colorful);
                break;
        }
    }
}
