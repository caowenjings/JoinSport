package com.example.jingjing.xin.Stadium;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.jingjing.xin.Fragment.BookingFragment;
import com.example.jingjing.xin.R;

/**
 * Created by jingjing on 2018/5/21.
 */

public class SearchStadium extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.stadium_search);

        initView();
        initDate();

    }
    private void initView(){

    }
    private void  initDate(){


    }
}
