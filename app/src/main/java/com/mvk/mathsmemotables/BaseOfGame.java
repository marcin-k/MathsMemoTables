package com.mvk.mathsmemotables;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * Created by marcin on 26/07/2017.
 */

public class BaseOfGame extends Activity {

    //number of pairs of cards to be flipped
    protected static int numberOfCardPairs;

    //Runnable references
    private MyRunnable mRunnable = new MyRunnable(this);
    protected TimeRunnable mTimeRunnable = new TimeRunnable(this);

    //references to cards and text to hide
    private static ArrayList<ImageView> cards;
    private static ArrayList<TextView> textOnCards;

    private static ImageView cardTwo;


    //clapping monkey animation reference
    static AnimationDrawable frameAnimation;

    //UI
    @BindView(R.id.result1) TextView result1;
    @BindView(R.id.result2) TextView result2;
    @BindView(R.id.result3) TextView result3;

    //cards
    protected int[] backButtons;
    protected int[] textButtons;

    //equations and results arrays
    private int [] firstElements;
    private int [] secondElements;
    private int [] result;

    //position of elements in array, used to randomly allocate elements
    protected int [] positions;

    //tracks flipped over cards
    protected boolean[] selected;

    //tracks cards already hidden - to prevent from flipping pairs back
    protected boolean[] alreadyCompleted;

    //game duration in seconds
    protected int gameDuration;

//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up array lists for cards to be hidden
        cards = new ArrayList<>();
        textOnCards = new ArrayList<>();

        //set up arrays based on difficulty level selected
        backButtons = new int[Controller.getInstance().getNumberOfCards()];
        textButtons = new int[Controller.getInstance().getNumberOfCards()];
        firstElements = new int[Controller.getInstance().getNumberOfCards()/2];
        secondElements = new int[Controller.getInstance().getNumberOfCards()/2];
        result = new int[Controller.getInstance().getNumberOfCards()/2];
        positions = new int[Controller.getInstance().getNumberOfCards()];

        backButtons[0] = R.id.q1back;        backButtons[1] = R.id.q2back;
        backButtons[2] = R.id.q3back;        backButtons[3] = R.id.q4back;
        backButtons[4] = R.id.q5back;        backButtons[5] = R.id.q6back;
        backButtons[6] = R.id.q7back;        backButtons[7] = R.id.q8back;
        backButtons[8] = R.id.q9back;        backButtons[9] = R.id.q10back;

        textButtons[0] = R.id.q1text;        textButtons[1] = R.id.q2text;
        textButtons[2] = R.id.q3text;        textButtons[3] = R.id.q4text;
        textButtons[4] = R.id.q5text;        textButtons[5] = R.id.q6text;
        textButtons[6] = R.id.q7text;        textButtons[7] = R.id.q8text;
        textButtons[8] = R.id.q9text;        textButtons[9] = R.id.q10text;

        //resets the duration counter
        Controller.getInstance().setGameDuration(0);

        //handler to measure the duration of the game
        mHandler.postDelayed(mTimeRunnable, 1000);


    }

//*************************** Shuffling method for position array **********************************
    // Implementing Fisher–Yates shuffle
    //used to randomly allocate elements of the array
    static void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = Controller.getInstance().getNumberOfCards() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

//********************************** Card flipping logic *******************************************
    public void cardSelected(ImageView button) {
        //---------------------------------- if element is hidden ----------------------------------
        if (button.getAlpha() == 0.0f) {
            //
        }
        //---------------------------- if two element is already selected --------------------------
        else if (positionOfAlreadySelectedElement(selected) == -2) {
            Log.d("WhereAreWe", "1");
         //checks if user doesn't clicks on cards with correct answer which still display on screen
            if (!alreadyCompleted[getButtonNumber(button)-1]){
                //deselect all (and update tracking array (cardSelected))
                deselectAll();
                //select new element
                Log.d("WhereAreWe", "1.5");
                flipImage(button, getButtonNumber(button) - 1, false);
            }
        }
        //---------------------------- if one element is already selected --------------------------
        else if (positionOfAlreadySelectedElement(selected) != -1) {
            Log.d("WhereAreWe", "2");
            //if user press again on the same button - deselect it
            if (positionOfAlreadySelectedElement(selected) == getButtonNumber(button)) {
                Log.d("WhereAreWe", "2.5");
                flipImage(button, getButtonNumber(button) - 1, false);
            }

            //compare two values
            // positionOfAlreadySelectedElement(cardSelected) - position of item 1
            // getButtonNumber(button)                        - position of item 2
            else {

                //if  two cars are equal
                if (getValue(getTextAtPosition(getButtonNumber(button)).getText().toString()) ==
                        getValue(getTextAtPosition(positionOfAlreadySelectedElement(selected)).getText().toString())) {

                    //decrease number of pairs to track when to end game
                    numberOfCardPairs--;

                    Log.d("WhereAreWe", "3");
                    flipImage(button, getButtonNumber(button) - 1, true);

                    //references for image views and text view on the cards selected
                    //so they can be hidden inside the runnable
                    cards.add(button);
                    cards.add(getButtonAtPosition(positionOfAlreadySelectedElement(selected)));
                    textOnCards.add(getTextAtPosition(getButtonNumber(button)));
                    textOnCards.add(getTextAtPosition(positionOfAlreadySelectedElement(selected)));
                    cardTwo = getButtonAtPosition(positionOfAlreadySelectedElement(selected));
//                    textOnCardOne = getTextAtPosition(getButtonNumber(button));
//                    textOnCardTwo = getTextAtPosition(positionOfAlreadySelectedElement(selected));

                    //keep reference of position of correct answers
                    alreadyCompleted[getButtonNumber(button)-1]=true;
                    alreadyCompleted[positionOfAlreadySelectedElement(selected)-1]=true;
//                    Log.d("Arrays", getSelectedArray()+getAlreadySelectedArray());
//                    Log.d("Arrays", getSelectedArray()+getAlreadySelectedArray());

                    //start clapping animation
                    frameAnimation.start();
                    //calls the handler in 2 seconds
                    mHandler.postDelayed(mRunnable, 2000);

                    //display the equation on the blackboard
                    result1.setText(getTextAtPosition(getButtonNumber(button)).getText());
                    result2.setText("=");
                    result3.setText(getTextAtPosition(positionOfAlreadySelectedElement(selected)).getText());

                }
                else{
                    //select second one
                    Log.d("WhereAreWe", "4");
                    flipImage(button, getButtonNumber(button) - 1, false);
                }

            }
        }
        //------------------------------ if no elements have been cardSelected ---------------------
        else {
            Log.d("WhereAreWe", "5");
            flipImage(button, getButtonNumber(button) - 1, false);
        }
    }
//*************************** Returns (background) ImageView at position ***************************
    public ImageView getButtonAtPosition(int position){
        ImageView imageView = findViewById(backButtons[position-1]);
        return imageView;
    }

//**************************** Returns (text) TextView at position *********************************
    public TextView getTextAtPosition(int position){
        TextView textView = findViewById(textButtons[position-1]);
        return textView;
    }

//***************************** Returns position for ImageView *************************************
    public int getButtonNumber(ImageView imageView) {
        int imageNumber = -1;
        for (int i = 0; i < backButtons.length; i++) {
            if (imageView == findViewById(backButtons[i])) {
                //(i+1) - array starts with 0 while buttons count from 1
                imageNumber = i + 1;
            }
        }
        return imageNumber;
    }

//********************* Get previously cardSelected element position *******************************
    public int positionOfAlreadySelectedElement(boolean[] array){
        int toReturn = -1;
        int count = 0;
        for (int i=0; i<array.length; i++){
            if (array[i]==true){
                toReturn = i+1;
                count++;
            }
        }
        //if there was more than one number cardSelected return -2
        if (count>1)
            toReturn=-2;
        Log.d("AlreadySelectedPos", toReturn+" ");
        return toReturn;
    }

//******************************** Equations generator *********************************************
    public void generateEquations(){

        for (int i=0; i < firstElements.length; i++){
            int randomNum1;
            int randomNum2;

            do {
                //Loop checks it the first number its within the range of tables
                //cardSelected in settings
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
        for (int i=0; i < Controller.getInstance().getNumberOfCards()/2; i++){
            TextView textView = findViewById(textButtons[positions[i]]);
            textView.setText(firstElements[i]+" x "+secondElements[i]);
        }

        //allocate results
        for (int i=Controller.getInstance().getNumberOfCards()/2; i < Controller.getInstance().getNumberOfCards(); i++){
            TextView textView = findViewById(textButtons[positions[i]]);
            textView.setText(result[i-Controller.getInstance().getNumberOfCards()/2]+"");
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
                //don't flip cards with correct answer until runnable
                if (alreadyCompleted[i]==true){
                    selected[i]=false;
                }
                else {
                    ImageView imageView = findViewById(backButtons[i]);
                    Log.d("WhereAreWe", "6");
                    flipImage(imageView, i, false);
                }
            }
        }
    }

//*************************** Flipping image animation *********************************************
    public void flipImage(final ImageView iv, final int position, final boolean correctAnswer) {

        final Drawable notSelectedDrawable = getDrawable(R.drawable.button);
        final Drawable selectedDrawable = getDrawable(R.drawable.button_selected);
        final Drawable correctAnswerDrawable = getDrawable(R.drawable.button_correct);

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

                    iv.setImageDrawable(notSelectedDrawable);
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
                    if(correctAnswer){
                        iv.setImageDrawable(correctAnswerDrawable);
                        BaseOfGame.cardTwo.setImageDrawable(correctAnswerDrawable);
                    }
                    else{
                        iv.setImageDrawable(selectedDrawable);
                    }
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
//******************************* Hides values on all cards ****************************************
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

//***************************** Runnable to hide the cards *****************************************

    private static class MyHandler extends Handler {}
    private final MyHandler mHandler = new MyHandler();

    public class MyRunnable implements Runnable {
        private final WeakReference<Activity> mActivity;

        public MyRunnable(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        //hides the cards 2 seconds after they have been exposed
        @Override
        public void run() {
            Activity activity = mActivity.get();
            if (activity != null) {
                //hides cards
                for (ImageView imageView: BaseOfGame.getCards()){
                    imageView.setAlpha(0.0f);
                }
                //hides text on cards
                for (TextView textView: BaseOfGame.getTextOnCards()){
                    textView.setAlpha(0.0f);
                }

                BaseOfGame.getFrameAnimation().stop();

                //go to game over screen if no more cards left to be flipped
                if (getNumberOfPairsLeft()==0){


                    //passes the game duration to the controller
                    Controller.getInstance().setGameDuration(gameDuration);

                    //passes the equations elements to controller
                    Controller.getInstance().copyEquationsValues(firstElements, secondElements);

                    //go to game over screen
                    Intent intent = new Intent(activity, GameOver.class);
                    activity.startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
                }
            }
        }
    }

//*************************** Runnable to calculate game time **************************************

        public class TimeRunnable implements Runnable {
            private final WeakReference<Activity> mActivity;

            public TimeRunnable(Activity activity) {
                mActivity = new WeakReference<>(activity);
            }

            @Override
            public void run() {
                Activity activity = mActivity.get();
                if (activity != null) {
                    gameDuration++;
                    mHandler.postDelayed(mTimeRunnable, 1000);
                }
            }
        }

//********************************* Getters and Setters ********************************************

    public static ArrayList<ImageView> getCards() {
        return cards;
    }
    public static ArrayList<TextView> getTextOnCards() {
        return textOnCards;
    }
    public static int getNumberOfPairsLeft(){
        return numberOfCardPairs;
    }

    public static AnimationDrawable getFrameAnimation() {
        return frameAnimation;
    }


}

