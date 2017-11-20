package com.daobao.asus.iweather.Bean;

import java.util.List;

/**
 * Created by db on 2017/11/19.
 */

public class AirBean {

    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * air_now_city : {"aqi":"19","co":"0","main":"","no2":"34","o3":"31","pm10":"18","pm25":"8","pub_time":"2017-11-07 22:00","qlty":"优","so2":"2"}
         * air_now_station : [{"air_sta":"万寿西宫","aqi":"19","asid":"CNA1001","co":"0.4","lat":"39.8673","lon":"116.366","main":"-","no2":"37","o3":"29","pm10":"13","pm25":"5","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"定陵","aqi":"21","asid":"CNA1002","co":"0.4","lat":"40.2865","lon":"116.17","main":"-","no2":"7","o3":"66","pm10":"6","pm25":"1","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"东四","aqi":"16","asid":"CNA1003","co":"0.4","lat":"39.9522","lon":"116.434","main":"-","no2":"28","o3":"36","pm10":"16","pm25":"10","pub_time":"2017-11-07 22:00","qlty":"优","so2":"2"},{"air_sta":"天坛","aqi":"22","asid":"CNA1004","co":"0.4","lat":"39.8745","lon":"116.434","main":"-","no2":"38","o3":"29","pm10":"22","pm25":"8","pub_time":"2017-11-07 22:00","qlty":"优","so2":"1"},{"air_sta":"农展馆","aqi":"28","asid":"CNA1005","co":"0.6","lat":"39.9716","lon":"116.473","main":"-","no2":"56","o3":"15","pm10":"20","pm25":"12","pub_time":"2017-11-07 22:00","qlty":"优","so2":"2"},{"air_sta":"官园","aqi":"25","asid":"CNA1006","co":"0.4","lat":"39.9425","lon":"116.361","main":"-","no2":"50","o3":"13","pm10":"15","pm25":"10","pub_time":"2017-11-07 22:00","qlty":"优","so2":"1"},{"air_sta":"海淀区万柳","aqi":"31","asid":"CNA1007","co":"0.4","lat":"39.9934","lon":"116.315","main":"-","no2":"61","o3":"6","pm10":"28","pm25":"14","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"顺义新城","aqi":"16","asid":"CNA1008","co":"0.4","lat":"40.1438","lon":"116.72","main":"-","no2":"12","o3":"51","pm10":"16","pm25":"6","pub_time":"2017-11-07 22:00","qlty":"优","so2":"4"},{"air_sta":"怀柔镇","aqi":"15","asid":"CNA1009","co":"0.3","lat":"40.3937","lon":"116.644","main":"-","no2":"21","o3":"48","pm10":"12","pm25":"5","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"昌平镇","aqi":"15","asid":"CNA1010","co":"0.5","lat":"40.1952","lon":"116.23","main":"-","no2":"20","o3":"48","pm10":"7","pm25":"3","pub_time":"2017-11-07 22:00","qlty":"优","so2":"2"},{"air_sta":"奥体中心","aqi":"24","asid":"CNA1011","co":"0.4","lat":"40.0031","lon":"116.407","main":"-","no2":"48","o3":"21","pm10":"15","pm25":"9","pub_time":"2017-11-07 22:00","qlty":"优","so2":"3"},{"air_sta":"古城","aqi":"32","asid":"CNA1012","co":"0.4","lat":"39.9279","lon":"116.225","main":"-","no2":"36","o3":"20","pm10":"32","pm25":"3","pub_time":"2017-11-07 22:00","qlty":"优","so2":"1"}]
         * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.40528870","tz":"+8.0"}
         * status : ok
         * update : {"loc":"2017-11-07 22:46","utc":"2017-11-07 14:46"}
         */

        private AirNowCityBean air_now_city;
        private BasicBean basic;
        private String status;
        private UpdateBean update;
        private List<AirNowStationBean> air_now_station;

        public AirNowCityBean getAir_now_city() {
            return air_now_city;
        }

        public void setAir_now_city(AirNowCityBean air_now_city) {
            this.air_now_city = air_now_city;
        }

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

        public List<AirNowStationBean> getAir_now_station() {
            return air_now_station;
        }

        public void setAir_now_station(List<AirNowStationBean> air_now_station) {
            this.air_now_station = air_now_station;
        }

        public static class AirNowCityBean {
            /**
             * aqi : 19
             * co : 0
             * main :
             * no2 : 34
             * o3 : 31
             * pm10 : 18
             * pm25 : 8
             * pub_time : 2017-11-07 22:00
             * qlty : 优
             * so2 : 2
             */

            private String aqi;
            private String co;
            private String main;
            private String no2;
            private String o3;
            private String pm10;
            private String pm25;
            private String pub_time;
            private String qlty;
            private String so2;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getPub_time() {
                return pub_time;
            }

            public void setPub_time(String pub_time) {
                this.pub_time = pub_time;
            }

            public String getQlty() {
                return qlty;
            }

            public void setQlty(String qlty) {
                this.qlty = qlty;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }
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
             * tz : +8.0
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
             * loc : 2017-11-07 22:46
             * utc : 2017-11-07 14:46
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

        public static class AirNowStationBean {
            /**
             * air_sta : 万寿西宫
             * aqi : 19
             * asid : CNA1001
             * co : 0.4
             * lat : 39.8673
             * lon : 116.366
             * main : -
             * no2 : 37
             * o3 : 29
             * pm10 : 13
             * pm25 : 5
             * pub_time : 2017-11-07 22:00
             * qlty : 优
             * so2 : 3
             */

            private String air_sta;
            private String aqi;
            private String asid;
            private String co;
            private String lat;
            private String lon;
            private String main;
            private String no2;
            private String o3;
            private String pm10;
            private String pm25;
            private String pub_time;
            private String qlty;
            private String so2;

            public String getAir_sta() {
                return air_sta;
            }

            public void setAir_sta(String air_sta) {
                this.air_sta = air_sta;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getAsid() {
                return asid;
            }

            public void setAsid(String asid) {
                this.asid = asid;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
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

            public String getMain() {
                return main;
            }

            public void setMain(String main) {
                this.main = main;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getPub_time() {
                return pub_time;
            }

            public void setPub_time(String pub_time) {
                this.pub_time = pub_time;
            }

            public String getQlty() {
                return qlty;
            }

            public void setQlty(String qlty) {
                this.qlty = qlty;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }
        }
    }
}
