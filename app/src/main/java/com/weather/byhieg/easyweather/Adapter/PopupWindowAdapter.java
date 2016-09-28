package com.weather.byhieg.easyweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weather.byhieg.easyweather.Bean.HoursWeather;
import com.weather.byhieg.easyweather.R;

import java.util.List;

/**
 * Created by byhieg on 16-9-26.
 * Mail byhieg@gmail.com
 */

public class PopupWindowAdapter extends BaseAdapter{

    private List<HoursWeather> hoursWeathers;
    private LayoutInflater mInflater = null;


    public PopupWindowAdapter(List<HoursWeather> hoursWeathers, Context context) {
        this.hoursWeathers = hoursWeathers;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return hoursWeathers.size();
    }

    @Override
    public Object getItem(int position) {
        return hoursWeathers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_hours_future, parent,false);
            holder.tmp = (TextView)convertView.findViewById(R.id.weather_tmp);
            holder.wind = (TextView)convertView.findViewById(R.id.wind_hours);
            holder.pop = (TextView)convertView.findViewById(R.id.weather_cond);
            holder.update = (TextView) convertView.findViewById(R.id.update_time_hours);
            holder.futureDes = (TextView) convertView.findViewById(R.id.future_des);
            holder.more = (TextView) convertView.findViewById(R.id.more);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tmp.setText(hoursWeathers.get(position).getTmp());
        holder.wind.setText(hoursWeathers.get(position).getWind());
        holder.pop.setText(hoursWeathers.get(position).getPop());
        holder.update.setText(hoursWeathers.get(position).getUpdate());
        holder.futureDes.setText("今天每3小时天气速报");
        holder.more.setVisibility(View.GONE);
        return convertView;
    }

    class ViewHolder{
        public TextView futureDes;
        public TextView tmp;
        public TextView wind;
        public TextView pop;
        public TextView update;
        public TextView more;
    }
}
