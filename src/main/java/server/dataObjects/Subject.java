package server.dataObjects;

import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import server.ObjectIdJaxbAdapter;
import server.StudentsDataResource;
import server.SubjectsDataResource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//posiada nazwę, prowadzącego i kolekcję ocen
@XmlRootElement
public class Subject
{
    @InjectLink(resource = SubjectsDataResource.class, method="getSubjects",
            bindings ={
                    @Binding(name = "subjectId", value = "${instance.subjectId}")
            }, style =  InjectLink.Style.ABSOLUTE)
    @XmlElement(name="link")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    Link link; //will hold the link to view account details

    private int subjectId;

    @Id
    @XmlTransient
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId id;

    @NotNull
    private String subjectName;

    @NotNull
    private String teacher;

    @Embedded
    public ArrayList<Grade> grades;

    public Subject()
    {
    }

    public Subject(int subjectId, String name, String teacher)
    {
        this.subjectId = subjectId;
        this.subjectName = name;
        this.teacher = teacher;
        grades = new ArrayList<Grade>();
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
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

    public ArrayList<Grade> getGradesList()
    {
        return grades;
    }

    public void setGrades(ArrayList<Grade> grades)
    {
        this.grades = grades;
    }

    public Grade getGrade(int studentId)
    {
        for (Grade g : grades)
        {
            if (g.getStudent().getStudentId() == studentId)
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
            if (g.getStudent().getStudentId() == grade.getStudent().getStudentId())
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

    public int getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }

    @XmlTransient
    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public Link getLink()
    {
        return link;
    }

    public void setLink(Link link)
    {
        this.link = link;
    }
}
