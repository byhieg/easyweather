package com.weather.byhieg.easyweather.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shiqifeng on 2016/6/28.
 * Mail:byhieg@gmail.com
 */
public class Weather implements Serializable {

    /**
     * city : {"aqi":"77","co":"1","no2":"33","o3":"81","pm10":"58","pm25":"77","qlty":"优","so2":"2"}
     */

    private AqiBean aqi;
    /**
     * city : 北京
     * cnty : 中国
     * id : CN101010100
     * lat : 39.904000
     * lon : 116.391000
     * update : {"loc":"2016-06-28 13:51","utc":"2016-06-28 05:51"}
     */

    private BasicBean basic;
    /**
     * cond : {"code":"101","txt":"多云"}
     * fl : 25
     * hum : 80
     * pcpn : 0
     * pres : 1006
     * tmp : 21
     * vis : 8
     * wind : {"deg":"150","dir":"西风","sc":"3-4","spd":"10"}
     */

    private NowBean now;
    /**
     * alarms : [{}]
     * aqi : {"city":{"aqi":"77","co":"1","no2":"33","o3":"81","pm10":"58","pm25":"77","qlty":"优","so2":"2"}}
     * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.904000","lon":"116.391000","update":{"loc":"2016-06-28 13:51","utc":"2016-06-28 05:51"}}
     * daily_forecast : [{"astro":{"sr":"04:48","ss":"19:47"},"cond":{"code_d":"302","code_n":"302","txt_d":"雷阵雨","txt_n":"雷阵雨"},"date":"2016-06-28","hum":"54","pcpn":"26.0","pop":"98","pres":"1004","tmp":{"max":"25","min":"20"},"vis":"9","wind":{"deg":"202","dir":"无持续风向","sc":"微风","spd":"5"}},{"astro":{"sr":"04:48","ss":"19:47"},"cond":{"code_d":"302","code_n":"104","txt_d":"雷阵雨","txt_n":"阴"},"date":"2016-06-29","hum":"46","pcpn":"0.4","pop":"90","pres":"1003","tmp":{"max":"29","min":"22"},"vis":"10","wind":{"deg":"164","dir":"无持续风向","sc":"微风","spd":"2"}},{"astro":{"sr":"04:49","ss":"19:47"},"cond":{"code_d":"302","code_n":"104","txt_d":"雷阵雨","txt_n":"阴"},"date":"2016-06-30","hum":"23","pcpn":"2.5","pop":"74","pres":"1001","tmp":{"max":"32","min":"23"},"vis":"10","wind":{"deg":"116","dir":"无持续风向","sc":"微风","spd":"6"}},{"astro":{"sr":"04:49","ss":"19:46"},"cond":{"code_d":"302","code_n":"104","txt_d":"雷阵雨","txt_n":"阴"},"date":"2016-07-01","hum":"29","pcpn":"2.4","pop":"67","pres":"1001","tmp":{"max":"28","min":"22"},"vis":"10","wind":{"deg":"70","dir":"无持续风向","sc":"微风","spd":"5"}},{"astro":{"sr":"04:50","ss":"19:46"},"cond":{"code_d":"101","code_n":"302","txt_d":"多云","txt_n":"雷阵雨"},"date":"2016-07-02","hum":"31","pcpn":"0.0","pop":"50","pres":"1005","tmp":{"max":"31","min":"22"},"vis":"10","wind":{"deg":"169","dir":"无持续风向","sc":"微风","spd":"3"}},{"astro":{"sr":"04:50","ss":"19:46"},"cond":{"code_d":"302","code_n":"101","txt_d":"雷阵雨","txt_n":"多云"},"date":"2016-07-03","hum":"21","pcpn":"0.0","pop":"8","pres":"1005","tmp":{"max":"27","min":"21"},"vis":"10","wind":{"deg":"164","dir":"无持续风向","sc":"微风","spd":"10"}},{"astro":{"sr":"04:51","ss":"19:46"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2016-07-04","hum":"18","pcpn":"0.0","pop":"40","pres":"1005","tmp":{"max":"28","min":"22"},"vis":"10","wind":{"deg":"193","dir":"无持续风向","sc":"微风","spd":"2"}},{"astro":{"sr":"04:52","ss":"19:46"},"cond":{"code_d":"100","code_n":"305","txt_d":"晴","txt_n":"小雨"},"date":"2016-07-05","hum":"17","pcpn":"0.0","pop":"39","pres":"1008","tmp":{"max":"38","min":"23"},"vis":"10","wind":{"deg":"182","dir":"南风","sc":"微风","spd":"11"}},{"astro":{"sr":"04:52","ss":"19:46"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-07-06","hum":"21","pcpn":"0.0","pop":"24","pres":"1007","tmp":{"max":"41","min":"22"},"vis":"10","wind":{"deg":"162","dir":"东南风","sc":"微风","spd":"11"}},{"astro":{"sr":"04:53","ss":"19:46"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-07-07","hum":"25","pcpn":"0.0","pop":"0","pres":"1005","tmp":{"max":"42","min":"29"},"vis":"10","wind":{"deg":"176","dir":"南风","sc":"微风","spd":"11"}}]
     * hourly_forecast : [{"date":"2016-06-28 14:00","hum":"54","pop":"8","pres":"1004","tmp":"29","wind":{"deg":"202","dir":"西南风","sc":"微风","spd":"12"}},{"date":"2016-06-28 15:00","hum":"51","pop":"5","pres":"1004","tmp":"30","wind":{"deg":"209","dir":"西南风","sc":"微风","spd":"12"}},{"date":"2016-06-28 16:00","hum":"49","pop":"3","pres":"1004","tmp":"31","wind":{"deg":"216","dir":"西南风","sc":"微风","spd":"12"}},{"date":"2016-06-28 17:00","hum":"47","pop":"0","pres":"1004","tmp":"32","wind":{"deg":"222","dir":"西南风","sc":"微风","spd":"11"}},{"date":"2016-06-28 18:00","hum":"55","pop":"31","pres":"1004","tmp":"31","wind":{"deg":"159","dir":"东南风","sc":"微风","spd":"10"}},{"date":"2016-06-28 19:00","hum":"64","pop":"61","pres":"1004","tmp":"30","wind":{"deg":"96","dir":"东风","sc":"微风","spd":"9"}},{"date":"2016-06-28 20:00","hum":"72","pop":"92","pres":"1004","tmp":"29","wind":{"deg":"33","dir":"东北风","sc":"微风","spd":"7"}},{"date":"2016-06-28 21:00","hum":"77","pop":"91","pres":"1004","tmp":"28","wind":{"deg":"40","dir":"东北风","sc":"微风","spd":"6"}},{"date":"2016-06-28 22:00","hum":"82","pop":"91","pres":"1004","tmp":"27","wind":{"deg":"46","dir":"东北风","sc":"微风","spd":"5"}},{"date":"2016-06-28 23:00","hum":"87","pop":"91","pres":"1005","tmp":"27","wind":{"deg":"52","dir":"东北风","sc":"微风","spd":"5"}},{"date":"2016-06-29 00:00","hum":"88","pop":"90","pres":"1005","tmp":"25","wind":{"deg":"47","dir":"东北风","sc":"微风","spd":"5"}},{"date":"2016-06-29 01:00","hum":"89","pop":"90","pres":"1004","tmp":"23","wind":{"deg":"43","dir":"东北风","sc":"微风","spd":"5"}},{"date":"2016-06-29 02:00","hum":"90","pop":"90","pres":"1004","tmp":"22","wind":{"deg":"38","dir":"东北风","sc":"微风","spd":"5"}},{"date":"2016-06-29 03:00","hum":"91","pop":"76","pres":"1004","tmp":"21","wind":{"deg":"32","dir":"东北风","sc":"微风","spd":"6"}},{"date":"2016-06-29 04:00","hum":"91","pop":"63","pres":"1004","tmp":"21","wind":{"deg":"25","dir":"东北风","sc":"微风","spd":"6"}},{"date":"2016-06-29 05:00","hum":"92","pop":"49","pres":"1004","tmp":"21","wind":{"deg":"19","dir":"东北风","sc":"微风","spd":"7"}},{"date":"2016-06-29 06:00","hum":"89","pop":"33","pres":"1004","tmp":"22","wind":{"deg":"27","dir":"东北风","sc":"微风","spd":"6"}},{"date":"2016-06-29 07:00","hum":"85","pop":"18","pres":"1004","tmp":"23","wind":{"deg":"35","dir":"东北风","sc":"微风","spd":"6"}},{"date":"2016-06-29 08:00","hum":"82","pop":"2","pres":"1005","tmp":"24","wind":{"deg":"42","dir":"东北风","sc":"微风","spd":"6"}},{"date":"2016-06-29 09:00","hum":"75","pop":"2","pres":"1004","tmp":"26","wind":{"deg":"67","dir":"东北风","sc":"微风","spd":"7"}},{"date":"2016-06-29 10:00","hum":"68","pop":"1","pres":"1004","tmp":"29","wind":{"deg":"91","dir":"东风","sc":"微风","spd":"8"}},{"date":"2016-06-29 11:00","hum":"61","pop":"1","pres":"1004","tmp":"31","wind":{"deg":"115","dir":"东南风","sc":"微风","spd":"9"}},{"date":"2016-06-29 12:00","hum":"56","pop":"1","pres":"1004","tmp":"33","wind":{"deg":"131","dir":"东南风","sc":"微风","spd":"10"}},{"date":"2016-06-29 13:00","hum":"51","pop":"0","pres":"1003","tmp":"34","wind":{"deg":"148","dir":"东南风","sc":"微风","spd":"11"}}]
     * now : {"cond":{"code":"101","txt":"多云"},"fl":"25","hum":"80","pcpn":"0","pres":"1006","tmp":"21","vis":"8","wind":{"deg":"150","dir":"西风","sc":"3-4","spd":"10"}}
     * status : ok
     * suggestion : {"comf":{"brf":"较舒适","txt":"白天有降雨，但会使人们感觉有些热，不过大部分人仍会有比较舒适的感觉。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},"flu":{"brf":"较易发","txt":"相对今天出现了较大幅度降温，较易发生感冒，体质较弱的朋友请注意适当防护。"},"sport":{"brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。"},"trav":{"brf":"一般","txt":"温度适宜，风不大，有降水，旅游指数一般，外出请尽量避开降雨时段，若外出，请注意防雷防雨。"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}}
     */

    private String status;
    /**
     * comf : {"brf":"较舒适","txt":"白天有降雨，但会使人们感觉有些热，不过大部分人仍会有比较舒适的感觉。"}
     * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
     * drsg : {"brf":"舒适","txt":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"}
     * flu : {"brf":"较易发","txt":"相对今天出现了较大幅度降温，较易发生感冒，体质较弱的朋友请注意适当防护。"}
     * sport : {"brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。"}
     * trav : {"brf":"一般","txt":"温度适宜，风不大，有降水，旅游指数一般，外出请尽量避开降雨时段，若外出，请注意防雷防雨。"}
     * uv : {"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
     */

    private SuggestionBean suggestion;
    private List<AlarmsBean> alarms;
    /**
     * astro : {"sr":"04:48","ss":"19:47"}
     * cond : {"code_d":"302","code_n":"302","txt_d":"雷阵雨","txt_n":"雷阵雨"}
     * date : 2016-06-28
     * hum : 54
     * pcpn : 26.0
     * pop : 98
     * pres : 1004
     * tmp : {"max":"25","min":"20"}
     * vis : 9
     * wind : {"deg":"202","dir":"无持续风向","sc":"微风","spd":"5"}
     */


    private List<DailyForecastBean> daily_forecast;
    /**
     * date : 2016-06-28 14:00
     * hum : 54
     * pop : 8
     * pres : 1004
     * tmp : 29
     * wind : {"deg":"202","dir":"西南风","sc":"微风","spd":"12"}
     */

    private List<HourlyForecastBean> hourly_forecast;

    public AqiBean getAqi() {
        return aqi;
    }

    public void setAqi(AqiBean aqi) {
        this.aqi = aqi;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SuggestionBean getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(SuggestionBean suggestion) {
        this.suggestion = suggestion;
    }

    public List<AlarmsBean> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<AlarmsBean> alarms) {
        this.alarms = alarms;
    }

    public List<DailyForecastBean> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<HourlyForecastBean> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public static class AqiBean implements Serializable{
        /**
         * aqi : 77
         * co : 1
         * no2 : 33
         * o3 : 81
         * pm10 : 58
         * pm25 : 77
         * qlty : 优
         * so2 : 2
         */

        private CityBean city;

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public static class CityBean implements Serializable{
            private String aqi;
            private String co;
            private String no2;
            private String o3;
            private String pm10;
            private String pm25;
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

    public static class BasicBean implements Serializable{
        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        /**
         * loc : 2016-06-28 13:51
         * utc : 2016-06-28 05:51
         */

        private UpdateBean update;

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

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public static class UpdateBean implements Serializable{
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
    }

    public static class NowBean implements Serializable{
        /**
         * code : 101
         * txt : 多云
         */

        private CondBean cond;
        private String fl;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String vis;
        /**
         * deg : 150
         * dir : 西风
         * sc : 3-4
         * spd : 10
         */

        private WindBean wind;

        public CondBean getCond() {
            return cond;
        }

        public void setCond(CondBean cond) {
            this.cond = cond;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class CondBean implements Serializable{
            private String code;
            private String txt;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class WindBean implements Serializable{
            private String deg;
            private String dir;
            private String sc;
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }

    public static class SuggestionBean implements Serializable{
        /**
         * brf : 较舒适
         * txt : 白天有降雨，但会使人们感觉有些热，不过大部分人仍会有比较舒适的感觉。
         */

        private ComfBean comf;
        /**
         * brf : 不宜
         * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
         */

        private CwBean cw;
        /**
         * brf : 舒适
         * txt : 建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。
         */

        private DrsgBean drsg;
        /**
         * brf : 较易发
         * txt : 相对今天出现了较大幅度降温，较易发生感冒，体质较弱的朋友请注意适当防护。
         */

        private FluBean flu;
        /**
         * brf : 较不宜
         * txt : 有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。
         */

        private SportBean sport;
        /**
         * brf : 一般
         * txt : 温度适宜，风不大，有降水，旅游指数一般，外出请尽量避开降雨时段，若外出，请注意防雷防雨。
         */

        private TravBean trav;
        /**
         * brf : 弱
         * txt : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
         */

        private UvBean uv;

        public ComfBean getComf() {
            return comf;
        }

        public void setComf(ComfBean comf) {
            this.comf = comf;
        }

        public CwBean getCw() {
            return cw;
        }

        public void setCw(CwBean cw) {
            this.cw = cw;
        }

        public DrsgBean getDrsg() {
            return drsg;
        }

        public void setDrsg(DrsgBean drsg) {
            this.drsg = drsg;
        }

        public FluBean getFlu() {
            return flu;
        }

        public void setFlu(FluBean flu) {
            this.flu = flu;
        }

        public SportBean getSport() {
            return sport;
        }

        public void setSport(SportBean sport) {
            this.sport = sport;
        }

        public TravBean getTrav() {
            return trav;
        }

        public void setTrav(TravBean trav) {
            this.trav = trav;
        }

        public UvBean getUv() {
            return uv;
        }

        public void setUv(UvBean uv) {
            this.uv = uv;
        }

        public static class ComfBean implements Serializable{
            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class CwBean implements Serializable{
            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class DrsgBean implements Serializable{
            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class FluBean implements Serializable{
            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class SportBean implements Serializable{
            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class TravBean implements Serializable{
            private String brf;
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class UvBean implements Serializable{
        }
    }

    public static class AlarmsBean implements Serializable{
    }

    public static class DailyForecastBean implements Serializable{
        /**
         * sr : 04:48
         * ss : 19:47
         */

        private AstroBean astro;
        /**
         * code_d : 302
         * code_n : 302
         * txt_d : 雷阵雨
         * txt_n : 雷阵雨
         */

        private CondBean cond;
        private String date;
        private String hum;
        private String pcpn;
        private String pop;
        private String pres;
        /**
         * max : 25
         * min : 20
         */

        private TmpBean tmp;
        private String vis;
        /**
         * deg : 202
         * dir : 无持续风向
         * sc : 微风
         * spd : 5
         */

        private WindBean wind;

        public AstroBean getAstro() {
            return astro;
        }

        public void setAstro(AstroBean astro) {
            this.astro = astro;
        }

        public CondBean getCond() {
            return cond;
        }

        public void setCond(CondBean cond) {
            this.cond = cond;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
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

        public TmpBean getTmp() {
            return tmp;
        }

        public void setTmp(TmpBean tmp) {
            this.tmp = tmp;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class AstroBean implements Serializable{
            private String sr;
            private String ss;

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }
        }

        public static class CondBean implements Serializable{
            private String code_d;
            private String code_n;
            private String txt_d;
            private String txt_n;

            public String getCode_d() {
                return code_d;
            }

            public void setCode_d(String code_d) {
                this.code_d = code_d;
            }

            public String getCode_n() {
                return code_n;
            }

            public void setCode_n(String code_n) {
                this.code_n = code_n;
            }

            public String getTxt_d() {
                return txt_d;
            }

            public void setTxt_d(String txt_d) {
                this.txt_d = txt_d;
            }

            public String getTxt_n() {
                return txt_n;
            }

            public void setTxt_n(String txt_n) {
                this.txt_n = txt_n;
            }
        }

        public static class TmpBean implements Serializable{
            private String max;
            private String min;

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }
        }

        public static class WindBean implements Serializable{
            private String deg;
            private String dir;
            private String sc;
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }

    public static class HourlyForecastBean implements Serializable{
        private String date;
        private String hum;
        private String pop;
        private String pres;
        private String tmp;
        /**
         * deg : 202
         * dir : 西南风
         * sc : 微风
         * spd : 12
         */

        private WindBean wind;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public static class WindBean implements Serializable{
            private String deg;
            private String dir;
            private String sc;
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }
}
