package com.daobao.asus.iweather.adpter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daobao.asus.iweather.Bean.MultipleItem;
import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.util.MultiWeatherBgSelector;
import com.daobao.asus.iweather.util.NetState;
import com.daobao.asus.iweather.util.WeatherIconSelector;

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
        addItemType(MultipleItem.DetailsAirInfo, R.layout.item_air_info);
        addItemType(MultipleItem.ForecastView, R.layout.item_forecast);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        if(item.IsLoadInfoSuccess)
        {
            switch (helper.getItemViewType()) {
                case MultipleItem.NowWeatherView:
                    try
                    {
                        String IconName = WeatherIconSelector.WeatherIconName(item
                                .mNewWeatherBean.getHeWeather6().get(0).getNow().getCond_code());
                        //根据名字查找资源id
                        int Icon = context.getResources().getIdentifier(IconName,"mipmap",
                                context.getApplicationContext().getPackageName());
                        helper.setImageResource(R.id.T_weather_icon,Icon);
                        helper.setText(R.id.T_weather, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getNow().getCond_txt());
                        helper.setText(R.id.T_temperature, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getNow().getTmp()+"℃");
                        helper.setText(R.id.T_max_temperature, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getDaily_forecast().get(0).getTmp_max()+"℃");
                        helper.setText(R.id.T_min_temperature, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getDaily_forecast().get(0).getTmp_min()+"℃");
                        helper.setText(R.id.T_pm25, "PM25:"+item.mAirBean.getHeWeather6()
                                .get(0).getAir_now_city().getPm25());
                        helper.setText(R.id.T_air, "空气质量:"+item.mAirBean.getHeWeather6()
                                .get(0).getAir_now_city().getQlty());
                    }
                    catch (Exception e1){}
                    break;
                case MultipleItem.HoursWeatherView:
                    try
                    {
                        for (int i=1;i<8;i++)
                        {
                            // 根据名称查找控件id数值
                            int one_clock = context.getResources().getIdentifier("one_clock_"+i,
                                    "id", context.getApplicationContext().getPackageName());
                            String time = item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getHourly().get(i-1).getTime();
                            helper.setText(one_clock,time.substring(time.lastIndexOf(" "),time.length()));
                            // 根据名称查找控件id数值
                            int one_humidity = context.getResources().getIdentifier("one_humidity_"+i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(one_humidity, item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getHourly().get(i-1).getHum()+"%");
                            // 根据名称查找控件id数值
                            int one_temp = context.getResources().getIdentifier("one_temp_"+i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(one_temp, item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getHourly().get(i-1).getTmp()+"℃");
                            // 根据名称查找控件id数值
                            int one_wind = context.getResources().getIdentifier("one_wind_"+i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(one_wind, item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getHourly().get(i-1).getWind_sc());
                        }
                    }
                    catch (Exception e){}
                    break;
                case MultipleItem.SuggestionView:
                    try
                    {
                        helper.setText(R.id.comf_brief,"舒适指数---"+item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(0).getBrf());
                        helper.setText(R.id.comf_txt,item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(0).getTxt());
                        helper.setText(R.id.cloth_brief,"穿衣指数---"+item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(1).getBrf());
                        helper.setText(R.id.cloth_txt,item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(1).getTxt());
                        helper.setText(R.id.flu_brief,"感冒指数---"+item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(2).getBrf());
                        helper.setText(R.id.flu_txt,item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(2).getTxt());
                        helper.setText(R.id.sport_brief,"运动指数---"+item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(3).getBrf());
                        helper.setText(R.id.sport_txt,item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(3).getTxt());
                        helper.setText(R.id.travel_brief,"旅游指数---"+item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(4).getBrf());
                        helper.setText(R.id.travel_txt,item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(4).getTxt());
                    }
                    catch (Exception e) {}
                    break;
                case MultipleItem.ForecastView:
                    try
                    {
                        for(int i=1;i<8;i++) {
                            String IconName_forecast = WeatherIconSelector.WeatherIconName(item.mNewWeatherBean
                                    .getHeWeather6().get(0).getDaily_forecast().get(i-1).getCond_code_d());
                            //根据名字查找资源id
                            int Icon_forecast_id = context.getResources().getIdentifier(IconName_forecast,
                                    "mipmap", context.getApplicationContext().getPackageName());
                            //根据名称查找控件id数值
                            int forecast_icon= context.getResources().getIdentifier("forecast_icon_"+i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setImageResource(forecast_icon,Icon_forecast_id);
                            //根据名称查找控件id数值
                            int forecast_date = context.getResources().getIdentifier("forecast_date_"+i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(forecast_date,item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i-1).getDate());
                            //根据名称查找控件id数值
                            int forecast_temp = context.getResources().getIdentifier("forecast_temp_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(forecast_temp,"温度:"+item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i-1).getTmp_min()+"℃"+"/"+item
                                    .mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i-1).getTmp_max()+"℃");
                            //根据名称查找控件id数值
                            int forecast_txt = context.getResources().getIdentifier("forecast_txt_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            String txt = item.mNewWeatherBean.getHeWeather6().get(0).getDaily_forecast()
                                    .get(i-1).getCond_txt_d()+"  最高温度"+item.mNewWeatherBean
                                    .getHeWeather6().get(0).getDaily_forecast()
                                    .get(i-1).getTmp_max()+"℃  "+item.mNewWeatherBean
                                    .getHeWeather6().get(0).getDaily_forecast()
                                    .get(i-1).getWind_dir()+"  风力"+item.mNewWeatherBean
                                    .getHeWeather6().get(0).getDaily_forecast()
                                    .get(i-1).getWind_sc();
                            helper.setText(forecast_txt,txt);
                        }
                    }
                    catch (Exception e) {}
                    break;
                case MultipleItem.DetailsAirInfo:
                    try
                    {
                        helper.setText(R.id.air_aiq_tv,"空气指数:  "+item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getAqi());
                        helper.setText(R.id.air_qlty_tv,"空气质量:  "+item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getQlty());
                        helper.setText(R.id.air_main_pollutant_tv,"主要污染物:  "+item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getMain());
                        helper.setText(R.id.air_pm25_tv,"PM25指数:  "+item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getPm25());
                        helper.setText(R.id.air_no2_tv,"二氧化氮指数:  "+item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getNo2());
                        helper.setText(R.id.air_so2_tv,"二氧化硫指数:  "+item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getSo2());
                        helper.setText(R.id.air_co_tv,"一氧化碳指数:  "+item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getCo());
                        helper.setText(R.id.air_o3_tv,"臭氧指数:  "+item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getO3());
                    }
                    catch (Exception e){}
                    break;
            }
        }
    }
}
