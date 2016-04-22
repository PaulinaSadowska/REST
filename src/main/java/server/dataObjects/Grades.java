package server.dataObjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 22.04.2016.
 */
@XmlRootElement
public class Grades
{
    private ArrayList<Grade> grades = new ArrayList<Grade>();

    public ArrayList<Grade> getGrades()
    {
        return grades;
    }

    public Grades(){ }

    public void setGrades(ArrayList<Grade> grades)
    {
        this.grades = grades;
    }

    public void add(Grade grade){
        grades.add(grade);
    }

    public void remove(Grade grade){
        grades.remove(grade);
    }
}
