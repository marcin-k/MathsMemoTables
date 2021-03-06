package com.mvk.mathsmemotables;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by marcin on 10/08/2017.
 */

class Controller {

    //Minimal number of tables that needs to be cardSelected
    private final int MIN_NUM_OF_TABLES = 2;

    //booleans to change tables
    private boolean one;
    private boolean two;
    private boolean three;
    private boolean four;
    private boolean five;
    private boolean six;
    private boolean seven;
    private boolean eight;
    private boolean nine;

    //sound on/off
    private boolean letsMusicPlay;

    /*
     * gameDifficulty is a deciding factor when loading the game activity,
     * (number of cards on the screen - 28-hard, 18-medium, 10-easy)
     *  0 - easy - loads GamePlaySmall
     *  1 - medium - loads GamePlayMid
     *  2 - hard - loads GamePlayLarge
     */
    private int gameDifficulty;

    //duration of the game in seconds
    private int gameDuration;

    //copies of the arrays storing the equations
    private int[] copyOfFirstElement;
    private int[] copyOfSecondElement;

    //music player
    protected MediaPlayer mediaPlayer;

    //tracks if music was suppose to play when app is in background
    protected boolean wasMusicPlaying = false;

    private static final Controller ourInstance = new Controller();
    static Controller getInstance() {
        return ourInstance;
    }

//*********************************** Constructor **************************************************
    private Controller() {
        //sets to default value of true
        one = two = three = four = five = six = seven = eight = nine = true;
        //sets default game difficulty to medium
        gameDifficulty=1;

        letsMusicPlay = true;

        gameDuration=0;
    }

//************************* Disables or enables cardSelected table *********************************
    //flips the value for boolean table
    public void enableDisableTable(String number){
        boolean toReturn;
        switch (number){
            case "one":
                one = !one;
                break;
            case "two":
                two = !two;
                break;
            case "three":
                three = !three;
                break;
            case "four":
                four = !four;
                break;
            case "five":
                five = !five;
                break;
            case "six":
                six = !six;
                break;
            case "seven":
                seven = !seven;
                break;
            case "eight":
                eight = !eight;
                break;
            case "nine":
                nine = !nine;
                break;
        }
    }

//***************************** Checks how many tables is selected *********************************
    public int howManyTablesSelected(){
        int counter = 0;
        if (one)
            counter++;
        if (two)
            counter++;
        if (three)
            counter++;
        if (four)
            counter++;
        if (five)
            counter++;
        if (six)
            counter++;
        if (seven)
            counter++;
        if (eight)
            counter++;
        if (nine)
            counter++;
        return counter;
    }

//******************** Checks if number passed in is enabled (cardSelected) ************************
    public boolean checkNumber(int number){
        Log.d("TAG", "numberChecking "+number);
        boolean toReturn = false;
        if (number == 1)
            if (one)
                toReturn = true;
        if (number == 2)
            if (two)
                toReturn = true;
        if (number == 3)
            if (three)
                toReturn = true;
        if (number == 4)
            if (four)
                toReturn = true;
        if (number == 5)
            if (five)
                toReturn = true;
        if (number == 6)
            if (six)
                toReturn = true;
        if (number == 7)
            if (seven)
                toReturn = true;
        if (number == 8)
            if (eight)
                toReturn = true;
        if (number == 9)
            if (nine)
                toReturn = true;

        return toReturn;
    }

    //TODO: JUNIT test on the method
//*********** Copies the equations values to repeat questions in different form ********************
    public void copyEquationsValues(int[] firstElement, int[] secondElement){
        copyOfFirstElement  = Arrays.copyOf(firstElement, firstElement.length);
        copyOfSecondElement = Arrays.copyOf(secondElement, secondElement.length);
    }


//*********************** Returns number of cards used in the game *********************************
    public int getNumberOfCards(){
        if (gameDifficulty==0)
            return 10;
        else if(gameDifficulty==1)
            return 18;
        else
            return 28;
    }


//******************* Returns intend based on the difficulty level *********************************
    public Class getGameplayClass(){
        if (gameDifficulty==0)
            return GamePlaySmall.class;
        else if(gameDifficulty==1)
            return GamePlayMid.class;
        else
            return GamePlayLarge.class;
    }

//******************************* Getter and Setters ***********************************************
    public int getGameDifficulty() {
        return gameDifficulty;
    }

    public boolean isLetsMusicPlay() {
        return letsMusicPlay;
    }

    public void setLetsMusicPlay(boolean letsMusicPlay, Context context) {
        this.letsMusicPlay = letsMusicPlay;
        if (letsMusicPlay){
            //play music
            mediaPlayer = MediaPlayer.create(context, R.raw.bgloop);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        else{
            //stop the music
            mediaPlayer.stop();
        }
    }

    public int getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    public void setWasMusicPlaying(boolean setting){
        this.wasMusicPlaying = setting;
    }

    public boolean getWasMusicPlaying(){
        return this.wasMusicPlaying;
    }

    //************************** Navigation Button pressed animations **********************************

    public void setGameDifficulty(int gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }


    //on touch down - shrink
    protected void animateButtonTouched(ImageView imageView){
        ObjectAnimator buttonXDown = ObjectAnimator.ofFloat(imageView, "scaleX", 1, 0.9f);
        ObjectAnimator buttonYDown = ObjectAnimator.ofFloat(imageView, "scaleY", 1, 0.9f);
        AnimatorSet scalePlayButtonDown = new AnimatorSet();
        scalePlayButtonDown.playTogether(buttonXDown, buttonYDown);
        scalePlayButtonDown.setDuration(100);
        scalePlayButtonDown.start();

    }

    //on release - resize to original size
    protected void animateButtonReleased(ImageView imageView){
        ObjectAnimator buttonXUp = ObjectAnimator.ofFloat(imageView, "scaleX", 0.9f, 1);
        ObjectAnimator buttonYUp = ObjectAnimator.ofFloat(imageView, "scaleY", 0.9f, 1);
        AnimatorSet scalePlayButtonUp = new AnimatorSet();
        scalePlayButtonUp.playTogether(buttonXUp, buttonYUp);
        scalePlayButtonUp.setDuration(100);
        scalePlayButtonUp.start();
    }

    //on release - resize to original size and changes the imageView to newImageView
    // used for sound/no sound button
    protected void animateButtonReleased(ImageView imageView, int newImageView){
        imageView.setImageResource(newImageView);

        ObjectAnimator buttonXUp = ObjectAnimator.ofFloat(imageView, "scaleX", 0.9f, 1);
        ObjectAnimator buttonYUp = ObjectAnimator.ofFloat(imageView, "scaleY", 0.9f, 1);
        AnimatorSet scalePlayButtonUp = new AnimatorSet();
        scalePlayButtonUp.playTogether(buttonXUp, buttonYUp);
        scalePlayButtonUp.setDuration(100);
        scalePlayButtonUp.start();
    }
}
