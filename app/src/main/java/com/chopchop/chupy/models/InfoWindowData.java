package com.chopchop.chupy.models;

public class InfoWindowData {
    private String mLabel;
    private String mIcon;
    private Double mLatitude;
    private Double mLongitude;

    public InfoWindowData(String mLabel, String mIcon, Double mLatitude, Double mLongitude) {
        this.mLabel = mLabel;
        this.mIcon = mIcon;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public String getmLabel() {
        return mLabel;
    }

    public void setmLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public Double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }
}
