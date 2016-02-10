package com.cstewart.android.trycamel;

import android.content.Intent;
import android.net.Uri;
import android.test.AndroidTestCase;

public class IntentParserTest extends AndroidTestCase {

    private static final UrlData VALID_URL_DATA = new UrlData("http://camelcamelcamel.com/search?q=http%3A%2F%2Fwww.amazon.com%2FAndroid-Programming-Nerd-Ranch-Guide%2Fdp%2F0134171454%2Fref%3Dsr_1_3%3Fie%3DUTF8%26qid%3D1438264449%26sr%3D8-3%26keywords%3Dandroid%2Bprogramming", "http://www.amazon.com/Android-Programming-Nerd-Ranch-Guide/dp/0134171454/ref=sr_1_3?ie=UTF8&qid=1438264449&sr=8-3&keywords=android+programming");

    public void testIntentParse_validSendExtraText() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, VALID_URL_DATA.getUrl());
        intent.putExtra(Intent.EXTRA_SUBJECT, "Testing");

        Uri uri = IntentParser.parseIntent(intent);
        assertEquals(VALID_URL_DATA.getExpected(), uri.toString());
    }

    public void testIntentParse_validSendSubjectText() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Testing");
        intent.putExtra(Intent.EXTRA_SUBJECT, VALID_URL_DATA.getUrl());

        Uri uri = IntentParser.parseIntent(intent);
        assertEquals(VALID_URL_DATA.getExpected(), uri.toString());
    }

    public void testIntentParse_invalidExtras() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "Invalid");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Invalid");

        Uri uri = IntentParser.parseIntent(intent);
        assertEquals(null, uri);
    }

    public void testIntentParse_invalidAction() {
        Intent intent = new Intent("Invalid Action");
        intent.putExtra(Intent.EXTRA_SUBJECT, VALID_URL_DATA.getUrl());

        Uri uri = IntentParser.parseIntent(intent);
        assertEquals(null, uri);
    }

    public void testIntentParse_emptyIntent() {
        Intent intent = new Intent();
        Uri uri = IntentParser.parseIntent(intent);
        assertEquals(null, uri);
    }
}