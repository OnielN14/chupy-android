package com.chopchop.chupy.feature.write;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.read.adapter.ReadMaterialRecyclerViewAdapter;
import com.chopchop.chupy.feature.write.adapter.UserKontenListAdapter;
import com.chopchop.chupy.models.ReadMaterial;
import com.chopchop.chupy.utilities.ChupyServiceController;
import com.chopchop.chupy.utilities.SharedPrefManager;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DraftReadMaterialActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private EditText searchBarEditText;
    private ChupyServiceController serviceController = new ChupyServiceController();
    private SharedPrefManager chupySharedPrefManager;

    private List<ReadMaterial> kontenList = new ArrayList<>();
    private List<ReadMaterial.ReadMaterialListByDate> kontenListByDate = new ArrayList<>();

    private LinearLayout loadingArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_read_material);

        chupySharedPrefManager = new SharedPrefManager(this);

        bindView();
        bindData();


    }

    private void bindData() {
        fetchDraftKonten();
    }

    private void fetchDraftKonten() {
        loadingArea.setVisibility(View.VISIBLE);
        serviceController.getService().getDraftKontenFromUser(String.valueOf(chupySharedPrefManager.getSharedId())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                switch (response.code()){
                    case 200:
                        kontenList = serviceController.parseDataKontenFromService(response.body());
                        kontenListByDate = serviceController.parseKontenListByDate(kontenList);
                        recyclerView.setAdapter(new UserKontenListAdapter(kontenListByDate));
                        break;
                }
                loadingArea.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                loadingArea.setVisibility(View.GONE);
            }
        });
    }

    private void bindView() {
        mToolbar = findViewById(R.id.toolbar_write);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.write_draft_page_title);

        loadingArea = findViewById(R.id.linear_layout_loading);
        loadingArea.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recycler_view_user_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchBarEditText = findViewById(R.id.input_search);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
