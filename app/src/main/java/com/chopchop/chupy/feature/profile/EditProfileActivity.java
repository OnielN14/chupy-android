package com.chopchop.chupy.feature.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chopchop.chupy.R;
import com.chopchop.chupy.utilities.ChupyService;
import com.chopchop.chupy.utilities.ChupyServiceController;
import com.chopchop.chupy.utilities.SharedPrefManager;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_CODE = 1;
    private static final String TAG = "EditProfile";
    private Toolbar mToolbar;
    private CircleImageView imageProfile;
    private TextInputLayout profileEditName;
    private TextInputLayout profileEditEmail;
    private TextInputLayout profileEditPhone;
    private Button changePhotoButton;

    private RelativeLayout loadingArea;

    private File newImageProfile;

    private ChupyServiceController serviceController = new ChupyServiceController();
    private SharedPrefManager chupySharedManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getReadStoragePermission();
        chupySharedManager = new SharedPrefManager(this);

        Bundle bundle =  getIntent().getBundleExtra("userData");
        bindView();


        bindData(bundle);
    }

    private void bindData(Bundle bundle) {
        profileEditName.getEditText().setText(bundle.getString("userName"));
        profileEditEmail.getEditText().setText(bundle.getString("userEmail"));
        profileEditPhone.getEditText().setText(bundle.getString("userPhone"));
        Picasso.get().load(ChupyService.baseUrl+"/"+bundle.getString("userPhoto")).into(imageProfile);
    }

    private void bindView() {

        loadingArea = findViewById(R.id.relative_layout_loading_area);
        loadingArea.setVisibility(View.GONE);

        mToolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.profile_edit_page_title);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black);
        mToolbar.getNavigationIcon().mutate().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        imageProfile = findViewById(R.id.circle_image_edit_profile);
        changePhotoButton = findViewById(R.id.button_change_picture);
        changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.choose_picture)), PICK_IMAGE_CODE);
            }
        });

        profileEditName = findViewById(R.id.edit_text_edit_name);
        profileEditEmail = findViewById(R.id.edit_text_edit_email);
        profileEditPhone = findViewById(R.id.edit_text_edit_phone);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                imageProfile.setImageBitmap(bitmap);
                imageProfile.setVisibility(View.VISIBLE);

                newImageProfile = new File(uri.getPath());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.profile_save_change:
                Toast.makeText(this, getString(R.string.process_message_saving), Toast.LENGTH_SHORT).show();
                if (isFormEmpty(profileEditEmail.getEditText())) {
                    profileEditEmail.setError(getString(R.string.login_error_message_empty_email));
                }
                else if (isFormEmpty(profileEditName.getEditText())) {
                    profileEditName.setError(getString(R.string.profile_edit_message_error_name_empty));
                }
                else if (isFormEmpty(profileEditName.getEditText())) {
                    profileEditName.setError(getString(R.string.profile_edit_message_error_phone_empty));
                }
                else{
                    profileEditEmail.setErrorEnabled(false);
                    saveProfileChanges();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveProfileChanges() {

        loadingArea.setVisibility(View.VISIBLE);

        RequestBody reqFile;
        MultipartBody.Part imagePart;
        if (newImageProfile != null){
            reqFile = RequestBody.create(MediaType.parse("image/*"), newImageProfile);
            imagePart = MultipartBody.Part.createFormData("foto", newImageProfile.getName(), reqFile);
        }
        else{
            reqFile = null;
            imagePart = MultipartBody.Part.createFormData("none", "none");
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), profileEditName.getEditText().getText().toString());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), profileEditEmail.getEditText().getText().toString());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), profileEditPhone.getEditText().getText().toString());

        serviceController.getService().postEdit(chupySharedManager.getSharedId(), name, email, phone, imagePart).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                parseResponse(response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseResponse(JsonObject body) {
        Log.d(TAG, "parseResponse: "+ body);
        if (body != null){
            JsonObject data = body.getAsJsonObject("data");
            finish();
        }
        else{
            Toast.makeText(this, getString(R.string.process_message_error_undefined), Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isFormEmpty(EditText editText) {
        return editText.getText().length() == 0 || editText.getText() == null;
    }

    private void getReadStoragePermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);


        Log.d(TAG, "getReadStoragePermission: getting read storage permissions");
        String permissions = Manifest.permission.READ_EXTERNAL_STORAGE;

        if(ContextCompat.checkSelfPermission(this, permissions) == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{permissions},permissionCheck);
        }
    }
}
