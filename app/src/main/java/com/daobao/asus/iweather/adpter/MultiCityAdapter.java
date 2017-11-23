package com.daobao.asus.iweather.adpter;

import android.content.Context;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daobao.asus.iweather.Bean.NewWeatherBean;
import com.daobao.asus.iweather.R;
import com.daobao.asus.iweather.util.MultiWeatherBgSelector;
import com.daobao.asus.iweather.util.WeatherIconSelector;
import java.util.List;

/**
 * Created by db on 2017/11/22.
 */

public class MultiCityAdapter extends BaseItemDraggableAdapter<NewWeatherBean, BaseViewHolder>  {
    private Context context;
    public MultiCityAdapter(int layoutResId, @Nullable List data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, NewWeatherBean item) {
        try {
            String IconName = WeatherIconSelector.WeatherIconName(item.getHeWeather6()
                    .get(0).getNow().getCond_code());
            //根据名字查找资源id
            int Icon = context.getResources().getIdentifier(IconName,"mipmap",
                    context.getApplicationContext().getPackageName());
            helper.setText(R.id.item_multi_city,item.getHeWeather6().get(0).getBasic().getLocation());
            helper.setImageResource(R.id.item_multi_icon,Icon);
            helper.setText(R.id.item_multi_cond,item.getHeWeather6()
                    .get(0).getNow().getCond_txt());
            helper.setText(R.id.item_multi_fl,"体感温度:"+item.getHeWeather6()
                    .get(0).getNow().getFl()+"℃");
            helper.setText(R.id.item_multi_fl,"风向:"+item.getHeWeather6()
                    .get(0).getNow().getWind_dir()+"风");
            helper.setText(R.id.item_multi_fl,"风力:"+item.getHeWeather6()
                    .get(0).getNow().getWind_sc());
            helper.setText(R.id.item_multi_fl,"能见度:"+item.getHeWeather6()
                    .get(0).getNow().getVis()+"公里");
            helper.setText(R.id.item_multi_temperature,item.getHeWeather6()
                    .get(0).getNow().getTmp()+"℃");
            helper.setText(R.id.item_multi_max_temperature,item.getHeWeather6()
                    .get(0).getDaily_forecast().get(0).getTmp_max()+"℃");
            helper.setText(R.id.item_multi_min_temperature,item.getHeWeather6()
                    .get(0).getDaily_forecast().get(0).getTmp_min()+"℃");
            String BgName = MultiWeatherBgSelector.MultiWeatherBgName(item.getHeWeather6()
                    .get(0).getNow().getCond_code());
            //根据名字查找资源id
            int Bg = context.getResources().getIdentifier(BgName,"mipmap",
                    context.getApplicationContext().getPackageName());
            helper.setBackgroundRes(R.id.item_multi_bg,Bg);
        }
        catch (Exception e){}
    }
}
