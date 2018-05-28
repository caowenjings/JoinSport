package com.example.jingjing.xin.Find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jingjing.xin.R;

import okhttp3.MediaType;

/**
 * Created by jingjing on 2018/5/28.
 */

public class PostNeed extends AppCompatActivity{

    private TextView tv_title;
    private ImageView iv_title;
    private RelativeLayout tv_back;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        initView();
        initDate();
    }
    private void initView(){
        tv_title=(TextView)findViewById(R.id.tv_title);
        iv_title=(ImageView)findViewById(R.id.iv_title);
        tv_back=(RelativeLayout)findViewById(R.id.tv_back);
        tv_title.setText("注 册");
    }
    private void initDate(){

    }
}
