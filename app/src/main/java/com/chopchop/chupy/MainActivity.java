package com.chopchop.chupy;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.chopchop.chupy.feature.petservice.FragmentPetService;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MainActivity extends AppCompatActivity {

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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new FragmentPetService()).commit();
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
                    selectedFragments = new FragmentPetService();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, selectedFragments).commit();

            return true;
        }
    };
}
