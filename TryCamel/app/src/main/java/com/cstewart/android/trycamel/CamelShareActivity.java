package com.cstewart.android.trycamel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class CamelShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri camelUri = IntentParser.parseIntent(getIntent());

        if (camelUri == null) {
            Toast.makeText(this, R.string.parse_error, Toast.LENGTH_LONG).show();
        } else {
            Intent shareIntent = new Intent(Intent.ACTION_VIEW);
            shareIntent.setData(camelUri);
            startActivity(shareIntent);
        }

        finish();
    }
}
