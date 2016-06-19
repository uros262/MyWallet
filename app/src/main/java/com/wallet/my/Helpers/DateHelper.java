package com.wallet.my.Helpers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Uros on 06-Dec-15.
 */
public class DateHelper {

    public String getFormatedDate(String date){

        SimpleDateFormat asd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");

        String reformattedStr = null;
        try {
            reformattedStr = myFormat.format(asd.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
    }

    public String getFormatedDateTime(String date){

        SimpleDateFormat asd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        String reformattedStr = null;
        try {
            reformattedStr = myFormat.format(asd.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
    }

    public String getFormatedTime(String date){

        SimpleDateFormat asd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm");

        String reformattedStr = null;
        try {
            reformattedStr = myFormat.format(asd.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
    }

    public String getTimestampForDatabase(String date){

        SimpleDateFormat asd = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String reformattedStr = null;
        try {
            reformattedStr = myFormat.format(asd.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Timestamp.valueOf(reformattedStr).toString();
    }
}
