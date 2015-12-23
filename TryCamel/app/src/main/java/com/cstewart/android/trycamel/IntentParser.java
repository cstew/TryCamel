package com.cstewart.android.trycamel;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.URLUtil;

import java.util.List;

public class IntentParser {

    private static final String AMAZON_URL = "www.amazon.";
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

        String amazonId = parseAmazonId(url);
        if (TextUtils.isEmpty(amazonId)) {
            return null;
        }

        return Uri.parse(String.format(CAMEL_URL_FORMAT, amazonId));
    }

    public static String parseAmazonId(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        Uri uri = Uri.parse(url);

        if (!uri.getHost().startsWith(AMAZON_URL)) {
            return null;
        }

        List<String> segments = uri.getPathSegments();

        if (segments == null || segments.size() <= 0) {
            return null;
        }

        for (int i = segments.size() - 1; i >= 0; i--) {
            String segment = segments.get(i);

            if (segment.startsWith("ref")) {
                continue;
            }

            return segment;
        }

        return null;
    }

}
