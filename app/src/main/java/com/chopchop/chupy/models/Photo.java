package com.chopchop.chupy.models;

public class Photo {
    private int id;
    private String url;
    private String host;

    public Photo(int id, String url, String host) {
        this.id = id;
        this.url = url;
        this.host = host;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
