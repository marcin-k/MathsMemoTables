package com.mvk.mathsmemotables;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeScreen extends Activity {

    @BindView(R.id.playButton) Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ButterKnife.bind(this);

        playButton.setText("Play Now");
    }

    @OnClick(R.id.playButton)
    public void startDefaultGame(){
        Intent intent = new Intent(this, GamePlay0.class);
        startActivity(intent);
    }
}
