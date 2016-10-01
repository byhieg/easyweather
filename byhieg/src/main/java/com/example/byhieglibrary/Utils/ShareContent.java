package com.example.byhieglibrary.Utils;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by byhieg on 16-10-1.
 * Mail byhieg@gmail.com
 */

public class ShareContent {

    public static final int SHARE_TEXT = 1;
    public static final int SHARE_LINK = 2;
    public static final int SHARE_IMAGE = 3;

    private String activityTitle;
    private String msgText;
    private String link;
    private File imgPath;



    public Intent startShare(int flag) {
        return this.startShare(activityTitle, msgText, link, imgPath, flag);
    }

    public Intent startShare(String activityTitle, String msgText) {
        this.activityTitle = activityTitle;
        this.msgText = msgText;
        return this.startShare(activityTitle, null, null, null, SHARE_TEXT);
    }


    public Intent startShare(String activityTitle, File f) {
        this.activityTitle = activityTitle;
        return this.startShare(activityTitle, null, null, f, SHARE_IMAGE);
    }
    public Intent startShare(String activityTitle, String msgText, String link, File f, int flag){
        this.activityTitle = activityTitle;
        this.msgText = msgText;
        this.link = link;

        Intent intent = new Intent(Intent.ACTION_SEND);
        switch (flag) {
            case SHARE_LINK:
            case SHARE_TEXT:
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, msgText);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                return intent;

            case SHARE_IMAGE:
                if (f.exists() && f.isFile()) {
                    intent.setType("image/png");
                    Uri u = Uri.fromFile(f);
                    intent.putExtra(Intent.EXTRA_STREAM, u);
                }
                intent.putExtra(Intent.EXTRA_TEXT, msgText);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                return intent;

        }

        return null;

    }


    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }


    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImgPath(File imgPath) {
        this.imgPath = imgPath;
    }
}
