package com.weather.byhieg.easyweather.citymanage.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weather.byhieg.easyweather.data.bean.CityManageContext;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.tools.WeatherColor;

import java.util.List;

/**
 * Created by byhieg on 16-10-7.
 * Mail byhieg@gmail.com
 */

public class CityManageAdapter extends BaseAdapter{

    private List<CityManageContext> cities;
    private LayoutInflater mInflater = null;

    public CityManageAdapter(List<CityManageContext> cityManageContexts, Context context) {
        this.cities = cityManageContexts;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityManageViewHolder holder = new CityManageViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_city_manage, parent, false);

            holder.cityName = (TextView) convertView.findViewById(R.id.city_name);
            holder.cond = (TextView) convertView.findViewById(R.id.weather_cond);
            holder.tmp = (TextView) convertView.findViewById(R.id.weather);
            holder.time = (TextView) convertView.findViewById(R.id.updateTime);
            holder.hum = (TextView) convertView.findViewById(R.id.wet);
            holder.itemCard = (LinearLayout)convertView.findViewById(R.id.item_card);
            convertView.setTag(holder);
        }else{
            holder = (CityManageViewHolder) convertView.getTag();
        }
        holder.cityName.setText(cities.get(position).getCityName());
        String weatherCode = cities.get(position).getCode();
        int radius = 30;
        float[] outerR = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(ContextCompat.getColor(MyApplication.getAppContext(), WeatherColor.getWeatherColor(weatherCode)));
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        holder.itemCard.setBackground(shapeDrawable);
        holder.cond.setText("天气：" + cities.get(position).getCond());
        holder.tmp.setText("气温：" + cities.get(position).getTmp() + "°");
        holder.time.setText(cities.get(position).getTime());
        holder.hum.setText("湿度: " + cities.get(position).getHum() + "%");
        return convertView;
    }

    class CityManageViewHolder {
        private TextView cityName;
        private TextView cond;
        private TextView tmp;
        private TextView hum;
        private TextView time;
        private LinearLayout itemCard;
    }
}
