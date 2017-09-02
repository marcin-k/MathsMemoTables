package com.mvk.mathsmemotables;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class GamePlaySmall extends BaseOfGame {


    //UI references
    @BindView(R.id.homeButton) ImageView homeButton;
    @BindView(R.id.settingsButton) ImageView settingButton;
    @BindView(R.id.soundButton) ImageView soundButton;

//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        positions = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        setContentView(R.layout.activity_game_play_small);
        ButterKnife.bind(this);
        selected = new boolean[10];
        alreadyCompleted = new boolean[10];

        //sets all to false
        Arrays.fill(selected, false);
        Arrays.fill(alreadyCompleted, false);

        shuffleArray(positions);
        elementsAllocation();
        hideValues();

        setUpFonts(result1);
        setUpFonts(result2);
        setUpFonts(result3);
        for (int i = 0; i < selected.length; i++) {
            setUpFonts(getTextAtPosition(i+1));
        }

        //creates references to clapping monkey animation
        ImageView img = (ImageView) findViewById(R.id.clappingMonkey);
        img.setImageResource(R.drawable.monkey);
        // Get the background, which has been compiled to an AnimationDrawable object.
        frameAnimation = (AnimationDrawable) img.getDrawable();

    }



//************************************ Card OnClick ************************************************
    @OnClick({R.id.q1back, R.id.q2back, R.id.q3back, R.id.q4back, R.id.q5back, R.id.q6back, R.id.q7back,
            R.id.q8back, R.id.q9back, R.id.q10back})
    public void cardClicked(ImageView button) {
        cardSelected(button);
    }

//**************************** Navigation Buttons OnClick ******************************************
    @OnTouch(R.id.homeButton)
    public boolean touchHome(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Controller.getInstance().animateButtonTouched(homeButton);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Controller.getInstance().animateButtonReleased(homeButton);
            Intent intent = new Intent(this, WelcomeScreen.class);
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