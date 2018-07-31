package com.chopchop.chupy.utilities;

import com.chopchop.chupy.models.Response;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChupyService{

    final String baseUrl = "http://chuppy-rpl.azurewebsites.net";

    @GET("api/kontens")
    Call<JsonObject> listReadMaterial();

    @POST("api/kontens")
    Call<JsonObject> postKontens();

    @GET("api/kategoriKontens")
    Call<JsonObject> listReadMaterialCategory();

    @FormUrlEncoded
    @POST("api/register")
    Call<JsonObject> postRegister(@Field("name") String name, @Field("email") String email, @Field("jeniskelamin") String gender, @Field("password") String password, @Field("idHakakses") int idHakakses);

    @FormUrlEncoded
    @POST("api/login")
    Call<JsonObject> postLogin(@Field("email") String email, @Field("password") String password);
}
