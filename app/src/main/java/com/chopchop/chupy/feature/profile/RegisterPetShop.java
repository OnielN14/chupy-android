package com.chopchop.chupy.feature.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chopchop.chupy.R;
import com.chopchop.chupy.Retrofit.ApiClient;
import com.chopchop.chupy.Retrofit.ApiClientInterface;
import com.chopchop.chupy.feature.petservice.adapter.RecyclerViewAdapterProduct;
import com.chopchop.chupy.feature.petservice.adapter.RecyclerViewAdapterRegister;
import com.chopchop.chupy.feature.petservice.models.Example;
import com.chopchop.chupy.feature.petservice.models.PetServiceJson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPetShop extends AppCompatActivity{

    private static final String TAG = "Regis";

    private Button addLoc;

    private Toolbar toolbar;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mProduct = new ArrayList<>();

    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    private ArrayList<Integer> selectIds = new ArrayList<>();
    private RecyclerViewAdapterRegister recyclerViewAdapterRegister;
    private ApiClientInterface apiClientInterface;

    private Double lat,lng;
    private TextInputEditText petShopName;
    private TextInputEditText desc;
    private TextInputEditText alamat;
    private TextInputEditText status;
    private TextInputEditText notelp;
    private Button regis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_petshop);

        petShopName = findViewById(R.id.edShopName);
        desc = findViewById(R.id.edDesc);
        alamat = findViewById(R.id.edAlamat);
        status = findViewById(R.id.edStatus);
        notelp = findViewById(R.id.edNoTelp);
        regis = findViewById(R.id.btnRegis);

//        recyclerViewAdapterRegister = new RecyclerViewAdapterRegister(this, getImages());

        toolbar = findViewById(R.id.toolbar_regis);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Register Pet Shop ");
        }

        addLoc = findViewById(R.id.pickLoc);
        addLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPetShop.this, AddLocation.class);
                startActivity(intent);
            }
        });

        try{
            lat = getIntent().getExtras().getDouble("lat");
            lng = getIntent().getExtras().getDouble("lng");
        }catch (NullPointerException e){

        }

        Log.d(TAG, "onCreatedds: "+lat+ ","+lng);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPetShop();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }



    private void postPetShop(){
        final String nama = petShopName.getText().toString();
        final String deskripsi = desc.getText().toString();
        String address = alamat.getText().toString();
        String statusT = status.getText().toString();
        String noTlp = notelp.getText().toString();

        apiClientInterface = ApiClient.getApiClient().create(ApiClientInterface.class);
        retrofit2.Call<Example> post = apiClientInterface.post(nama,deskripsi,address,statusT,noTlp,lat,lng);
        post.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Toast.makeText(getApplicationContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponsesss: "+nama+","+deskripsi+","+lat+","+lng);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });

    }
}
