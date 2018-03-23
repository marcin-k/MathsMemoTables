package com.mvk.mathsmemotables;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class WelcomeScreen extends Activity {

    //UI references
    @BindView(R.id.playButton) ImageView playButton;
    @BindView(R.id.settingsButton) ImageView settingButton;
    @BindView(R.id.soundButton) ImageView soundButton;

    Foreground.Listener myListener;
//    = new Foreground.Listener(){
//        public void onBecameForeground(){
//            // ... whatever you want to do
//            Log.d("TAG2", "Foreground");
//            Controller.getInstance().setLetsMusicPlay(true, getApplicationContext());
//        }
//        public void onBecameBackground(){
//            // ... whatever you want to do
//            Controller.getInstance().setLetsMusicPlay(false, getApplicationContext());
//            Log.d("TAG2", "Background");
//        }
//    };

//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ButterKnife.bind(this);
        //starts the music only the first time around
        if (Controller.getInstance().mediaPlayer==null) {
            Controller.getInstance().setLetsMusicPlay(true, getApplicationContext());
        }

//        //face animation
//        ImageView img = (ImageView)findViewById(R.id.welcome_monkey_head);
//        img.setImageResource(R.drawable.welcome_monkey);
//        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();
//        frameAnimation.start();
//        getApplication().registerActivityLifecycleCallbacks(new AppLifecycleTracker());

        Foreground.init(getApplication());
//        Foreground.get(getApplicationContext());

        Foreground.Listener myListener = new Foreground.Listener(){
            public void onBecameForeground(){
                // ... whatever you want to do
                if (Controller.getInstance().wasMusicPlaying){
                   Controller.getInstance().setLetsMusicPlay(true, getApplicationContext());
                }
                Log.d("TAG2", "Foreground");
            }
            public void onBecameBackground(){
                // ... whatever you want to do
                Controller.getInstance().setWasMusicPlaying(Controller.getInstance().isLetsMusicPlay());
                Controller.getInstance().setLetsMusicPlay(false, getApplicationContext());
                Log.d("TAG2", "Background");
            }
        };

        Foreground.get(this).addListener(myListener);

    }

//**************************** Navigation Buttons OnClick ******************************************
    @OnTouch(R.id.playButton)
    public boolean touchPlay(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Controller.getInstance().animateButtonTouched(playButton);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Controller.getInstance().animateButtonReleased(playButton);
            Intent intent = new Intent(this, Controller.getInstance().getGameplayClass());
//            startActivity(intent);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        return true;
    }

    @OnTouch(R.id.settingsButton)
    public boolean touchSettings(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Controller.getInstance().animateButtonTouched(settingButton);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Controller.getInstance().animateButtonReleased(settingButton);
            Intent intent = new Intent(this, Settings.class);
            //startActivity(intent);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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
    @Override
    public void onDestroy(){
       super.onDestroy();
       Foreground.get(this).removeListener(myListener);
    }
}
