package com.example.jingjing.xin.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.example.jingjing.xin.Activity.LoginActivity;
import com.example.jingjing.xin.Activity.MainActivity;
import com.example.jingjing.xin.Banner.MyLoader;
import com.example.jingjing.xin.Base.BaseFragment;
import com.example.jingjing.xin.Bean.Need;
import com.example.jingjing.xin.Bean.User;
import com.example.jingjing.xin.Find.PostNeed;
import com.example.jingjing.xin.Find.PostNeedFalot;
import com.example.jingjing.xin.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.jingjing.xin.constant.Conatant.URL_FINDINFORMATION;

/**
 * Created by jingjing on 2018/4/24.
 */

public class FindFragment extends BaseFragment  implements OnBannerListener{

    private Banner find_banner;
    private ArrayList findlists;
    private LinearLayout add_sport;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefresh;
    private TextView tv_nofind;
    private ScrollView sv_find;
    private User user;
    private String city;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected View initView() {

        View view = View.inflate(mContext, R.layout.findfragment, null);
        find_banner = (Banner) view.findViewById(R.id.baner_find);
        add_sport = (LinearLayout) view.findViewById(R.id.add_sport);
        return  view;
    }

    @Override
    protected void initData() {
        setfindBanner();//轮播图

        user = (User) getActivity().getIntent().getSerializableExtra("user");

        add_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), PostNeedFalot.class);
                Bundle mbundle = new Bundle();
                mbundle.putSerializable("user",user);
                intent.putExtras(mbundle);
                startActivity(intent);
            }
        });

    }

    //banner轮播图
    private void setfindBanner() {
        findlists = new ArrayList<>();
        findlists.add(R.drawable.find_one);
        findlists.add(R.drawable.find_two);
        findlists.add(R.drawable.find_three);
        find_banner.setDelayTime(3000);//图片间隔时间
        find_banner.setImages(findlists);//加载图片集合
        find_banner.setImageLoader(new MyLoader());
        find_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置格式
        find_banner.isAutoPlay(true);//自动加载
        find_banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);//设置指示器的位置，小点点，左中右
        find_banner.start(); //开始
        find_banner.setOnBannerListener(this);
    }
    @Override
    public void OnBannerClick(int position) {
    }


}
