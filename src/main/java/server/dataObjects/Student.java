package server.dataObjects;

import org.bson.types.ObjectId;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import server.StudentsDataResource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//posiada numer indeksu (unikalny identyfikator), imię, nazwisko, dataurodzenia (informacje nie mogą być puste)
@XmlRootElement
@Entity
public class Student
{
        @InjectLink(resource = StudentsDataResource.class, method="getStudent",
                bindings ={@Binding(name = "studentId", value = "${instance.studentId}")}, style =  InjectLink.Style.ABSOLUTE)
        @XmlElement(name="view")
        @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
        Link view; //will hold the link to view account details

        private int studentId;

        @Id
        private ObjectId id;

        @NotNull
        private String name;

        @NotNull
        private String surname;

        @NotNull
        private SimpleDate birthDate;

        //Introducing the dummy constructor
        public Student() {
        }

        public Student(int id, String name, String surname, SimpleDate birthDate){
                this.studentId = id;
                this.name = name;
                this.surname = surname;
                this.birthDate = birthDate;
        }

        public String getName()
        {
                return name;
        }

        public String getSurname()
        {
                return surname;
        }

        public SimpleDate getBirthDate()
        {
                return birthDate;
        }


        public void setName(String name)
        {
                this.name = name;
        }

        public void setSurname(String surname)
        {
                this.surname = surname;
        }

        public void setBirthDate(SimpleDate birthDate)
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
}
