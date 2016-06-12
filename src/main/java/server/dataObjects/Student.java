package server.dataObjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import server.ObjectIdJaxbAdapter;
import server.StudentsDataResource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//posiada numer indeksu (unikalny identyfikator), imię, nazwisko, dataurodzenia (informacje nie mogą być puste)
@XmlRootElement
@Entity
public class Student
{
    @InjectLink(resource = StudentsDataResource.class, method = "getStudent",
            bindings = {@Binding(name = "studentId", value = "${instance.studentId}")}, style = InjectLink.Style.ABSOLUTE)
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    Link link; //will hold the link to view account details

    @XmlTransient
    private int studentId;

    @Id
    @XmlJavaTypeAdapter(ObjectIdJaxbAdapter.class)
    private ObjectId id;

    @NotNull
    private String firstName;

    @NotNull
    private String surname;

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING,
            pattern="yyyy-MM-dd", timezone="CET")
    private Date birthDate;

    //Introducing the dummy constructor
    public Student()
    {
    }

    public Student(int id, String firstName, String surname, Date birthDate)
    {
        this.studentId = id;
        this.firstName = firstName;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getSurname()
    {
        return surname;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }


    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }


    public int getStudentId()
    {
        return studentId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
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
