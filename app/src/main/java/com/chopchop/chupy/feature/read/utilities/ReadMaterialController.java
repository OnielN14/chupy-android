package com.chopchop.chupy.feature.read.utilities;

import android.util.Log;

import com.chopchop.chupy.feature.read.adapter.ReadMaterialRecyclerViewAdapter;
import com.chopchop.chupy.model.Photo;
import com.chopchop.chupy.model.ReadMaterial;
import com.chopchop.chupy.model.Tag;
import com.chopchop.chupy.utilities.ChupyService;
import com.chopchop.chupy.utilities.ChupyServiceController;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ReadMaterialController extends ChupyServiceController{

    public Call<JsonObject> fetchCategoryCall(){
        Call<JsonObject> call = getService().listReadMaterialCategory();
        return call;
    }

    public Call<JsonObject> fetchReadMaterialCall(){
        Call<JsonObject> call = getService().listReadMaterial();
        return call;
    }
}
