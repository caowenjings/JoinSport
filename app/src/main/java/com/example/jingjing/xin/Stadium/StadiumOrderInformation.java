package com.example.jingjing.xin.Stadium;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.jingjing.xin.R;

/**
 * Created by jingjing on 2018/5/24.
 */

public class StadiumOrderInformation extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.stadium_orderinformation);

        initView();
        initDate();

    }
    private void initView(){

    }
    private void  initDate(){

    }
}
