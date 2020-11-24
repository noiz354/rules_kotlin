package com.tokopedia.utils.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

// Gist
// https://gist.github.com/oseparovic/d9ee771927ac5f3aefc8ba0b99c0cf38
// Relevant SO question
// http://stackoverflow.com/questions/40369287/what-pattern-should-be-used-to-parse-rfc-3339-datetime-strings-in-java
// Relevant different behaviour when unit testing
// https://stackoverflow.com/questions/35139588/dateformat-parseexception-only-during-unit-testing

public class RfcDateTimeParser {

    // note I have excluded two valid 3339 patterns that contain the Z literal. This is because
    // java does not properly assume the Z literal indicates UTC so we have to manually handle it
    // in our parsing function instead. For reference the other two patterns would be:
    // "yyyy-MM-dd'T'HH:mm:ss'Z'"
    // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    // also please note that ZZZZZ == XXX in java 7. This project was initially created for Android
    // so XXX is not supported
    // the third pattern (without Z) was added to support unit testing
    public final static String[] RFC_3339 = {
            "yyyy-MM-dd'T'HH:mm:ssZZZZZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ",
            "yyyy-MM-dd'T'HH:mm:ss"
    };
    public final static String[] RFC_822 = {
            "EEE, dd MMM yy HH:mm:ss zzz"
    };
    public final static String[] RFC_2822 = {
            "EEE, dd MMM yyyy HH:mm:ss zzz"
    };

    public static Date parseDateString(String timestamp, String[] rfcPatterns) {
        if (timestamp == null || timestamp.isEmpty()) {
            return null;
        }

        // java doesn't properly handle the 'Z' literal so we replace it manually with UTC time
        if (timestamp.contains("Z")) {
            timestamp = timestamp.replaceAll("Z$", "+0000");
        }

        // loop through all of our patterns and try each one
        // http://stackoverflow.com/a/4024604/740474
        for (String formatString : rfcPatterns) {
            try {
                return new SimpleDateFormat(formatString, Locale.getDefault()).parse(timestamp);
            } catch (ParseException e) {
                // SimpleDateFormat couldn't parse the date, catch and continue
                e.printStackTrace();
            }
        }

        // failed to parse date
        return null;
    }

    public static Date parseDateString(String timestamp) {
        // generic method for parsing any supported rfc timestamp. Concat all patterns and feed into
        // our parser
        ArrayList<String> allRfcPatterns = new ArrayList<>();
        allRfcPatterns.addAll(Arrays.asList(RFC_822));
        allRfcPatterns.addAll(Arrays.asList(RFC_2822));
        allRfcPatterns.addAll(Arrays.asList(RFC_3339));
        return parseDateString(timestamp, allRfcPatterns.toArray(new String[allRfcPatterns.size()]));
    }
}
