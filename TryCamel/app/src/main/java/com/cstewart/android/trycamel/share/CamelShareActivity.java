package com.cstewart.android.trycamel.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.cstewart.android.trycamel.IntentParser;
import com.cstewart.android.trycamel.R;
import com.cstewart.android.trycamel.web.WebViewActivity;

public class CamelShareActivity extends Activity {
    private static final String TAG = "CamelShareActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri camelUri = IntentParser.parseIntent(getIntent());
        if (camelUri == null) {
            Toast.makeText(this, R.string.parse_error, Toast.LENGTH_LONG).show();
            logParseException(getIntent());
        } else {
            Intent shareIntent = WebViewActivity.newIntent(this, camelUri.toString());
            startActivity(shareIntent);
        }

        finish();
    }

    private void logParseException(Intent intent) {
        Log.e(TAG, "Unable to send to CamelCamelCamel: " + getIntent().toString());

        if (Intent.ACTION_MAIN.equals(intent.getAction())) {
            return;
        }

        Crashlytics.setString("action", intent.getAction());
        Crashlytics.setString("dataString", intent.getDataString());

        // Log all extras
        Bundle bundle = intent.getExtras();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if (value != null) {
                String extraString = String.format("%s %s (%s)", key, value.toString(), value.getClass().getName());
                Crashlytics.setString("extra", extraString);
            }
        }

        Crashlytics.logException(new UnsupportedOperationException("Unable to parse " + getIntent().toString()));
    }
}
