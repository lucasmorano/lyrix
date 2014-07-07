package com.mrn.lyrix;

import android.app.Activity;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by lucasmorano on 7/6/14.
 */
public abstract class LyricProvider extends AsyncTask<String, Void, String> {

    private OkHttpClient httpClient;

    protected OkHttpClient getClient() {
        if(httpClient == null){
            httpClient = new OkHttpClient();
        }
        return httpClient;
    }

    protected abstract String generateProviderUrl(String artist, String track);

    protected abstract String extractLyrics(String rawLyrics);

    protected abstract String getProviderEndpoint();

}
