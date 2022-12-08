package com.example.receptionsystem;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private final String PREF_NAME = "TEST_PREF";
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    public SharedPrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefEditor = pref.edit();
    }

    public boolean setStringValue(String key, String value) {
        return prefEditor.putString(key, value).commit();
    }

    public String getStringValue(String stringKeyValue) {
        return pref.getString(stringKeyValue, "");
    }
}
