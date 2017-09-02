package com.mvk.mathsmemotables;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

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


//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (checkBox == (CheckBox)findViewById(R.id.oneBox))
            return "one";
        else if (checkBox == (CheckBox)findViewById(R.id.twoBox))
            return "two";
        else if (checkBox == (CheckBox)findViewById(R.id.threeBox))
            return "three";
        else if (checkBox == (CheckBox)findViewById(R.id.fourBox))
            return "four";
        else if (checkBox == (CheckBox)findViewById(R.id.fiveBox))
            return "five";
        else if (checkBox == (CheckBox)findViewById(R.id.sixBox))
            return "six";
        else if (checkBox == (CheckBox)findViewById(R.id.sevenBox))
            return "seven";
        else if (checkBox == (CheckBox)findViewById(R.id.eightBox))
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
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Controller.getInstance().animateButtonTouched(playButton);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Controller.getInstance().animateButtonReleased(playButton);
            Intent intent = new Intent(this, Controller.getInstance().getGameplayClass());
            startActivity(intent);
        }
        return true;
    }

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


}
