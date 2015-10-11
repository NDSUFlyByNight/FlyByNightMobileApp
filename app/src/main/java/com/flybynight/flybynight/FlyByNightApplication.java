package com.flybynight.flybynight;

import android.app.Application;
import android.content.Context;

/**
 * Created by closestudios on 10/11/15.
 */
public class FlyByNightApplication extends Application {

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext() {
        return context;
    }

}
