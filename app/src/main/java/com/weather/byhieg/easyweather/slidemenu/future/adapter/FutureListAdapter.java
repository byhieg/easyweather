package com.weather.byhieg.easyweather.slidemenu.future.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.data.bean.FutureContext;
import com.weather.byhieg.easyweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byhieg on 16-10-2.
 * Mail byhieg@gmail.com
 */

public class FutureListAdapter extends RecyclerView.Adapter {

    private List<FutureContext> futureList;


    public FutureListAdapter(List<FutureContext> futureList) {
        this.futureList = futureList;
        Logger.d(futureList.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_future, parent,false);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new FutureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FutureContext context = futureList.get(position);
        ((FutureViewHolder)holder).cond.setText(context.getCond());
        ((FutureViewHolder)holder).tmp.setText(context.getTmp());
        ((FutureViewHolder)holder).wind.setText(context.getWind() + "km/h");
        ((FutureViewHolder)holder).vis.setText(context.getVis() + "km");
        ((FutureViewHolder)holder).pop.setText(context.getPop() + "%");
        ((FutureViewHolder)holder).sunrise.setText(context.getSunrise());
        ((FutureViewHolder)holder).sunset.setText(context.getSunset());
        ((FutureViewHolder)holder).pcpn.setText(context.getPcpn() + "mm");
        ((FutureViewHolder)holder).hum.setText(context.getHum() + "%");
        ((FutureViewHolder)holder).pres.setText(context.getPres() + "帕");
        ((FutureViewHolder)holder).des.setText(context.getDes());
        ((FutureViewHolder)holder).time.setText(context.getTime());
    }

    @Override
    public int getItemCount() {
        return futureList.size();
    }

    class FutureViewHolder extends RecyclerView.ViewHolder {

        TextView cond;
        TextView tmp;
        TextView wind;
        TextView vis;
        TextView pop;
        TextView sunrise;
        TextView sunset;
        TextView pcpn;
        TextView hum;
        TextView pres;
        TextView des;
        TextView time;

        FutureViewHolder(View itemView) {
            super(itemView);
            cond = (TextView) itemView.findViewById(R.id.cond);
            tmp = (TextView) itemView.findViewById(R.id.tmp);
            wind = (TextView) itemView.findViewById(R.id.wind);
            vis = (TextView) itemView.findViewById(R.id.vis);
            pop = (TextView) itemView.findViewById(R.id.pop);
            sunrise = (TextView) itemView.findViewById(R.id.sunrise);
            sunset = (TextView) itemView.findViewById(R.id.sunset);
            pcpn = (TextView) itemView.findViewById(R.id.pcpn);
            hum = (TextView) itemView.findViewById(R.id.hum);
            pres = (TextView) itemView.findViewById(R.id.pres);
            des = (TextView) itemView.findViewById(R.id.des);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
