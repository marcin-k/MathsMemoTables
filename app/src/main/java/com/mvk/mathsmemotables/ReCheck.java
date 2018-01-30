package com.mvk.mathsmemotables;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReCheck extends AppCompatActivity {

    @BindView(R.id.resultRecheck)
    EditText resultRecheck;

//*************************************** OnCreate *************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_check);

        ButterKnife.bind(this);
        resultRecheck.setText("");
        resultRecheck.requestFocus();
        resultRecheck.setInputType(InputType.TYPE_CLASS_NUMBER);

        //Shows keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        //Hiding keyboard
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(resultRecheck.getWindowToken(), 0);
    }
}
