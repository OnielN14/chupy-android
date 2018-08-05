package com.chopchop.chupy.feature.petservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chopchop.chupy.R;
import com.chopchop.chupy.Retrofit.ApiClient;
import com.chopchop.chupy.Retrofit.ApiClientInterface;
import com.chopchop.chupy.feature.petservice.adapter.RecyclerViewAdapterProduct;
import com.chopchop.chupy.feature.petservice.models.PetServiceJson;
import com.chopchop.chupy.feature.petservice.models.Example;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTokoActivity extends AppCompatActivity {


    private static final String TAG = "MapsLoc";
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private boolean FLAG_COLLAPSED = true;
    private String title;
    private ApiClientInterface apiClientInterface;
    private int id;
    private TextView nama;
    private TextView deskripsi, alamat, notelp, status;
    private ImageView mapImage, imageToko;
    private Button btnTlp;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Boolean mLocationPermissionsGranted = false;
    private String url;
    private Marker mMarker;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mProduct = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_toko_activity);
        nama = (TextView) findViewById(R.id.detNama);
        deskripsi = (TextView) findViewById(R.id.detDeskripsi);
        alamat = (TextView) findViewById(R.id.detAlamat);
        notelp = (TextView) findViewById(R.id.detTelp);
        mapImage = (ImageView) findViewById(R.id.mapLocation);
        status = (TextView) findViewById(R.id.detStatus);
        imageToko = findViewById(R.id.deskImage);
        btnTlp = findViewById(R.id.btnKontak);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//
        id = Integer.parseInt(getIntent().getExtras().getString("idMarker"));
        title = getIntent().getExtras().getString("title");
        Log.d("Id", id + " " + title);

        setCollaptingToolbar();
        getDetail();
        getImages();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDetail() {
        apiClientInterface = ApiClient.getApiClient().create(ApiClientInterface.class);
        retrofit2.Call<Example> call = apiClientInterface.get();
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                List<PetServiceJson> list = response.body().getData();
                Log.d("Detail", list.get(id - 1).getNama());
                nama.setText(list.get(id - 1).getNama());
                deskripsi.setText(list.get(id - 1).getDeskripsi());
                alamat.setText("Alamat   : " + list.get(id - 1).getAlamat());
                status.setText("Status    : " + list.get(id - 1).getStatusToko());
                notelp.setText("No. Telp : " + list.get(id - 1).getNotelepon());
//
//                petShopM(list.get(id-1).getNama(),Double.valueOf(list.get(id-1).getLatitude()),Double.valueOf(list.get(id-1).getLongitude()), list.get(id-1).getDeskripsi(),list.get(id-1).getAlamat());

                Picasso.get()
                        .load("https://chuppy-rpl.azurewebsites.net" + list.get(id - 1).getFoto())
                        .into(imageToko);

                Drawable circleDrawable = getResources().getDrawable(R.drawable.icon_map_pin_pet_service);
                BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
                Picasso.get()
                        .load("http://maps.google.com/maps/api/staticmap?center="
                                + Double.valueOf(list.get(id - 1).getLatitude()) + "," + Double.valueOf(list.get(id - 1).getLongitude()) + "&zoom=15&markers=icon:https://image.ibb.co/kMpsm8/icon_map_pin_pet_servicesssssssssssssssssssssssss.png|"
                                + Double.valueOf(list.get(id - 1).getLatitude()) + "," + Double.valueOf(list.get(id - 1).getLongitude()) + "|"
                                + Double.valueOf(list.get(id - 1).getLatitude()) + "," + Double.valueOf(list.get(id - 1).getLongitude()) + "&path=color:0x0000FF80|weight:5|"
                                + Double.valueOf(list.get(id - 1).getLatitude()) + "," + Double.valueOf(list.get(id - 1).getLongitude()) + "&size=700x500&sensor=false")
                        .into(mapImage);
                notelp(list.get(id-1).getNotelepon());
//                http://maps.google.com/maps/api/staticmap?center=25.3176452,82.97391440000001,&zoom=15&markers=25.3176452,82.97391440000001|25.3176452,82.97391440000001&path=color:0x0000FF80|weight:5|25.3176452,82.97391440000001&size=175x175&sensor=TRUE_OR_FALSE
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

    private void notelp(final String phoneNumber) {
        btnTlp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
            }
        });

    }
    private void setCollaptingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.pic_appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                collapsingToolbar.setTitle(title);
            }
        });
    }


    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void getImages(){
        mProduct.add("https://image.ibb.co/bXsUzT/icon_indicator_mammals.png");
        mNames.add("Cat");
        mProduct.add("https://image.ibb.co/nNLGeT/icon_indicator_mammals_v2.png");
        mNames.add("Dog");
        mProduct.add("https://image.ibb.co/cuF4Yo/icon_indicator_birds.png");
        mNames.add("Bird");
        mProduct.add("https://image.ibb.co/hnXJto/icon_indicator_reptiles.png");
        mNames.add("Reptile");
        mProduct.add("https://image.ibb.co/bUnJto/icon_indicator_pet_supplies.png");
        mNames.add("Supplies");

        initRecyclerView();
    }

    private void initRecyclerView (){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.imgProduct);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterProduct adapterProduct = new RecyclerViewAdapterProduct(this, mNames, mProduct);
        recyclerView.setAdapter(adapterProduct);
    }



}
