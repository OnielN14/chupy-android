package com.chopchop.chupy.utilities;

import com.chopchop.chupy.models.Response;
import com.google.gson.JsonObject;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ChupyService{

    final String baseUrl = "http://chuppy-rpl.azurewebsites.net";

    @GET("api/kontens")
    Call<JsonObject> listReadMaterial();

    @POST("api/kontens")
    Call<JsonObject> postKontens();

    @GET("api/kategoriKontens")
    Call<JsonObject> listReadMaterialCategory();

    @FormUrlEncoded
    @POST("api/login")
    Call<JsonObject> postLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    Call<JsonObject> postRegister(@Field("name") String name, @Field("email") String email, @Field("jeniskelamin") String gender, @Field("password") String password, @Field("idHakakses") int idHakakses);

    @Multipart
    @POST("api/edit-profil/{userId}")
    Call<JsonObject> postEdit(@Path("userId") int userId, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("notelepon") RequestBody noTelepon, @Part MultipartBody.Part file);
}
