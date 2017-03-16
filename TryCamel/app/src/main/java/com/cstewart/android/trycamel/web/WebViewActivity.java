package com.cstewart.android.trycamel.web;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cstewart.android.trycamel.R;
import com.cstewart.android.trycamel.databinding.ActivityWebViewBinding;

import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "WebViewActivity.url";

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra(EXTRA_URL);

        final ActivityWebViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
        binding.webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                binding.webview.loadUrl(url, getHeaders());
                return false;
            }
        });
        binding.webview.loadUrl(url, getHeaders());
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://camelcamelcamel.com/");
        return headers;
    }
}
