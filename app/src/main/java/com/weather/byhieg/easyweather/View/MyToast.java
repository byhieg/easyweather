package com.weather.byhieg.easyweather.View;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.byhieg.easyweather.R;

public class MyToast {
    private static MyToast myToast;
    private Toast toast;

    private MyToast(){
    }
    public static MyToast createMyToast(){
        if (myToast == null) {
            myToast = new MyToast();
        }

        return myToast;
    }

    public void ToastShow(Context context, String string) {
        View layout = LayoutInflater.from(context).inflate(R.layout.layout_toast,null);
        TextView textView = (TextView) layout.findViewById(R.id.text);
        textView.setText(string);
        if(toast == null){
            toast = new Toast(context);
        }
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
