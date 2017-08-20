package com.mvk.mathsmemotables;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by marcin on 26/07/2017.
 */

public class BaseOfGame extends Activity {

    //cards
    int[] backButtons  = new int[28];
    int[] textButtons = new int[28];

    //equations and results arrays
    int [] firstElements = new int[14];
    int [] secondElements = new int[14];
    int [] result = new int[14];

    //position of elements array, used to randomly allocate elements
    int [] positions = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27};

    //tracks flipped over cards
    boolean[] selected;

//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backButtons[0] = R.id.q1back;        backButtons[1] = R.id.q2back;
        backButtons[2] = R.id.q3back;        backButtons[3] = R.id.q4back;
        backButtons[4] = R.id.q5back;        backButtons[5] = R.id.q6back;
        backButtons[6] = R.id.q7back;        backButtons[7] = R.id.q8back;
        backButtons[8] = R.id.q9back;        backButtons[9] = R.id.q10back;
        backButtons[10] = R.id.q11back;        backButtons[11] = R.id.q12back;
        backButtons[12] = R.id.q13back;        backButtons[13] = R.id.q14back;
        backButtons[14] = R.id.q15back;        backButtons[15] = R.id.q16back;
        backButtons[16] = R.id.q17back;        backButtons[17] = R.id.q18back;
        backButtons[18] = R.id.q19back;        backButtons[19] = R.id.q20back;
        backButtons[20] = R.id.q21back;        backButtons[21] = R.id.q22back;
        backButtons[22] = R.id.q23back;        backButtons[23] = R.id.q24back;
        backButtons[24] = R.id.q25back;        backButtons[25] = R.id.q26back;
        backButtons[26] = R.id.q27back;        backButtons[27] = R.id.q28back;

        textButtons[0] = R.id.q1text;        textButtons[1] = R.id.q2text;
        textButtons[2] = R.id.q3text;        textButtons[3] = R.id.q4text;
        textButtons[4] = R.id.q5text;        textButtons[5] = R.id.q6text;
        textButtons[6] = R.id.q7text;        textButtons[7] = R.id.q8text;
        textButtons[8] = R.id.q9text;        textButtons[9] = R.id.q10text;
        textButtons[10] = R.id.q11text;        textButtons[11] = R.id.q12text;
        textButtons[12] = R.id.q13text;        textButtons[13] = R.id.q14text;
        textButtons[14] = R.id.q15text;        textButtons[15] = R.id.q16text;
        textButtons[16] = R.id.q17text;        textButtons[17] = R.id.q18text;
        textButtons[18] = R.id.q19text;        textButtons[19] = R.id.q20text;
        textButtons[20] = R.id.q21text;        textButtons[21] = R.id.q22text;
        textButtons[22] = R.id.q23text;        textButtons[23] = R.id.q24text;
        textButtons[24] = R.id.q25text;        textButtons[25] = R.id.q26text;
        textButtons[26] = R.id.q27text;        textButtons[27] = R.id.q28text;

        shuffleArray(positions);
    }

//*************************** Shuffling method for position array **********************************
    // Implementing Fisher–Yates shuffle
    //used to randomly allocate elements of the array
    static void shuffleArray(int[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

//*************************** Returns (background) ImageView at position ***************************
    public ImageView getButtonAtPosition(int position){
        ImageView imageView = (ImageView)findViewById(backButtons[position-1]);
        return imageView;
    }

//**************************** Returns (text) TextView at position *********************************
    public TextView getTextAtPosition(int position){
        TextView textView = (TextView)findViewById(textButtons[position-1]);
        return textView;
    }

//***************************** Returns position for ImageView *************************************
    public int getButtonNumber(ImageView imageView) {
        int imageNumber = -1;
        for (int i = 0; i < backButtons.length; i++) {
            if (imageView == (ImageView)findViewById(backButtons[i])) {
                //(i+1) - array starts with 0 while buttons count from 1
                imageNumber = i + 1;
            }
        }
        return imageNumber;
    }

//********************* Get previously selected element position ***********************************
    public int positionOfAlreadySelectedElement(boolean[] array){
        int toReturn = -1;
        int count = 0;
        for (int i=0; i<array.length; i++){
            if (array[i]==true){
                toReturn = i+1;
                count++;
            }
        }
        //if there was more than one number selected return -2
        if (count>1)
            toReturn=-2;
        return toReturn;
    }

//******************************** Equations generator *********************************************
    public void generateEquations(){

        for (int i=0; i < firstElements.length; i++){
            int randomNum1;
            int randomNum2;

            do {
                //Loop checks it the first number its within the range of tables
                //selected in settings
                do {
                    randomNum1 = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                }while (!Controller.getInstance().checkNumber(randomNum1));

                //number from 1 to 9 inclusive
                randomNum2 = ThreadLocalRandom.current().nextInt(1, 9 + 1);


            }while (checkNumbers(randomNum1, randomNum2, firstElements.length));

            firstElements[i] = randomNum1;
            secondElements[i] = randomNum2;
            result[i] = randomNum1 * randomNum2;
        }
    }

    //checks if pair of numbers already exists in the arrays
    //return true if not unique
    //       false if unique
    public boolean checkNumbers(int numberOne, int numberTwo, int range){
        boolean notUnique = false;
        for (int i=0; i<range; i++){

            if ((firstElements[i]==numberOne && secondElements[i]==numberTwo)||
                    (firstElements[i]==numberTwo && secondElements[i]==numberOne)){
                notUnique=true;
            }
        }
        return notUnique;
    }

//********************** Allocation Method for buttons content *************************************
    public void elementsAllocation(){
        generateEquations();

        //allocate all equations
        for (int i=0; i < 14; i++){
            TextView textView = (TextView)findViewById(textButtons[positions[i]]);
            textView.setText(firstElements[i]+" x "+secondElements[i]);
        }

        //allocate results
        for (int i=14; i < 28; i++){
            TextView textView = (TextView)findViewById(textButtons[positions[i]]);
            textView.setText(result[i-14]+"");
        }
    }

//********************** Returns value of equation or result ***************************************
    public int getValue(String content){
        int numberToReturn = -1;
        if (content.contains("x")){
            //position 0 and 4 correspond to first and last elements in
            //equation
            int firstNumber=Character.getNumericValue(content.charAt(0));
            int secondNumber=Character.getNumericValue(content.charAt(4));
            numberToReturn = firstNumber * secondNumber;
        }
        else{
            numberToReturn = Integer.parseInt(content);
        }
        return numberToReturn;
    }

//*************************** Deselects image animation ********************************************
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

//********************************* Hide/reveal value **********************************************
    public void hideReveal(ImageView iv, boolean hide){
        int position = getButtonNumber(iv);
        if (hide){
            getTextAtPosition(position).setAlpha(0.0f);
        }
        else{
            getTextAtPosition(position).setAlpha(1.0f);
        }
    }
//************************************* Flip All ***************************************************
    public void hideValues(){
        for(int i=0; i<selected.length; i++){
            getTextAtPosition(i+1).setAlpha(0.0f);
        }
    }

//************************************* Set up fonts ***********************************************
    public void setUpFonts(TextView textView){
        //Set up font for provided TextView
        Typeface berkshireSwash = Typeface.createFromAsset(getAssets(),
                "fonts/BerkshireSwash-Regular.ttf");
        Typeface pacifico = Typeface.createFromAsset(getAssets(),
                "fonts/Pacifico-Regular.ttf");

        textView.setTypeface(pacifico);
    }
}
