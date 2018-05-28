package com.example.jingjing.xin.Stadium;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingjing.xin.Adapter.OrderInfromationAdapter;
import com.example.jingjing.xin.Bean.Book;
import com.example.jingjing.xin.Bean.Stadium;
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

import static com.example.jingjing.xin.constant.Conatant.URL_ORDERINFORMATION;

/**
 * Created by jingjing on 2018/5/24.
 */

public class StadiumOrderInformation extends AppCompatActivity {

    private TextView tv_title;
    private ImageView iv_title;
    private RelativeLayout tv_back;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView tv_nobooking;
    private List<Book> mData = null;
    Book book = new Book();
    private User user;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.stadium_orderinformation);

        initView();
        initDate();

    }

    private void initView() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        tv_back = (RelativeLayout) findViewById(R.id.tv_back);
        tv_title.setText("我的预约");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tv_nobooking = (TextView) findViewById(R.id.tv_nobooking);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);


    }

    private void initDate() {
        user = (User) getIntent().getSerializableExtra("user");
        stadiumOrderInformation(user);

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void stadiumOrderInformation(User user) {

        String loadingUrl = URL_ORDERINFORMATION;
        new OrderInformationAsyncTask().execute(loadingUrl, String.valueOf(user.getUserId()));

    }

    private class OrderInformationAsyncTask extends AsyncTask<String, Integer, String> {
        public OrderInformationAsyncTask() {

        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
                json.put("userId", params[1]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response = okHttpClient.newCall(request).execute();
                results = response.body().string();
                //判断请求是否成功
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
            List<Book> mDate = new ArrayList<>();
            if (!"null".equals(s)) {
                try {
                    JSONArray results = new JSONArray(s);
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject js = results.getJSONObject(i);
                        Book book = new Book();
                        book.setBookingId(js.getInt("bookingId"));
                        book.setStadiumname(js.getString("stadiumname"));
                        book.setPlaceName(js.getString("placename"));
                        book.setTime(js.getString("time"));
                        book.setTime_order(js.getString("time_order"));
                        mDate.add(book);
                    }
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(StadiumOrderInformation.this, DividerItemDecoration.VERTICAL));
                    OrderInfromationAdapter adapter = new OrderInfromationAdapter(StadiumOrderInformation.this, mDate);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("结果为空");

                List<Book> mData2 = new ArrayList<>();
                tv_nobooking.setText("没有预约的场地");
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new DividerItemDecoration(StadiumOrderInformation.this, DividerItemDecoration.VERTICAL));
                OrderInfromationAdapter adapter = new OrderInfromationAdapter(StadiumOrderInformation.this, mDate);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(adapter);//适配器

                Toast.makeText(StadiumOrderInformation.this, "您还没有预定", Toast.LENGTH_SHORT).show();

            }
        }
    }

}