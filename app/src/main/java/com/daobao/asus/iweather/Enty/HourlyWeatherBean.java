package com.daobao.asus.iweather.Enty;

import java.util.List;

/**
 * Created by db on 2017/11/19.
 */

public class HourlyWeatherBean {

    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.40528870","tz":"8.0"}
         * hourly : [{"cloud":"8","cond_code":"100","cond_txt":"晴","hum":"84","pop":"0","pres":"1018","time":"2017-10-27 01:00","tmp":"8","wind_deg":"49","wind_dir":"东北风","wind_sc":"微风","wind_spd":"2"},{"cloud":"8","cond_code":"100","cond_txt":"晴","hum":"81","pop":"0","pres":"1018","time":"2017-10-27 04:00","tmp":"8","wind_deg":"29","wind_dir":"东北风","wind_sc":"微风","wind_spd":"2"},{"cloud":"6","cond_code":"100","cond_txt":"晴","hum":"95","pop":"0","pres":"1019","time":"2017-10-27 07:00","tmp":"8","wind_deg":"37","wind_dir":"东北风","wind_sc":"微风","wind_spd":"2"},{"cloud":"2","cond_code":"100","cond_txt":"晴","hum":"75","pop":"0","pres":"1018","time":"2017-10-27 10:00","tmp":"14","wind_deg":"108","wind_dir":"东南风","wind_sc":"微风","wind_spd":"3"},{"cloud":"0","cond_code":"100","cond_txt":"晴","hum":"62","pop":"0","pres":"1016","time":"2017-10-27 13:00","tmp":"16","wind_deg":"158","wind_dir":"东南风","wind_sc":"微风","wind_spd":"6"},{"cloud":"0","cond_code":"100","cond_txt":"晴","hum":"73","pop":"0","pres":"1016","time":"2017-10-27 16:00","tmp":"15","wind_deg":"162","wind_dir":"东南风","wind_sc":"微风","wind_spd":"6"},{"cloud":"3","cond_code":"100","cond_txt":"晴","hum":"92","pop":"0","pres":"1018","time":"2017-10-27 19:00","tmp":"13","wind_deg":"206","wind_dir":"西南风","wind_sc":"微风","wind_spd":"4"},{"cloud":"19","cond_code":"100","cond_txt":"晴","hum":"96","pop":"0","pres":"1019","time":"2017-10-27 22:00","tmp":"13","wind_deg":"212","wind_dir":"西南风","wind_sc":"微风","wind_spd":"1"}]
         * status : ok
         * update : {"loc":"2017-10-26 23:09","utc":"2017-10-26 15:09"}
         */

        private BasicBean basic;
        private String status;
        private UpdateBean update;
        private List<HourlyBean> hourly;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public List<HourlyBean> getHourly() {
            return hourly;
        }

        public void setHourly(List<HourlyBean> hourly) {
            this.hourly = hourly;
        }

        public static class BasicBean {
            /**
             * cid : CN101010100
             * location : 北京
             * parent_city : 北京
             * admin_area : 北京
             * cnty : 中国
             * lat : 39.90498734
             * lon : 116.40528870
             * tz : 8.0
             */

            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }

            public String getAdmin_area() {
                return admin_area;
            }

            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getTz() {
                return tz;
            }

            public void setTz(String tz) {
                this.tz = tz;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2017-10-26 23:09
             * utc : 2017-10-26 15:09
             */

            private String loc;
            private String utc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getUtc() {
                return utc;
            }

            public void setUtc(String utc) {
                this.utc = utc;
            }
        }

        public static class HourlyBean {
            /**
             * cloud : 8
             * cond_code : 100
             * cond_txt : 晴
             * hum : 84
             * pop : 0
             * pres : 1018
             * time : 2017-10-27 01:00
             * tmp : 8
             * wind_deg : 49
             * wind_dir : 东北风
             * wind_sc : 微风
             * wind_spd : 2
             */

            private String cloud;
            private String cond_code;
            private String cond_txt;
            private String hum;
            private String pop;
            private String pres;
            private String time;
            private String tmp;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }
    }
}
