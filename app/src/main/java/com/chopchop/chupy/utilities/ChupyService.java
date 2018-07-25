package com.chopchop.chupy.utilities;

import com.chopchop.chupy.model.ReadMaterial;
import com.chopchop.chupy.model.ReadMaterialCategory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface ChupyService{

    final String baseUrl = "http://chuppy-rpl.azurewebsites.net";

    @GET("api/kontens")
    Call<JsonObject> listReadMaterial();

    @GET("api/kategoriKontens")
    Call<JsonObject> listReadMaterialCategory();


}
