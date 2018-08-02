package com.chopchop.chupy.feature.write;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.chopchop.chupy.R;

public class DraftReadMaterialActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private EditText searchBarEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_read_material);

        bindView();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.write_draft_page_title);

    }

    private void bindView() {
        mToolbar = findViewById(R.id.toolbar_write);
        recyclerView = findViewById(R.id.recycler_view_user_post);
        searchBarEditText = findViewById(R.id.input_search);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
