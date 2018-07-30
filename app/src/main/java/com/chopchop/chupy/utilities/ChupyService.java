package com.chopchop.chupy.utilities;

import com.google.gson.JsonObject;

import retrofit2.Call;
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


}
