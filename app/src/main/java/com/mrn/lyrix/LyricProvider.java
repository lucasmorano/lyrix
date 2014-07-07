package com.mrn.lyrix;

import android.app.Activity;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by lucasmorano on 7/6/14.
 */
public abstract class LyricProvider extends AsyncTask<String, Void, String> {

    private OkHttpClient httpClient;

//                  TODO FUTURE PROVIDERS INTENTS
//        iF.addAction("com.android.music.metachanged");
//        iF.addAction("com.android.music.playstatechanged");
//        iF.addAction("com.android.music.playbackcomplete");
//        iF.addAction("com.android.music.queuechanged");
//        iF.addAction("com.android.music.metachanged");
//        iF.addAction("com.htc.music.metachanged");
//        iF.addAction("fm.last.android.metachanged");
//        iF.addAction("com.sec.android.app.music.metachanged");
//        iF.addAction("com.nullsoft.winamp.metachanged");
//        iF.addAction("com.amazon.mp3.metachanged");
//        iF.addAction("com.miui.player.metachanged");
//        iF.addAction("com.real.IMP.metachanged");
//        iF.addAction("com.sonyericsson.music.metachanged");
//        iF.addAction("com.rdio.android.metachanged");
//        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
//        iF.addAction("com.andrew.apollo.metachanged");


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
