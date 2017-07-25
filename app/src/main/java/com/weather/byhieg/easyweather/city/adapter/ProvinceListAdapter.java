package com.weather.byhieg.easyweather.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weather.byhieg.easyweather.data.bean.ProvinceContext;
import com.weather.byhieg.easyweather.R;

import java.util.List;

/**
 * Created by byhieg on 16-9-15.
 * Mail byhieg@gmail.com
 */
public class ProvinceListAdapter extends BaseAdapter{

    private List<ProvinceContext> provinces;

    private LayoutInflater mInflater = null;

    public ProvinceListAdapter(List<ProvinceContext> provinces,Context context) {
        this.provinces = provinces;
        this.mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return provinces.size();
    }

    @Override
    public Object getItem(int position) {
        return provinces.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProvinceViewHolder viewHolder = new ProvinceViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_province, parent,false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.province_name);
            viewHolder.arrow = (ImageView) convertView.findViewById(R.id.enter);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ProvinceViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(provinces.get(position).getName());
        viewHolder.arrow.setImageResource(provinces.get(position).getImage());
        return convertView;
    }

    class ProvinceViewHolder {

        public TextView name;
        public ImageView arrow;

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public ImageView getArrow() {
            return arrow;
        }

        public void setArrow(ImageView arrow) {
            this.arrow = arrow;
        }
    }

}
