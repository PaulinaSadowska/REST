package server.dataObjects;

import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinkNoFollow;
import org.mongodb.morphia.annotations.Id;
import server.ObjectIdJaxbAdapter;
import server.SubjectsDataResource;

import javax.validation.constraints.*;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//obiekt stanowiący zawierający wartość liczbową (2.0 – 5.0), datę wystawienia i przypisanego studenta
@XmlRootElement(name="grades")
public class Grade
{
    @InjectLink(resource = SubjectsDataResource.class, method="getStudentGrade",
            bindings ={
                    @Binding(name = "studentId", value = "${instance.studentId}"),
                    @Binding(name = "subjectId", value = "${instance.subjectId}")
            }, style =  InjectLink.Style.ABSOLUTE)
    @XmlElement(name="gradeView")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    Link gradeView; //will hold the link to view account details

    @Id
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId id;


    @NotNull
    @DecimalMin("2.0")
    @DecimalMax("5.0")
    private double grade;

    @NotNull
    private int studentId;

    @NotNull
    private SimpleDate date;

    private int subjectId;

    public Grade(){}

    public Grade(double grade, SimpleDate date, int id, int subjectId){
        this.grade = (int)grade;
        this.date = date;
        this.studentId = id;
        this.subjectId = subjectId;
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
}
