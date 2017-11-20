package com.daobao.asus.iweather.activity;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.adpter.MyFragmentPagerAdapter;
import com.daobao.asus.iweather.fragment.MainFragment;
import com.daobao.asus.iweather.fragment.MultiCityFragment;
import com.daobao.asus.iweather.util.MySharedpreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private NavigationView mNavView;
    private DrawerLayout mDrawerLayout;
    private MainFragment mMainFragment;
    private MultiCityFragment mMultiCityFragment;
    private ArrayList<Fragment> fragments;
    private FragmentPagerAdapter mAdapter;
    private RelativeLayout main_header;
    private SharedPreferences.Editor  editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editor = MySharedpreference.getInstance(MainActivity.this);
        initView();
        initDrawer();
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        mNavView = findViewById(R.id.nav_view);
        main_header = findViewById(R.id.main_header);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        fragments = new ArrayList<>();
        setSupportActionBar(mToolbar);
        initToolBar();
        mMainFragment = new MainFragment();
        mMultiCityFragment = new MultiCityFragment();
        fragments.add(mMainFragment);
        fragments.add(mMultiCityFragment);
        mTabLayout.setupWithViewPager(mViewPager,false);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(mAdapter);
        //判断打开程序的时间 如果是晚上或者清晨更换背景图
        SimpleDateFormat mDateFormat = new SimpleDateFormat("hh:mm");
        String date = mDateFormat .format(new Date());
        if(date.compareTo("07:00")<=0||date.compareTo("19:00")>=0)
        {
            main_header.setBackgroundResource(R.mipmap.sun_main_night);
            //获取NavigationView的headerView布局
            mNavView.getHeaderView(0).setBackgroundResource(R.mipmap.header_back_night);
        }
    }

    private void initToolBar() {
        getSupportActionBar().setTitle("绵阳");
    }

    /**
     * 初始化抽屉
     */
    private void initDrawer() {
        if (mNavView != null) {
            mNavView.setNavigationItemSelectedListener(this);
            ActionBarDrawerToggle toggle =
                    new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,R.string.open_nav,
                            R.string.close_nav);
            mDrawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
