package com.chopchop.chupy.Retrofit;

import com.chopchop.chupy.models.CobaJson;
import com.chopchop.chupy.models.Example;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiClientInterface {
    @GET("api/petshops")
    Call<Example> get();

    @GET("api/petshops/{id}")
    Call<Example> getDetail(@Path("id") int id);

}
