package com.example.jingjing.xin.Stadium;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.jingjing.xin.Bean.App;
import com.example.jingjing.xin.Fragment.BookingFragment;
import com.example.jingjing.xin.R;

/**
 * Created by jingjing on 2018/5/21.
 */

public class SearchStadium extends AppCompatActivity {

    private TextView btn_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.searchstadium);

        initView();
        initDate();

    }
    private void initView(){
        btn_back = (TextView)findViewById(R.id.btn_back);

    }
    private void  initDate(){

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchStadium.this, BookingFragment.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
