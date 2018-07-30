package com.chopchop.chupy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chopchop.chupy.feature.write.DraftReadMaterialActivity;
import com.chopchop.chupy.feature.write.NewReadMaterialActivity;
import com.chopchop.chupy.models.Tag;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentWrite extends Fragment {

    private Toolbar mToolbar;
    private List<Tag> tagList = new ArrayList<>();

    public FragmentWrite() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmenpt

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_write, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar_write);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Post");

        setHasOptionsMenu(true);

        fetchTag();

        return rootView;
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
