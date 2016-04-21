import javax.xml.bind.annotation.XmlRootElement;
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
    private static final HashSet<Double> validGrades = new HashSet<Double>(Arrays.asList(2.0, 3.0, 3.5, 4.0, 4.5, 5.0));
    private double grade;
    private Date date;
    private int studentId;

    public Grade(double grade, Date date, int id){
        this.grade = grade;
        this.date = date;
        this.studentId = id;
    }

    public double getGrade()
    {
        return grade;
    }
    public Date getDate()
    {
        return date;
    }
    public int getStudentId()
    {
        return studentId;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
    public void setGrade(double grade)
    {
        if(validGrades.contains(grade))
            this.grade = grade;
    }
    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }
}
