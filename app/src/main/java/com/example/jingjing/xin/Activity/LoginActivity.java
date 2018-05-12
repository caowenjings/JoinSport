package com.example.jingjing.xin.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingjing.xin.Bean.User;
import com.example.jingjing.xin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.example.jingjing.xin.constant.Conatant.URL_LOGIN;

/**
 * Created by jingjing on 2018/5/9.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_username;
    private EditText et_password;
    private TextView btn_register;
    private TextView btn_forgetpwd;
    private Button btn_login;
    private String username,password;
    private CheckBox remember_pwd;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.login);

        initView();
        initData();

    }

    private void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_register = (TextView) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgetpwd=(TextView)findViewById(R.id. btn_forgetpwd);
        remember_pwd=(CheckBox)findViewById(R.id.remember_pwd);
    }

    private void initData() {
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_forgetpwd.setOnClickListener(this);
    }
    public void getEditString(){
        username=et_username.getText().toString();
        password=et_password.getText().toString();
    }

    @Override
    public void onClick(View v) {
        getEditString();
        switch (v.getId()) {
            case R.id.btn_login:
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    Login(username,password);
                } else {
                    Toast.makeText(this, "账号、密码都不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_forgetpwd:
                Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;

        }
    }


    private void Login(String username, String password) {
        String loginUrl = URL_LOGIN;
        new LoginAsyncTask().execute(loginUrl, username, password);
    }

    private class LoginAsyncTask extends AsyncTask<String, Integer, String> {
        public LoginAsyncTask() {
        }

        @Override
        protected String doInBackground(String... params) {//后台执行具体的下载逻辑
            Response response = null;
            String results = null;
            JSONObject json = new JSONObject();
            try {
                json.put("username", params[1]);
                json.put("password", params[2]);
                OkHttpClient okHttpClient = new OkHttpClient();//发送网络请求
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()//断点下载从哪个字节开始下载
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
        protected void onPostExecute(String s) {//通知最后的下载结果
            System.out.println(s);
            if (s != null) {
                try {
                    JSONObject results = new JSONObject(s);
                    String loginresult = results.getString("result");
                    System.out.println("22");
                    System.out.println(loginresult);
                    final User user = new User();
                    if (!"0".equals(loginresult)) {
                        user.setUserId(results.getInt("userId"));
                        user.setUsername(results.getString("username"));
                        user.setPassword(results.getString("password"));
                        user.setRealname(results.getString("realname"));
                        user.setSex(results.getString("sex"));
                        user.setTel(results.getString("tel"));
                        user.setMyright(results.getString("myRight"));
                        Toast.makeText(LoginActivity.this, "正在登陆，请稍后", Toast.LENGTH_LONG).show();
                        new Handler(new Handler.Callback() {
                            //处理接收到的消息的方法，防止堵塞主线程
                            @Override
                            public boolean handleMessage(Message arg0) {
                                //实现页面跳转
                                Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putString("user", String.valueOf(user));
                                intent.putExtras(mBundle);
                                startActivity(intent);
                                finish();
                                return false;
                            }
                        }).sendEmptyMessageDelayed(0, 2000);
                    } else {
                        Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("结果为空");
                Toast.makeText(LoginActivity.this, "网络未连接", Toast.LENGTH_LONG).show();

            }
        }
    }}