package com.weather.byhieg.easyweather.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.weather.byhieg.easyweather.data.bean.CityContext;
import com.weather.byhieg.easyweather.R;

import java.util.List;

/**
 * Created by byhieg on 16-9-15.
 * Mail byhieg@gmail.com
 */
public class CityListAdapter extends BaseAdapter{

    private List<CityContext> cities;
    private LayoutInflater mInflater = null;

    public CityListAdapter(List<CityContext> cityContexts, Context context) {
        this.cities = cityContexts;
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
        CityViewHolder viewHolder = new CityViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_city, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.city_name);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (CityViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(cities.get(position).getCityName());
        return convertView;
    }


    class CityViewHolder {

        public TextView name;

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

    }
}
