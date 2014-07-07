package com.mrn.lyrix.util;

/**
 * Created by lucasmorano on 7/6/14.
 */
public class URLUtils {

    public static String formatStringToUrl(String text) {
         return text.replaceAll("[',.$#@%&]", "").replaceAll("\\.", "").replaceAll("\\s+", "").replaceAll("\\*", "c").toLowerCase();
    }
}
