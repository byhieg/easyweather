package cn.byhieg.monitor;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by byhieg on 2017/7/25.
 * Contact with byhieg@gmail.com
 */

public class TimeMonitor {

    private final String TAG = "TimeMonitor";
    private int monitorId = -1;

    private LinkedHashMap<String, Long> mTimeTag = new LinkedHashMap<>();

    private long mStartTime = 0;

    public TimeMonitor(int id) {
        Log.d(TAG, "init TimeMonitor id : " + id);
        monitorId = id;
        startMoniter();
    }

    public int getMonitorId(){
        return monitorId;
    }

    public void startMoniter(){
        if (mTimeTag.size() > 0) {
            mTimeTag.clear();
        }
        mStartTime = System.currentTimeMillis();
    }

    public void recordingTimeTag(String tag) {
        if (mTimeTag.get(tag) != null) {
            mTimeTag.remove(tag);
        }
        long time = System.currentTimeMillis() - mStartTime;
        Log.d(TAG,tag + ":" + time);
        mTimeTag.put(tag,time);
    }

    public void end(String tag) {
        recordingTimeTag(tag);
        testShowData();
//        end(writeLog);
    }

    public void end(boolean writeLog) {
        if (writeLog) {

        }
//        testShowData();
    }

    public void testShowData(){

        if (mTimeTag.size() <= 0) {
            Log.e(TAG, "mTimeTag is empty");
        }
//        Iterator iterator = mTimeTag.keySet().iterator();
//        while (iterator.hasNext()) {
//            String tag = (String) iterator.next();
//            Log.d(TAG, tag + ":" + mTimeTag.get(tag));
//        }
    }

    public HashMap<String,Long> getTimeTag(){
        return mTimeTag;
    }
}
