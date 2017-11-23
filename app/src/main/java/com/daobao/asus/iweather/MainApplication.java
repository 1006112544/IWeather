package com.daobao.asus.iweather;

import android.app.Application;
import android.content.Context;

/**
 * Created by db on 2017/11/17.
 */

public class MainApplication extends Application{
    public static Context Instance;
    @Override
    public void onCreate() {
        super.onCreate();
        Instance = getApplicationContext();
    }
}
