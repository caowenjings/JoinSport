package com.example.jingjing.xin.Stadium;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingjing.xin.Adapter.SetStadiumAdapter;
import com.example.jingjing.xin.Adapter.StadiumAdapter;
import com.example.jingjing.xin.Bean.Stadium;
import com.example.jingjing.xin.Bean.User;
import com.example.jingjing.xin.Find.SetStadiumDialog;
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
import static com.example.jingjing.xin.constant.Conatant.URL_PICTURE;
import static com.example.jingjing.xin.constant.Conatant.URL_SEARCHSTADIUM;

/**
 * Created by jingjing on 2018/5/24.
 */

public class SearchStadiumone extends AppCompatActivity {
    /*
       private SetStadiumDialog.SetStadiumListener setStadiumListener;
    private NumberPicker numberPicker;
    private LinearLayoutManager layoutManager;
    private EditText et_search;
    private ImageButton iv_delete;
    private RecyclerView recyclerView;
    private String city;
    private Stadium stadium;
    private Stadium set_stadium;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);//点击Dialog外围可以消除Dialog
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 100);//设置高宽
        getDialog().setCanceledOnTouchOutside(false);

        View view = View.inflate(getActivity(), R.layout.list_changguan, null);//布局
        et_search=(EditText)view.findViewById(R.id.et_search);
        iv_delete=(ImageButton)view.findViewById(R.id.iv_delete);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {//风格
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, R.style.PlaceDialog);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {//当acvitity中的oncreate返回后，回调用这里方法
        super.onActivityCreated(savedInstanceState);
        initStadium();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }


    public interface SetStadiumListener {//设置接口

        void onSetStadiumComplete(Stadium stadium);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            setStadiumListener = (SetStadiumDialog.SetStadiumListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void  initStadium() {

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String stadiuname = et_search.getText().toString();
                    if(stadiuname.length()==0){
                        iv_delete.setVisibility(View.GONE);//如果输入框里面的内容为0,就隐藏
                    }else {
                        iv_delete.setVisibility(View.VISIBLE);
                        //  Search(stadiuname,city);
                        return false;
                    }
                }
                return false;
            }
        });
    }


    private void SetStadium(String stadiuname,String city) {
        String SearchUrl = URL_SEARCHSTADIUM;
        new SetStadiumDialog.SetStadiumAsyncTask().execute(SearchUrl,stadiuname,city);
    }
    private class SetStadiumAsyncTask extends AsyncTask<String, Integer, String> {
        public SetStadiumAsyncTask () {
        }

        @Override
        protected String doInBackground(String... params) {
            Response response = null;
            String results = null;
            JSONObject json=new JSONObject();
            try {
                json.put("stadiumname",params[1]);
                json.put("city",params[2]);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(requestBody)
                        .build();
                response=okHttpClient.newCall(request).execute();
                results=response.body().string();
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
            System.out.println("返回的数据："+s);
            List<Stadium> mData = new ArrayList<>();
            if (!"null".equals(s)){
                try {
                    JSONArray results = new JSONArray(s);
                    for(int i=0;i<results.length();i++){
                        JSONObject js= results.getJSONObject(i);
                        Stadium stadium = new Stadium();
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
                        mData.add(stadium);
                    }
                    List<Stadium> mData2 = new ArrayList<>();
                    for(int i=0;i<mData.size();i++){
                        Stadium stadium = new Stadium();
                        stadium.setMainpicture(mData.get(i).getMainpicture());
                        stadium.setAdress(mData.get(i).getAdress());
                        stadium.setCity(mData.get(i).getCity());
                        stadium.setAircondition(mData.get(i).getAircondition());
                        stadium.setArea(mData.get(i).getArea());
                        stadium.setStadiumname(mData.get(i).getStadiumname());
                        stadium.setIndoor(mData.get(i).getIndoor());
                        stadium.setNum(mData.get(i).getNum());
                        stadium.setStadiumtype(mData.get(i).getStadiumtype());
                        stadium.setStadiumId(mData.get(i).getStadiumId());
                        mData2.add(stadium);
                    }
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                    SetStadiumAdapter adapter = new SetStadiumAdapter(getContext(),mData2);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setAdapter(adapter);
                    adapter.SetStadiumOnClickListener(new SetStadiumAdapter.SetStadiumOnClickListener() {
                        @Override
                        public void onItemClick(View itemView, int postition) {
                            set_stadium= stadium;
                            onDestroy();
                            onDismiss(getDialog());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println("结果为空");
                List<Stadium> mData2 = new ArrayList<>();
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                SetStadiumAdapter adapter = new SetStadiumAdapter(getContext(),mData2);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getContext(),"没有查询到该场馆",Toast.LENGTH_SHORT).show();

            }
        }

    }*/

}
/*
    private Context context;
    private List<Stadium> mstadiumList;
    private User mUser;
    private SetStadiumOnClickListener setStadiumOnClickListener;
    private Stadium stadium;


    static class ViewHolder extends RecyclerView.ViewHolder {

        View setstadiumAdapter;
        ImageView stadiumpicture;
       // RatingBar ratingBar;
        TextView stadiumname;
        TextView stadiumtype;
        TextView stadiumaddress;

        public ViewHolder(View view) {
            super(view);
            setstadiumAdapter = view;

            stadiumpicture = (ImageView)view.findViewById(R.id.iv_stadiumpicture);
           // ratingBar = (RatingBar)view.findViewById(R.id.rb_ratbar);
            stadiumname = (TextView)view.findViewById(R.id.tv_stadiumname);
            stadiumtype = (TextView)view.findViewById(R.id.tv_stadiumtype);
            stadiumaddress= (TextView)view.findViewById(R.id.tv_stadiumaddress);
        }
    }

    public SetStadiumAdapter(Context context ,List<Stadium> stadiumList){
        context = context;
        mstadiumList = stadiumList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_changguan,parent,false);
        final ViewHolder holder = new ViewHolder(view);
       holder.setstadiumAdapter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(setStadiumOnClickListener != null){
                   int postion = holder.getLayoutPosition();
                   Stadium stadium = mstadiumList.get(postion);
                   setStadiumOnClickListener.onItemClick(stadium);
               }
           }
       });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        stadium = mstadiumList.get(position);
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
        ImageLoader imageLoader = ImageLoader.getInstance();
        ImageLoader.getInstance().init(configuration);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.error) // 设置图片加载或解码过程中发生错误显示的图片
                .showImageOnLoading(R.drawable.loading)
                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
                .delayBeforeLoading(1000)  // 下载前的延迟时间
                .build();
        ImageLoader.getInstance().displayImage(stadium.getMainpicture(), holder.stadiumpicture,options);
        holder.stadiumname.setText(stadium.getStadiumname());
        holder.stadiumaddress.setText(stadium.getAdress());
        holder.stadiumtype.setText(stadium.getStadiumtype());


    }

    @Override
    public int getItemCount() {
        return mstadiumList.size();
    }


    public interface SetStadiumOnClickListener{//1.定义点击事件的回调
        void onItemClick(Stadium stadium);
    }

    public void SetStadiumOnClickListener(SetStadiumOnClickListener setStadiumOnClickListener){//2.定义一个方法
        this.setStadiumOnClickListener=setStadiumOnClickListener;
    }
}*/