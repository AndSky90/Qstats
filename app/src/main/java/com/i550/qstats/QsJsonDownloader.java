/*
package com.i550.qstats;

import android.os.HandlerThread;
import android.util.Log;

public class QsJsonDownloader<T> extends HandlerThread {
    private static final String DOWNLOADERTAG = "QsJsonDownloader";
    private boolean hasQuit = false;

    public QsJsonDownloader() {
        super(DOWNLOADERTAG);
    }

    @Override
    public boolean quit() {
        hasQuit=true;
        return super.quit();
    }
    public void queueDownload(T target, String url){
        Log.i(DOWNLOADERTAG, url);
    }
}

*/
