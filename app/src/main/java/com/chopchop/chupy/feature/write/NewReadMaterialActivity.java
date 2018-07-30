package com.chopchop.chupy.feature.write;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chopchop.chupy.R;
import com.chopchop.chupy.models.ReadMaterialCategory;
import com.chopchop.chupy.models.Tag;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NewReadMaterialActivity extends AppCompatActivity {

    private static final String TAG = "Debuggin lol";
    private static final int PICK_IMAGE_CODE = 1;

    private Toolbar mToolbar;

    private EditText readMaterialTitle;
    private AppCompatSpinner categoryList;
    private EditText readMaterialDescription;
    private Button addPictureButton;
    private ImageView previewImage;

    ArrayAdapter<String> nachoAdapterArrayAdapter;
    private NachoTextView tagNachoTextView;

    private List<ReadMaterialCategory> readMaterialCategoryList = new ArrayList<>();
    private List<Tag> tagList = new ArrayList<>();
    private String[] categoryTitleList = {"News", "Article", "Tips & Tricks"};
    private List<String> tagSuggestion = new ArrayList<>();

    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_read_material);

        bindView();
        initCategoryData();

        Bundle bundle = getIntent().getExtras();

        Type listOfTag = new TypeToken<List<Tag>>(){}.getType();

        tagList = new Gson().fromJson(bundle.getString("TagList"),listOfTag);

        for (Tag tag: tagList) {
            tagSuggestion.add(tag.getTagName());
        }

        nachoAdapterArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tagSuggestion);
        tagNachoTextView.setAdapter(nachoAdapterArrayAdapter);
        tagNachoTextView.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);

        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, categoryTitleList);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.setAdapter(categoryArrayAdapter);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.write_new_page_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initCategoryData() {
        readMaterialCategoryList.add(new ReadMaterialCategory(1,"News"));
        readMaterialCategoryList.add(new ReadMaterialCategory(2,"Article"));
        readMaterialCategoryList.add(new ReadMaterialCategory(3,"Tips & Trick"));
    }

    private void bindView() {
        tagNachoTextView = findViewById(R.id.nacho_text_view_new_content_tag);
        mToolbar = findViewById(R.id.toolbar_write);
        readMaterialTitle = findViewById(R.id.edit_text_new_content_title);
        readMaterialDescription = findViewById(R.id.edit_text_new_content_description);
        categoryList = findViewById(R.id.spinner_new_content_category);
        addPictureButton = findViewById(R.id.button_add_picture);
        previewImage = findViewById(R.id.image_view_preview_new_content);
        previewImage.setVisibility(View.GONE);

        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.choose_picture)), PICK_IMAGE_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                previewImage.setImageBitmap(bitmap);
                previewImage.setVisibility(View.VISIBLE);

                imageFile = new File(uri.getPath());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_new_content_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.write_post_save:
                Toast.makeText(this, "Saving.. just kidding", Toast.LENGTH_SHORT).show();

//                for (Chip chip: tagNachoTextView.getAllChips()) {
//
//                }

                String newContentTitle = readMaterialTitle.getText().toString();
                String newContentDescription = readMaterialDescription.getText().toString();
                String newContentCategory = categoryList.getSelectedItem().toString();
                String[] newContentTags = (String[]) tagNachoTextView.getAllChips().toArray();


                break;

            case R.id.write_post_publish:
                Toast.makeText(this, "Publishing.. just kidding (lol)", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
