package com.example.jingjing.xin.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alex.widget.banner.tips.TipsBanner;
import com.alex.widget.banner.tips.listener.OnBottomTipsClickListener;
import com.alex.widget.banner.tips.listener.OnTopTipsClickListener;
import com.example.jingjing.xin.Adapter.GridViewAdapter;
import com.example.jingjing.xin.Adapter.MyPagerAdapter;
import com.example.jingjing.xin.Banner.MyLoader;
import com.example.jingjing.xin.Base.BaseFragment;
import com.example.jingjing.xin.Bean.AppGrid;
import com.example.jingjing.xin.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static java.security.AccessController.getContext;

/**
 * Created by jingjing on 2018/4/24.
 */
public class BookingFragment extends BaseFragment implements OnBannerListener {

    private Banner banner;
    private ArrayList bannerLists;

    private ViewPager viewPager;
    private List<View> mViewList;
    private List<AppGrid> mDatas;//数据源
    private LinearLayout mDots;//装小圆点的
    private LayoutInflater inflater;//布局服务器：用来找Layout布局的，并且实例化
    private int pageCount;//总的页数
    private int pageSize = 8;//每一页显示的个数
    private int curIndex = 0;//当前显示的是第几页
    private  String[] iconname = {"篮球","网球","游泳","击剑","足球","健身","高尔夫球","羽毛球","羽毛球"};

    private  int[] icon = {R.drawable.grid_basketball,R.drawable.grid_fencing, R.drawable.grid_swim,
            R.drawable.grid_fencing,R.drawable.grid_soccer, R.drawable.grid_bodybuilding,
            R.drawable.grid_golf,R.drawable.grid_tennis,R.drawable.grid_tennis};

    private TipsBanner tipsBanner;
    private List<String> tipsList;
    private  String[] news = {"最新篮球比赛6月开始！","今天停水泳泳馆不开门","系统更新请等待","詹姆斯今天绝杀！","蓝区健身馆今天开张半价！"};
    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.bookingfrgment, null);
        banner = (Banner) view.findViewById(R.id.banner);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mDots = (LinearLayout) view.findViewById(R.id.dots);
        tipsBanner= (TipsBanner)view.findViewById(R.id.tipsbanner);
        return view;
    }

    @Override
    protected void initData() {
        setBanner();//轮播图
        setGridview();//添加GridView
        setgonggao();//公告
    }

    //banner设置轮播图
    private void setBanner() {
        bannerLists = new ArrayList<>();
        bannerLists.add(R.drawable.tu_one);
        bannerLists.add(R.drawable.tu_two);
        bannerLists.add(R.drawable.tu_three);

        banner.setDelayTime(3000);//图片间隔时间
        banner.setImages(bannerLists);//加载图片集合
        banner.setImageLoader(new MyLoader());
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置格式
        banner.isAutoPlay(true);//自动加载
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置，小点点，左中右
        banner.start(); //开始
        banner.setOnBannerListener(this);
    }
    @Override
    public void OnBannerClick(int position) {
    }


    public void initDatas() {
        mDatas = new ArrayList<AppGrid>();
        for (int i = 0; i < icon.length; i++) {
            mDatas.add(new AppGrid(iconname[i],icon[i]));
        }
    }

    public void  setGridview(){//viewpager+gridview
        initDatas();//GridView中添加数据

        inflater = LayoutInflater.from(getContext());//Math.ceil(x/y) 向上取整，即返回大于或等于X/y结果值的整数
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);//总的页数=总数/每页数量，并取整

        mViewList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            View view=inflater.inflate(R.layout.grid_view,null);
            GridView gridView=(GridView)view.findViewById(R.id.gridview) ;
            mViewList.add(gridView);

            GridViewAdapter gridViewAdapter=new GridViewAdapter(getContext(),mDatas,i,pageSize);
            gridView.setAdapter(gridViewAdapter);//使用GridView作为每个ViewPager的页面，也就是说每个ViewPager的页面都是inflate出一个GridView新实例

            MyPagerAdapter pagerAdapter=new MyPagerAdapter(mViewList); //设置适配器
            viewPager.setAdapter(pagerAdapter);
            setDots();//添加小圆点

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Toast.makeText(getContext(), mDatas.get(pos).getIconName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setDots() {

        for (int i = 0; i < pageCount; i++) {
            mDots.addView(inflater.inflate(R.layout.dots, null));//加载布局
        }
        mDots.getChildAt(0).findViewById(R.id.v_dot)  // 默认显示第一页
                .setBackgroundResource(R.drawable.selecet);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {//viewpager的点击事件
            public void onPageSelected(int position) {

                mDots.getChildAt(curIndex)  // 取消圆点选中
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.unselecet);

                mDots .getChildAt(position) // 圆点选中
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.selecet);
                curIndex = position;
            }


            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    public void setgonggao(){
        tipsList = new ArrayList<String>();
        for (int i=0;i<news.length;i++) {
            tipsList.add(news[i]);
        }
        tipsBanner.setTipsList(tipsList);
        tipsBanner.start();



        tipsBanner.setOnTopTipsClickListener(new OnTopTipsClickListener() {
            @Override
            public void OnTopTipsClick(int position) {
                Log.d(TAG,  "点击" + position);
            }
        });

        tipsBanner.setOnBottomTipsClickListener(new OnBottomTipsClickListener() {
            @Override
            public void OnBottomTipsClick(int position) {
                Log.d(TAG,  "点击" + position);
            }
        });
    }
}

