package com.example.jingjing.xin.Stadium;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.jingjing.xin.Bean.Stadium;
import com.example.jingjing.xin.Bean.User;
import com.example.jingjing.xin.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import static com.example.jingjing.xin.constant.Conatant.URL_DELETECOLLECTION;
import static com.example.jingjing.xin.constant.Conatant.URL_INSERTCOLLECTION;
import static com.example.jingjing.xin.constant.Conatant.URL_ISCOLLECTED;
import static com.example.jingjing.xin.constant.Conatant.URL_LOADINGORDER;

/**
 * Created by jingjing on 2018/5/21.
 */

public class StadiumActivity extends AppCompatActivity {

    private TextView tv_stadiumname;
    private TextView tv_stadiumname1;
    private TextView tv_area;
    private TextView tv_stadiumtype;
    private TextView tv_num;
    private TextView tv_indoor;
    private TextView tv_aircondition;
    private TextView tv_adress;
    private TextView tv_opentime;
    private Stadium stadium;
    private ImageView tv_back;
    private ImageView btn_share;
    private ImageView icon_stadium;
    private RatingBar ratingBar;
    private Button btn_order;
    private User user;
    private boolean firstcollect = true;
    private boolean deletecollect = true;
    private ToggleButton btn_collection;
    private String userId, stadiumId;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stadium_information);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initview();
        initdata();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initview() {
        tv_stadiumname = (TextView) findViewById(R.id.tv_stadiumname);
        tv_back = (ImageView) findViewById(R.id.iv_back);
        btn_share = (ImageView) findViewById(R.id.btn_share);
        icon_stadium = (ImageView) findViewById(R.id.icon_stadium);
        tv_stadiumname1 = (TextView) findViewById(R.id.tv_stadiumname1);
        tv_stadiumtype = (TextView) findViewById(R.id.tv_stadiumtype);
        tv_area = (TextView) findViewById(R.id.tv_area);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_indoor = (TextView) findViewById(R.id.tv_indoor);
        tv_aircondition = (TextView) findViewById(R.id.tv_aircondition);
        tv_opentime = (TextView) findViewById(R.id.tv_opentime);
        tv_adress = (TextView) findViewById(R.id.tv_stadiumaddress);
        ratingBar = (RatingBar) findViewById(R.id.rb_ratbar);
        btn_order = (Button) findViewById(R.id.btn_order);
        btn_collection = (ToggleButton) findViewById(R.id.btn_collection);
        getWindow().setStatusBarColor(Color.parseColor("#FF029ACC"));

    }


    private void initdata() {
        stadium = (Stadium) getIntent().getSerializableExtra("stadium");
        user = (User) getIntent().getSerializableExtra("user");
        stadiumId = String.valueOf(stadium.getStadiumId());
        userId = String.valueOf(user.getUserId());
        iscollection(stadium.getStadiumId(),user.getUserId());//调用方法

        System.out.println("userId:" + user.getUserId());

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_stadiumname.setText(stadium.getStadiumname());
        tv_stadiumname1.setText(stadium.getStadiumname());
        tv_stadiumtype.setText(stadium.getStadiumtype());
        tv_area.setText(stadium.getArea() + "平方米");
        tv_num.setText(stadium.getNum() + "人");
        tv_opentime.setText(stadium.getOpentime());
        if (stadium.getIndoor() == 1) {
            tv_indoor.setText(" 是");
        } else {
            tv_indoor.setText(" 否");
        }
        if (stadium.getAircondition() == 1) {
            tv_aircondition.setText(" 是");
        } else {
            tv_aircondition.setText(" 否");
        }
        tv_adress.setText(stadium.getCity() + stadium.getAdress());
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.error) // 设置图片加载或解码过程中发生错误显示的图片
                .showImageOnLoading(R.drawable.load)
                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
                .delayBeforeLoading(1000)  // 下载前的延迟时间
                .build();
        ImageLoader.getInstance().displayImage(stadium.getMainpicture(), icon_stadium, options);

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StadiumActivity.this, StadiumOrder.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("user", user);
                mBundle.putSerializable("stadium", stadium);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        btn_collection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btn_collection.setBackgroundResource(isChecked ? R.drawable.stadium_collection1 :R.drawable.stadium_collection);
                if (buttonView.isChecked()) {
                    if (firstcollect) {
                        collection(stadium.getStadiumId(), user.getUserId(), true);
                     //   Toast.makeText(StadiumActivity.this, "收藏成功", Toast.LENGTH_LONG).show();
                    } else {
                    }
                } else {
                    if (deletecollect) {
                        collection(stadium.getStadiumId(), user.getUserId(), false);
                     //   Toast.makeText(StadiumActivity.this, "取消收藏", Toast.LENGTH_LONG).show();
                    } else {}
                }
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // shareText("分享场馆","热点",stadium.getStadiumname());
                shareImg("分享场馆","热点",stadium.getStadiumname(),stadium.getMainpicture());
            }
        });
    }


    private void stadiuminformation(String stadiunmId, boolean flag) {//显示场馆的信息
        String loadingUrl = URL_LOADINGORDER;
        new StadiuminformationAsyncTask().execute(loadingUrl, stadiunmId);
    }

    private class StadiuminformationAsyncTask extends AsyncTask<String, Integer, String> {
        public StadiuminformationAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
                json.put("stadiumId", params[1]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response = okHttpClient.newCall(request).execute();
                results = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("返回的数据：" + s);
            stadium = new Stadium();
            if (s != null) {
                try {
                    JSONObject json = new JSONObject(s);
                    String js = json.getString("results");
                    if (!"0".equals(js)) {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("结果为空");
            }
        }
    }


    private void  collection(int stadiunmId, int userId, boolean flag) {//收藏
        String SearchUrl = null;
        if (flag) {
            SearchUrl = URL_INSERTCOLLECTION;//添加收藏
        } else {
            SearchUrl = URL_DELETECOLLECTION;//取消收藏
        }
        new StadiumCollectionAsyncTask().execute(SearchUrl, String.valueOf(stadiunmId), String.valueOf(userId));
    }

    private class StadiumCollectionAsyncTask extends AsyncTask<String, Integer, String> {
        public StadiumCollectionAsyncTask(){
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
                json.put("stadiumId", params[1]);
                json.put("userId", params[2]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response = okHttpClient.newCall(request).execute();
                results = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("返回的数据：" + s);
            if (!"".equals(s)) {
                try {
                    JSONObject results = new JSONObject(s);
                    String loginresult = results.getString("result");
                    if (loginresult.equals("1")) {
                        Toast.makeText(StadiumActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    } else if (loginresult.equals("2")) {
                        Toast.makeText(StadiumActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                    } else if (loginresult.equals("3")) {

                    } else {
                        Toast.makeText(StadiumActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("结果为空");
            }
        }
    }

    private  void iscollection(int stadiumId, int userId){//记住收藏的状态
        String SearchUrl = URL_ISCOLLECTED;
        new IscollectionAsyncTask().execute(SearchUrl,String.valueOf(stadiumId),String.valueOf(userId));

    }
    private class IscollectionAsyncTask extends AsyncTask<String,Integer,String>{
        public IscollectionAsyncTask(){

        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
                json.put("stadiumId", params[1]);
                json.put("userId", params[2]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response = okHttpClient.newCall(request).execute();
                results = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);
           if(s != null){
               try {
                   JSONObject result = new JSONObject(s);
                   String js = result.getString("result");
                   if(js.equals("1")){
                       btn_collection.setChecked(false);
                   }else {
                       btn_collection.setChecked(true);
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }else {
               System.out.println("结果为空");
           }
        }
    }
/*
    private void shareText(String dlgTitle, String subject, String content){
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
        }
    }
    */
    private void shareImg(String dlgTitle, String subject, String content, String uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (content != null && !"".equals(content)) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
        }

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
        }
    }

}