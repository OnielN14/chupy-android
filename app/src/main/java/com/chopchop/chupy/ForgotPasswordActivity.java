package com.chopchop.chupy;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chopchop.chupy.utilities.ChupyServiceController;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputLayout wrapperEmailInput;
    private Button requestButton;
    private ChupyServiceController serviceController = new ChupyServiceController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        bindView();

    }

    private void bindView() {
        mToolbar = findViewById(R.id.toolbar_forgot_password);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.forgot_password_page_title));

        wrapperEmailInput = findViewById(R.id.wrapper_edit_text_forgot_password_email);
        requestButton = findViewById(R.id.button_request_forgot_password);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ForgotPasswordActivity.this, getString(R.string.development_message), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
