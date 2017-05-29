package com.weather.byhieg.easyweather.data.bean;

import java.util.List;

/**
 * Created by byhieg on 16-10-7.
 * Mail byhieg@gmail.com
 */

public class UrlViewSpot {



    private String status;
    /**
     * city : 青铜峡旅游区
     * cnty : 中国
     * id : CN10117030601A
     * lat : 105.99
     * lon : 37.88
     */

    private List<CityInfoBean> city_info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CityInfoBean> getCity_info() {
        return city_info;
    }

    public void setCity_info(List<CityInfoBean> city_info) {
        this.city_info = city_info;
    }

    public static class CityInfoBean {
        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
