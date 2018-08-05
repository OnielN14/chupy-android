package com.chopchop.chupy.feature.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.chopchop.chupy.R;
import com.chopchop.chupy.models.ReadMaterial;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ReadDetail extends AppCompatActivity {

    ReadMaterial readMaterial;

    TextView readTitle;
    TextView readDate;
    ImageView readImage;
    TextView readDescription;
    Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_read_detail);

        bindView();

        Bundle bundle = getIntent().getExtras();
        readMaterial = new Gson().fromJson(bundle.getString("readObject"), ReadMaterial.class);

        bindDataToView(readMaterial);
    }

    private void bindDataToView(ReadMaterial readMaterial) {
        readTitle.setText(readMaterial.getTitle());
        getSupportActionBar().setTitle(readMaterial.getTitle());
        readDate.setText(readMaterial.getDate());
        readDescription.setText(readMaterial.getDescription());
        if (readMaterial.getPhoto() != null){
            Picasso.get().load(readMaterial.getPhoto().getHost()+'/'+readMaterial.getPhoto().getUrl()).into(readImage);
        }

    }

    private void bindView() {
        readTitle = findViewById(R.id.text_view_read_title);
        readDate = findViewById(R.id.text_view_read_date);
        readDescription = findViewById(R.id.text_view_read_description);
        readImage = findViewById(R.id.image_view_read_image);
        mToolbar = findViewById(R.id.toolbar_read);

        settingSupportActionBar(mToolbar);
    }

    private void settingSupportActionBar(Toolbar mToolbar) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
