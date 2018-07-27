package com.chopchop.chupy.utilities;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChupyService{

    final String baseUrl = "http://chuppy-rpl.azurewebsites.net";

    @GET("api/kontens")
    Call<JsonObject> listReadMaterial();

    @GET("api/kategoriKontens")
    Call<JsonObject> listReadMaterialCategory();


}
