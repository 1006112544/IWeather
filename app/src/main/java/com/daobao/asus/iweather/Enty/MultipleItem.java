package com.daobao.asus.iweather.Enty;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by db on 2017/11/18.
 */

public class MultipleItem implements MultiItemEntity {
    /**
     * 当前天气
     */
    public static final int NowWeatherView = 1;
    /**
     * 每小时天气情况
     */
    public static final int HoursWeatherView = 2;
    /**
     *当日建议
     */
    public static final int SuggestionView = 3;
    /**
     *未来三天天气预报
     */
    public static final int ForecastView = 4;
    private int itemType;
    public ForecastWeatherBean mForecastWeatherBean;
    public NowWeatherBean mNowWeatherBean;
    public HourlyWeatherBean mHourlyWeatherBean;
    public LifeBean mLifeBean;
    public AirBean mAirBean;

    public MultipleItem(int itemType,NowWeatherBean mNowWeatherBean,
                        ForecastWeatherBean mForecastWeatherBean,
                        AirBean mAirBean,
                        HourlyWeatherBean mHourlyWeatherBean,
                        LifeBean mLifeBean)
    {
        this.itemType = itemType;
        this.mForecastWeatherBean = mForecastWeatherBean;
        this.mNowWeatherBean = mNowWeatherBean;
        this.mAirBean = mAirBean;
        this.mHourlyWeatherBean = mHourlyWeatherBean;
        this.mLifeBean = mLifeBean;
    }
    @Override
    public int getItemType() {
        return itemType;
    }
}
