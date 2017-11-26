package com.daobao.asus.iweather.util;

/**
 * Created by db on 2017/11/19.
 */

public class MultiWeatherBgSelector {
    public static String MultiWeatherBgName(String data)
    {
      if(data.equals("502"))
      {
          return "haze_bg";
      }
      else if(data.equals("304"))
      {
          return "hail_bg";
      }
      else if(data.equals("102"))
      {
          return "few_cloudy_bg";
      }
      else if(data.equals("100")||data.equals("900"))
      {
          return "sunny_bg";
      }
      else if(data.equals("101")||data.equals("103"))
      {
          return "cloudy_bg";
      }
      else if(data.equals("104")||(data.compareTo("200")>=0&&data.compareTo("213")<=0)||data.equals("901"))
      {
          return "overcast_bg";
      }
      else if(data.compareTo("400")>=0&&data.compareTo("406")<=0)
      {
          return "snowrain_bg";
      }
      else if(data.equals("305")||data.equals("309"))
      {
          return "light_rain_bg";
      }
      else if(data.equals("307")||data.equals("308")||(data.compareTo("310")>=0&&data.compareTo("313")<=0))
      {
          return "heavy_rain_bg";
      }
      else if(data.compareTo("301")>=0&&data.compareTo("303")<=0)
      {
          return "thundershower_bg";
      }
      else if(data.equals("400")||data.equals("401")||data.equals("407"))
      {
          return "light_snow_bg";
      }
      else if(data.equals("300")||data.equals("306"))
      {
          return "rain_bg";
      }
      else if(data.equals("402")||data.equals("403"))
      {
          return "heavy_snow_bg";
      }
      else if(data.equals("500")||data.equals("501")||(data.compareTo("310")>=0&&data.compareTo("313")<=0))
      {
          return "fog_bg";
      }
      else return "unknow_bg";
    }
}
