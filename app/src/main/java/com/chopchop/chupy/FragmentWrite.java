package com.chopchop.chupy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWrite extends Fragment {

    private Toolbar mToolbar;
    private RecyclerView publishedKontenList;
    private LinearLayout loadingArea;
    private Button deleteButton;
    private Button editButton;

    private List<Tag> tagList = new ArrayList<>();
    private List<ReadMaterial> kontenList = new ArrayList<>();
    private List<ReadMaterial.ReadMaterialListByDate> kontenListByDate = new ArrayList<>();

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
        bindData();

        return rootView;
    }

    private void bindData() {
        fetchKonten();
    }

    private void fetchKonten() {
        loadingArea.setVisibility(View.VISIBLE);
        serviceController.getService().getPublishedKontenFromUser(String.valueOf(chupySharedPrefManager.getSharedId())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                switch (response.code()){
                    case 200:
                        kontenList = serviceController.parseDataKontenFromService(response.body());
                        kontenListByDate = serviceController.parseKontenListByDate(kontenList);
                        publishedKontenList.setAdapter( new UserKontenListAdapter(kontenListByDate));
                        break;
                }

                loadingArea.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), getString(R.string.process_message_error_undefined), Toast.LENGTH_SHORT).show();
                loadingArea.setVisibility(View.GONE);
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

        deleteButton = rootView.findViewById(R.id.button_delete_post);
        editButton = rootView.findViewById(R.id.button_edit_post);
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
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
