package com.daobao.asus.iweather.Bean;

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
     * 详细空气质量
     */
    public static final int DetailsAirInfo = 4;
    /**
     *未来七天天气预报
     */
    public static final int ForecastView = 5;
    private int itemType;
    public NewWeatherBean mNewWeatherBean;
    public AirBean mAirBean;
    public boolean IsLoadInfoSuccess;

    public MultipleItem(int itemType,
                        AirBean mAirBean,
                        NewWeatherBean mNewWeatherBean,
                        boolean IsLoadInfoSuccess)
    {
        this.itemType = itemType;
        this.mAirBean = mAirBean;
        this.mNewWeatherBean = mNewWeatherBean;
        this.IsLoadInfoSuccess = IsLoadInfoSuccess;
    }
    @Override
    public int getItemType() {
        return itemType;
    }
}
