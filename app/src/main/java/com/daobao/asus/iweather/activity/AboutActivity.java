package com.daobao.asus.iweather.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.daobao.asus.iweather.R;

/**
 * Created by db on 2017/11/25.
 */

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private Button mCodeButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.about_toolbar);
        mCodeButton = findViewById(R.id.about_bt_code);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.this.onBackPressed();
            }
        });
        mCodeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.about_bt_code:
                Intent intent =new Intent();//创建Intent对象
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                String data = "https://github.com/1006112544/IWeather";//获取编辑框里面的文本内容
                intent.setData(Uri.parse(data));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                break;
        }
    }
}
