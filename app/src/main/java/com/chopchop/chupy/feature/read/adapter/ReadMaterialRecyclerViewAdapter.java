package com.chopchop.chupy.feature.read.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chopchop.chupy.R;
import com.chopchop.chupy.models.ReadMaterial;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReadMaterialRecyclerViewAdapter extends RecyclerView.Adapter<ReadMaterialRecyclerViewAdapter.ViewHolder>{
    private List<ReadMaterial> readMaterialList;

    public ReadMaterialRecyclerViewAdapter(List<ReadMaterial> readMaterialList) {
        this.readMaterialList = readMaterialList;
    }

    public void updateData(List<ReadMaterial> readMaterialList){
        this.readMaterialList = readMaterialList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReadMaterialRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.template_item_read_material, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadMaterialRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.bindData(readMaterialList.get(position));
    }

    @Override
    public int getItemCount() {
        return readMaterialList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemThumbnail;
        private TextView itemTitle;
        private TextView itemDate;
        private TextView itemDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            itemThumbnail = itemView.findViewById(R.id.image_view_item_thumbnail);
            itemTitle = itemView.findViewById(R.id.text_view_item_title);
            itemDate = itemView.findViewById(R.id.text_view_item_date);
            itemDescription = itemView.findViewById(R.id.text_view_item_description);
        }

        public void bindData(ReadMaterial item){

            // dummy image
            if (item.getPhoto() == null){
                itemThumbnail.setBackground(itemView.getResources().getDrawable(R.drawable.chupy_box));
            }
            else{
                Picasso.get().load(item.getPhoto().getHost()+'/'+item.getPhoto().getUrl()).into(itemThumbnail);
            }

            String tempTitle = item.getTitle();
            if (item.getTitle().length() > 40){
                tempTitle = item.getTitle().substring(0,40)+"...";
            }
            itemTitle.setText(tempTitle);

            String tempDesc = item.getDescription();
            if (item.getDescription().length() > 40){
                tempDesc = item.getDescription().substring(0,40)+"...";
            }
            itemDescription.setText(tempDesc);

            itemDate.setText(item.getDate());
        }
    }
}
