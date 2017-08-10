package com.mvk.mathsmemotables;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Settings extends AppCompatActivity {

    //ui
    @BindView(R.id.oneBox) CheckBox oneBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);
        //oneBox.setChecked(true);
    }

    //TODO: fix it! doesnt change the value of enableDisable
    @OnClick({R.id.oneBox, R.id.twoBox, R.id.threeBox, R.id.fourBox, R.id.fiveBox,
            R.id.sixBox, R.id.sevenBox, R.id.eightBox, R.id.nineBox})
    public void changeOne(CheckBox checkBox){
        if (checkBox.isChecked()){
            checkBox.setChecked(true);
        }
        else{
            checkBox.setChecked(false);
        }
        boolean changed;
        Log.d("TAG", getStringFromCheckBox(checkBox)+"");
        changed = Controller.getInstance().enableDisableTable(getStringFromCheckBox(checkBox));
    }

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

}
