package com.cstewart.android.trycamel.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.cstewart.android.trycamel.R;
import com.cstewart.android.trycamel.databinding.ViewTutorialScreenBinding;
import com.cstewart.android.trycamel.tutorial.Screen;

public class TutorialScreenView extends FrameLayout {

    private Screen screen;
    private ViewTutorialScreenBinding binding;

    public TutorialScreenView(Context context) {
        this(context, null);
    }

    public TutorialScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_tutorial_screen, this, true);
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
        updateUI();
    }

    private void updateUI() {
        if (screen == null) {
            return;
        }

        binding.image.setImageResource(screen.getImageResId());
        binding.title.setText(screen.getTextResId());
        binding.subtitle.setText(screen.getSubtitleResId());

        Linkify.addLinks(binding.title, Linkify.WEB_URLS);
    }
}
