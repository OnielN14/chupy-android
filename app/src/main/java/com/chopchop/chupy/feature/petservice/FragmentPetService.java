package com.chopchop.chupy.feature.petservice;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chopchop.chupy.feature.petservice.adapter.CustomInfoWindowAdapter;
import com.chopchop.chupy.feature.petservice.adapter.PlaceAutoCompleteAdapter;
import com.chopchop.chupy.R;
import com.chopchop.chupy.Retrofit.ApiClient;
import com.chopchop.chupy.Retrofit.ApiClientInterface;
import com.chopchop.chupy.feature.petservice.adapter.RecyclerViewAdapterProduct;
import com.chopchop.chupy.feature.petservice.adapter.RecyclerViewNearby;
import com.chopchop.chupy.feature.petservice.models.PetServiceJson;
import com.chopchop.chupy.feature.petservice.models.Example;
import com.chopchop.chupy.feature.petservice.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPetService extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener{


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();

        }
    }


    private static final String TAG = "FragmentPetService";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));

//    private static final LatLng PetShop1 = new LatLng(-6.8898286,107.6168761);

    //Widget
    private AutoCompleteTextView mSearchText;
    private ImageView imgSearchIcon;
    private ImageView mGps;
    private SlidingUpPanelLayout layout;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutoCompleteAdapter mPlaceAutoCompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private Marker mMarker;
    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();

    private ApiClientInterface apiClientInterface;

    private View view;
    private PetServiceJson coba;

    RelativeLayout relativeLayout;
    BottomSheetBehavior bottomSheetBehavior;

    private ArrayList<String> mImgToko = new ArrayList<>();
    private ArrayList<String> mNamaToko = new ArrayList<>();
    private ArrayList<String> mDeskToko = new ArrayList<>();

    public FragmentPetService() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_petservice, container, false);
        mSearchText = (AutoCompleteTextView) view.findViewById(R.id.input_search);
        imgSearchIcon = (ImageView) view.findViewById(R.id.ic_magnify);
        mGps = (ImageView) view.findViewById(R.id.ic_gps);
        layout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);

        imgSearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocate();
                hideSoftKeyboard();
            }
        });
        getMarker();

        getLocationPermission();
        return view;

    }


    private void getMarker(){
        Log.d("marker","zzzz");
        apiClientInterface = ApiClient.getApiClient().create(ApiClientInterface.class);
        retrofit2.Call<Example> call = apiClientInterface.get();
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(retrofit2.Call<Example> call, Response<Example> response) {
                List<PetServiceJson> list= response.body().getData();

                Log.d(TAG, "onResponse: coba");
                for (int i = 0; i<list.size(); i++){
                    Log.d(TAG, "onResponse: coba1");
                    petShop1(list.get(i).getNama(),Double.valueOf(list.get(i).getLatitude()),Double.valueOf(list.get(i).getLongitude()), list.get(i).getDeskripsi(),list.get(i).getAlamat(),list.get(i).getFoto());
                    mHashMap.put(mMarker, Integer.valueOf(list.get(i).getId()));
                    getImages(list.get(i).getFoto(), list.get(i).getNama(),list.get(i).getDeskripsi(),list.get(i).getId());

                }
            }

            @Override
            public void onFailure(retrofit2.Call<Example> call, Throwable t) {

            }
        });
    }

    public void init() {
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        mSearchText.setOnItemClickListener(mAutoCompleteClickListener);

        mPlaceAutoCompleteAdapter = new PlaceAutoCompleteAdapter(getActivity(), mGoogleApiClient, LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutoCompleteAdapter);

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                    geoLocate();
//                    return true;
                }
                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });

//        try{
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Log.d(TAG, "onClick: clicked place info");
//                    try {
                        if (mMarker.isInfoWindowShown()){
                            mMarker.hideInfoWindow();
                        }else {
//                            Log.d(TAG, "onClick: placce info: " + mPlace.toString());
                            mMarker.showInfoWindow();
                        }
//                    }catch (NullPointerException e){
//                        Log.d(TAG, "onClick: NullPointerException " + e.getMessage());
//                    }
                    return false;
                }
            });

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Log.d("Marker ID", String.valueOf(mHashMap.get(marker)));
                    Intent intent = new Intent(getContext(), DetailTokoActivity.class);
                    intent.putExtra("idMarker", String.valueOf(mHashMap.get(marker)));
                    intent.putExtra("title", marker.getTitle());

                    startActivity(intent);
                }
            });
  //      }catch (NullPointerException e){
//            e.printStackTrace();
//        }




//        hideSoftKeyboard();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(Objects.requireNonNull(getActivity()));
            mGoogleApiClient.disconnect();
        }
    }



//    public void onPause() {
//            super.onPause();
////        mGoogleApiClient.stopAutoManage(getActivity());
//            mGoogleApiClient.stopAutoManage(Objects.requireNonNull(getActivity()));
//            mGoogleApiClient.disconnect();
//    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            try{
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                        DEFAULT_ZOOM,
                                        "My Location");
                            }catch (NullPointerException e){
                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                                dialogBuilder.setTitle("Attention").setMessage("Please, turn on your GPS service");

                                AlertDialog dialog = dialogBuilder.create();
                                dialog.show();
                            }


                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(getContext(), "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle("Attention").setMessage("In order to run this app you need to give permission to this app");

            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }catch(NullPointerException e){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            dialogBuilder.setTitle("Attention").setMessage("Please, turn on your GPS service");

            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }
    }

    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getContext()));

        if(placeInfo != null){
            try{
//                String snippet = "Address: " + placeInfo.getAddress() + "\n" +
//                        "Phone Number: " + placeInfo.getPhoneNumber() + "\n" +
//                        "Website: " + placeInfo.getWebsiteUri() + "\n" +
//                        "Price Rating: " + placeInfo.getRating() + "\n";
//
//                MarkerOptions options = new MarkerOptions()
//                        .position(latLng)
//                        .title(placeInfo.getName())
//                        .snippet(snippet);
//                mMarker = mMap.addMarker(options);

            }catch (NullPointerException e){
                Log.e(TAG, "moveCamera: NullPointerException: " + e.getMessage() );
            }
        }else{
//            mMap.addMarker(new MarkerOptions().position(latLng));
        }

        hideSoftKeyboard();
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
//            MarkerOptions options = new MarkerOptions()
//                    .position(latLng)
//                    .title(title);
//            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");

                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }


    public void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        
        if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "getLocationPermission: cobahayoo1");
            if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                Log.d(TAG, "getLocationPermission: cobahayoo2");
                initMap();
            }else{
                ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(getActivity(), permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void popup(){
        Log.d(TAG, "popup: gegegeg");
        Fragment fragment2 = new Fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =        fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();

                }
            }
        }
    }



    private void hideSoftKeyboard(){
        final InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    /*------------------------------------- Google places API autocomplete sugestion --------------------------------------*/
    private AdapterView.OnItemClickListener mAutoCompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutoCompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            getMarker();
//            petShop1();
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if(!places.getStatus().isSuccess()){
                Log.d(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try{
//                mPlace = new PlaceInfo();
//                mPlace.setName(place.getName().toString());
//                Log.d(TAG, "onResult: name: " + place.getName());
//                mPlace.setAddress(place.getAddress().toString());
//                Log.d(TAG, "onResult: address: " + place.getAddress());
////                mPlace.setAttributions(place.getAttributions().toString());
////                Log.d(TAG, "onResult: attributions: " + place.getAttributions());
//                mPlace.setId(place.getId());
//                Log.d(TAG, "onResult: id:" + place.getId());
//                mPlace.setLatlng(place.getLatLng());
//                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
//                mPlace.setRating(place.getRating());
//                Log.d(TAG, "onResult: rating: " + place.getRating());
//                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
//                Log.d(TAG, "onResult: phone number: " + place.getPhoneNumber());
//                mPlace.setWebsiteUri(place.getWebsiteUri());
//                Log.d(TAG, "onResult: website uri: " + place.getWebsiteUri());
//
//                Log.d(TAG, "onResult: place: " + mPlace.toString());
            }catch (NullPointerException e){
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage() );
            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace);
//            petShop1();
            getMarker();
            places.release();
        }
    };

    private void petShop1(String nama, Double latitude, Double longitude, String deskripsi, String alamat, String foto){
        try{
            Drawable circleDrawable = getResources().getDrawable(R.drawable.icon_map_pin_pet_service);
            BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);



            MarkerOptions options = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(nama)
                    .snippet(deskripsi)
                    .icon(markerIcon);

            PlaceInfo info = new PlaceInfo();
            info.setImage(foto);
            info.setAddress(alamat);

            if (mLocationPermissionsGranted){
                CustomInfoWindowAdapter customInfoWindow = new CustomInfoWindowAdapter(getActivity());
                mMap.setInfoWindowAdapter(customInfoWindow);
                mMarker = mMap.addMarker(options);
                mMarker.setTag(info);
            }
        }catch (IllegalStateException e){

        }

    }


    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    List<PetServiceJson> list = new ArrayList<PetServiceJson>();

    private void getImages(String foto, String nama, String deskripsi, int id){
        mImgToko.add("https://image.ibb.co/n4Dp18/contoh1.jpg");
        mNamaToko.add(nama);
        mDeskToko.add(deskripsi);
        Log.d(TAG, "getImages: recyc");

        PetServiceJson serviceJson = new PetServiceJson();
        serviceJson.setNama(nama);
        serviceJson.setDeskripsi(deskripsi);
        serviceJson.setId(id);
        serviceJson.setFoto(foto);
        list.add(serviceJson);

        //        mImgToko.add("https://image.ibb.co/bXsUzT/icon_indicator_mammals.png");
//        mNamaToko.add("Cat");
//        mDeskToko.add("korong");

        initRecyclerViewNearby();
    }
    private void initRecyclerViewNearby (){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.detToko);
        recyclerView.setLayoutManager(layoutManager);
//        RecyclerViewNearby adapterProduct = new RecyclerViewNearby(getContext(), mNamaToko, mDeskToko,mImgToko);

        RecyclerViewNearby adapterProduct = new RecyclerViewNearby(getContext(), list);
        recyclerView.setAdapter(adapterProduct);
    }

}
