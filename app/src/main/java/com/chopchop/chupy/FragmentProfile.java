package com.chopchop.chupy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chopchop.chupy.feature.profile.ChangePasswordActivity;
import com.chopchop.chupy.feature.profile.EditProfileActivity;
import com.chopchop.chupy.utilities.ChupyService;
import com.chopchop.chupy.utilities.ChupyServiceController;
import com.chopchop.chupy.utilities.SharedPrefManager;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;


public class FragmentProfile extends Fragment {

    private CircleImageView profileImage;
    private TextView profileEditProfile;
    private TextView profileChangePassword;
    private TextView about;
    private TextView profileLogout;

    private SharedPrefManager chupySharedPrefManager;
    private ChupyServiceController serviceController = new ChupyServiceController();

    private String userName;
    private String userEmail;
    private String userPhone;
    private String userPhoto;

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
        retrievingUserData();



        return rootView;
    }

    private void retrievingUserData() {
        Log.d(TAG, "retrievingUserData: "+chupySharedPrefManager.getSharedEmail());
        Log.d(TAG, "retrievingUserData: "+chupySharedPrefManager.getSharedPassword());
        serviceController.getService().postLogin(chupySharedPrefManager.getSharedEmail(), chupySharedPrefManager.getSharedPassword()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onResponse: "+response.body());
                preparedUserData(response.body().getAsJsonObject("body"));
                Toast.makeText(getActivity(), "Data Retrievied", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void preparedUserData(JsonObject userData) {
        userName = userData.get("name").getAsString();
        userEmail = userData.get("email").getAsString();
        userPhone = userData.get("notelepon").getAsString();
        userPhoto = userData.get("foto").getAsString();

        Picasso.get().load(ChupyService.baseUrl+"/"+userPhoto).into(profileImage);
    }


    private void bindView(final ViewGroup rootView) {
        profileImage = rootView.findViewById(R.id.circle_image_profile);

        profileEditProfile = rootView.findViewById(R.id.text_view_edit_profile);
        profileEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "->"+userEmail, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName", userName);
                bundle.putString("userEmail", userEmail);
                bundle.putString("userPhone", userPhone);
                bundle.putString("userPhoto", userPhoto);
                intent.putExtra("userData", bundle);
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
