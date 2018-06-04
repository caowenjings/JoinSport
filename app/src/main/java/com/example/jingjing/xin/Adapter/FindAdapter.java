package com.example.jingjing.xin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jingjing.xin.Bean.Need;
import com.example.jingjing.xin.Bean.User;
import com.example.jingjing.xin.Find.ExitJoinNeed;
import com.example.jingjing.xin.Find.FindActivity;
import com.example.jingjing.xin.Find.FindmeActivity;
import com.example.jingjing.xin.R;

import java.util.List;

import okhttp3.MediaType;

/**
 * Created by jingjing on 2018/6/1.
 */

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.ViewHolder> {

    private Context mcontext;
    private List<Need> mneed;
    private User muser;
    private boolean mme;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public FindAdapter(Context context,List<Need> need,User user,boolean me){
        mcontext = context;
        mneed = need;
        muser = user;
        mme = me;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
         View findview;

        TextView tv_stadiumname;
        TextView tv_username;
        TextView tv_num;
        TextView tv_time;
        TextView tv_time_join;
        TextView tv_remark;

        public ViewHolder(View view) {
            super(view);
            findview = view ;
            tv_stadiumname = (TextView)view.findViewById(R.id.stadium_name);
            tv_username = (TextView)view.findViewById(R.id.user_name);
            tv_num = (TextView)view.findViewById(R.id.tv_num);
            tv_time = (TextView)view.findViewById(R.id.tv_time);
            tv_time_join = (TextView)view.findViewById(R.id.tv_num_join);
            tv_remark = (TextView)view.findViewById(R.id.tv_remark);
        }


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_find,parent,false);
       final ViewHolder holder = new ViewHolder(view);

        holder.findview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();//获取用户点击的position
                Need need = mneed.get(position);//通过position传给相应的need实例
                if(mme){
                    if (muser.getUserId()== need.getUserId()){
                        Intent intent = new Intent(mcontext, FindmeActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("need", need);
                        mBundle.putSerializable("user",muser);
                        intent.putExtras(mBundle);
                        mcontext.startActivity(intent);
                    }else {
                        Intent intent = new Intent(mcontext, FindActivity.class);
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("need", need);
                        mBundle.putSerializable("user",muser);
                        intent.putExtras(mBundle);
                        mcontext.startActivity(intent);
                    }
                }else {
                    Intent intent = new Intent(mcontext, ExitJoinNeed.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("need", need);
                    mBundle.putSerializable("user",muser);
                    intent.putExtras(mBundle);
                    mcontext.startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Need need = mneed.get(position);
        holder.tv_username.setText(need.getUsername());
        holder.tv_stadiumname.setText("场馆名："+need.getStadiumname());
        holder.tv_num.setText("召集人数："+need.getNum());
        holder.tv_time.setText("时间："+need.getTime());
        holder.tv_time_join.setText("加入人数:"+need.getNum_join());
        holder.tv_remark.setText("备注："+need.getRemark());


    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override

    public int getItemCount() {
        return mneed.size();
    }

}
