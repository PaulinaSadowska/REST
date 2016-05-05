package server.dataObjects;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.annotations.Reference;
import server.StudentsDataResource;
import server.SubjectsDataResource;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulina Sadowska on 22.04.2016.
 */
@XmlRootElement
public class Grades
{
    @InjectLinks({
            @InjectLink(resource = StudentsDataResource.class, rel = "students"),
            @InjectLink(resource = SubjectsDataResource.class, rel = "subjects")
    })
    @XmlElement(name="link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    public List<Link> links;

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
