package com.chopchop.chupy.feature.petservice.models;

import java.security.PrivateKey;

public class MyData {
    private String mNames;
    private String mProduct;

    public MyData(String mNames, String mProduct) {
        this.mNames = mNames;
        this.mProduct = mProduct;
    }

    public String getmNames() {
        return mNames;
    }

    public void setmNames(String mNames) {
        this.mNames = mNames;
    }

    public String getmProduct() {
        return mProduct;
    }

    public void setmProduct(String mProduct) {
        this.mProduct = mProduct;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;

        MyData itemCompare = (MyData) obj;
        if(itemCompare.getmNames().equals(this.getmNames()))
            return true;

        if(itemCompare.getmProduct().equals(this.getmProduct()))
            return true;

        return false;
    }
}
