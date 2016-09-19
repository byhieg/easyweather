package com.weather.byhieg.easyweather.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weather.byhieg.easyweather.Bean.DrawerContext;
import com.weather.byhieg.easyweather.Interface.MyItemClickListener;
import com.weather.byhieg.easyweather.R;

import java.util.ArrayList;

public class DrawerListAdapter extends RecyclerView.Adapter{

    private ArrayList<DrawerContext> drawerList;
    private MyItemClickListener listener;

    public DrawerListAdapter(ArrayList<DrawerContext> drawerList) {
        this.drawerList = drawerList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer_list, parent,false);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(view,(int)view.getTag());
                }
            }
        });
        return new DrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        DrawerViewHolder drawerViewHolder = (DrawerViewHolder)holder;
//        drawerViewHolder.position = position;
        DrawerContext drawerContext = drawerList.get(position);
        ((DrawerViewHolder) holder).arrow.setVisibility(View.GONE);
        ((DrawerViewHolder) holder).icon.setImageResource(drawerContext.getImage());
        ((DrawerViewHolder) holder).name.setText(drawerContext.getName());
        ((DrawerViewHolder) holder).itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return drawerList.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    class DrawerViewHolder extends RecyclerView.ViewHolder{

        public ImageView icon;
        public TextView name;
        public ImageView arrow;

        public DrawerViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            arrow = (ImageView) itemView.findViewById(R.id.arrow);
        }
    }
}
