package server.dataObjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.converters.IntegerConverter;
import server.DateParamConverterProvider;
import server.ObjectIdJaxbAdapter;
import server.SubjectsDataResource;

import javax.validation.constraints.*;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//obiekt stanowiący zawierający wartość liczbową (2.0 – 5.0), datę wystawienia i przypisanego studenta
@XmlRootElement(name="grades")
@Embedded
public class Grade
{
    @InjectLink(resource = SubjectsDataResource.class, method="getStudentGrade",
            bindings ={
                    @Binding(name = "studentId", value = "${instance.studentId}"),
                    @Binding(name = "subjectId", value = "${instance.subjectId}")
            }, style =  InjectLink.Style.ABSOLUTE)
    @XmlElement(name="gradeView")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    Link link; //will hold the link to view account details

    @NotNull
    @DecimalMin("2.0")
    @DecimalMax("5.0")
    private double grade;

    private int studentId;


    @Reference
    private Student student;


    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING,
            pattern="yyyy-MM-dd", timezone="CET")
    private Date gradeDate;

    private int subjectId;

    public Grade(){}

    public Grade(double grade, Date gradeDate, int subjectId, Student student){
        this.grade = (int)grade;
        this.gradeDate = gradeDate;
        this.studentId = student.getStudentId();
        this.student = student;
        this.subjectId = subjectId;
    }

    public double getGrade()
    {
        return grade;
    }
    public Date getGradeDate()
    {
        return gradeDate;
    }
    public int getStudentId()
    {
        return studentId;
    }

    public void setGradeDate(Date date)
    {
        this.gradeDate = date;
    }
    public void setGrade(double grade)
    {
            this.grade = grade;
    }

    public int getSubjectId()
    {
        return subjectId;
    }

    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }

    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
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
