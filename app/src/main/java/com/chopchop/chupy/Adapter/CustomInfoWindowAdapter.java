package com.chopchop.chupy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chopchop.chupy.R;
import com.chopchop.chupy.models.InfoWindowData;
import com.chopchop.chupy.models.PlaceInfo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.w3c.dom.Text;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

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


        tvTitle.setText(marker.getTitle());
        tvInclude.setText(marker.getSnippet());

        PlaceInfo placeInfo = (PlaceInfo) marker.getTag();

        int imageId = mContext.getResources().getIdentifier(placeInfo.getImage().toLowerCase(),"drawable", mContext.getPackageName());
        tvImage.setImageResource(imageId);


//        tvInclude.setText(placeInfo.getInclude());
        tvAddress.setText(placeInfo.getAddress());
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
