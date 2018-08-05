package com.chopchop.chupy.feature.write.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.read.adapter.ReadMaterialRecyclerViewAdapter;
import com.chopchop.chupy.models.ReadMaterial;

import java.util.List;

public class UserKontenListAdapter extends RecyclerView.Adapter<UserKontenListAdapter.ViewHolder> {

    private List<ReadMaterial.ReadMaterialListByDate> dataList;

    public UserKontenListAdapter(List<ReadMaterial.ReadMaterialListByDate> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_write_main_content_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView readMaterialRecyclerView;
        private ReadMaterialRecyclerViewAdapter readMaterialRecyclerViewAdapter;
        private TextView date;

        public ViewHolder(View itemView) {
            super(itemView);

            readMaterialRecyclerView = itemView.findViewById(R.id.recycler_view_user_post_child);
            readMaterialRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false));

            date = itemView.findViewById(R.id.text_view_list_date);

        }

        private void bindData(ReadMaterial.ReadMaterialListByDate readMaterialListByDate){
            date.setText(readMaterialListByDate.getDate().toString());

            readMaterialRecyclerViewAdapter = new ReadMaterialRecyclerViewAdapter(readMaterialListByDate.getReadMaterialList(), ReadMaterialRecyclerViewAdapter.EDITABLE);
            readMaterialRecyclerView.setAdapter(readMaterialRecyclerViewAdapter);
        }
    }
}
