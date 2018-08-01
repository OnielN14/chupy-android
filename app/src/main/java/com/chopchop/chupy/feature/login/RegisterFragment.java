package com.chopchop.chupy.feature.login;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chopchop.chupy.LoginActivity;
import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.login.utilities.LoginRegisterController;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class RegisterFragment extends Fragment {

    private TextInputLayout wrapperNameRegister;
    private TextInputLayout wrapperEmailRegister;
    private TextInputLayout wrapperPasswordRegister;
    private TextInputLayout wrapperPasswordConfirmRegister;
    private AppCompatSpinner genderSpinner;
    private Button registerButton;

    private RelativeLayout loadingArea;

    private LoginRegisterController serviceController = new LoginRegisterController();

    private final int userCommonAccessLevel = 3;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_register, container, false);

        bindView(rootView);

        return rootView;
    }

    private void bindView(ViewGroup rootView) {
        loadingArea = rootView.findViewById(R.id.relative_layout_loading_area);
        loadingArea.setVisibility(View.GONE);

        wrapperNameRegister = rootView.findViewById(R.id.wrapper_edit_text_register_name);
        wrapperEmailRegister = rootView.findViewById(R.id.wrapper_edit_text_register_email);
        wrapperPasswordRegister = rootView.findViewById(R.id.wrapper_edit_text_register_password);
        wrapperPasswordConfirmRegister = rootView.findViewById(R.id.wrapper_edit_text_register_password_confirm);
        registerButton = rootView.findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        genderSpinner = rootView.findViewById(R.id.spinner_register_gender);
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<String>(rootView.getContext(), R.layout.custom_simple_spinner_item, rootView.getResources().getStringArray(R.array.gender)){

            @Override
            public boolean isEnabled(int position) {
                if (position == 0){
                    return false;
                }
                else{
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                TextView spinnerItem = (TextView) view;

                if (position == 0){
                    spinnerItem.setTextColor(Color.GRAY);
                }
                else{
                    spinnerItem.setTextColor(Color.BLACK);
                }

                return view;
            }
        };
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderSpinnerAdapter);

    }

    private void doRegister() {
        if (isFormEmpty(wrapperNameRegister.getEditText()) || isFormEmpty(wrapperEmailRegister.getEditText()) || isFormEmpty(wrapperPasswordRegister.getEditText()) || isFormEmpty(wrapperPasswordConfirmRegister.getEditText()) || !isGenderSelected() || !isPasswordSame(wrapperPasswordRegister.getEditText().getText().toString(), wrapperPasswordConfirmRegister.getEditText().getText().toString())){
            Toast.makeText(getActivity(), "Ada yang kosong", Toast.LENGTH_SHORT).show();

            if (isFormEmpty(wrapperNameRegister.getEditText())){
                wrapperNameRegister.setError(getString(R.string.register_error_message_empty_name));
            }

            if (isFormEmpty(wrapperEmailRegister.getEditText())){
                wrapperEmailRegister.setError(getString(R.string.login_error_message_empty_email));
            }

            if (isFormEmpty(wrapperPasswordRegister.getEditText())){
                wrapperPasswordRegister.setError(getString(R.string.login_error_message_empty_password));
            }

            if (!isPasswordSame(wrapperPasswordRegister.getEditText().getText().toString(), wrapperPasswordConfirmRegister.getEditText().getText().toString())){
                wrapperPasswordConfirmRegister.setError(getString(R.string.profile_change_password_error_no_same));
            }

            if (isGenderSelected()){
                Toast.makeText(getActivity(), getString(R.string.register_error_message_no_selected_gender), Toast.LENGTH_SHORT).show();
            }

        }
        else{

            loadingArea.setVisibility(View.VISIBLE);
//            Toast.makeText(getActivity(), "Aman", Toast.LENGTH_SHORT).show();
            serviceController.attempRegister(wrapperNameRegister.getEditText().getText().toString(), wrapperEmailRegister.getEditText().getText().toString(), parseGender(genderSpinner.getSelectedItemPosition()), wrapperPasswordRegister.getEditText().getText().toString(), userCommonAccessLevel).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    clearInput();
                    loadingArea.setVisibility(View.GONE);
                    Log.d(TAG, "onResponse: "+response.body());
//                    LoginFragment.newInstance().registrationSuccess(response.body().getAsJsonObject("data").get("email").getAsString());
                    ((LoginActivity) getActivity()).getLoginPageViewPager().setCurrentItem(0);

                    Toast.makeText(getActivity(), getString(R.string.registration_success), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.printStackTrace();
                    loadingArea.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Something is wrong"+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void clearInput() {
        wrapperNameRegister.getEditText().setText(null);
        wrapperEmailRegister.getEditText().setText(null);
        wrapperPasswordRegister.getEditText().setText(null);
        wrapperPasswordConfirmRegister.getEditText().setText(null);
        genderSpinner.setSelection(0);
    }

    private boolean isGenderSelected() {
        return genderSpinner.getSelectedItemPosition() != 0;

    }

    private boolean isPasswordSame(String password, String rePassword){
        if (password.equals(rePassword)){
            return true;
        }
        else{
            return false;
        }
    }

    private String parseGender(int genderPosition){
        String selectedGender;
        switch (genderPosition){
            case 1:
                selectedGender = "laki-laki";
                break;
            case 2:
                selectedGender = "perempuan";
                break;
            default:
                selectedGender = "laki-laki";
        }

        return selectedGender;
    }

    private boolean isFormEmpty(EditText editText) {
        return editText.getText().length() == 0 || editText.getText() == null;
    }

}
