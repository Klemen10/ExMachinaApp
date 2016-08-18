package com.reevel.testreevel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Klemen on 17.8.2016.
 */

public class Util {

    public static float modulate(float value, float min, float max, float a, float b) {

        if (value > max) return b;
        else if (value < min) return a;
        else return (((b - a) * (value - min)) / (max - min)) + a;
    }

    public static String convertDate(String dateString) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(dateString);
            formatter = new SimpleDateFormat("MMM dd, yyyy");
            return formatter.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    public static String convertRuntime(int runtime){

        long hours = TimeUnit.MINUTES.toHours(runtime);
        long remainMinute = runtime - TimeUnit.HOURS.toMinutes(hours);
        return String.format(Locale.US, "%2d", hours) + "hr " + String.format(Locale.US, "%02d", remainMinute)+"min";
    }
}
