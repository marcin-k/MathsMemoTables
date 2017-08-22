package com.mvk.mathsmemotables;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GamePlayLarge extends BaseOfGame {

//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        positions = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};

        backButtons[10] = R.id.q11back;        backButtons[11] = R.id.q12back;
        backButtons[12] = R.id.q13back;        backButtons[13] = R.id.q14back;
        backButtons[14] = R.id.q15back;        backButtons[15] = R.id.q16back;
        backButtons[16] = R.id.q17back;        backButtons[17] = R.id.q18back;

        backButtons[18] = R.id.q19back;        backButtons[19] = R.id.q20back;
        backButtons[20] = R.id.q21back;        backButtons[21] = R.id.q22back;
        backButtons[22] = R.id.q23back;        backButtons[23] = R.id.q24back;
        backButtons[24] = R.id.q25back;        backButtons[25] = R.id.q26back;
        backButtons[26] = R.id.q27back;        backButtons[27] = R.id.q28back;

        textButtons[10] = R.id.q11text;        textButtons[11] = R.id.q12text;
        textButtons[12] = R.id.q13text;        textButtons[13] = R.id.q14text;
        textButtons[14] = R.id.q15text;        textButtons[15] = R.id.q16text;
        textButtons[16] = R.id.q17text;        textButtons[17] = R.id.q18text;

        textButtons[18] = R.id.q19text;        textButtons[19] = R.id.q20text;
        textButtons[20] = R.id.q21text;        textButtons[21] = R.id.q22text;
        textButtons[22] = R.id.q23text;        textButtons[23] = R.id.q24text;
        textButtons[24] = R.id.q25text;        textButtons[25] = R.id.q26text;
        textButtons[26] = R.id.q27text;        textButtons[27] = R.id.q28text;

        setContentView(R.layout.activity_game_play_large);
        ButterKnife.bind(this);
        selected = new boolean[28];
        //sets all to false
        Arrays.fill(selected, false);

        shuffleArray(positions);
        elementsAllocation();
        hideValues();

        setUpFonts(result1);
        setUpFonts(result2);
        setUpFonts(result3);
        for (int i = 0; i < selected.length; i++) {
            setUpFonts(getTextAtPosition(i + 1));
        }

        ImageView img = (ImageView) findViewById(R.id.clappingMonkey);
        img.setImageResource(R.drawable.monkey);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();
        // Start the animation (looped playback by default).
//        frameAnimation.start();

    }



//************************************ Card OnClick ************************************************
    @OnClick({R.id.q1back, R.id.q2back, R.id.q3back, R.id.q4back, R.id.q5back, R.id.q6back, R.id.q7back,
            R.id.q8back, R.id.q9back, R.id.q10back, R.id.q11back, R.id.q12back, R.id.q13back, R.id.q14back,
            R.id.q15back, R.id.q16back, R.id.q17back, R.id.q18back, R.id.q19back, R.id.q20back, R.id.q21back,
            R.id.q22back, R.id.q23back, R.id.q24back, R.id.q25back, R.id.q26back, R.id.q27back, R.id.q28back,})
    public void cardClicked(ImageView button) {
        cardSelected(button);
    }
}
