package com.chopchop.chupy;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.chopchop.chupy.feature.login.LoginActivityFragmentAdapter;
import com.chopchop.chupy.utilities.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager loginPageViewPager;
    private TabLayout loginPageTabLayout;
    private LoginActivityFragmentAdapter loginFragmentAdapter;

    private SharedPrefManager chupySharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        chupySharedPrefManager = new SharedPrefManager(this);
        if (chupySharedPrefManager.getSharedLoggedStatus()){
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        bindView();

    }

    private void bindView() {
        mToolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(mToolbar);

        loginPageTabLayout = findViewById(R.id.tab_layout_login);
        for(String menuTitle : getResources().getStringArray(R.array.login_tab_menu)){
            loginPageTabLayout.addTab(loginPageTabLayout.newTab().setText(menuTitle));
        }

        loginFragmentAdapter = new LoginActivityFragmentAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.login_tab_menu));
        loginPageViewPager = findViewById(R.id.view_pager_login);
        loginPageViewPager.setAdapter(loginFragmentAdapter);
        loginPageViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(loginPageTabLayout));
        loginPageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loginPageViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
