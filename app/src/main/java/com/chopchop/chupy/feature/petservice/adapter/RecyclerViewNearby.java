package com.chopchop.chupy.feature.petservice.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.petservice.DetailTokoActivity;
import com.chopchop.chupy.feature.petservice.models.PetServiceJson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewNearby extends RecyclerView.Adapter<RecyclerViewNearby.ViewHolder>{

    private ArrayList<String> mNamaToko = new ArrayList<>();
    private ArrayList<String> mDeskTOko = new ArrayList<>();
    private ArrayList<String> mImage = new ArrayList<>();
    private Context mContext;


    private List<PetServiceJson> list;

    public RecyclerViewNearby(Context mContext, List<PetServiceJson> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public RecyclerViewNearby(Context mContext, ArrayList<String> mNamaToko, ArrayList<String> mDeskTOko, ArrayList<String> mImage) {
        this.mNamaToko = mNamaToko;
        this.mDeskTOko = mDeskTOko;
        this.mImage = mImage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_listener, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final PetServiceJson tes = list.get(position);

        Picasso.get()
                .load("https://chuppy-rpl.azurewebsites.net"+tes.getFoto())
                .into(holder.imageView);
//        Glide.with(mContext)
//                .asBitmap()
//                .load(tes.getFoto())
//                .into(holder.imageView);
        holder.namaToko.setText(tes.getNama());
        holder.deskripsi.setText(tes.getDeskripsi());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Coba", tes.getId() +" " + tes.getNama());
                // Toast.makeText(mContext, "tes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, DetailTokoActivity.class);
                intent.putExtra("idMarker", String.valueOf(tes.getId()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView namaToko;
        TextView deskripsi;
        private View mView;

        public ViewHolder(View itemView){
            super(itemView);
            this.mView = itemView;
            imageView = itemView.findViewById(R.id.imageToko);
            namaToko = itemView.findViewById(R.id.namaToko);
            deskripsi = itemView.findViewById(R.id.deskToko);
        }
    }
}
