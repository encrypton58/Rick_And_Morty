package com.em_and_ei.company.rickandmorty.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class HelperSharedPreferences {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static Context context;
    private static HelperSharedPreferences helper;

    private HelperSharedPreferences(Context ctx) {
        context = ctx;
        preferences = context.getSharedPreferences("morty", Context.MODE_PRIVATE);
         this.editor = preferences.edit();

    }

    public static synchronized HelperSharedPreferences getInstance(Context context){
        if(helper == null){
            helper = new HelperSharedPreferences(context);
        }
        return helper;
    }

    public static void saveInt(String key, int value){
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getIntValue(String key){
        return preferences.getInt(key, 1);
    }

}
