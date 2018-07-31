package com.chopchop.chupy.feature.login;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.chopchop.chupy.R;
import com.chopchop.chupy.feature.login.utilities.LoginRegisterController;

public class RegisterFragment extends Fragment {

    private TextInputLayout wrapperNameRegister;
    private TextInputLayout wrapperEmailRegister;
    private TextInputLayout wrapperPasswordRegister;
    private TextInputLayout wrapperPasswordConfirmRegister;
    private AppCompatSpinner genderSpinner;
    private Button registerButton;

    private LoginRegisterController serviceController = new LoginRegisterController();

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
        if ()
    }

}
