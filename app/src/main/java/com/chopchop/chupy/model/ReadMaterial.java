package com.chopchop.chupy.model;

public class ReadMaterial {
    private int id;
    private String title;
    private String description;
    private String date;
    private String imageName;

    public ReadMaterial(int id, String title, String description, String date, String imageName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
