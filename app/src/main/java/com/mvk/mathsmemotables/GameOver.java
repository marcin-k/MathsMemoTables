package com.mvk.mathsmemotables;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class GameOver extends AppCompatActivity {

    @BindView(R.id.textView)TextView time;
    @BindView(R.id.homeButton) ImageView homeButton;
//    @BindView(R.id.checkYourselfButton) Button checkYourselfButton;


//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        ButterKnife.bind(this);

        //to be fixed
        time.setText("Congrats!\nIt took you only \n"+Controller.getInstance().getGameDuration()+" seconds");
//        time.setText("It took you: "+Controller.getInstance().getGameDuration()%60+" minutes and" +
//                " "+(Controller.getInstance().getGameDuration()-Controller.getInstance().getGameDuration()%60)+" seconds");
    }



//**************************** Navigation Buttons OnClick ******************************************
//    @OnTouch(R.id.checkYourselfButton)
//    public boolean checkYourself(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            //TODO: needs a custom button
//            //Controller.getInstance().animateButtonTouched(checkYourselfButton);
//        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            //TODO: needs a custom button
//            //Controller.getInstance().animateButtonReleased(checkYourselfButton);
//            Intent intent = new Intent(this, ReCheck.class);
//            startActivity(intent,
//                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//        }
//        return true;
//    }
    @OnTouch(R.id.homeButton)
    public boolean touchHome(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Controller.getInstance().animateButtonTouched(homeButton);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Controller.getInstance().animateButtonReleased(homeButton);
            Intent intent = new Intent(this, WelcomeScreen.class);
    //      startActivity(intent);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        return true;
    }


}
