package com.daobao.asus.iweather.adpter;

import android.content.Context;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daobao.asus.iweather.Enty.MultipleItem;
import com.daobao.asus.iweather.R;

import java.util.List;

/**
 * Created by db on 2017/11/18.
 */

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context context;
    public MultipleItemQuickAdapter(List<MultipleItem> data,Context context) {
        super(data);
        addItemType(MultipleItem.NowWeatherView, R.layout.item_temperature);
        addItemType(MultipleItem.HoursWeatherView, R.layout.item_hour_info);
        addItemType(MultipleItem.SuggestionView, R.layout.item_suggestion);
        addItemType(MultipleItem.ForecastView, R.layout.item_forecast);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.NowWeatherView:
                helper.setText(R.id.T_weather, item.mNowWeatherBean.getHeWeather6()
                        .get(0).getNow().getCond_txt());
                helper.setText(R.id.T_temperature, item.mNowWeatherBean.getHeWeather6()
                        .get(0).getNow().getTmp()+"℃");
                helper.setText(R.id.T_max_temperature, item.mForecastWeatherBean.getHeWeather6()
                        .get(0).getDaily_forecast().get(0).getTmp_max()+"℃");
                helper.setText(R.id.T_min_temperature, item.mForecastWeatherBean.getHeWeather6()
                        .get(0).getDaily_forecast().get(0).getTmp_min()+"℃");
                helper.setText(R.id.T_pm25, "PM25:"+item.mAirBean.getHeWeather6()
                        .get(0).getAir_now_city().getPm25());
                helper.setText(R.id.T_air, "空气质量:"+item.mAirBean.getHeWeather6()
                        .get(0).getAir_now_city().getAqi());
                break;
            case MultipleItem.HoursWeatherView:
//                for (int i=0;i<7;i++)
//                {
//                    int intialResID = context.getResources().getIdentifier("one_clock_"+1, "id",
//                            context.getApplicationContext().getPackageName());
//                    helper.setText(intialResID, item.mHourlyWeatherBean.getHeWeather6()
//                            .get(0).getHourly().get(i).getTime());
//                    helper.setText(intialResID, item.mHourlyWeatherBean.getHeWeather6()
//                            .get(0).getHourly().get(i).getTmp());
//                    helper.setText(intialResID, item.mHourlyWeatherBean.getHeWeather6()
//                            .get(0).getHourly().get(i).getHum());
//                    helper.setText(intialResID, item.mHourlyWeatherBean.getHeWeather6()
//                            .get(0).getHourly().get(i).getWind_sc());
//                }
//                Log.d("cc", "convert: "+item.mHourlyWeatherBean.getHeWeather6()
//                        .get(0).getHourly().get(3).getTime());
                break;
            case MultipleItem.SuggestionView:
                break;
            case MultipleItem.ForecastView:
                break;
        }
    }



}
