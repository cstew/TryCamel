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
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setBuiltInZoomControls(true);
        binding.webview.getSettings().setDisplayZoomControls(false);
        binding.webview.setWebViewClient(new CamelClient(binding.webview));
        binding.webview.loadUrl(url, getHeaders());
    }

    private static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://camelcamelcamel.com/");
        return headers;
    }

    private static class CamelClient extends WebViewClient {

        private WebView webView;

        CamelClient(WebView webView) {
            this.webView = webView;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url, getHeaders());
            return false;
        }
    }

}
