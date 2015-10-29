package com.vfdev.wheelyoumrmee;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class Animations {

    TranslateAnimation mButtonAnimation;

    // -------- Public methods
    public Animations() {
        setupButtonAnimation();
    }

    public Animation getButtonAnimation() {
        return mButtonAnimation;
    }

    // -------- Other methods
    private void setupButtonAnimation() {
        mButtonAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.05f);
        mButtonAnimation.setDuration(150);
    }

}
