package com.chopchop.chupy.feature.petservice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chopchop.chupy.R;

import java.util.ArrayList;

public class RecyclerViewAdapterRegister extends RecyclerView.Adapter<RecyclerViewAdapterRegister.ViewHolder> {
    private static final String TAG = "RC";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mProduct = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterRegister(Context context, ArrayList<String> names, ArrayList<String> product) {
        this.mNames = names;
        this.mProduct = product;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_listener, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mProduct.get(position))
                .into(holder.imageView);
        holder.name.setText(mNames.get(position));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;

        public ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageProduct);
            name = itemView.findViewById(R.id.productName);
        }
    }
}
