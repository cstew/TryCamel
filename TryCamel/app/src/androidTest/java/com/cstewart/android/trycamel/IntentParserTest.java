package com.cstewart.android.trycamel;

import android.content.Intent;
import android.net.Uri;
import android.test.AndroidTestCase;

import java.util.Arrays;
import java.util.List;

public class IntentParserTest extends AndroidTestCase {

    private static final UrlData VALID_URL_DATA = new UrlData("http://camelcamelcamel.com/search?q=0134171454", "http://www.amazon.com/Android-Programming-Nerd-Ranch-Guide/dp/0134171454/ref=sr_1_3?ie=UTF8&qid=1438264449&sr=8-3&keywords=android+programming");

    private List<UrlData> mUrlData = Arrays.asList(
            new UrlData("0134171454", "http://www.amazon.com/Android-Programming-Nerd-Ranch-Guide/dp/0134171454/ref=sr_1_3?ie=UTF8&qid=1438264449&sr=8-3&keywords=android+programming"),
            new UrlData("B003L1ZYYM", "http://www.amazon.com/dp/B003L1ZYYM/ref=gbps_img_s-3_0842_40b26d04?smid=ATVPDKIKX0DER&pf_rd_p=2338600842&pf_rd_s=slot-3&pf_rd_t=701&pf_rd_i=gb_main&pf_rd_m=ATVPDKIKX0DER&pf_rd_r=06E4BZMDYB8MS58VW74P"),
            new UrlData("B00UJ3IREE", "http://www.amazon.com/Roku-Streaming-Player-4210R-Processor/dp/B00UJ3IREE?psc=1&SubscriptionId=AKIAJ7T5BOVUVRD2EFYQ&tag=camelproducts-20&linkCode=xm2&camp=2025&creative=165953&creativeASIN=B00UJ3IREE"),
            new UrlData("B00UJ3IREE", "http://www.amazon.com/Roku-Streaming-Player-4210R-Processor/dp/B00UJ3IREE/?psc=1&SubscriptionId=AKIAJ7T5BOVUVRD2EFYQ&tag=camelproducts-20&linkCode=xm2&camp=2025&creative=165953&creativeASIN=B00UJ3IREE"),
            new UrlData("B001Q4HQVU", "http://www.amazon.com/gp/aw/d/B001Q4HQVU/ref=s9_hps_gw_g229_i1?pf_rd_m=ATVPDKIKX0DER&pf_rd_s=mobile-3&pf_rd_r=1JJ13KWT66TEX5DPG3G9&pf_rd_t=36701&pf_rd_p=1873877502&pf_rd_i=mobile"),
            new UrlData(null, "http://www.amazon.com"),
            new UrlData(null, "http://www.google.com/dp/B003L1ZYYM/ref=gbps_img_s"),
            new UrlData(null, ""),
            new UrlData(null, null)
    );


    public void testParseAmazonId() {
        for (UrlData data : mUrlData) {
            assertEquals(data.getExpected(), IntentParser.parseAmazonId(data.getUrl()));
        }
    }

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
}