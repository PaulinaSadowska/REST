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
    private String name;
    private String teacher;
    private Collection<Grade> grades = new ArrayList<Grade>();

    public Subject(String name, String teacher){
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

    public void addGrade(Grade grade){
        grades.add(grade);
    }

    public Collection<Grade> getGrades(){
        return grades;
    }

    public String toString(){
        String result =  "name: "+name+"\n"+
                "teacher: "+teacher+"\n"+
                "GRADES\n";
        for(Grade grade: grades){
            result += grade.toString()+"\n";
        }
        return result;
    }
}
