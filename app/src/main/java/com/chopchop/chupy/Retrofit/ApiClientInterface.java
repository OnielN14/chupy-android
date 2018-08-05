package com.chopchop.chupy.Retrofit;

import com.chopchop.chupy.feature.petservice.models.Example;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiClientInterface {
    @GET("api/petshops")
    Call<Example> get();

    @FormUrlEncoded
    @POST("api/petshops")
    Call<Example> post(@Field("nama")String name,
                       @Field("deskripsi")String deskripsi,
                       @Field("alamat")String alamat,
                       @Field("statusToko")String status,
                       @Field("notelepon")String notelp,
                       @Field("latitude")Double latitude,
                       @Field("longitude")Double longitude);

}
