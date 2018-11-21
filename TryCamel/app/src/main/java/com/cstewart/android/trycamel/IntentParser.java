package com.cstewart.android.trycamel;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class IntentParser {

    public static Uri parseIntent(String urlFormat, Intent intent) {

        List<String> potentialUrls = new ArrayList<>();
        potentialUrls.add(intent.getStringExtra(Intent.EXTRA_TEXT));
        potentialUrls.add(intent.getStringExtra(Intent.EXTRA_SUBJECT));
        potentialUrls.add(intent.getDataString());

        // 1. Hunt for a URL
        for (String candidate : potentialUrls) {
            String foundUrl = findUrl(candidate);
            if (TextUtils.isEmpty(foundUrl)) {
                continue;
            }

            String amazonId = Uri.encode(foundUrl);
            if (TextUtils.isEmpty(amazonId)) {
                return null;
            }

            return Uri.parse(String.format(urlFormat, amazonId));
        }

        // 2. Fallback on the EXTRA_TEXT value if there is one
        String extraText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (!TextUtils.isEmpty(extraText)) {
            String amazonId = Uri.encode(extraText);
            if (TextUtils.isEmpty(amazonId)) {
                return null;
            }

            return Uri.parse(String.format(urlFormat, amazonId));
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
