package com.mvk.mathsmemotables;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeScreen extends Activity {

    @BindView(R.id.playButton) ImageView playButton;
    @BindView(R.id.select) ImageView select;
    @BindView(R.id.tables) ImageView tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.playButton)
    public void startDefaultGame(){
        animateButtonClick(playButton);
        Intent intent = new Intent(this, GamePlay0.class);
        startActivity(intent);
    }

    @OnClick({R.id.select, R.id.tables})
    public void pickTables(){
        animateButtonClick(select);
        animateButtonClick(tables);

    }

    private void animateButtonClick(ImageView imageView){
        ObjectAnimator buttonXDown = ObjectAnimator.ofFloat(imageView, "scaleX", 1, 0.9f);
        ObjectAnimator buttonYDown = ObjectAnimator.ofFloat(imageView, "scaleY", 1, 0.9f);
        AnimatorSet scalePlayButtonDown = new AnimatorSet();
        scalePlayButtonDown.playTogether(buttonXDown, buttonYDown);
        scalePlayButtonDown.setDuration(100);

        ObjectAnimator buttonXUp = ObjectAnimator.ofFloat(imageView, "scaleX", 0.9f, 1);
        ObjectAnimator buttonYUp = ObjectAnimator.ofFloat(imageView, "scaleY", 0.9f, 1);
        AnimatorSet scalePlayButtonUp = new AnimatorSet();
        scalePlayButtonDown.playTogether(buttonXUp, buttonYUp);
        scalePlayButtonDown.setDuration(100);

        AnimatorSet buttonClick = new AnimatorSet();
        buttonClick.playSequentially(scalePlayButtonDown, scalePlayButtonUp);
        buttonClick.start();

    }
}
