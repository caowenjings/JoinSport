package com.example.jingjing.xin.Find;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingjing.xin.Bean.Need;
import com.example.jingjing.xin.Bean.User;
import com.example.jingjing.xin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.jingjing.xin.constant.Conatant.URL_INSERTNEED;
import static com.example.jingjing.xin.constant.Conatant.URL_NEEDINFORMATION;

/**
 * Created by jingjing on 2018/5/28.
 */

public class PostNeed extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageView iv_title;
    private RelativeLayout tv_back;

    private FloatingActionButton fab_add_sport;
    private String city;
    private User user;
    private LinearLayoutManager layoutManager;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postneed);

        initView();
        initDate();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        tv_back = (RelativeLayout) findViewById(R.id.tv_back);
        tv_title.setText("约运动");

        fab_add_sport = (FloatingActionButton) findViewById(R.id.fab_add_sport);

    }

    private void initDate() {
        tv_back.setOnClickListener(this);
        fab_add_sport.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;

            case R.id.fab_add_sport:
                if ("".equals(city)) {
                    Toast.makeText(PostNeed.this, "未获取到当前城市", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(PostNeed.this, PostNeedFalot.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("user",user);
                     intent.putExtras(mBundle);
                    startActivity(intent);
                }
                 default:
                  break;
        }
    }

}

