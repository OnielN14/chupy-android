package com.chopchop.chupy.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_CHUPPY_APP = "spChuppyApp";
    public static final String SP_NAME = "name";
    public static final String SP_ID = "id";
    public static final String SP_EMAIL = "email";
    public static final String SP_PASSWORD = "password";

    public static final String SP_LOGGED_STATUS = "spLoggedStatus";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_CHUPPY_APP, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void setSharedPreferencesString(String sharedKey, String value){
        sharedPreferencesEditor.putString(sharedKey, value);
        sharedPreferencesEditor.commit();
    }

    public void setSharedPreferencesInt(String sharedKey, int value){
        sharedPreferencesEditor.putInt(sharedKey, value);
        sharedPreferencesEditor.commit();
    }

    public void setSharedPreferencesBoolean(String sharedKey, boolean value){
        sharedPreferencesEditor.putBoolean(sharedKey, value);
        sharedPreferencesEditor.commit();
    }

    public String getSharedName(){
        return sharedPreferences.getString(SP_NAME,"");
    }

    public String getSharedEmail(){
        return sharedPreferences.getString(SP_EMAIL,"");
    }

    public String getSharedPassword(){
        return sharedPreferences.getString(SP_PASSWORD,"");
    }

    public int getSharedId(){
        return sharedPreferences.getInt(SP_ID,0);
    }

    public boolean getSharedLoggedStatus(){
        return sharedPreferences.getBoolean(SP_LOGGED_STATUS,false);
    }
}

