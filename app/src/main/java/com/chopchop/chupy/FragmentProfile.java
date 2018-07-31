package com.chopchop.chupy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chopchop.chupy.feature.profile.ChangePasswordActivity;
import com.chopchop.chupy.feature.profile.EditProfileActivity;
import com.chopchop.chupy.utilities.SharedPrefManager;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentProfile extends Fragment {

    private CircleImageView profileImage;
    private TextView profileEditProfile;
    private TextView profileChangePassword;
    private TextView about;
    private TextView profileLogout;

    private SharedPrefManager chupySharedPrefManager;

    public FragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        chupySharedPrefManager = new SharedPrefManager(getActivity());

        bindView(rootView);


        return rootView;
    }

    private void bindView(final ViewGroup rootView) {
        profileImage = rootView.findViewById(R.id.circle_image_profile);

        profileEditProfile = rootView.findViewById(R.id.text_view_edit_profile);
        profileEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        profileChangePassword = rootView.findViewById(R.id.text_view_change_password);
        profileChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        about = rootView.findViewById(R.id.text_view_about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             AlertDialog aboutDialog =  initAboutApp();
             aboutDialog.show();
            }
        });

        profileLogout = rootView.findViewById(R.id.text_view_logout);
        profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogout();
                Toast.makeText(getActivity(), "Loggin out...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doLogout() {
        chupySharedPrefManager.setSharedPreferencesBoolean(SharedPrefManager.SP_LOGGED_STATUS, false);
        startActivity(new Intent(getActivity(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        getActivity().finish();
    }

    private AlertDialog initAboutApp() {
        AlertDialog.Builder alerBuilder = new AlertDialog.Builder(getContext());
        alerBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.alert_about_page,null));

        return alerBuilder.create();
    }

}
