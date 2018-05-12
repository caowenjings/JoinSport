package com.example.jingjing.xin.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingjing.xin.Banner.MyLoader;
import com.example.jingjing.xin.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jingjing on 2018/4/24.
 */

public class FindFragment extends Fragment implements OnBannerListener{
    private Banner find_banner;
    private ArrayList findlists;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.findfragment,container,false);
        find_banner=(Banner)view.findViewById(R.id.baner_find);
        setfindBanner();
        return view;
    }
    //banner轮播图
    private void setfindBanner(){
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

