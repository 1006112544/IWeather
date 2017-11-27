package com.daobao.asus.iweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.util.MySharedpreference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by db on 2017/11/26.
 */

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private TimerTask task;
    private boolean IsCreatMainActivity = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        editor = MySharedpreference.getInstance(SplashActivity .this);
        task = new TimerTask() {
            @Override
            public void run() {
                if(!IsCreatMainActivity)
                {
                    IsCreatMainActivity = true;
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,2000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!IsCreatMainActivity)
        {
            task.cancel();
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            IsCreatMainActivity = true;
            finish();
        }
        return super.onTouchEvent(event);
    }
}
