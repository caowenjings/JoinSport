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
 * Created by jingjing on 2018/6/1.
 */

public class FindmeActivity extends AppCompatActivity {

    private TextView tv_title;
    private ImageView iv_title;
    private RelativeLayout tv_back;

    private TextView tv_stadiumname;
    private TextView tv_username;
    private TextView tv_time;
    private TextView tv_num;
    private TextView tv_time_join;
    private TextView tv_remark;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.find_me);

        initView();
        initData();
    }
    private void initView(){

        tv_title=(TextView)findViewById(R.id.tv_title);
        iv_title=(ImageView)findViewById(R.id.iv_title);
        tv_back=(RelativeLayout)findViewById(R.id.tv_back);
        tv_title.setText("发现详情");


    }
    private void initData(){

    }
}
