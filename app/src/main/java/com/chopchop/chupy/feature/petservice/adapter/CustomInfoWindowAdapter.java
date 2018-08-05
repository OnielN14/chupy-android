package com.chopchop.chupy.feature.petservice.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.petservice.models.PlaceInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

public class CustomInfoWindowAdapter extends AppCompatActivity implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void rendowWindowText(Marker marker, View view){

        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        ImageView tvImage = (ImageView) view.findViewById(R.id.icon_places);
        TextView tvInclude = (TextView) view.findViewById(R.id.include);
        TextView tvAddress = (TextView) view.findViewById(R.id.address);

//        tvInclude.setSelected(true);

        tvTitle.setText(marker.getTitle());
        tvInclude.setText(marker.getSnippet());

        PlaceInfo placeInfo = (PlaceInfo) marker.getTag();


        Picasso.get()
                .load("https://chuppy-rpl.azurewebsites.net"+placeInfo.getImage())
                .into(tvImage);


//        int imageId = mContext.getResources().getIdentifier(placeInfo.getImage().toLowerCase(),"drawable", mContext.getPackageName());
//        tvImage.setImageResource(imageId);


//        tvInclude.setText(placeInfo.getInclude());
        tvAddress.setText(placeInfo.getAddress());         tvAddress.setText(placeInfo.getAddress());
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
