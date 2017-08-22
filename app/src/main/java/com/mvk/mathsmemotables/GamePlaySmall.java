package com.mvk.mathsmemotables;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GamePlaySmall extends BaseOfGame {

//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        positions = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        setContentView(R.layout.activity_game_play_small);
        ButterKnife.bind(this);
        selected = new boolean[10];
        //sets all to false
        Arrays.fill(selected, false);

        shuffleArray(positions);
        elementsAllocation();
        hideValues();

        setUpFonts(result1);
        setUpFonts(result2);
        setUpFonts(result3);
        for (int i = 0; i < selected.length; i++) {
            setUpFonts(getTextAtPosition(i+1));
        }

        ImageView img = (ImageView) findViewById(R.id.clappingMonkey);
        img.setImageResource(R.drawable.monkey);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();
        // Start the animation (looped playback by default).
//        frameAnimation.start();

    }



//************************************ Card OnClick ************************************************
    @OnClick({R.id.q1back, R.id.q2back, R.id.q3back, R.id.q4back, R.id.q5back, R.id.q6back, R.id.q7back,
            R.id.q8back, R.id.q9back, R.id.q10back})
    public void cardClicked(ImageView button) {
        cardSelected(button);
    }
}