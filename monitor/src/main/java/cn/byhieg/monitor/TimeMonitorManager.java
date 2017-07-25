package cn.byhieg.monitor;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by byhieg on 2017/7/25.
 * Contact with byhieg@gmail.com
 */

public class TimeMonitorManager {

    private static TimeMonitorManager mTimeMonitorManager = null;

    private static Context mContext = null;

    private HashMap<Integer,TimeMonitor>timeMonitorList = null;

    public synchronized static TimeMonitorManager getInstance(){
        if (mTimeMonitorManager == null) {
            mTimeMonitorManager = new TimeMonitorManager();
        }
        return mTimeMonitorManager;
    }

    public TimeMonitorManager(){
        timeMonitorList = new HashMap<>();
    }

    public void resetTimeMonitor(int id) {
        if (timeMonitorList.get(id) != null) {
            timeMonitorList.remove(id);
        }

        getTimeMonitor(id);
    }

    public TimeMonitor getTimeMonitor(int id) {
        TimeMonitor monitor = timeMonitorList.get(id);
        if (monitor == null) {
            monitor = new TimeMonitor(id);
            timeMonitorList.put(id, monitor);
        }
        return monitor;
    }
}
