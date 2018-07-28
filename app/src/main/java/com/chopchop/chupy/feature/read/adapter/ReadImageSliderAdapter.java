package com.chopchop.chupy.feature.read.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chopchop.chupy.R;
import com.chopchop.chupy.models.ReadMaterial;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReadImageSliderAdapter extends PagerAdapter {
    private List<ReadMaterial> readMaterials;
    private LayoutInflater inflater;
    private Context context;

    public ReadImageSliderAdapter(List<ReadMaterial> readMaterials, Context context) {
        this.readMaterials = readMaterials;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageSliderPage = inflater.inflate(R.layout.template_image_slider_item, container, false);

        ImageView sliderImage = imageSliderPage.findViewById(R.id.image_view_slider_image);
        TextView sliderDate = imageSliderPage.findViewById(R.id.text_view_slider_item_date);
        TextView sliderTitle = imageSliderPage.findViewById(R.id.text_view_slider_item_title);

        sliderDate.setText(readMaterials.get(position).getDate());
        sliderTitle.setText(readMaterials.get(position).getTitle());

        if (readMaterials.get(position).getPhoto() == null){
            sliderImage.setBackground(container.getResources().getDrawable(R.drawable.chupy_box));
        }
        else{
            Picasso.get().load(readMaterials.get(position).getPhoto().getHost()+'/'+readMaterials.get(position).getPhoto().getUrl()).into(sliderImage);
        }

        container.addView(imageSliderPage);

        return imageSliderPage;
    }

    @Override
    public int getCount() {
        return readMaterials.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}
