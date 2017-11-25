package com.daobao.asus.iweather.util;

/**
 * Created by db on 2017/11/19.
 */

public class WeatherIconSelector {
    public static String WeatherIconName(String data)
    {
        if(data.equals("103"))
        {
            return "cloudytosunny";
        }
        else if(data.equals("102"))
        {
            return "few_clouds";
        }
        else if(data.equals("304"))
        {
            return "hail";
        }
        else if(data.equals("501")||data.equals("502"))
        {
            return "fog";
        }
        else if(data.compareTo("200")>=0&&data.compareTo("213")<=0)
        {
            return "windy";
        }
        else if((data.compareTo("400")>=0&&data.compareTo("403")<=0)||data.equals("407"))
        {
            return "snow";
        }
        else if(data.equals("100")||data.equals("900"))
        {
            return "sunny";
        }
        else if(data.compareTo("301")>=0&&data.compareTo("303")<=0)
        {
            return "thunder_rain";
        }
        else if(data.equals("101")||data.equals("103")||data.equals("104")||data.equals("502")||
                data.equals("503")||data.equals("504")||data.equals("507")||data.equals("508"))
        {
            return "cloudy";
        }
        else if (data.equals("300")||data.equals("307")||data.equals("308")||data.equals("310")||
                data.equals("311")||data.equals("312")||data.equals("313"))
        {
            return "heavy_rain";
        }
        else if(data.equals("305")||data.equals("306")||data.equals("309")||
                data.equals("404")||data.equals("405")||data.equals("406")||data.equals("901"))
        {
            return "light_rain";
        }
        else return "none";
    }
}
