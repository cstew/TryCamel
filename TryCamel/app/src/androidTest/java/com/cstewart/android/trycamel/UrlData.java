package com.cstewart.android.trycamel;

public class UrlData {
    private String mUrl;
    private String mExpected;

    public UrlData(String expected, String url) {
        mExpected = expected;
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getExpected() {
        return mExpected;
    }
}
