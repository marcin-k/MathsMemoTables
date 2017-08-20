package com.mvk.mathsmemotables;

import android.animation.Animator;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GamePlay0 extends BaseOfGame {

    //UI
    @BindView(R.id.result1) TextView result1;
    @BindView(R.id.result2) TextView result2;
    @BindView(R.id.result3) TextView result3;

//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play0);
        ButterKnife.bind(this);
        selected = new boolean[28];
        //sets all to false
        Arrays.fill(selected, false);
        elementsAllocation();

        hideValues();

        setUpFonts(result1);
        setUpFonts(result2);
        setUpFonts(result3);
        for (int i = 0; i < selected.length; i++) {
            setUpFonts(getTextAtPosition(i + 1));
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
            R.id.q8back, R.id.q9back, R.id.q10back, R.id.q11back, R.id.q12back, R.id.q13back, R.id.q14back,
            R.id.q15back, R.id.q16back, R.id.q17back, R.id.q18back, R.id.q19back, R.id.q20back, R.id.q21back,
            R.id.q22back, R.id.q23back, R.id.q24back, R.id.q25back, R.id.q26back, R.id.q27back, R.id.q28back,})
    public void selected(ImageView button) {
    //---------------------------------- if element is hidden --------------------------------------
        if (button.getAlpha() == 0.0f) {
            //
        }
    //---------------------------- if two element is already selected ------------------------------
        else if (positionOfAlreadySelectedElement(selected) == -2) {
            //deselect all (and update tracking array (selected))
            deselectAll();
            //select new element
            flipImage(button, getButtonNumber(button) - 1);
        }
    //---------------------------- if one element is already selected ------------------------------
        else if (positionOfAlreadySelectedElement(selected) != -1) {
            //if user press again on the same button - deselect it
            if (positionOfAlreadySelectedElement(selected) == getButtonNumber(button)) {
                flipImage(button, getButtonNumber(button) - 1);
            }
            //TODO: finish the method
            //compare two values
            // positionOfAlreadySelectedElement(selected) - position of item 1
            // getButtonNumber(button)                    - position of item 2
            else {
                //TODO: change string comparison to maths compare
                //select second one
                flipImage(button, getButtonNumber(button) - 1);

                if (getValue(getTextAtPosition(getButtonNumber(button)).getText().toString()) ==
                        getValue(getTextAtPosition(positionOfAlreadySelectedElement(selected)).getText().toString())) {

                    //hides buttons with the same text
                    button.setAlpha(0.0f);
                    getButtonAtPosition(positionOfAlreadySelectedElement(selected)).setAlpha(0.0f);

                    //hides text on the buttons
                    getTextAtPosition(getButtonNumber(button)).setVisibility(View.INVISIBLE);
                    getTextAtPosition(positionOfAlreadySelectedElement(selected)).setVisibility(View.INVISIBLE);

                    result1.setText(getTextAtPosition(getButtonNumber(button)).getText());
                    result2.setText("=");
                    result3.setText(getTextAtPosition(positionOfAlreadySelectedElement(selected)).getText());
                }
            }
        }
    //------------------------------ if no elements have been selected -----------------------------
        else {
            flipImage(button, getButtonNumber(button) - 1);
        }
    }
}
