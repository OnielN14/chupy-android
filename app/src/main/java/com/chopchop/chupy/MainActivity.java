package com.chopchop.chupy;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.chopchop.chupy.feature.petservice.FragmentPetService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MainActivity extends AppCompatActivity {

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String TAG = "Request";
    private BottomNavigationViewEx navbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getReadStoragePermission();

        navbar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navbar.enableAnimation(false);
        navbar.enableItemShiftingMode(false);
        navbar.enableShiftingMode(false);

        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);

        navbar.setOnNavigationItemSelectedListener(botnav);

        if (savedInstanceState == null){
            if(isServicesOK()){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new FragmentPetService()).commit();
            }

        }
    }

    private void getReadStoragePermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);


        Log.d(TAG, "getReadStoragePermission: getting read storage permissions");
        String permissions = Manifest.permission.READ_EXTERNAL_STORAGE;

        if(ContextCompat.checkSelfPermission(this, permissions) == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{permissions},permissionCheck);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener botnav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragments = null;

            switch (item.getItemId()) {
                case R.id.nav_petservice:
                    if (isServicesOK()){
                        selectedFragments = new FragmentPetService();
                    }
                    break;
                case R.id.nav_read:
                    selectedFragments = new FragmentRead();
                    break;
                case R.id.nav_write:
                    selectedFragments = new FragmentWrite();
                    break;
                case R.id.nav_profile:
                    selectedFragments = new FragmentProfile();
                    break;

            }
            if (isServicesOK()){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, selectedFragments).commit();
            }
            return true;
        }
    };

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
