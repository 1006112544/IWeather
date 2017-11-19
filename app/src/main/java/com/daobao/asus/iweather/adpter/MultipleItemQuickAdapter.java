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
                      //根据名称查找控件id数值
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
                break;
            case MultipleItem.SuggestionView:
                helper.setText(R.id.cloth_brief,"穿衣指数---"+item.mLifeBean.getHeWeather6()
                        .get(0).getLifestyle().get(1).getBrf());
                helper.setText(R.id.cloth_txt,item.mLifeBean.getHeWeather6()
                        .get(0).getLifestyle().get(1).getTxt());
                helper.setText(R.id.flu_brief,"感冒指数---"+item.mLifeBean.getHeWeather6()
                        .get(0).getLifestyle().get(2).getBrf());
                helper.setText(R.id.flu_txt,item.mLifeBean.getHeWeather6()
                        .get(0).getLifestyle().get(2).getTxt());
                helper.setText(R.id.sport_brief,"运动指数---"+item.mLifeBean.getHeWeather6()
                        .get(0).getLifestyle().get(3).getBrf());
                helper.setText(R.id.sport_txt,item.mLifeBean.getHeWeather6()
                        .get(0).getLifestyle().get(3).getTxt());
                helper.setText(R.id.travel_brief,"旅游指数---"+item.mLifeBean.getHeWeather6()
                        .get(0).getLifestyle().get(4).getBrf());
                helper.setText(R.id.travel_txt,item.mLifeBean.getHeWeather6()
                        .get(0).getLifestyle().get(4).getTxt());
                break;
            case MultipleItem.ForecastView:
                for(int i=1;i<4;i++) {
                    //根据名称查找控件id数值
                    int forecast_date = context.getResources().getIdentifier("forecast_date_"+i, "id",
                            context.getApplicationContext().getPackageName());
                    if (i == 1) {
                        helper.setText(forecast_date, "今天");
                    } else if (i == 2)
                    {
                        helper.setText(forecast_date, "明天");
                    }
                    else if (i == 3)
                    {
                        helper.setText(forecast_date, "后天");
                    }
                    //根据名称查找控件id数值
                    int forecast_temp = context.getResources().getIdentifier("forecast_temp_" + i, "id",
                            context.getApplicationContext().getPackageName());
                    helper.setText(forecast_temp,"最低温度:"+item.mForecastWeatherBean.getHeWeather6()
                            .get(0).getDaily_forecast().get(i-1).getTmp_min()+"℃");
                    //根据名称查找控件id数值
                    int forecast_txt = context.getResources().getIdentifier("forecast_txt_" + i, "id",
                            context.getApplicationContext().getPackageName());
                    String txt = item.mForecastWeatherBean.getHeWeather6().get(0).getDaily_forecast()
                            .get(i-1).getCond_txt_d()+"  最高温度"+item.mForecastWeatherBean.getHeWeather6().get(0).getDaily_forecast()
                            .get(i-1).getTmp_max()+"℃  "+item.mForecastWeatherBean.getHeWeather6().get(0).getDaily_forecast()
                            .get(i-1).getWind_dir()+"  风力"+item.mForecastWeatherBean.getHeWeather6().get(0).getDaily_forecast()
                            .get(i-1).getWind_sc();
                    helper.setText(forecast_txt,txt);
                }
                break;
        }
    }



}
