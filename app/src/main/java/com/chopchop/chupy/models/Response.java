package com.chopchop.chupy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("body")
    @Expose
    private String body;

    public Response(boolean status, String message, String body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
