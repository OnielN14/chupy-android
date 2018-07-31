package com.chopchop.chupy.feature.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chopchop.chupy.R;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_CODE = 1;
    private Toolbar mToolbar;
    private CircleImageView imageProfile;
    private EditText profileEditName;
    private EditText profileEditEmail;
    private EditText profileEditPhone;
    private Button changePhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bindView();

    }

    private void bindView() {
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
                Toast.makeText(this, "Saving... lol", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
