package com.cstewart.android.trycamel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

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
            Intent shareIntent = new Intent(Intent.ACTION_VIEW);
            shareIntent.setData(camelUri);
            startActivity(shareIntent);
        }

        finish();
    }

    private void logParseException(Intent intent) {
        Log.e(TAG, "Unable to send to CamelCamelCamel: " + getIntent().toString());

        if (Intent.ACTION_MAIN.equals(intent.getAction())) {
            return;
        }

        String extraText = intent.getStringExtra(Intent.EXTRA_TEXT);
        String extraSubject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        Crashlytics.setString("action", intent.getAction());
        Crashlytics.setString("dataString", intent.getDataString());
        Crashlytics.setString("extraText", extraText);
        Crashlytics.setString("extraSubject", extraSubject);
        Crashlytics.logException(new UnsupportedOperationException("Unable to parse " + getIntent().toString()));
    }
}
