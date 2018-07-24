package com.chopchop.chupy.model;

import java.util.ArrayList;
import java.util.List;

public class ReadMaterial {
    private int id;
    private String title;
    private String description;
    private String date;
    private String categoryName;
    private int categoryId;
    private List<Tag> tagList;
    private Photo photo;

    public ReadMaterial(int id, String title, String description, String categoryName, int categoryId, String date, Photo photo, List<Tag> tagList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.date = date;
        this.photo = photo;
        this.tagList = tagList;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

}

