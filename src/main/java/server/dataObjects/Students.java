package server.dataObjects;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import server.StudentsDataResource;
import server.SubjectsDataResource;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
@XmlRootElement
public class Students
{
    @InjectLinks({
            @InjectLink(resource = StudentsDataResource.class, rel = "self"),
            @InjectLink(resource = SubjectsDataResource.class, rel = "subjects"),
    })
    @XmlElement(name="link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

    private List<Student> studentsList = new ArrayList<Student>();

    public Students()
    {
    }

    public void addStudent(Student student){
        studentsList.add(student);
    }

    public int getAvailableStudentId(){
        int i = studentsList.get(studentsList.size()-1).getId();
        return ++i;
    }

    public Student getStudent(int studentId){
        for(Student s: studentsList){
            if(s.getId()==studentId){
                return s;
            }
        }
        return null;
    }

    public void editStudent(Student student)
    {
        for (int i = 0; i < studentsList.size(); i++)
        {
            if(studentsList.get(i).getId()==student.getId()){
                studentsList.remove(i);
                studentsList.add(i, student);
                return;
            }
        }
    }

    public void deleteStudent(int studentId)
    {
        for(Student s: studentsList){
            if(s.getId()==studentId){
                studentsList.remove(s);
                return;
            }
        }
    }

    public List<Student> getStudentsList(){
        return studentsList;
    }

    public void setStudentsList(List<Student> studentsList)
    {
        this.studentsList = studentsList;
    }

    public List<Link> getLink()
    {
        return links;
    }

    public void setLink(List<Link> links)
    {
        this.links = links;
    }
}
