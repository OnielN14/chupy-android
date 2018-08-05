package com.chopchop.chupy.feature.read.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.write.NewReadMaterialActivity;
import com.chopchop.chupy.models.ReadMaterial;
import com.chopchop.chupy.utilities.ChupyServiceController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadMaterialRecyclerViewAdapter extends RecyclerView.Adapter<ReadMaterialRecyclerViewAdapter.ViewHolder>{

    public static final int EDITABLE = 0;
    public static final int READ_ONLY = 1;

    private List<ReadMaterial> readMaterialList;
    private int listType = READ_ONLY;

    private ChupyServiceController serviceController = new ChupyServiceController();

    public ReadMaterialRecyclerViewAdapter(List<ReadMaterial> readMaterialList) {
        this.readMaterialList = readMaterialList;
    }

    public ReadMaterialRecyclerViewAdapter(List<ReadMaterial> readMaterialList, int listType) {
        this(readMaterialList);
        this.listType = listType;
    }

    public void updateData(List<ReadMaterial> readMaterialList){
        this.readMaterialList = readMaterialList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReadMaterialRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutResource = R.layout.template_item_read_material;
        if (listType == EDITABLE){
            layoutResource = R.layout.template_item_read_material_user_post;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);

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
        private TextView itemCategory;
        private Button deleteButton;
        private Button editButton;


        public ViewHolder(View itemView) {
            super(itemView);

            itemThumbnail = itemView.findViewById(R.id.image_view_item_thumbnail);
            itemTitle = itemView.findViewById(R.id.text_view_item_title);
            itemDate = itemView.findViewById(R.id.text_view_item_date);
            itemDescription = itemView.findViewById(R.id.text_view_item_description);
            if(listType != READ_ONLY){
                itemCategory = itemView.findViewById(R.id.text_view_item_category);
                deleteButton = itemView.findViewById(R.id.button_delete_post);
                editButton = itemView.findViewById(R.id.button_edit_post);
            }
        }

        public void bindData(final ReadMaterial item){

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

            if (listType != READ_ONLY){
                itemCategory.setText(itemView.getResources().getStringArray(R.array.read_tab_menu)[item.getCategoryId()-1]);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                        builder.setMessage(itemView.getResources().getString(R.string.alert_delete_confirmation)).setPositiveButton(R.string.alert_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(itemView.getContext(), itemView.getResources().getString(R.string.process_message_deleting), Toast.LENGTH_SHORT).show();
                                serviceController.getService().deletePost(item.getId()).enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        switch (response.code()){
                                            case 200:
                                                Toast.makeText(itemView.getContext(), itemView.getResources().getString(R.string.process_message_deleted), Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(itemView.getContext(), itemView.getResources().getString(R.string.process_message_error_undefined), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.create().show();
                    }
                });

                editButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), NewReadMaterialActivity.class);
                        intent.putExtra("kontenData", new Gson().toJson(item));
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }
    }
}
