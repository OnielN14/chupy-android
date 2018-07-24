package com.chopchop.chupy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chopchop.chupy.Retrofit.ApiClient;
import com.chopchop.chupy.Retrofit.ApiClientInterface;
import com.chopchop.chupy.models.CobaJson;
import com.chopchop.chupy.models.Example;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class DetailTokoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private boolean FLAG_COLLAPSED = true;
    private String title;
    private ApiClientInterface apiClientInterface;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_toko_activity);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        id = Integer.parseInt(getIntent().getExtras().getString("idMarker"));
        title = getIntent().getExtras().getString("title");
        Log.d("Id", id + " " + title);

        setCollaptingToolbar();
        getMarker();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void getMarker(){
        Log.d("marker","zzzz");
        apiClientInterface = ApiClient.getApiClient().create(ApiClientInterface.class);
        retrofit2.Call<Example> call = apiClientInterface.getDetail(id);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(retrofit2.Call<Example> call, Response<Example> response) {
                List<CobaJson> list= response.body().getData();
                Log.d("Detail", list.get(0).getNama());
            }

            @Override
            public void onFailure(retrofit2.Call<Example> call, Throwable t) {

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
                collapsingToolbar.setTitle(title);
            }
        });
    }
}
