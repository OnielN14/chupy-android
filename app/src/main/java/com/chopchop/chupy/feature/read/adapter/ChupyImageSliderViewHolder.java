package com.chopchop.chupy.feature.read.adapter;

import android.view.View;
import android.widget.TextView;

import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ChupyImageSliderViewHolder extends ImageSlideViewHolder {

    TextView itemTitle;
    TextView itemDate;

    public ChupyImageSliderViewHolder(View itemView) {
        super(itemView);

    }

    @Override
    public void bindImageSlide(String imageUrl) {
        super.bindImageSlide(imageUrl);
    }

    @Override
    public void bindImageSlide(int imageResourceId) {
        super.bindImageSlide(imageResourceId);
    }

    @Override
    public void bindImageSlide(String url, int placeHolder, int error) {
        super.bindImageSlide(url, placeHolder, error);
    }
}
