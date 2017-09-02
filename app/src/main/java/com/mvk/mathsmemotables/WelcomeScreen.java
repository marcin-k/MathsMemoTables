package com.mvk.mathsmemotables;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class WelcomeScreen extends Activity {

    //UI references
    @BindView(R.id.playButton) ImageView playButton;
    @BindView(R.id.settingsButton) ImageView settingButton;
    @BindView(R.id.soundButton) ImageView soundButton;


//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ButterKnife.bind(this);


//        //face animation
//        ImageView img = (ImageView)findViewById(R.id.welcome_monkey_head);
//        img.setImageResource(R.drawable.welcome_monkey);
//        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();
//        frameAnimation.start();

    }

//**************************** Navigation Buttons OnClick ******************************************
    @OnTouch(R.id.playButton)
    public boolean touchPlay(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Controller.getInstance().animateButtonTouched(playButton);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Controller.getInstance().animateButtonReleased(playButton);
            Intent intent = new Intent(this, Controller.getInstance().getGameplayClass());
            startActivity(intent);
        }
        return true;
    }

    @OnTouch(R.id.settingsButton)
    public boolean touchSettings(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Controller.getInstance().animateButtonTouched(settingButton);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Controller.getInstance().animateButtonReleased(settingButton);
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        }
        return true;
    }

    @OnTouch(R.id.soundButton)
    public boolean touchSound(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                Controller.getInstance().animateButtonTouched(soundButton);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                Controller.getInstance().animateButtonReleased(soundButton);
                if (Controller.getInstance().isLetsMusicPlay()){
                    Controller.getInstance().setLetsMusicPlay(false);
                    Controller.getInstance().animateButtonReleased(soundButton, R.drawable.no_sound);
                }
                else {
                    Controller.getInstance().setLetsMusicPlay(true);
                    Controller.getInstance().animateButtonReleased(soundButton, R.drawable.sound);
                }
            }
            return true;
    }
}
