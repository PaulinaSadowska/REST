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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulina Sadowska on 21.04.2016.
 */
@XmlRootElement
public class Subjects
{
    @InjectLinks({
            @InjectLink(resource = StudentsDataResource.class, rel = "students"),
            @InjectLink(resource = SubjectsDataResource.class, rel = "self")
    })
    @XmlElement(name="link")
    @XmlElementWrapper(name = "links")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    List<Link> links;

    private List<Subject> subjectsList = new ArrayList<Subject>();

    public Subjects()
    {
    }

    public Subjects(List<Subject> subjectsList)
    {
        this.subjectsList = subjectsList;
    }

    public void addSubject(Subject subject){
        subjectsList.add(subject);
    }

    public int getAvailableId(){
        int id = subjectsList.get(subjectsList.size()-1).getSubjectId();
        return ++id;
    }


    public Subject getSubject(int subjectId){
        for(Subject s: subjectsList){
            if(s.getSubjectId() == subjectId){
                return s;
            }
        }
        return null;
    }

    public Grade getStudentGrade(int subjectId, int studentId)
    {
        Subject subject = getSubject(subjectId);
        return subject.getGrade(studentId);
    }


    public Grades getGrades(int subjectId)
    {
        Subject subject = getSubject(subjectId);
        if(subject!=null)
            return new Grades(subject.getGradesList());

        return null;

    }

    public void editSubject(Subject subjectToEdit, Subject subject)
    {
        subjectsList.remove(subjectToEdit);
        subjectsList.add(subject);
    }

    public void deleteSubject(int subjectId)
    {
        subjectsList.remove(getSubject(subjectId));
    }

    public List<Subject> getSubjectsList(){
        return subjectsList;
    }

    public void setSubjectsList(List<Subject> subjectsList)
    {
        this.subjectsList = subjectsList;
    }

    public int getSubjectsListSize()
    {
        return subjectsList.size();
    }
}
