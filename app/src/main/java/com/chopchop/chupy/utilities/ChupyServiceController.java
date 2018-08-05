package com.chopchop.chupy.utilities;

import com.chopchop.chupy.models.Photo;
import com.chopchop.chupy.models.ReadMaterial;
import com.chopchop.chupy.models.Tag;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChupyServiceController {

    private Retrofit controller;
    private ChupyService service;

    private final String defaultDateFormat = "yyyy-MM-dd hh:mm:ss";

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

    public List<ReadMaterial> parseDataKontenFromService(JsonObject response){

        List<ReadMaterial> tempReadMaterials = new ArrayList<>();
        List<Tag> contentTagList, tempGlobalTagList = new ArrayList<>();
        Photo tempPhoto;
        JsonArray data = response.getAsJsonArray("data");
        for (JsonElement item: data) {

            if (item.getAsJsonObject().get("foto").getAsJsonArray().size() != 0){
                tempPhoto = parsePhotoFromData(item.getAsJsonObject().get("foto").getAsJsonArray().get(0));
            }
            else{
                tempPhoto = null;
            }

            contentTagList = parseTagFromContent(item.getAsJsonObject().get("tag"));
            tempGlobalTagList.addAll(contentTagList);

            tempReadMaterials.add(new ReadMaterial(item.getAsJsonObject().get("id").getAsInt(), item.getAsJsonObject().get("judul").getAsString(), item.getAsJsonObject().get("deskripsi").getAsString(), item.getAsJsonObject().get("kategori").getAsString(), item.getAsJsonObject().get("idKategori").getAsInt(), parseDate(item.getAsJsonObject().get("tanggalPost").getAsString()), tempPhoto, contentTagList, item.getAsJsonObject().get("statuspost").getAsString()));

        }

        return tempReadMaterials;
    }

    private String parseDate(String tanggalPost) {
        Locale idLocale = new Locale.Builder().setLanguage("in").setRegion("ID").build();

        SimpleDateFormat parse = new SimpleDateFormat(defaultDateFormat);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", idLocale);
        try {
            Date parsedDate = parse.parse(tanggalPost);
            String newDate = formatter.format(parsedDate);
            return newDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return "Date Error";
        }
    }


    private List<Tag> parseTagFromContent(JsonElement tag) {
        List<Tag> tempTag = new ArrayList<>();
        for (JsonElement item : tag.getAsJsonArray()){
            tempTag.add(new Tag(item.getAsJsonObject().get("id").getAsInt(), item.getAsJsonObject().get("tag").getAsString()));
        }

        return tempTag;
    }

    private Photo parsePhotoFromData(JsonElement foto){
        return new Photo(foto.getAsJsonObject().get("id").getAsInt(), foto.getAsJsonObject().get("foto").getAsString(), ChupyService.baseUrl);
    }
}
