package com.chopchop.chupy.utilities;

import com.chopchop.chupy.models.Response;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ChupyService{

    final String baseUrl = "http://chuppy-rpl.azurewebsites.net";

    @GET("api/kontens")
    Call<JsonObject> listReadMaterial();

    @GET("api/kontens/{userId}/draft")
    Call<JsonObject> getDraftKontenFromUser(@Path("userId") String userId);

    @GET("api/kontens/{userId}/published")
    Call<JsonObject> getPublishedKontenFromUser(@Path("userId") String userId);

    @GET("api/kategoriKontens")
    Call<JsonObject> listReadMaterialCategory();

    @POST("api/kontens/{kontenId}/delete")
    Call<JsonObject> deletePost(@Path("kontenId") int kontenId);

    @Multipart
    @POST("api/kontens/{kontenId}/edit")
    Call<JsonObject> editKonten(@Path("kontenId") int kontenId, @Part("judul") RequestBody judul, @Part("deskripsi") RequestBody deskripsi, @Part("statuspost") RequestBody statuspost, @Part("tag[]") List<String> tag, @Part("idKategori") RequestBody idKategori, @Part MultipartBody.Part file);

    @Multipart
    @POST("api/kontens")
    Call<JsonObject> postKonten(@Part("userId") RequestBody userId, @Part("judul") RequestBody judul, @Part("deskripsi") RequestBody deskripsi, @Part("statuspost") RequestBody statuspost, @Part("tag[]") List<String> tag, @Part("idKategori") RequestBody idKategori, @Part MultipartBody.Part file);



    @FormUrlEncoded
    @POST("api/login")
    Call<JsonObject> postLogin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    Call<JsonObject> postRegister(@Field("name") String name, @Field("email") String email, @Field("jeniskelamin") String gender, @Field("password") String password, @Field("idHakakses") int idHakakses);

    @FormUrlEncoded
    @POST("api/change-password/{userId}")
    Call<JsonObject> postChangePassword(@Path("userId") int userId, @Field("oldpassword") String oldPassword, @Field("newpassword") String newPassword);

    @Multipart
    @POST("api/edit-profil/{userId}")
    Call<JsonObject> postEdit(@Path("userId") int userId, @Part("name") RequestBody name, @Part("email") RequestBody email, @Part("notelepon") RequestBody noTelepon, @Part MultipartBody.Part file);
}
