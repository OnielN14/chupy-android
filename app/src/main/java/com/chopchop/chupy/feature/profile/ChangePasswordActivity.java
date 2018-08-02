package com.chopchop.chupy.feature.profile;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chopchop.chupy.R;
import com.chopchop.chupy.utilities.ChupyServiceController;
import com.chopchop.chupy.utilities.SharedPrefManager;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputLayout wrapperPasswordOld;
    private TextInputLayout wrapperPasswordNew;
    private TextInputLayout wrapperPasswordConfirm;
    private Button changePasswordButton;
    private RelativeLayout loadingArea;
    private ChupyServiceController serviceController = new ChupyServiceController();
    private SharedPrefManager chupySharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        chupySharedPrefManager = new SharedPrefManager(this);

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
                doChangePassword();
            }
        });

        loadingArea = findViewById(R.id.relative_layout_loading_area);
        loadingArea.setVisibility(View.GONE);
    }

    private void doChangePassword() {
        if (!isNewPasswordSame(wrapperPasswordNew.getEditText().getText().toString(), wrapperPasswordConfirm.getEditText().getText().toString())){
            wrapperPasswordConfirm.setError(getString(R.string.profile_change_password_error_no_same));
        }
        else if(isFormEmpty(wrapperPasswordOld.getEditText())){
            wrapperPasswordOld.setError(getString(R.string.login_error_message_empty_password));
        }
        else if(isFormEmpty(wrapperPasswordNew.getEditText())){
            wrapperPasswordNew.setError(getString(R.string.login_error_message_empty_password));
        }
        else if(isFormEmpty(wrapperPasswordConfirm.getEditText())){
            wrapperPasswordConfirm.setError(getString(R.string.login_error_message_empty_password));
        }
        else{
            wrapperPasswordConfirm.setErrorEnabled(false);
            changePasswordAction();
        }
    }

    private void changePasswordAction() {
        Toast.makeText(this, getString(R.string.process_message_saving), Toast.LENGTH_SHORT).show();
        loadingArea.setVisibility(View.VISIBLE);
        serviceController.getService().postChangePassword(chupySharedPrefManager.getSharedId(), wrapperPasswordOld.getEditText().getText().toString(), wrapperPasswordConfirm.getEditText().getText().toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                switch (response.code()){
                    case 200:
                        requestSuccess();
                        break;
                    case 401:
                    case 400:
                        requestFailed();
                        break;
                    default:
                        loadingArea.setVisibility(View.GONE);
                }
                loadingArea.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ChangePasswordActivity.this, getString(R.string.process_message_error_undefined), Toast.LENGTH_SHORT).show();
                loadingArea.setVisibility(View.GONE);
            }
        });
    }

    private void requestSuccess() {
        Toast.makeText(this, getString(R.string.profile_change_password_message_success), Toast.LENGTH_SHORT).show();
        chupySharedPrefManager.setSharedPreferencesString(SharedPrefManager.SP_PASSWORD,wrapperPasswordConfirm.getEditText().getText().toString());
        finish();
    }

    private void requestFailed() {
        wrapperPasswordOld.setError(getString(R.string.profile_change_password_error_wrong_password));
        wrapperPasswordConfirm.getEditText().setText("");
    }

    private boolean isNewPasswordSame(String password, String rePassword){
        if (password.equals(rePassword)){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean isFormEmpty(EditText editText) {
        return editText.getText().length() == 0 || editText.getText() == null;
    }
}
