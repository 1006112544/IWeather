package com.daobao.asus.iweather.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.util.MySharedpreference;

/**
 * Created by db on 2017/11/25.
 */

public class SettingActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private Spinner main_spinner;
    private Spinner multi_spinner;
    private Switch mSwitch;
    private int main_anim;
    private int multi_anim;
    private SharedPreferences.Editor  editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        editor = MySharedpreference.getInstance(SettingActivity.this);
        initView();
    }
    private void initView() {
        mToolbar = findViewById(R.id.setting_Toolbar);
        main_spinner = findViewById(R.id.main_item_anim);
        multi_spinner = findViewById(R.id.multi_city_item_anim);
        mSwitch = findViewById(R.id.status_bar_Is_presenter);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.onBackPressed();
            }
        });
        getSupportActionBar().setTitle("设置");
        main_anim = MySharedpreference.preferences.getInt("Main_anim",0);
        multi_anim = MySharedpreference.preferences.getInt("Multi_anim",0);
        setSpinnerChecked(main_anim,main_spinner);
        setSpinnerChecked(multi_anim,multi_spinner);
        mSwitch.setChecked(MySharedpreference.preferences.getBoolean("Status_title",false));
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("Status_title",b);
                editor.commit();
            }
        });
        main_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("Main_anim",i);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        multi_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("Multi_anim",i);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void setSpinnerChecked(int modle,Spinner mSpinner)
    {
        switch (modle)
        {
            case 0:
                mSpinner.setSelection(0);
                break;
            case 1:
                mSpinner.setSelection(1);
                break;
            case 2:
                mSpinner.setSelection(2);
                break;
            case 3:
                mSpinner.setSelection(3);
                break;
            case 4:
                mSpinner.setSelection(4);
                break;
        }
    }
}
