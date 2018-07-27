package com.chopchop.chupy.models;

import com.chopchop.chupy.Coba;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

//    @SerializedName("data")
//    @Expose
//    private List<CobaJson> data = null;
//
//    public List<CobaJson> getData() {
//        return data;
//    }
//
//    public void setData(List<CobaJson> data) {
//        this.data = data;
//    }

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<CobaJson> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<CobaJson> getData() {
        return data;
    }

    public void setData(List<CobaJson> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
