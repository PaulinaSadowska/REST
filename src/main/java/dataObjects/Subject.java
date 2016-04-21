package dataObjects;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//posiada nazwę, prowadzącego i kolekcję ocen
@XmlRootElement
public class Subject
{
    @NotNull
    private String name;

    @NotNull
    private String teacher;

    public Subject()
    {
    }

    private ArrayList<Grade> grades = new ArrayList<Grade>();

    public Subject(String name, String teacher)
    {
        this.name = name;
        this.teacher = teacher;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTeacher()
    {
        return teacher;
    }

    public void setTeacher(String teacher)
    {
        this.teacher = teacher;
    }

    public void addGrade(Grade grade)
    {
        grades.add(grade);
    }

    public ArrayList<Grade> getGrades()
    {
        return grades;
    }

    public Grade getGrade(int studentId)
    {
        for (Grade g : grades)
        {
            if (g.getStudentId() == studentId)
            {
                return g;
            }
        }
        return null;
    }


    public boolean editGrade(Grade grade)
    {
        for (Grade g : grades)
        {
            if (g.getStudentId() == grade.getStudentId())
            {
                grades.remove(g);
                grades.add(grade);
                return true;
            }
        }
        return false;
    }

    public boolean deleteGrade(int studentId)
    {
        Grade g = getGrade(studentId);
        if (g != null)
        {
            grades.remove(g);
            return true;
        }
        return false;
    }
}
