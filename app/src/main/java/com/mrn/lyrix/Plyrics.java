package com.mrn.lyrix;

import android.app.Activity;
import android.util.Log;

import com.mrn.lyrix.util.URLUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lucasmorano on 7/6/14.
 */
public class Plyrics extends LyricProvider {

    private MainActivity activity;
    private static final String ENDPOINT = "http://www.plyrics.com/lyrics/";

    private String lyrics;

    public Plyrics(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String generateProviderUrl(String artist, String track) {
        if(artist.startsWith("The")){
            artist = artist.substring(3, artist.length());
        }
        return ENDPOINT + URLUtils.formatStringToUrl(artist) + "/" + URLUtils.formatStringToUrl(track) + ".html";
    }

    @Override
    protected String extractLyrics(String rawLyrics) {
        Matcher matcher = Pattern.compile("(?<=(<!-- start of lyrics -->)).*?(?=(<!-- end of lyrics -->))", Pattern.DOTALL).matcher(rawLyrics);
        if(matcher.find()){
            return matcher.group().replaceAll("<br />","");
        }
        return "Sorry, I was not able to find lyrics to this song :(";
    }

    @Override
    protected String getProviderEndpoint() {
        return ENDPOINT;
    }

    @Override
    protected String doInBackground(String... params) {
        String artist = params[0];
        String track = params[1];
        Request request = new Request.Builder().url(generateProviderUrl(artist, track)).get().build();
        Response response = null;
        try {
            response = getClient().newCall(request).execute();
            String rawLyrics = response.body().string();
            lyrics = extractLyrics(rawLyrics);
        } catch (IOException e) {
            Log.e("LYRIX - ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String lyrics) {
        activity.updateLyrics(this.lyrics);
    }
}
