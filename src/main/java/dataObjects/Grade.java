package dataObjects;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//obiekt stanowiący zawierający wartość liczbową (2.0 – 5.0, co 0.5), datę wystawienia i przypisanego studenta
@XmlRootElement
public class Grade
{
    @NotNull
    @DecimalMin("2.0")
    @DecimalMax("5.0")
    private double grade;
    @NotNull
    private int studentId;
    @NotNull
    private SimpleDate date;

    public Grade(){}

    public Grade(double grade, SimpleDate date, int id){
        this.grade = grade;
        this.date = date;
        this.studentId = id;
    }

    public double getGrade()
    {
        return grade;
    }
    public SimpleDate getDate()
    {
        return date;
    }
    public int getStudentId()
    {
        return studentId;
    }

    public void setDate(SimpleDate date)
    {
        this.date = date;
    }
    public void setGrade(long grade)
    {
            this.grade = grade;
    }
    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }
}
