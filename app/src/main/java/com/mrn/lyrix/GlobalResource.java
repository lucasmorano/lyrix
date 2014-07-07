package com.mrn.lyrix;

/**
 * Created by lucasmorano on 7/7/14.
 */
public class GlobalResource {

    private static String title;

    private static String lyrics;

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        GlobalResource.title = title;
    }

    public static String getLyrics() {
        return lyrics;
    }

    public static void setLyrics(String lyrics) {
        GlobalResource.lyrics = lyrics;
    }
}
