package com.reevel.testreevel;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.reevel.testreevel.util.Util;

/**
 * Created by Klemen on 18.8.2016.
 */

public class CustomAnimations {

    public static void startAnimationsOnImageView(int oldY, int newY, View view) {

        float oldAlpha = Util.modulate(oldY, 0, 600, 1, 0.2f);
        float newAlpha = Util.modulate(newY, 0, 600, 1, 0.2f);

        AlphaAnimation alphaAnimation = new AlphaAnimation(oldAlpha, newAlpha);
        alphaAnimation.setDuration(1);

        float oldScale = Util.modulate(oldY, 0, 600, 1, 0.9f);
        float newScale = Util.modulate(newY, 0, 600, 1, 0.9f);

        ScaleAnimation scaleAnimation = new ScaleAnimation(oldScale, newScale, oldScale, newScale, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(true);

        view.startAnimation(animationSet);
    }


    public static void startAnimationsOnPlayButtonView(int oldY, int newY, ImageView imagePlayCircle, FrameLayout playButtonFrameLayout) {

        float oldAlpha = Util.modulate(oldY, 0, 300, 1, 0);
        float newAlpha = Util.modulate(oldY, 0, 300, 1, 0);

        if(newAlpha < 0.8){
            imagePlayCircle.clearAnimation();
        }else if(newAlpha > 0.8){
            startAnimationsOnPlayButtonCircle(imagePlayCircle);
        }

        AlphaAnimation alphaAnimation = new AlphaAnimation(oldAlpha, newAlpha);
        alphaAnimation.setDuration(1);
        alphaAnimation.setFillBefore(true);

        float oldScale = Util.modulate(oldY, 0, 600, 1, 0.5f);
        float newScale = Util.modulate(newY, 0, 600, 1, 0.5f);

        ScaleAnimation scaleAnimation = new ScaleAnimation(oldScale, newScale, oldScale, newScale, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setFillAfter(true);

        playButtonFrameLayout.startAnimation(animationSet);

    }

    public static void startAnimationsOnPlayButtonCircle(ImageView imagePlayCircle) {

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2.5f, 1, 2.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(Animation.INFINITE);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setStartOffset(800);

        imagePlayCircle.startAnimation(animationSet);
    }



}
