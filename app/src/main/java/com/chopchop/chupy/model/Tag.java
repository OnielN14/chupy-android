package com.chopchop.chupy.model;

public class Tag {
    private String tagName;
    private int tagStatus = 0;

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(String tagName, int tagStatus) {
        this(tagName);
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
}
