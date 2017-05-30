package com.weather.byhieg.easyweather.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byhieg on 17/5/23.
 * Contact with byhieg@gmail.com
 */

public class Weather  implements Serializable{
    /**
     * aqi : {"city":{"aqi":"61","co":"1","no2":"75","o3":"107","pm10":"72","pm25":"33","qlty":"良","so2":"10"}}
     * basic : {"city":"成都","cnty":"中国","id":"CN101270101","lat":"30.65946198","lon":"104.06573486","update":{"loc":"2017-05-13 21:53","utc":"2017-05-13 13:53"}}
     * daily_forecast : [{"astro":{"mr":"21:52","ms":"07:54","sr":"06:10","ss":"19:49"},"cond":{"code_d":"100","code_n":"305","txt_d":"晴","txt_n":"小雨"},"date":"2017-05-13","hum":"54","pcpn":"0.1","pop":"64","pres":"1008","tmp":{"max":"32","min":"17"},"uv":"11","vis":"16","wind":{"deg":"101","dir":"无持续风向","sc":"3-4","spd":"11"}},{"astro":{"mr":"22:41","ms":"08:38","sr":"06:10","ss":"19:50"},"cond":{"code_d":"101","code_n":"305","txt_d":"多云","txt_n":"小雨"},"date":"2017-05-14","hum":"63","pcpn":"3.8","pop":"99","pres":"1011","tmp":{"max":"29","min":"17"},"uv":"9","vis":"18","wind":{"deg":"173","dir":"南风","sc":"微风","spd":"8"}},{"astro":{"mr":"23:29","ms":"09:25","sr":"06:09","ss":"19:51"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-05-15","hum":"52","pcpn":"0.3","pop":"76","pres":"1014","tmp":{"max":"28","min":"17"},"uv":"11","vis":"17","wind":{"deg":"177","dir":"南风","sc":"微风","spd":"8"}},{"astro":{"mr":"null","ms":"10:15","sr":"06:08","ss":"19:51"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2017-05-16","hum":"49","pcpn":"0.0","pop":"80","pres":"1014","tmp":{"max":"29","min":"17"},"uv":"11","vis":"18","wind":{"deg":"295","dir":"南风","sc":"微风","spd":"8"}},{"astro":{"mr":"00:13","ms":"11:07","sr":"06:08","ss":"19:52"},"cond":{"code_d":"305","code_n":"104","txt_d":"小雨","txt_n":"阴"},"date":"2017-05-17","hum":"55","pcpn":"0.0","pop":"0","pres":"1009","tmp":{"max":"31","min":"18"},"uv":"9","vis":"18","wind":{"deg":"215","dir":"南风","sc":"微风","spd":"3"}},{"astro":{"mr":"00:56","ms":"12:02","sr":"06:07","ss":"19:52"},"cond":{"code_d":"104","code_n":"104","txt_d":"阴","txt_n":"阴"},"date":"2017-05-18","hum":"56","pcpn":"0.0","pop":"55","pres":"1008","tmp":{"max":"29","min":"19"},"uv":"6","vis":"16","wind":{"deg":"146","dir":"南风","sc":"微风","spd":"4"}},{"astro":{"mr":"01:37","ms":"12:58","sr":"06:07","ss":"19:53"},"cond":{"code_d":"101","code_n":"104","txt_d":"多云","txt_n":"阴"},"date":"2017-05-19","hum":"59","pcpn":"4.4","pop":"18","pres":"1008","tmp":{"max":"29","min":"19"},"uv":"6","vis":"18","wind":{"deg":"53","dir":"南风","sc":"微风","spd":"5"}}]
     * hourly_forecast : [{"cond":{"code":"100","txt":"晴"},"date":"2017-05-13 22:00","hum":"50","pop":"43","pres":"1008","tmp":"26","wind":{"deg":"30","dir":"东北风","sc":"微风","spd":"9"}}]
     * now : {"cond":{"code":"104","txt":"阴"},"fl":"28","hum":"48","pcpn":"0","pres":"1006","tmp":"26","vis":"8","wind":{"deg":"82","dir":"东北风","sc":"微风","spd":"10"}}
     * status : ok
     * suggestion : {"air":{"brf":"较差","txt":"气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。"},"comf":{"brf":"较不舒适","txt":"白天天气晴好，明媚的阳光在给您带来好心情的同时，也会使您感到有些热，不很舒适。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"炎热","txt":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"},"flu":{"brf":"少发","txt":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"},"sport":{"brf":"较不宜","txt":"天气较好，无雨水困扰，但考虑气温很高，请注意适当减少运动时间并降低运动强度，运动后及时补充水分。"},"trav":{"brf":"适宜","txt":"天气较好，但丝毫不会影响您的心情。微风，虽天气稍热，却仍适宜旅游，不要错过机会呦！"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}}
     */

    private AqiBean aqi;
    private BasicBean basic;
    private NowBean now;
    private String status;
    private SuggestionBean suggestion;
    private List<DailyForecastBean> daily_forecast;
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
         * city : {"aqi":"61","co":"1","no2":"75","o3":"107","pm10":"72","pm25":"33","qlty":"良","so2":"10"}
         */

        private CityBean city;

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public static class CityBean implements Serializable {
            /**
             * aqi : 61
             * co : 1
             * no2 : 75
             * o3 : 107
             * pm10 : 72
             * pm25 : 33
             * qlty : 良
             * so2 : 10
             */

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
        /**
         * city : 成都
         * cnty : 中国
         * id : CN101270101
         * lat : 30.65946198
         * lon : 104.06573486
         * update : {"loc":"2017-05-13 21:53","utc":"2017-05-13 13:53"}
         */

        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
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
            /**
             * loc : 2017-05-13 21:53
             * utc : 2017-05-13 13:53
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
    }

    public static class NowBean implements Serializable {
        /**
         * cond : {"code":"104","txt":"阴"}
         * fl : 28
         * hum : 48
         * pcpn : 0
         * pres : 1006
         * tmp : 26
         * vis : 8
         * wind : {"deg":"82","dir":"东北风","sc":"微风","spd":"10"}
         */

        private CondBean cond;
        private String fl;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String vis;
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
            /**
             * code : 104
             * txt : 阴
             */

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
            /**
             * deg : 82
             * dir : 东北风
             * sc : 微风
             * spd : 10
             */

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
         * air : {"brf":"较差","txt":"气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。"}
         * comf : {"brf":"较不舒适","txt":"白天天气晴好，明媚的阳光在给您带来好心情的同时，也会使您感到有些热，不很舒适。"}
         * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
         * drsg : {"brf":"炎热","txt":"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。"}
         * flu : {"brf":"少发","txt":"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。"}
         * sport : {"brf":"较不宜","txt":"天气较好，无雨水困扰，但考虑气温很高，请注意适当减少运动时间并降低运动强度，运动后及时补充水分。"}
         * trav : {"brf":"适宜","txt":"天气较好，但丝毫不会影响您的心情。微风，虽天气稍热，却仍适宜旅游，不要错过机会呦！"}
         * uv : {"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
         */

        private AirBean air;
        private ComfBean comf;
        private CwBean cw;
        private DrsgBean drsg;
        private FluBean flu;
        private SportBean sport;
        private TravBean trav;
        private UvBean uv;

        public AirBean getAir() {
            return air;
        }

        public void setAir(AirBean air) {
            this.air = air;
        }

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

        public static class AirBean implements Serializable{
            /**
             * brf : 较差
             * txt : 气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。
             */

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

        public static class ComfBean implements Serializable{
            /**
             * brf : 较不舒适
             * txt : 白天天气晴好，明媚的阳光在给您带来好心情的同时，也会使您感到有些热，不很舒适。
             */

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
            /**
             * brf : 不宜
             * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
             */

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
            /**
             * brf : 炎热
             * txt : 天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。
             */

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
            /**
             * brf : 少发
             * txt : 各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。
             */

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

        public static class SportBean implements Serializable {
            /**
             * brf : 较不宜
             * txt : 天气较好，无雨水困扰，但考虑气温很高，请注意适当减少运动时间并降低运动强度，运动后及时补充水分。
             */

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

        public static class TravBean implements Serializable {
            /**
             * brf : 适宜
             * txt : 天气较好，但丝毫不会影响您的心情。微风，虽天气稍热，却仍适宜旅游，不要错过机会呦！
             */

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
            /**
             * brf : 弱
             * txt : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
             */

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
    }

    public static class DailyForecastBean implements Serializable {
        /**
         * astro : {"mr":"21:52","ms":"07:54","sr":"06:10","ss":"19:49"}
         * cond : {"code_d":"100","code_n":"305","txt_d":"晴","txt_n":"小雨"}
         * date : 2017-05-13
         * hum : 54
         * pcpn : 0.1
         * pop : 64
         * pres : 1008
         * tmp : {"max":"32","min":"17"}
         * uv : 11
         * vis : 16
         * wind : {"deg":"101","dir":"无持续风向","sc":"3-4","spd":"11"}
         */

        private AstroBean astro;
        private CondBeanX cond;
        private String date;
        private String hum;
        private String pcpn;
        private String pop;
        private String pres;
        private TmpBean tmp;
        private String uv;
        private String vis;
        private WindBeanX wind;

        public AstroBean getAstro() {
            return astro;
        }

        public void setAstro(AstroBean astro) {
            this.astro = astro;
        }

        public CondBeanX getCond() {
            return cond;
        }

        public void setCond(CondBeanX cond) {
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

        public String getUv() {
            return uv;
        }

        public void setUv(String uv) {
            this.uv = uv;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public WindBeanX getWind() {
            return wind;
        }

        public void setWind(WindBeanX wind) {
            this.wind = wind;
        }

        public static class AstroBean implements Serializable{
            /**
             * mr : 21:52
             * ms : 07:54
             * sr : 06:10
             * ss : 19:49
             */

            private String mr;
            private String ms;
            private String sr;
            private String ss;

            public String getMr() {
                return mr;
            }

            public void setMr(String mr) {
                this.mr = mr;
            }

            public String getMs() {
                return ms;
            }

            public void setMs(String ms) {
                this.ms = ms;
            }

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

        public static class CondBeanX implements Serializable{
            /**
             * code_d : 100
             * code_n : 305
             * txt_d : 晴
             * txt_n : 小雨
             */

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
            /**
             * max : 32
             * min : 17
             */

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

        public static class WindBeanX implements Serializable{
            /**
             * deg : 101
             * dir : 无持续风向
             * sc : 3-4
             * spd : 11
             */

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
        /**
         * cond : {"code":"100","txt":"晴"}
         * date : 2017-05-13 22:00
         * hum : 50
         * pop : 43
         * pres : 1008
         * tmp : 26
         * wind : {"deg":"30","dir":"东北风","sc":"微风","spd":"9"}
         */

        private CondBeanXX cond;
        private String date;
        private String hum;
        private String pop;
        private String pres;
        private String tmp;
        private WindBeanXX wind;

        public CondBeanXX getCond() {
            return cond;
        }

        public void setCond(CondBeanXX cond) {
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

        public WindBeanXX getWind() {
            return wind;
        }

        public void setWind(WindBeanXX wind) {
            this.wind = wind;
        }

        public static class CondBeanXX implements Serializable{
            /**
             * code : 100
             * txt : 晴
             */

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

        public static class WindBeanXX implements Serializable{
            /**
             * deg : 30
             * dir : 东北风
             * sc : 微风
             * spd : 9
             */

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
