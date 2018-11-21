package com.cstewart.android.trycamel;

import android.content.Context;
import android.content.SharedPreferences;

public class TryCamelPreferences {
    private static final String SHARED_PREFS_TRY_CAMEL = "trycamel";
    private static final String PREF_URL = "search_url";

    private Context context;

    public TryCamelPreferences(Context context) {
        this.context = context;
    }

    public String getUrl() {
        String[] localeUrls = context.getResources().getStringArray(R.array.locale_url);

        return getSharedPreferences()
                .getString(PREF_URL, localeUrls[0]);
    }

    public void setUrl(String url) {
        getSharedPreferences()
                .edit()
                .putString(PREF_URL, url)
                .apply();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(SHARED_PREFS_TRY_CAMEL, Context.MODE_PRIVATE);
    }

}
