package com.chopchop.chupy.feature.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chopchop.chupy.MainActivity;
import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.login.utilities.LoginRegisterController;
import com.chopchop.chupy.utilities.SharedPrefManager;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

public class LoginFragment extends Fragment {

    private TextInputLayout wrapperEmailLogin;
    private TextInputLayout wrapperPasswordLogin;
    private TextView forgotPasswordTextView;
    private Button loginButton;

    private LoginRegisterController serviceController = new LoginRegisterController();

    private RelativeLayout loadingArea;

    private SharedPrefManager chupySharedPrefManager;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }

//    public void registrationSuccess(String email){
//        ((TextInputLayout) getActivity().findViewById(R.id.wrapper_edit_text_login_email)).getEditText().setText(email);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

        chupySharedPrefManager = new SharedPrefManager(getActivity());

        bindView(rootView);


        return rootView;
    }

    private void bindView(ViewGroup rootView) {

        loadingArea = rootView.findViewById(R.id.relative_layout_loading_area);
        loadingArea.setVisibility(View.GONE);

        wrapperEmailLogin = rootView.findViewById(R.id.wrapper_edit_text_login_email);
        wrapperPasswordLogin = rootView.findViewById(R.id.wrapper_edit_text_login_password);
        forgotPasswordTextView = rootView.findViewById(R.id.text_view_forgot_password);
        loginButton = rootView.findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    private void doLogin(){
        if (isFormEmpty(wrapperEmailLogin.getEditText()) && isFormEmpty(wrapperPasswordLogin.getEditText())){
            if (isFormEmpty(wrapperEmailLogin.getEditText())){
                wrapperEmailLogin.setError(getString(R.string.login_error_message_empty_email));
            }

            if (isFormEmpty(wrapperPasswordLogin.getEditText())){
                wrapperPasswordLogin.setError(getString(R.string.login_error_message_empty_password));
            }

        }
        else{
            wrapperEmailLogin.setErrorEnabled(false);
            wrapperPasswordLogin.setErrorEnabled(false);

            loadingArea.setVisibility(View.VISIBLE);
            serviceController.attempLogin(wrapperEmailLogin.getEditText().getText().toString(), wrapperPasswordLogin.getEditText().getText().toString()).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                    Log.d(TAG, "onResponse: login = "+response.body());
                    proceedLogin(response.body());
                    Toast.makeText(getActivity(), "Loggin in ...", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.printStackTrace();
                    loadingArea.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something is wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void proceedLogin(JsonObject body) {
        if (body != null){
            setUserSession(body.getAsJsonObject("body"));
//            Log.d(TAG, "proceedLogin: " + body.getAsJsonObject("body"));
            openMainActivity();
        }
        else{
            Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
            loginFailed();
        }
    }

    private void openMainActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        getActivity().finish();
    }

    private void setUserSession(JsonObject userData) {
        chupySharedPrefManager.setSharedPreferencesInt(SharedPrefManager.SP_ID, userData.get("id").getAsInt());

        chupySharedPrefManager.setSharedPreferencesString(SharedPrefManager.SP_EMAIL, userData.get("email").getAsString());
        chupySharedPrefManager.setSharedPreferencesString(SharedPrefManager.SP_PASSWORD, wrapperPasswordLogin.getEditText().getText().toString());
        chupySharedPrefManager.setSharedPreferencesString(SharedPrefManager.SP_NAME, userData.get("name").getAsString());
        chupySharedPrefManager.setSharedPreferencesBoolean(SharedPrefManager.SP_LOGGED_STATUS, true);
    }

    private void loginFailed() {
        loadingArea.setVisibility(View.GONE);
        wrapperEmailLogin.setError(getString(R.string.login_error_message_wrong_email));wrapperPasswordLogin.setError(getString(R.string.login_error_message_wrong_password));
        wrapperEmailLogin.setErrorEnabled(true);
        wrapperPasswordLogin.setErrorEnabled(true);
    }

    private boolean isFormEmpty(EditText editText) {
        return editText.getText().length() == 0 || editText.getText() == null;
    }

}
