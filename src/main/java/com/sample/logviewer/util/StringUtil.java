package com.sample.logviewer.util;

public class StringUtil {

    public static String ellipsify(final String string, final int targetSize) {
        return ellipsifyStartingAt(string, 0, targetSize);
    }

    public static boolean isEmpty(final String str) {
        return (str == null) || (str.length() == 0);
    }

    private static String ellipsifyStartingAt(final String string, final int startIndex, final int targetSize) {
        if (null == string) {
            return "";
        }
        if (string.length() < targetSize) {
            return string;
        }
        return string.substring(startIndex, targetSize - 3) + "...";
    }
}
