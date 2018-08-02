package com.chopchop.chupy.models;

public class Tag {
    private int id;
    private String tagName;
    private int tagStatus = 0;

    public Tag(int id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public Tag(int id, String tagName, int tagStatus) {
        this(id, tagName);
        this.tagStatus = tagStatus;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(int tagStatus) {
        this.tagStatus = tagStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
