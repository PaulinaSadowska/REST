package server.dataObjects;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
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
public class Student
{
        @InjectLink(resource = StudentsDataResource.class, method="getStudent",
                bindings ={@Binding(name = "studentId", value = "${instance.id}")}, style =  InjectLink.Style.ABSOLUTE)
        @XmlElement(name="view")
        @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
        Link view; //will hold the link to view account details

        private int id;

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
                this.id = id;
                this.name = name;
                this.surname = surname;
                this.birthDate = birthDate;
        }

        public int getId()
        {
                return id;
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

        public void setId(int id)
        {
                this.id = id;
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


}
