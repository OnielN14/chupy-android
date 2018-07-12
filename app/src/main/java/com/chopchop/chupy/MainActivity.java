package com.chopchop.chupy;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationViewEx navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navbar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navbar.enableAnimation(false);
        navbar.enableItemShiftingMode(false);
        navbar.enableShiftingMode(false);

        navbar.setOnNavigationItemSelectedListener(botnav);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, new FragmentPetService()).commit();
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
