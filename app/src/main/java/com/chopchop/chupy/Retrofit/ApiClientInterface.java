package com.chopchop.chupy.Retrofit;

import com.chopchop.chupy.feature.petservice.models.Example;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiClientInterface {
    @GET("api/petshops")
    Call<Example> get();

}
