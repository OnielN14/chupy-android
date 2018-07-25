package com.chopchop.chupy.utilities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChupyServiceController {

    private Retrofit controller;
    private ChupyService service;

    public ChupyServiceController() {
        controller = new Retrofit.Builder()
                .baseUrl(ChupyService.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = controller.create(ChupyService.class);
    }

    public ChupyService getService() {
        return service;
    }
}
