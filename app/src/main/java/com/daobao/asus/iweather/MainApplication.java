package com.daobao.asus.iweather;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.daobao.asus.iweather.util.MySharedpreference;

/**
 * Created by db on 2017/11/17.
 */

public class MainApplication extends Application{
    public static Context Instance;
    @Override
    public void onCreate() {
        super.onCreate();
        Instance = getApplicationContext();
        setRegisterActivityLifecycleCallbacks();
    }

    /**
     * 在application中监听所有aictivity的生命周期
     */
    public void setRegisterActivityLifecycleCallbacks()
    {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                //判断是否隐藏所有activity的状态栏
                if(MySharedpreference.preferences.getBoolean("Status_title",false))
                {
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                else
                {
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
