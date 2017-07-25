package cn.byhieg.betterload.mydownload;

/**
 * Created by byhieg on 17/3/16.
 * Contact with byhieg@gmail.com
 */

public class DownLoadManager {

    private static DownLoadManager manager;

    private DownLoadManager(){

    }

    public static DownLoadManager getManager(){
        if (manager == null) {
            synchronized (DownLoadManager.class) {
                if (manager == null) {
                    manager = new DownLoadManager();
                }
            }
        }
        return manager;
    }



}
