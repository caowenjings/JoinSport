package com.example.jingjing.xin.Stadium;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.jingjing.xin.Adapter.StadiumAdapter;
import com.example.jingjing.xin.Bean.Stadium;
import com.example.jingjing.xin.Bean.User;
import com.example.jingjing.xin.R;
import com.example.jingjing.xin.User.UserInformationActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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

import static com.example.jingjing.xin.constant.Conatant.URL_DELETECOLLECTION;
import static com.example.jingjing.xin.constant.Conatant.URL_INSERTCOLLECTION;
import static com.example.jingjing.xin.constant.Conatant.URL_PICTURE;

/**
 * Created by jingjing on 2018/5/21.
 */

public class StadiumActivity extends AppCompatActivity {

    private ImageView tv_back;
    private ImageView icon_stadium;
    private TextView  tv_stadiumname;
    private ToggleButton btn_collection;
    private ImageView btn_share;
    private TextView  tv_stadiumname1;
    private TextView  tv_stadiumtype;
    private RatingBar ratingBar;
    private TextView  tv_area;
    private TextView  tv_num;
    private TextView  tv_indoor;
    private TextView  tv_aircondition;
    private TextView  tv_adress;
    private TextView  tv_opentime;
    private Button  btn_order;
    private User user;
    private Stadium stadium;
    private RecyclerView recyclerView;



    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.stadium_information);

        initDate();
        initView();

    }
    private void initView(){

        tv_back = (ImageView)findViewById(R.id.tv_back);
        tv_stadiumname = (TextView) findViewById(R.id.tv_stadiumname);
        btn_collection = (ToggleButton) findViewById(R.id.btn_collection);
        btn_share= (ImageView)findViewById(R.id.btn_share);
        tv_stadiumname1= (TextView) findViewById(R.id.tv_stadiumname1);
        tv_stadiumtype= (TextView) findViewById(R.id.tv_stadiumtype);
        ratingBar =(RatingBar)findViewById(R.id.rb_ratbar);
        tv_adress = (TextView) findViewById(R.id.tv_stadiumaddress);
        tv_area = (TextView) findViewById(R.id.tv_area);
        tv_opentime = (TextView) findViewById(R.id.tv_opentime);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_indoor = (TextView) findViewById(R.id.tv_indoor);
        tv_aircondition= (TextView) findViewById(R.id.tv_aircondition);
        icon_stadium = (ImageView)findViewById(R.id.icon_stadium);
        btn_order = (Button)findViewById(R.id.btn_order);
    }

    private void initDate(){
        stadium = (Stadium) getIntent().getSerializableExtra("stadium");
        user = (User) getIntent().getSerializableExtra("user");

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.error) // 设置图片加载或解码过程中发生错误显示的图片
                .showImageOnLoading(R.drawable.loading)
                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
                .delayBeforeLoading(1000)  // 下载前的延迟时间
                .build();
        ImageLoader.getInstance().displayImage(stadium.getMainpicture(),icon_stadium,options);

    }

    private void searchstadium(int stadiunmId, int userId, boolean flag) {
        String SearchUrl = null;
        if (flag) {
            SearchUrl = URL_INSERTCOLLECTION;
        } else {
            SearchUrl = URL_DELETECOLLECTION;
        }
        new SearchstadiumAsyncTask().execute(SearchUrl, String.valueOf(stadiunmId), String.valueOf(userId));
    }

    private class SearchstadiumAsyncTask extends AsyncTask<String,Integer,String>{
        public SearchstadiumAsyncTask(){

        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            Response response = null;
            JSONObject json = new JSONObject();
            try{
                json.put("stadiumId",params[1]);
                json.put("userId",params[2]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response = okHttpClient.newCall(request).execute();
                result = response.body().string();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);
            List<Stadium> mDate = new ArrayList<Stadium>();
            Stadium stadium = new Stadium();
            if(s != null){
                try {
                    JSONArray results= new JSONArray(s);//先将对象转成json数组
                    for(int i=0;i<results.length();i++){
                        JSONObject js = results.getJSONObject(i);//解析为数组
                        stadium.setStadiumId(js.getInt("stadiumId"));
                        stadium.setStadiumname(js.getString("stadiumname"));
                        stadium.setStadiumtype(js.getString("stadiumtypeId"));
                        stadium.setArea(js.getString("area"));
                        stadium.setIndoor(js.getInt("indoor"));
                        stadium.setAircondition(js.getInt("aircondition"));
                        stadium.setCity(js.getString("city"));
                        stadium.setMainpicture(URL_PICTURE+js.getString("mainpicture"));
                        stadium.setAdress(js.getString("adress"));
                        stadium.setNum(js.getString("num"));
                        stadium.setOpentime(js.getString("opentime"));
                        mDate.add(stadium);

                        tv_stadiumname.setText(stadium.getStadiumname());
                        tv_stadiumname1.setText(stadium.getStadiumname());
                        tv_stadiumtype.setText(stadium.getStadiumtype());
                        tv_area .setText(stadium.getArea()+"平方米");
                        tv_num.setText(stadium.getNum()+"人");
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
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("结果为空");
                Toast.makeText(StadiumActivity.this, "网络未连接", Toast.LENGTH_LONG).show();

            }
        }
    }
}
