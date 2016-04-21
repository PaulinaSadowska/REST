package dataObjects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Paulina Sadowska on 21.04.2016.
 */
@XmlRootElement
public class SimpleDate
{
    @NotNull
    private int year;

    @NotNull
    @Min(1)
    @Max(12)
    private int month;

    @NotNull
    @Min(1)
    @Max(31)
    private int day;

    public SimpleDate(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getMonth()
    {
        return month;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }
}
