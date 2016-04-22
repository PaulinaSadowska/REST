package server.dataObjects;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//obiekt stanowiący zawierający wartość liczbową (2.0 – 5.0), datę wystawienia i przypisanego studenta
@XmlRootElement(name="grades")
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
        this.grade = (int)grade;
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
    public void setGrade(double grade)
    {
            this.grade = grade;
    }
    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }
}
