package com.chopchop.chupy.feature.read.utilities;

import com.chopchop.chupy.utilities.ChupyServiceController;
import com.google.gson.JsonObject;

import retrofit2.Call;

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
