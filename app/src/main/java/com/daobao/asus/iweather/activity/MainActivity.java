package com.daobao.asus.iweather.activity;

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
import android.view.MenuItem;
import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.adpter.MyFragmentPagerAdapter;
import com.daobao.asus.iweather.fragment.MainFragment;
import com.daobao.asus.iweather.fragment.MultiCityFragment;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDrawer();
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        mNavView = findViewById(R.id.nav_view);
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
