package server.dataObjects;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import server.StudentsDataResource;
import server.SubjectsDataResource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//posiada nazwę, prowadzącego i kolekcję ocen
@XmlRootElement
public class Subject
{
    @InjectLink(resource = SubjectsDataResource.class, method="getSubjects",
            bindings ={
                    @Binding(name = "subjectId", value = "${instance.id}")
            }, style =  InjectLink.Style.ABSOLUTE)
    @XmlElement(name="view")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    Link view; //will hold the link to view account details

    private int id;

    @NotNull
    private String name;

    @NotNull
    private String teacher;

    private Grades grades;

    public Subject()
    {
    }

    public Subject(int id, String name, String teacher)
    {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        grades = new Grades();
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

    public Grades getGrades()
    {
        return grades;
    }

    public void setGrades(Grades grades)
    {
        this.grades = grades;
    }

    public Grade getGrade(int studentId)
    {
        for (Grade g : grades.getGrades())
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
        for (Grade g : grades.getGrades())
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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
}
