package com.flybynight.flybynight.api;

import android.content.Context;

import com.flybynight.flybynight.FlyByNightApplication;
import com.flybynight.flybynight.Preferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit.MockRestAdapter;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by closestudios on 10/11/15.
 */
public class FlyByNightApi {

    static Gson gson;
    static FlyByNightService mService;

    static boolean MOCK = true;

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .create();
        }
        return gson;
    }
    public static FlyByNightService getApi() {
        if(mService == null) {
            if(MOCK) {
                mService = getMockService();
            } else {
                mService = getRestService();
            }

        }
        return mService;
    }


    private static FlyByNightService getMockService() {
        return getMockRestAdapter().create(FlyByNightMockService.class, new FlyByNightMockService());
    }

    private static MockRestAdapter getMockRestAdapter() {
       return MockRestAdapter.from(getRestAdapter());
    }

    private static RestAdapter getRestAdapter() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("https://www.someendpoint.com/v1/")
                .setRequestInterceptor(new UserAuthInterceptor(FlyByNightApplication.getAppContext()))
                .setConverter(new GsonConverter(getGson()));
        return builder.build();
    }

    private static FlyByNightService getRestService() {
        return getRestAdapter().create(FlyByNightService.class);
    }

    public static class UserAuthInterceptor implements RequestInterceptor {

        Context context;

        public UserAuthInterceptor(Context context) {
            this.context = context;
        }

        @Override
        public void intercept(RequestFacade request) {
            if(Preferences.getPrefs(context).getToken() != null) {
                request.addHeader("UserToken", "" + Preferences.getPrefs(context).getToken());
            }
        }
    }


}
