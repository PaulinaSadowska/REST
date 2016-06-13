package server.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by Paulina Sadowska on 07.05.2016.
 */
public class DateUtils
{
    public static Date getDate(int year, int month, int day)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();
    }

    public static String getDateString(Date date)
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}
