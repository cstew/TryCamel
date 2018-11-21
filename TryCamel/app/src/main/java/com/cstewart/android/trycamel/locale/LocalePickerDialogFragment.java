package com.cstewart.android.trycamel.locale;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.cstewart.android.trycamel.R;
import com.cstewart.android.trycamel.TryCamelPreferences;

public class LocalePickerDialogFragment extends DialogFragment {

    private int selectedIndex = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final TryCamelPreferences preferences = new TryCamelPreferences(getContext());
        int savedIndex = getCurrentIndex(preferences.getUrl());

        selectedIndex = savedIndex;

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.chose_amazon_store)
                .setSingleChoiceItems(R.array.locale_name, savedIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedIndex = i;
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] localeUrls = getContext().getResources().getStringArray(R.array.locale_url);
                        preferences.setUrl(localeUrls[selectedIndex]);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private int getCurrentIndex(String currentUrl) {
        String[] localeUrls = getContext().getResources().getStringArray(R.array.locale_url);
        for (int i = 0; i < localeUrls.length; i++) {
            String url = localeUrls[i];
            if (url.equals(currentUrl)) {
                return i;
            }
        }

        return 0;
    }

}
