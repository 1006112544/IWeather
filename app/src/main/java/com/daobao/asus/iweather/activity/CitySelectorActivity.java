package com.daobao.asus.iweather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daobao.asus.iweather.CityDB.db.DBManager;
import com.daobao.asus.iweather.CityDB.db.WeatherDB;
import com.daobao.asus.iweather.CityDB.domain.City;
import com.daobao.asus.iweather.CityDB.domain.Province;
import com.daobao.asus.iweather.adpter.CitySelectorAdapter;
import java.util.ArrayList;
import com.daobao.asus.iweather.R;

/**
 * Created by db on 2017/11/23.
 */

public class CitySelectorActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private ArrayList<Province> ProvinceList = new ArrayList<>();
    private ArrayList<City> CityList = new ArrayList<>();
    private ArrayList<String> data = new ArrayList<>();
    private Province selectedProvince;
    private CitySelectorAdapter mAdapter;
    public static final int SelectorSuccessFromMenu = 959;
    public static final int SelectorSuccessFromMultiCity = 960;
    private final int LEVEL_PROVINCE = 1;
    private final int LEVEL_CITY = 2;
    private int currentLevel;//当前页面等级

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        initView();
        DBManager.getInstance().openDatabase();
        queryProvinces();
    }
    private void initView() {
        mRecyclerView = findViewById(R.id.city_select_recyclerView);
        mToolbar = findViewById(R.id.city_select_toolbar);
        mCollapsingToolbarLayout = findViewById(R.id.city_select_toolbar_layout);
    }
    private void initAdapter()
    {
        mAdapter = new CitySelectorAdapter(R.layout.item_city_selector,data);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(currentLevel == LEVEL_PROVINCE)
                {
                    //点击了省份
                    selectedProvince = ProvinceList.get(position);
                    mCollapsingToolbarLayout.setTitle("选择城市");
                    queryCities();
                }
                else
                {
                    //点击了城市
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("CityName",CityList.get(position).mCityName);
                    intent.putExtras(bundle);
                    CitySelectorActivity.this.setResult(-1,intent);
                    quit();
                }
            }
        });
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CitySelectorActivity.this.onBackPressed();
            }
        });

    }
    /**
     * 查询全国所有的省，从数据库查询
     */
    private void queryProvinces() {
        ProvinceList.addAll(WeatherDB.loadProvinces(DBManager.getInstance().getDatabase()));
        data.clear();
        for (Province province : ProvinceList) {
            data.add(province.mProName);
        }
        currentLevel = LEVEL_PROVINCE;
        if(mAdapter==null)
        {
            initAdapter();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else mAdapter.notifyDataSetChanged();
    }
    /**
     * 查询选中省份的所有城市，从数据库查询
     */
    private void queryCities() {
        CityList.addAll(WeatherDB.loadCities(DBManager.getInstance().getDatabase(), selectedProvince.mProSort));
        data.clear();
        for (City city:CityList)
        {
            data.add(city.mCityName);
        }
        currentLevel = LEVEL_CITY;
        mAdapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(0);
    }
    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_PROVINCE) {
            quit();
        } else {
            queryProvinces();
            CityList.clear();
            mRecyclerView.smoothScrollToPosition(0);
            mCollapsingToolbarLayout.setTitle("选择省份");
        }
    }
    private void quit()
    {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
