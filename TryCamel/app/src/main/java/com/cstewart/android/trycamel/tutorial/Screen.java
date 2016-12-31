package com.cstewart.android.trycamel.tutorial;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

public class Screen {

    private int colorResId;
    private int textResId;
    private int subtitleResId;
    private int imageResId;

    public Screen(@ColorRes int colorResId, @StringRes int textResId, @StringRes int subtextResId, @DrawableRes int imageResId) {
        this.colorResId = colorResId;
        this.textResId = textResId;
        this.subtitleResId = subtextResId;
        this.imageResId = imageResId;
    }

    public int getColor(Context context) {
        return ContextCompat.getColor(context, colorResId);
    }

    public int getTextResId() {
        return textResId;
    }

    public int getSubtitleResId() {
        return subtitleResId;
    }

    public int getImageResId() {
        return imageResId;
    }
}
