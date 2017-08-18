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

    @BindView(R.id.q1text) TextView q1text;
    @BindView(R.id.q2text) TextView q2text;
    @BindView(R.id.q5text) TextView q5text;
    @BindView(R.id.q6text) TextView q6text;

    @BindView(R.id.q1back) ImageView q1back;

    @BindView(R.id.result1) TextView result1;
    @BindView(R.id.result2) TextView result2;
    @BindView(R.id.result3) TextView result3;



    boolean[] selected;
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
        for (int i=0; i<selected.length; i++){
            setUpFonts(getTextAtPosition(i+1));
        }

//        ImageView img = (ImageView)findViewById(R.id.imageView);
//        img.setBackgroundResource(R.drawable.monkey);
//
//        // Get the background, which has been compiled to an AnimationDrawable object.
//        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
//
//        // Start the animation (looped playback by default).
//        frameAnimation.start();
    }

//--------------------------------------OnClick Method----------------------------------------------
    @OnClick({R.id.q1back, R.id.q2back, R.id.q3back, R.id.q4back, R.id.q5back, R.id.q6back, R.id.q7back,
            R.id.q8back, R.id.q9back, R.id.q10back, R.id.q11back, R.id.q12back, R.id.q13back, R.id.q14back,
            R.id.q15back, R.id.q16back, R.id.q17back, R.id.q18back, R.id.q19back, R.id.q20back, R.id.q21back,
            R.id.q22back, R.id.q23back, R.id.q24back, R.id.q25back, R.id.q26back, R.id.q27back, R.id.q28back,})
    public void selected(ImageView button){
//**************************** if element is hidden***********************
        if (button.getAlpha()==0.0f){
            //
        }
//****************** if two element is already selected ******************
        else if (positionOfAlreadySelectedElement(selected)==-2){
            //deselect all (and update tracking array (selected))
            deselectAll();
            //select new element
            flipImage(button, getButtonNumber(button)-1);
        }
//****************** if one element is already selected ******************
        else if (positionOfAlreadySelectedElement(selected)!=-1){
            //if user press again on the same button - deselect it
            if (positionOfAlreadySelectedElement(selected)== getButtonNumber(button)){
                flipImage(button, getButtonNumber(button)-1);
            }
            //TODO: finish the method
            //compare two values
            // positionOfAlreadySelectedElement(selected) - position of item 1
            // getButtonNumber(button)                    - position of item 2
            else{
                //TODO: change string comparison to maths compare
                //select second one
                flipImage(button, getButtonNumber(button)-1);

                if (getValue(getTextAtPosition(getButtonNumber(button)).getText().toString())==
                        getValue(getTextAtPosition(positionOfAlreadySelectedElement(selected)).getText().toString())){

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
//****************** if no elements have been selected ******************
        else {
            flipImage(button, getButtonNumber(button)-1);
        }
    }
//--------------------------------------------------------------------------------------------------

//*************************** Deselects image animation *********************************************
    public void deselectAll(){
        for (int i = 0; i < backButtons.length; i++) {
            if (selected[i]==true){
                ImageView imageView = (ImageView)findViewById(backButtons[i]);
                flipImage(imageView, i);
            }
        }
    }

//*************************** Flipping image animation *********************************************
public void flipImage(final ImageView iv, final int position) {
    final Drawable drawable = getResources().getDrawable(R.drawable.button);
    final Drawable drawable2 = getResources().getDrawable(R.drawable.button_selected);

    iv.setRotationY(0f);

    if (selected[position] == true) {
        //duration of first half flip
        iv.animate().setDuration(100);
        iv.animate().rotationY(90f).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                hideReveal(iv, true);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                iv.setImageDrawable(drawable);
                selected[position] = false;
                iv.setRotationY(270f);
                iv.animate().rotationY(360f).setListener(null);
                //duration of second half of flip
                iv.animate().setDuration(100);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    } else {
        //duration of first half flip
        iv.animate().setDuration(100);
        iv.animate().rotationY(-90f).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                hideReveal(iv, false);
                iv.setImageDrawable(drawable2);
                selected[position] = true;
                iv.setRotationY(-270f);
                iv.animate().rotationY(-360f).setListener(null);
                //duration of second half of flip
                iv.animate().setDuration(100);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    }

}

    //***************************** Hide/reveal value **********************************************
    public void hideReveal(ImageView iv, boolean hide){
        int position = getButtonNumber(iv);
        if (hide){
            getTextAtPosition(position).setAlpha(0.0f);
        }
        else{
            getTextAtPosition(position).setAlpha(1.0f);
        }
    }
    //********************************* Flip All ***************************************************
    public void hideValues(){
        for(int i=0; i<selected.length; i++){
            getTextAtPosition(i+1).setAlpha(0.0f);
        }
    }

    //********************************* Set up fonts ***********************************************
    public void setUpFonts(TextView textView){
        //Set up font for provided TextView
        Typeface berkshireSwash = Typeface.createFromAsset(getAssets(), "fonts/BerkshireSwash-Regular.ttf");
        Typeface pacifico = Typeface.createFromAsset(getAssets(), "fonts/Pacifico-Regular.ttf");

        textView.setTypeface(pacifico);
    }

}
