package com.tcc.lucca.scoutup.backgroundTasks;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lucca on 13/10/17.
 */

public class SharedPrefManager {

    private final static String SHARED_PREF_NAME = "sharedpref";
    private final static String KEY_ACESS_TOKEN = "token";


    private static Context context;

    private static SharedPrefManager sharedPrefManager;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){

        if(sharedPrefManager==null){

            sharedPrefManager = new SharedPrefManager(context);
        }
        return sharedPrefManager;
    }

    public boolean storeToken(String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACESS_TOKEN, token);
        editor.apply();

        return true;

    }

    public String getToken(){

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);

        return sharedPreferences.getString(KEY_ACESS_TOKEN, null);

    }
}
