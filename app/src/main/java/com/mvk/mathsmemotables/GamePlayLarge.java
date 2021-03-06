package com.mvk.mathsmemotables;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.transition.Fade;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class GamePlayLarge extends BaseOfGame {

    //UI references
    @BindView(R.id.homeButton) ImageView homeButton;
    @BindView(R.id.settingsButton) ImageView settingButton;
    @BindView(R.id.soundButton) ImageView soundButton;

//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupTransitions();

        numberOfCardPairs = 14;

        positions = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18,
                19, 20, 21, 22, 23, 24, 25, 26, 27};

        backButtons[10] = R.id.q11back;        backButtons[11] = R.id.q12back;
        backButtons[12] = R.id.q13back;        backButtons[13] = R.id.q14back;
        backButtons[14] = R.id.q15back;        backButtons[15] = R.id.q16back;
        backButtons[16] = R.id.q17back;        backButtons[17] = R.id.q18back;

        backButtons[18] = R.id.q19back;        backButtons[19] = R.id.q20back;
        backButtons[20] = R.id.q21back;        backButtons[21] = R.id.q22back;
        backButtons[22] = R.id.q23back;        backButtons[23] = R.id.q24back;
        backButtons[24] = R.id.q25back;        backButtons[25] = R.id.q26back;
        backButtons[26] = R.id.q27back;        backButtons[27] = R.id.q28back;

        textButtons[10] = R.id.q11text;        textButtons[11] = R.id.q12text;
        textButtons[12] = R.id.q13text;        textButtons[13] = R.id.q14text;
        textButtons[14] = R.id.q15text;        textButtons[15] = R.id.q16text;
        textButtons[16] = R.id.q17text;        textButtons[17] = R.id.q18text;

        textButtons[18] = R.id.q19text;        textButtons[19] = R.id.q20text;
        textButtons[20] = R.id.q21text;        textButtons[21] = R.id.q22text;
        textButtons[22] = R.id.q23text;        textButtons[23] = R.id.q24text;
        textButtons[24] = R.id.q25text;        textButtons[25] = R.id.q26text;
        textButtons[26] = R.id.q27text;        textButtons[27] = R.id.q28text;

        setContentView(R.layout.activity_game_play_large);
        ButterKnife.bind(this);
        selected = new boolean[28];
        alreadyCompleted = new boolean[28];

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
            setUpFonts(getTextAtPosition(i + 1));
        }

        //creates references to clapping monkey animation
        ImageView img = findViewById(R.id.clappingMonkey);
        img.setImageResource(R.drawable.monkey);
        // Get the background, which has been compiled to an AnimationDrawable object.
        frameAnimation = (AnimationDrawable) img.getDrawable();

    }



//************************************ Card OnClick ************************************************
    @OnClick({R.id.q1back, R.id.q2back, R.id.q3back, R.id.q4back, R.id.q5back, R.id.q6back, R.id.q7back,
            R.id.q8back, R.id.q9back, R.id.q10back, R.id.q11back, R.id.q12back, R.id.q13back, R.id.q14back,
            R.id.q15back, R.id.q16back, R.id.q17back, R.id.q18back, R.id.q19back, R.id.q20back, R.id.q21back,
            R.id.q22back, R.id.q23back, R.id.q24back, R.id.q25back, R.id.q26back, R.id.q27back, R.id.q28back,})
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
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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
                Controller.getInstance().setLetsMusicPlay(false, getApplicationContext());
                Controller.getInstance().animateButtonReleased(soundButton, R.drawable.no_sound);
            }
            else {
                Controller.getInstance().setLetsMusicPlay(true, getApplicationContext());
                Controller.getInstance().animateButtonReleased(soundButton, R.drawable.sound);
            }
        }
        return true;
    }

//**************************** Sets up Activity Transitions ****************************************
    public void setupTransitions(){
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
    }

//*************************************** OnResume *************************************************
    @Override
    protected void onResume() {
        super.onResume();
        //changes the music button if music is not playing
        if (!Controller.getInstance().isLetsMusicPlay()){
            Controller.getInstance().animateButtonReleased(soundButton, R.drawable.no_sound);
        }
        else{
            Controller.getInstance().animateButtonReleased(soundButton, R.drawable.sound);
        }
    }
}
