package com.cstewart.android.trycamel.tutorial;

import android.animation.ArgbEvaluator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.cstewart.android.trycamel.R;
import com.cstewart.android.trycamel.databinding.ActivityTutorialBinding;
import com.cstewart.android.trycamel.view.TutorialScreenView;

import java.util.Arrays;
import java.util.List;

public class TutorialActivity extends AppCompatActivity {

    private List<Screen> screens = Arrays.asList(
            new Screen(R.color.amber_500, R.string.tutorial_intro, R.string.tutorial_intro_subtitle, R.drawable.tutorial_1),
            new Screen(R.color.brown_500, R.string.tutorial_intro_2, R.string.tutorial_intro_subtitle_2, R.drawable.tutorial_2),
            new Screen(R.color.green_500, R.string.tutorial_share_instruction, R.string.tutorial_share_instruction_subtitle, R.drawable.tutorial_amazon_share),
            new Screen(R.color.green_500, R.string.tutorial_screen_share_browser, R.string.tutorial_screen_share_browser_subtitle, R.drawable.tutorial_browser_share),
            new Screen(R.color.green_500, R.string.tutorial_screen_share_fake, R.string.tutorial_screen_share_fake_subtitle, R.drawable.tutorial_fake_share),
            new Screen(R.color.light_blue_500, R.string.tutorial_screen_4, R.string.tutorial_screen_subtitle_4, R.drawable.tutorial_4),
            new Screen(R.color.blue_gray_700, R.string.tutorial_screen_5, R.string.tutorial_screen_subtitle_5, R.drawable.tutorial_1)
    );

    private ActivityTutorialBinding binding;
    private ArgbEvaluator argbEvaluator;
    private TutorialAdapter tutorialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        argbEvaluator = new ArgbEvaluator();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial);
        tutorialAdapter = new TutorialAdapter(screens);
        binding.viewpager.setAdapter(tutorialAdapter);
        binding.viewpager.addOnPageChangeListener(pageChangeListener);

        binding.indicator.setViewPager(binding.viewpager);

        binding.next.setOnClickListener(nextClickListener);
    }

    private static class TutorialAdapter extends PagerAdapter {

        private List<Screen> screens;

        public TutorialAdapter(List<Screen> screens) {
            this.screens = screens;
        }

        @Override
        public int getCount() {
            return screens.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TutorialScreenView view = new TutorialScreenView(container.getContext());
            container.addView(view);

            Screen screen = screens.get(position);
            view.setScreen(screen);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Screen getScreen(int position) {
            return screens.get(position);
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        private int getColor(int position) {
            return tutorialAdapter.getScreen(position).getColor(TutorialActivity.this);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            boolean isBeforeLastPage = position < tutorialAdapter.getCount() - 1;

            int currentColor = getColor(position);
            int calculatedColor = currentColor;

            if (isBeforeLastPage) {
                int nextColor = getColor(position + 1);
                calculatedColor = (int) argbEvaluator.evaluate(positionOffset, currentColor, nextColor);
            }

            binding.getRoot().setBackgroundColor(calculatedColor);
        }

        @Override
        public void onPageSelected(int position) {
            boolean isAtEnd = position >= tutorialAdapter.getCount() - 1;
            int nextButtonVisibility = isAtEnd ? View.INVISIBLE : View.VISIBLE;
            binding.next.setVisibility(nextButtonVisibility);
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };

    private View.OnClickListener nextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1, true);
        }
    };
}
