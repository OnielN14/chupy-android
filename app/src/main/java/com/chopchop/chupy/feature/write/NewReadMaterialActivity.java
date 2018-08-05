package com.chopchop.chupy.feature.write;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.chopchop.chupy.FragmentRead;
import com.chopchop.chupy.R;
import com.chopchop.chupy.models.ReadMaterial;
import com.chopchop.chupy.models.ReadMaterialCategory;
import com.chopchop.chupy.models.Tag;
import com.chopchop.chupy.utilities.ChupyServiceController;
import com.chopchop.chupy.utilities.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.chip.ChipInfo;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewReadMaterialActivity extends AppCompatActivity {

    private static final String TAG = "Debuggin lol";

    private static final String POST_TYPE_DRAFT = "draft";
    private static final String POST_TYPE_PUBLISH = "publish";

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
    private List<String> tagSuggestion = new ArrayList<>();

    private File imageFile;
    private ReadMaterial readMaterial;

    private SharedPrefManager chupySharedPrefManager;
    private ChupyServiceController serviceController = new ChupyServiceController();

    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_read_material);

        chupySharedPrefManager = new SharedPrefManager(this);

        bindView();
        initCategoryData();
        bindData();

        for (Tag tag: tagList) {
            tagSuggestion.add(tag.getTagName());
        }

        initTagNachoTextView();

    }

    private void initTagNachoTextView() {

        nachoAdapterArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, tagSuggestion);
        tagNachoTextView.setAdapter(nachoAdapterArrayAdapter);
        tagNachoTextView.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
    }

    private void fetchTag() {
        if (FragmentRead.tagList.size() == 0){
            FragmentRead.triggerReadMaterialFetching();
        }
        tagList = FragmentRead.tagList;
    }

    private void bindData() {
//        Bundle bundle = getIntent().getExtras();
//        Type listOfTag = new TypeToken<List<Tag>>(){}.getType();
//        tagList = new Gson().fromJson(bundle.getString("TagList"),listOfTag);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            editMode = true;
            readMaterial = new Gson().fromJson(bundle.getString("kontenData"), ReadMaterial.class);
            readMaterialTitle.setText(readMaterial.getTitle());
            readMaterialDescription.setText(readMaterial.getDescription());
            try{
                Picasso.get().load(readMaterial.getPhoto().getHost()+'/'+readMaterial.getPhoto().getUrl()).into(previewImage);
                previewImage.setVisibility(View.VISIBLE);
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }

            List<ChipInfo> chipInfoList = new ArrayList<>();
            for (Tag tag : readMaterial.getTagList()){
                chipInfoList.add( new ChipInfo(tag.getTagName(),null));
            }

            tagNachoTextView.setTextWithChips(chipInfoList);
            getSupportActionBar().setTitle(getString(R.string.write_edit_page_title));

        }

        Toast.makeText(this, "Edit mode "+editMode, Toast.LENGTH_SHORT).show();

        fetchTag();
    }

    private void initCategoryData() {
        readMaterialCategoryList.add(new ReadMaterialCategory(1,"News"));
        readMaterialCategoryList.add(new ReadMaterialCategory(2,"Article"));
        readMaterialCategoryList.add(new ReadMaterialCategory(3,"Tips & Trick"));
    }

    private void bindView() {
        tagNachoTextView = findViewById(R.id.nacho_text_view_new_content_tag);
        mToolbar = findViewById(R.id.toolbar_write);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.write_new_page_title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readMaterialTitle = findViewById(R.id.edit_text_new_content_title);
        readMaterialDescription = findViewById(R.id.edit_text_new_content_description);
        categoryList = findViewById(R.id.spinner_new_content_category);
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, getResources().getStringArray(R.array.read_tab_menu));
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.setAdapter(categoryArrayAdapter);

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
                postKonten(POST_TYPE_DRAFT);

                break;

            case R.id.write_post_publish:
                postKonten(POST_TYPE_PUBLISH);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void postKonten(final String postTypeDraft) {

        String sendingMessage = "";
        if (postTypeDraft == POST_TYPE_DRAFT){
            sendingMessage = getString(R.string.process_message_saving);
        }
        else if (postTypeDraft == POST_TYPE_PUBLISH){
            sendingMessage = getString(R.string.process_message_publishing);
        }

        Toast.makeText(this, sendingMessage, Toast.LENGTH_LONG);

        RequestBody reqFile;
        MultipartBody.Part imagePart;
        if (imageFile != null){
            reqFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
            imagePart = MultipartBody.Part.createFormData("foto", imageFile.getName(), reqFile);
        }
        else{
            reqFile = null;
            imagePart = MultipartBody.Part.createFormData("none", "none");
        }

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(chupySharedPrefManager.getSharedId()));
        RequestBody judul = RequestBody.create(MediaType.parse("text/plain"), readMaterialTitle.getText().toString());
        RequestBody deskripsi = RequestBody.create(MediaType.parse("text/plain"), readMaterialDescription.getText().toString());
        RequestBody statuspost = RequestBody.create(MediaType.parse("text/plain"), postTypeDraft);
        RequestBody idKategori = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(categoryList.getSelectedItemPosition()+1) );


        if (editMode){
//            RequestBody idKonten = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(readMaterial.getId()));
            doEdit(readMaterial.getId(), judul, deskripsi, statuspost, idKategori, imagePart, postTypeDraft);
        }
        else{
            doPost(userId, judul, deskripsi, statuspost, idKategori, imagePart, postTypeDraft);
        }
    }

    private void doEdit(int idKonten, RequestBody judul, RequestBody deskripsi, RequestBody statuspost, RequestBody idKategori, MultipartBody.Part imagePart, final String postTypeDraft) {
        Toast.makeText(this, getString(R.string.process_message_saving), Toast.LENGTH_SHORT).show();

        serviceController.getService().editKonten(idKonten,judul, deskripsi, statuspost, parseChipsToStringArray(tagNachoTextView.getAllChips()),idKategori, imagePart).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(TAG, "onResponse: "+response.errorBody());
                switch (response.code()){
                    case 200:
                        String responseMessage = "";
                        if (postTypeDraft == POST_TYPE_DRAFT){
                            responseMessage = NewReadMaterialActivity.this.getString(R.string.process_message_saved);
                        }
                        else if (postTypeDraft == POST_TYPE_PUBLISH){
                            responseMessage = NewReadMaterialActivity.this.getString(R.string.process_message_published);
                        }

                        Toast.makeText(NewReadMaterialActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Log.d(TAG, "onResponse: Failed "+response.raw());
                }

                Log.d(TAG, "onResponse: Success :"+response.body());

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(NewReadMaterialActivity.this, NewReadMaterialActivity.this.getString(R.string.process_message_error_undefined), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void doPost(RequestBody userId, RequestBody judul, RequestBody deskripsi, RequestBody statuspost, RequestBody idKategori, MultipartBody.Part imagePart, final String postTypeDraft) {
        Toast.makeText(this, getString(R.string.process_message_publishing), Toast.LENGTH_SHORT).show();
        serviceController.getService().postKonten(userId,judul, deskripsi, statuspost, parseChipsToStringArray(tagNachoTextView.getAllChips()),idKategori, imagePart).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d(TAG, "onResponse: "+response.errorBody());
                switch (response.code()){
                    case 200:
                        String responseMessage = "";
                        if (postTypeDraft == POST_TYPE_DRAFT){
                            responseMessage = NewReadMaterialActivity.this.getString(R.string.process_message_saved);
                        }
                        else if (postTypeDraft == POST_TYPE_PUBLISH){
                            responseMessage = NewReadMaterialActivity.this.getString(R.string.process_message_published);
                        }

                        Toast.makeText(NewReadMaterialActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        Log.d(TAG, "onResponse: Failed "+response.raw());
                }

                Log.d(TAG, "onResponse: Success :"+response.body());

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(NewReadMaterialActivity.this, NewReadMaterialActivity.this.getString(R.string.process_message_error_undefined), Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<String> parseChipsToStringArray(List<Chip> allChips) {
        List<String> tempString = new ArrayList<>();
        for (Chip item: allChips) {
            tempString.add(item.getText().toString());
        }

        return tempString;
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
