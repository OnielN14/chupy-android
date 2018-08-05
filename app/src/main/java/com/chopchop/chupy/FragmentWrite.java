package com.chopchop.chupy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chopchop.chupy.feature.write.DraftReadMaterialActivity;
import com.chopchop.chupy.feature.write.NewReadMaterialActivity;
import com.chopchop.chupy.feature.write.adapter.UserKontenListAdapter;
import com.chopchop.chupy.models.ReadMaterial;
import com.chopchop.chupy.models.Tag;
import com.chopchop.chupy.utilities.ChupyServiceController;
import com.chopchop.chupy.utilities.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWrite extends Fragment {

    private Toolbar mToolbar;
    private RecyclerView publishedKontenList;
    private UserKontenListAdapter userKontenListAdapter;
    private LinearLayout loadingArea;

    private List<Tag> tagList = new ArrayList<>();
    private List<ReadMaterial> kontenList = new ArrayList<>();

    private SharedPrefManager chupySharedPrefManager;
    private ChupyServiceController serviceController = new ChupyServiceController();

    public FragmentWrite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmenpt

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_write, container, false);

        chupySharedPrefManager = new SharedPrefManager(container.getContext());

        bindView(rootView);
        fetchTag();
        fetchKonten();
        bindData();

        return rootView;
    }

    private void fetchKonten() {
        loadingArea.setVisibility(View.VISIBLE);
        serviceController.getService().getPublishedKontenFromUser(String.valueOf(chupySharedPrefManager.getSharedId())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                switch (response.code()){
                    case 200:
                        
                        break;
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.process_message_error_undefined), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindView(ViewGroup rootView) {
        loadingArea = rootView.findViewById(R.id.linear_layout_loading);
        loadingArea.setVisibility(View.GONE);

        mToolbar = rootView.findViewById(R.id.toolbar_write);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Post");
        setHasOptionsMenu(true);

        publishedKontenList = rootView.findViewById(R.id.recycler_view_user_post);
        publishedKontenList.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.VERTICAL, false));

    }

    private void fetchTag() {
        if (FragmentRead.tagList.size() == 0){
            FragmentRead.triggerReadMaterialFetching();
        }
        tagList = FragmentRead.tagList;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.write_main_page_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.write_post_new:
                intent = new Intent(getActivity(), NewReadMaterialActivity.class);
                intent.putExtra("TagList", new Gson().toJson(tagList));
                startActivity(intent);
                break;
            case R.id.write_post_draft:

                intent = new Intent(getActivity(), DraftReadMaterialActivity.class);
//                intent.putExtra("TagList", new Gson().toJson(tagList));
                startActivity(intent);

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
