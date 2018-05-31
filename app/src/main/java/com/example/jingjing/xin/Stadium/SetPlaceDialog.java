package com.example.jingjing.xin.Stadium;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingjing.xin.Bean.Place;
import com.example.jingjing.xin.Bean.Stadium;
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

import static com.example.jingjing.xin.constant.Conatant.URL_PLACENAME;


@SuppressLint("ValidFragment")
public class SetPlaceDialog extends DialogFragment {
    private List<String> place = new ArrayList<>();
    private ListView listView;
    private TextView tv;
    private SetPlaceListener setPlaceListener;
    private String place1;
    private Stadium mStadium;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @SuppressLint("ValidFragment")
    public SetPlaceDialog(Stadium mStadium) {
        this.mStadium = mStadium;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getContext(), R.layout.list_place, null);//布局
        listView = (ListView) view.findViewById(R.id.lv_place);
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.PlaceDialog);

    }

    @Override
    public void onStart() {//设置dialog的宽高，背景
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {//当acvitity中的oncreate返回后，回调用这里方法
        super.onActivityCreated(savedInstanceState);
        getPlace(mStadium);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }


    public interface SetPlaceListener {//Fragment与activity的通信，内部回调接口

        void onSetPlaceComplete(String placel);//把方法封装在接口中，在activity中需要用到方法的实现这个接口即可
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            setPlaceListener = (SetPlaceListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onDestroy() {
        setPlaceListener.onSetPlaceComplete(place1);
        super.onDestroy();
    }

    private void getPlace(Stadium stadium) {
        String loadingUrl = URL_PLACENAME;
        new getPlaceAsyncTask().execute(loadingUrl, String.valueOf(stadium.getStadiumId()));
    }

    private class getPlaceAsyncTask extends AsyncTask<String, Integer, String> {
        public getPlaceAsyncTask() {
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
            System.out.println("返回的数据：" + s);
            List<Place> mData = new ArrayList<>();
            if (!"null".equals(s)) {
                try {
                    JSONArray results = new JSONArray(s);
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject js = results.getJSONObject(i);
                        Place place = new Place();
                        place.setStadiumId(js.getInt("stadiumId"));
                        place.setPlaceId(js.getInt("placeId"));
                        place.setState(js.getString("state"));
                        place.setMaterial(js.getString("material"));
                        place.setPlacename(js.getString("placename"));
                        mData.add(place);
                    }
                    for (int i = 0; i < mData.size(); i++) {
                        place.add(mData.get(i).getPlacename());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, place);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            place1 = place.get(position);
                            onDestroy();
                            onDismiss(getDialog());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("结果为空");
                Toast.makeText(getContext(), "该场馆暂时没添加场地", Toast.LENGTH_LONG).show();
            }
        }
    }
}