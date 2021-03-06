package com.mvk.mathsmemotables;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static android.widget.Toast.LENGTH_SHORT;
import static com.mvk.mathsmemotables.R.id.playButton;


public class Settings extends Activity {

    //ui
    @BindView(R.id.oneBox) CheckBox oneBox;
    @BindView(R.id.twoBox) CheckBox twoBox;
    @BindView(R.id.threeBox) CheckBox threeBox;
    @BindView(R.id.fourBox) CheckBox fourBox;
    @BindView(R.id.fiveBox) CheckBox fiveBox;
    @BindView(R.id.sixBox) CheckBox sixBox;
    @BindView(R.id.sevenBox) CheckBox sevenBox;
    @BindView(R.id.eightBox) CheckBox eightBox;
    @BindView(R.id.nineBox) CheckBox nineBox;
    @BindView(R.id.difficulty) SeekBar difficulty;
    @BindView(R.id.difficultyIndicator) TextView difficultyIndicator;
    @BindView(R.id.playButton) ImageView playButton;
    @BindView(R.id.homeButton) ImageView homeButton;
    @BindView(R.id.soundButton) ImageView soundButton;


//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Transitions
        setupTransitions();

        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);
        if(Controller.getInstance().checkNumber(1))
            oneBox.setChecked(true);
        if(Controller.getInstance().checkNumber(2))
            twoBox.setChecked(true);
        if(Controller.getInstance().checkNumber(3))
            threeBox.setChecked(true);
        if(Controller.getInstance().checkNumber(4))
            fourBox.setChecked(true);
        if(Controller.getInstance().checkNumber(5))
            fiveBox.setChecked(true);
        if(Controller.getInstance().checkNumber(6))
            sixBox.setChecked(true);
        if(Controller.getInstance().checkNumber(7))
            sevenBox.setChecked(true);
        if(Controller.getInstance().checkNumber(8))
            eightBox.setChecked(true);
        if(Controller.getInstance().checkNumber(9))
            nineBox.setChecked(true);

        //sets the difficulty level
        loadDiffuclty();

    //-------------------------- Difficulty Update -------------------------------------------------
        difficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //updates label
                updateDifficultyLabel(i);
                //updates value stored in controller
                Controller.getInstance().setGameDifficulty(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

//*************************** Tables Selection OnClick *********************************************
    //TODO: If at least two values are cardSelected
    @OnClick({R.id.oneBox, R.id.twoBox, R.id.threeBox, R.id.fourBox, R.id.fiveBox,
            R.id.sixBox, R.id.sevenBox, R.id.eightBox, R.id.nineBox})
    public void changeOne(CheckBox checkBox){
        if (checkBox.isChecked()){
            checkBox.setChecked(true);
        }
        //TODO: validation tobe added here
        else{
            checkBox.setChecked(false);
        }
        boolean changed;
        Log.d("TAG", getStringFromCheckBox(checkBox)+"");
        Controller.getInstance().enableDisableTable(getStringFromCheckBox(checkBox));

    }

//********************* Returns String format number from checkbox *********************************
    private String getStringFromCheckBox(CheckBox checkBox){
        if (checkBox == findViewById(R.id.oneBox))
            return "one";
        else if (checkBox == findViewById(R.id.twoBox))
            return "two";
        else if (checkBox == findViewById(R.id.threeBox))
            return "three";
        else if (checkBox == findViewById(R.id.fourBox))
            return "four";
        else if (checkBox == findViewById(R.id.fiveBox))
            return "five";
        else if (checkBox == findViewById(R.id.sixBox))
            return "six";
        else if (checkBox == findViewById(R.id.sevenBox))
            return "seven";
        else if (checkBox == findViewById(R.id.eightBox))
            return "eight";
        else
            return "nine";
    }
//******************* Updates the difficulty seekbar and textview **********************************
    private void loadDiffuclty(){
        int difficulty = Controller.getInstance().getGameDifficulty();
        this.difficulty.setProgress(difficulty);
        updateDifficultyLabel(difficulty);
    }

    //updates the difficulty TextView
    private void updateDifficultyLabel(int difficulty){
        if (difficulty==0)
            difficultyIndicator.setText("Easy");
        else if (difficulty==1)
            difficultyIndicator.setText("Medium");
        else difficultyIndicator.setText("Hard");
    }

//**************************** Navigation Buttons OnClick ******************************************
    @OnTouch(R.id.playButton)
    public boolean touchPlay(MotionEvent event) {
        if (checkSettins()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Controller.getInstance().animateButtonTouched(playButton);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                Controller.getInstance().animateButtonReleased(playButton);
                Intent intent = new Intent(this, Controller.getInstance().getGameplayClass());
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            }
        }
        return true;
    }

    @OnTouch(R.id.homeButton)
    public boolean touchHome(MotionEvent event) {
        if (checkSettins()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Controller.getInstance().animateButtonTouched(homeButton);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                Controller.getInstance().animateButtonReleased(homeButton);
                Intent intent = new Intent(this, WelcomeScreen.class);
//            startActivity(intent);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            }
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

//********************** Checks if sufficient number of tables has been selected *******************
    //returns true if everything is fine
    public boolean checkSettins(){
        //for easy and medium checks if at least one value is selected
        if (difficultyIndicator.getText().equals("Easy")||
                difficultyIndicator.getText().equals("Medium")){
            if (Controller.getInstance().howManyTablesSelected() >0){
                return true;
            }
            else{
                Toast.makeText(getBaseContext(),"Please make sure to select at least one" +
                                " table",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        //for difficult checks if at least 2 tables have been selected
        else{
            if (Controller.getInstance().howManyTablesSelected() >1){
                return true;
            }
            else{
                Toast.makeText(getBaseContext(),"Please make sure to select at least two" +
                                " tables, or change difficulty level",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

//****************************** Prevents back button messing up settings **************************
    @Override
    public void onBackPressed() {
        if (checkSettins()){
            super.onBackPressed();
        }
    }

//*************************************** OnResume *************************************************
    @Override
    protected void onResume() {
        super.onResume();


//        if (Controller.getInstance().wasMusicPlaying){
//            Controller.getInstance().setLetsMusicPlay(true, getApplicationContext());
//        }
        //changes the music button if music is not playing
        if (!Controller.getInstance().isLetsMusicPlay()){
            Controller.getInstance().animateButtonReleased(soundButton, R.drawable.no_sound);
        }
        else{
            Controller.getInstance().animateButtonReleased(soundButton, R.drawable.sound);
        }
    }


//**************************************** OnStop **************************************************
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Controller.getInstance().setWasMusicPlaying(Controller.getInstance().isLetsMusicPlay());
//        Controller.getInstance().setLetsMusicPlay(false, getApplicationContext());
//    }
}
