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
    @BindView(R.id.twoBox) CheckBox twoBox;
    @BindView(R.id.threeBox) CheckBox threeBox;
    @BindView(R.id.fourBox) CheckBox fourBox;
    @BindView(R.id.fiveBox) CheckBox fiveBox;
    @BindView(R.id.sixBox) CheckBox sixBox;
    @BindView(R.id.sevenBox) CheckBox sevenBox;
    @BindView(R.id.eightBox) CheckBox eightBox;
    @BindView(R.id.nineBox) CheckBox nineBox;

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

    }

//*************************** Tables Selection OnClick *********************************************
    //TODO: If at least two values are selected
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

}
