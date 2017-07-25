package com.weather.byhieg.easyweather.slidemenu.future;

import com.weather.byhieg.easyweather.tools.DateUtil;
import com.weather.byhieg.easyweather.data.bean.FutureContext;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.source.WeatherRepository;
import com.weather.byhieg.easyweather.tools.WeatherJsonConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byhieg on 17/5/30.
 * Contact with byhieg@gmail.com
 */

public class FuturePresenter implements FutureContract.Presenter {


    private WeatherRepository mWeatherRepository;
    private FutureContract.View mView;


    public FuturePresenter(FutureContract.View view) {
        mView = view;
        mWeatherRepository = WeatherRepository.getInstance();
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        loadData();
    }

    @Override
    public void loadData() {
        List<FutureContext> lists = new ArrayList<>();
        HWeather weather = mWeatherRepository.getLocalWeather(mWeatherRepository.getShowCity());
        String[] weeks = new String[0];
        try {
            weeks = DateUtil.
                    getNextWeek(new SimpleDateFormat("yyyy-MM-dd").
                            parse(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(0).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < WeatherJsonConverter.getWeather(weather).getDaily_forecast().size(); i++) {
            FutureContext fc = new FutureContext();
            fc.setCond(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getCond().getTxt_d());
            fc.setHum(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getHum());
            fc.setTmp(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getTmp().getMax() + "°" + "/" +
                    WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getTmp().getMin() + "°");

            fc.setWind(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getWind().getSpd());
            fc.setVis(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getVis());
            fc.setPop(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getPop());
            fc.setSunrise(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getAstro().getSr());
            fc.setSunset(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getAstro().getSs());
            fc.setPcpn(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getPcpn());
            fc.setPres(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getPres());
            fc.setDes(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getWind().getDir());
            fc.setTime(weeks[i]);
            lists.add(fc);
        }
        mView.showListView(lists);
    }
}
