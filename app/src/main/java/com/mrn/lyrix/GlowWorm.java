package com.mrn.lyrix;

import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lucasmorano on 7/6/14.
 */
public class GlowWorm extends LyricProvider {

    private MainActivity activity;
    private static final String ENDPOINT = "http://www.vagalume.com.br/";

    private String lyrics;

    public GlowWorm(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String generateProviderUrl(String artist, String track) {
        if(artist.startsWith("The")){
            artist = artist.substring(3, artist.length());
        }
        return ENDPOINT + formatStringToUrl(artist) + "/" + formatStringToUrl(track) + ".html";
    }

    @Override
    protected String extractLyrics(String rawLyrics) {
        Matcher matcher = Pattern.compile("(?<=(<div itemprop=description>)).*?(?=(</div>))", Pattern.DOTALL).matcher(rawLyrics);
        if(matcher.find()){
            return matcher.group().replaceAll("<br />|<br/>","\n");
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
            Log.e("LYRIX - GW ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String lyrics) {
        activity.updateLyrics(this.lyrics);
    }

    public static String formatStringToUrl(String text) {
        return text.replaceAll("\\[(.*?)\\]", "").trim().replaceAll("[',.$#@%&]|", "").replaceAll("\\.", "").replaceAll("\\s+", "-").replaceAll("\\*", "c").toLowerCase();
    }
}
