package com.cstewart.android.trycamel;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.URLUtil;

public class IntentParser {

    private static final String CAMEL_URL_FORMAT = "http://camelcamelcamel.com/search?q=%1$s";

    public static Uri parseIntent(Intent intent) {

        String url = null;
        if (Intent.ACTION_SEND.equals(intent.getAction())) {
            String extraText = intent.getStringExtra(Intent.EXTRA_TEXT);
            String extraSubject = intent.getStringExtra(Intent.EXTRA_SUBJECT);

            if (!TextUtils.isEmpty(extraText) && URLUtil.isNetworkUrl(extraText)) {
                url = extraText;
            } else if (!TextUtils.isEmpty(extraSubject) && URLUtil.isNetworkUrl(extraSubject)) {
                url = extraSubject;
            }
        }

        if (TextUtils.isEmpty(url)) {
            return null;
        }

        String amazonId = Uri.encode(url);
        if (TextUtils.isEmpty(amazonId)) {
            return null;
        }

        return Uri.parse(String.format(CAMEL_URL_FORMAT, amazonId));
    }

}
