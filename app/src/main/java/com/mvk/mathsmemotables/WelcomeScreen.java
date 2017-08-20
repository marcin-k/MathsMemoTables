package com.mvk.mathsmemotables;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class WelcomeScreen extends Activity {

    //UI references
    @BindView(R.id.playButton) ImageView playButton;
    @BindView(R.id.settingsButton) ImageView settingButton;
    @BindView(R.id.soundButton) ImageView soundButton;

    //sound on/off
    boolean letsMusicPlay;


//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ButterKnife.bind(this);

        letsMusicPlay = true;

//        //face animation
//        ImageView img = (ImageView)findViewById(R.id.welcome_monkey_head);
//        img.setImageResource(R.drawable.welcome_monkey);
//        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();
//        frameAnimation.start();

    }

//**************************** Navigation Buttons OnClick*******************************************
    @OnTouch(R.id.playButton)
    public boolean touchPlay(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            animateButtonTouched(playButton);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            animateButtonReleased(playButton);
            Intent intent = new Intent(this, GamePlay0.class);
            startActivity(intent);
        }
        return true;
    }

    @OnTouch(R.id.settingsButton)
    public boolean touchSettings(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            animateButtonTouched(settingButton);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            animateButtonReleased(settingButton);
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }
        return true;
    }

    @OnTouch(R.id.soundButton)
    public boolean touchSound(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                animateButtonTouched(soundButton);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                animateButtonReleased(soundButton);
                if (letsMusicPlay){
                    letsMusicPlay=false;
                    animateButtonReleased(soundButton, R.drawable.no_sound);
                }
                else {
                    letsMusicPlay=true;
                    animateButtonReleased(soundButton, R.drawable.sound);
                }
            }
            return true;
    }

//************************** Navigation Button pressed animations **********************************

    //on touch down - shrink
    private void animateButtonTouched(ImageView imageView){
        ObjectAnimator buttonXDown = ObjectAnimator.ofFloat(imageView, "scaleX", 1, 0.9f);
        ObjectAnimator buttonYDown = ObjectAnimator.ofFloat(imageView, "scaleY", 1, 0.9f);
        AnimatorSet scalePlayButtonDown = new AnimatorSet();
        scalePlayButtonDown.playTogether(buttonXDown, buttonYDown);
        scalePlayButtonDown.setDuration(100);
        scalePlayButtonDown.start();

    }

    //on release - resize to original size
    private void animateButtonReleased(ImageView imageView){
        ObjectAnimator buttonXUp = ObjectAnimator.ofFloat(imageView, "scaleX", 0.9f, 1);
        ObjectAnimator buttonYUp = ObjectAnimator.ofFloat(imageView, "scaleY", 0.9f, 1);
        AnimatorSet scalePlayButtonUp = new AnimatorSet();
        scalePlayButtonUp.playTogether(buttonXUp, buttonYUp);
        scalePlayButtonUp.setDuration(100);
        scalePlayButtonUp.start();
    }

    //on release - resize to original size and changes the imageView to newImageView
    // used for sound/no sound button
    private void animateButtonReleased(ImageView imageView, int newImageView){
        imageView.setImageResource(newImageView);

        ObjectAnimator buttonXUp = ObjectAnimator.ofFloat(imageView, "scaleX", 0.9f, 1);
        ObjectAnimator buttonYUp = ObjectAnimator.ofFloat(imageView, "scaleY", 0.9f, 1);
        AnimatorSet scalePlayButtonUp = new AnimatorSet();
        scalePlayButtonUp.playTogether(buttonXUp, buttonYUp);
        scalePlayButtonUp.setDuration(100);
        scalePlayButtonUp.start();
    }

}
