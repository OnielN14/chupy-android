package com.chopchop.chupy.feature.profile;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chopchop.chupy.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputLayout wrapperPasswordOld;
    private TextInputLayout wrapperPasswordNew;
    private TextInputLayout wrapperPasswordConfirm;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        bindView();

    }

    private void bindView() {
        mToolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.profile_change_password_page_title);

        wrapperPasswordOld = findViewById(R.id.wrapper_edit_text_password_old);
        wrapperPasswordNew = findViewById(R.id.wrapper_edit_text_password_new);
        wrapperPasswordConfirm = findViewById(R.id.wrapper_edit_text_password_confirm);

        changePasswordButton = findViewById(R.id.button_change_password);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewPasswordSame(wrapperPasswordNew.getEditText().getText().toString(), wrapperPasswordConfirm.getEditText().getText().toString())){
                    wrapperPasswordConfirm.setError(getString(R.string.profile_change_password_error_no_same));
                }
                else{
                    wrapperPasswordConfirm.setErrorEnabled(false);
                    changePasswordAction();
                }
            }
        });
    }

    private void changePasswordAction() {
        Toast.makeText(this, "Changing...", Toast.LENGTH_SHORT).show();
    }

    private boolean isNewPasswordSame(String password, String rePassword){
        if (password.equals(rePassword)){
            return true;
        }
        else{
            return false;
        }
    }
}
