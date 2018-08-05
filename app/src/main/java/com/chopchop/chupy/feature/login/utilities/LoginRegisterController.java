package com.chopchop.chupy.feature.login.utilities;

import com.chopchop.chupy.utilities.ChupyServiceController;
import com.google.gson.JsonObject;

import retrofit2.Call;

public class LoginRegisterController extends ChupyServiceController{

    public Call<JsonObject> attempLogin(String email, String password){
        return getService().postLogin(email, password);
    }

    public Call<JsonObject> attempRegister(String name, String email, String gender, String password, int idHakakses){
        return getService().postRegister(name, email, gender, password, idHakakses);
    }

}
