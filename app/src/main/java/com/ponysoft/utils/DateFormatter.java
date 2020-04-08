package com.ponysoft.utils;

import java.util.Calendar;

public class DateFormatter {

    public static String dateStringFromMillisSeconds(long millisSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisSeconds);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String dateStr = "" + year + "-";

        if (month < 10) {

            dateStr = dateStr + "0" + month + "-";
        } else {

            dateStr = dateStr + month + "-";
        }

        if (day < 10) {

            dateStr = dateStr + "0" + day + " ";
        } else {

            dateStr = dateStr + day + " ";
        }

        if (hour < 10) {

            dateStr = dateStr + "0" + hour + ":";
        } else {
            dateStr = dateStr + hour + ":";
        }

        if (minute < 10) {

            dateStr = dateStr + "0" + minute + ":";
        } else {
            dateStr = dateStr + minute + ":";
        }

        if (second < 10) {

            dateStr = dateStr + "0" + second;
        } else {
            dateStr = dateStr + second;
        }

        return dateStr;
    }
}
