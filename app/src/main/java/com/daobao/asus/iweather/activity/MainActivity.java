package com.daobao.asus.iweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.daobao.asus.iweather.Dialog.OtherCityDialog;
import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.adpter.MyFragmentPagerAdapter;
import com.daobao.asus.iweather.fragment.MainFragment;
import com.daobao.asus.iweather.fragment.MultiCityFragment;
import com.daobao.asus.iweather.util.MySharedpreference;
import com.daobao.asus.iweather.util.NetState;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,FloatingActionButton.OnClickListener{
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private NavigationView mNavView;
    private DrawerLayout mDrawerLayout;
    private MainFragment mMainFragment;
    private FloatingActionButton mFab;
    private MultiCityFragment mMultiCityFragment;
    private CollapsingToolbarLayout toolbar_layout;
    private ArrayList<Fragment> fragments;
    private FragmentPagerAdapter mAdapter;
    private ImageView banner;
    private SharedPreferences.Editor  editor;
    private  Calendar calendar = Calendar.getInstance();
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
        toolbar_layout = findViewById(R.id.toolbar_layout);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);
        banner = findViewById(R.id.banner);
        mFab = findViewById(R.id.fab);
        mFab.hide();
        mFab.setOnClickListener(this);
        fragments = new ArrayList<>();
        setSupportActionBar(mToolbar);
        mMainFragment = new MainFragment(new MainFragment.InitInfoListener() {
            @Override
            public void InitSuccessed(String cityName) {
                //修改标题
                toolbar_layout.setTitle(cityName);
            }
        });
        toolbar_layout.setTitle(MySharedpreference.preferences.getString("City","成都"));
        mMultiCityFragment = new MultiCityFragment();
        fragments.add(mMainFragment);
        fragments.add(mMultiCityFragment);
        mTabLayout.setupWithViewPager(mViewPager,false);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(mAdapter);
        //判断打开程序的时间 如果是晚上或者清晨更换背景图
        long time=System.currentTimeMillis();
        calendar.setTimeInMillis(time);
        int mHour = calendar.get(Calendar.HOUR);
        int amp = calendar.get(Calendar.AM_PM);
        if((amp==0&&mHour<7)||(amp==1)&&mHour>=7)
        {
            //上午七点前下午七点后都需要更改背景
            banner.setImageResource(R.mipmap.sun_main_night);
            //获取NavigationView的headerView布局
            mNavView.getHeaderView(0).setBackgroundResource(R.mipmap.header_back_night);
        }
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position==0) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }
        });
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
        switch (item.getItemId())
        {
            case R.id.nav_city:
                Intent intent = new Intent(MainActivity.this, CitySelectorActivity.class);
                startActivityForResult(intent,CitySelectorActivity.SelectorSuccessFromMenu);
                break;
            case R.id.nav_other_city:
                mDrawerLayout.closeDrawer(mNavView);
                new OtherCityDialog(MainActivity.this, new OtherCityDialog.MyDialogListenner() {
                    @Override
                    public void ClickedSure(String cityName) {
                        UpdataViewForMain(cityName);
                    }
                }).show();
                break;
            case R.id.nav_multi_cities:
                //关闭侧滑栏
                mDrawerLayout.closeDrawer(mNavView);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.nav_set:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                break;
            case R.id.nav_about:
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                break;
        }
        return false;
    }
    //接收选择器选中的结果：
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CitySelectorActivity.SelectorSuccessFromMenu) {
            //来自于菜单选择城市的回调
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();

                //城市名称
                String cityName = bundle.getString("CityName");

                //获取到城市名称后可自行使用...
                UpdataViewForMain(cityName);
            }
        }
        else if(requestCode == CitySelectorActivity.SelectorSuccessFromMultiCity)
        {
            //来自于多城市添加的回调
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();
                //城市名称
                String cityName = bundle.getString("CityName");

                //获取到城市名称可自行使用...
                UpdataViewForMultiCity(cityName);
            }
        }
    }

    @Override
    public void onClick(View view) {
        //首先跳转到列表页面，通过startActivityForResult实现页面跳转传值
        Intent intent = new Intent(MainActivity.this, CitySelectorActivity.class);
        startActivityForResult(intent,CitySelectorActivity.SelectorSuccessFromMultiCity);
    }

    /**
     * 更新主页天气页面
     */
    private void UpdataViewForMain(String cityName)
    {
        //关闭侧滑栏
        mDrawerLayout.closeDrawer(mNavView);
        //获取OldCity 以便新城市不可加载时恢复数据
        String OldCity = MySharedpreference.preferences.getString("City",null);
        //保存城市
        editor.putString("City",cityName);
        editor.commit();
        //更新数据
        mMainFragment.UpDataUi(OldCity);
        //RecyclerView回到顶部
        mMainFragment.mRecyclerView.smoothScrollToPosition(0);
    }
    /**
     * 更新多城市管理页面
     */
    private void UpdataViewForMultiCity(String cityName)
    {
        int MultiCityNum = MySharedpreference.preferences.getInt("MultiCityNum",0);
        //获取主页城市
        String mainCity = MySharedpreference.preferences.getString("City","成都");
        if(MultiCityNum>=3)
        {
            Toast.makeText(MainActivity.this,"多城市数量不能超过3个",Toast.LENGTH_SHORT).show();
        }
        else if(mainCity.contains(cityName)||cityName.contains(mainCity))
        {
            Toast.makeText(MainActivity.this,"该城市已经存在",Toast.LENGTH_SHORT).show();
        }
        else if(MultiCityNum!=0)
        {
            int i;
            for(i=1;i<=MultiCityNum;i++)
            {
                if(cityName.equals(MySharedpreference.preferences.getString("MultiCity"+i,null)))
                {
                    Toast.makeText(MainActivity.this,"该城市已经存在",Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if(i>MultiCityNum)//没有查找到重名城市
            {
                if(NetState.isNetworkAvailable(getApplicationContext()))
                {
                    MultiCityNum++;
                    editor.putString("MultiCity"+MultiCityNum,cityName);
                    editor.putInt("MultiCityNum",MultiCityNum);
                    editor.commit();
                    mMultiCityFragment.UpDataUi();
                }
                else Toast.makeText(MainActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            if(NetState.isNetworkAvailable(getApplicationContext()))
            {
                MultiCityNum++;
                editor.putString("MultiCity"+MultiCityNum,cityName);
                editor.putInt("MultiCityNum",MultiCityNum);
                editor.commit();
                mMultiCityFragment.UpDataUi();
            }
            else Toast.makeText(MainActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(mNavView))
        {
            mDrawerLayout.closeDrawer(mNavView);
        }
        else
        {
            super.onBackPressed();
        }


    }
}
