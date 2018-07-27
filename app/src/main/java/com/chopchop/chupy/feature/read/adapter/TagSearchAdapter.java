package com.chopchop.chupy.feature.read.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chopchop.chupy.R;
import com.chopchop.chupy.models.Tag;

import java.util.List;

public class TagSearchAdapter extends RecyclerView.Adapter<TagSearchAdapter.ViewHolder> {
    private List<Tag> dataSet;

    public TagSearchAdapter(List<Tag> dummyTags) {
        this.dataSet = dummyTags;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.template_item_tag, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tagName.setText(dataSet.get(position).getTagName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tagName;

        public ViewHolder(View itemView) {
            super(itemView);
            tagName = itemView.findViewById(R.id.text_view_tag_name);
        }
    }
}
