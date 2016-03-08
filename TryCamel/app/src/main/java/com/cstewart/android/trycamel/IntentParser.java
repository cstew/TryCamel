package com.cstewart.android.trycamel;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class IntentParser {

    private static final String CAMEL_URL_FORMAT = "http://camelcamelcamel.com/search?q=%1$s";

    public static Uri parseIntent(Intent intent) {

        List<String> potentialUrls = new ArrayList<>();
        potentialUrls.add(intent.getStringExtra(Intent.EXTRA_TEXT));
        potentialUrls.add(intent.getStringExtra(Intent.EXTRA_SUBJECT));
        potentialUrls.add(intent.getDataString());

        for (String candidate : potentialUrls) {
            String foundUrl = findUrl(candidate);
            if (TextUtils.isEmpty(foundUrl)) {
                continue;
            }

            String amazonId = Uri.encode(foundUrl);
            if (TextUtils.isEmpty(amazonId)) {
                return null;
            }

            return Uri.parse(String.format(CAMEL_URL_FORMAT, amazonId));
        }

        return null;
    }

    private static String findUrl(String value) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }

        Matcher matcher = Patterns.WEB_URL.matcher(value);
        if (matcher.find()) {
            return matcher.group(0);
        }

        return null;
    }

}
