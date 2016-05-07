package server.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by Paulina Sadowska on 07.05.2016.
 */
public class DateUtils
{
    public static Date getDate(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();
    }

    public static Date getDate(String date){
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        return getDate(year, month, day);
    }
}
