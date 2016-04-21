package dataObjects;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//posiada numer indeksu (unikalny identyfikator), imię, nazwisko, dataurodzenia (informacje nie mogą być puste)
@XmlRootElement
public class Student
{
        @NotNull
        private int id;

        @NotNull
        private String name;

        @NotNull
        private String surname;

        @NotNull
        private String birthDate;

        //Introducing the dummy constructor
        public Student() {
                id = 9;
        }

        public Student(int id, String name, String surname, String birthDate){
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

        public String getBirthDate()
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

        public void setBirthDate(String birthDate)
        {
                this.birthDate = birthDate;
        }

        public String toString(){
                return "STUDENT: " + getId() + "\n"
                        + getName() + " " + getSurname() + "\n"
                        + getBirthDate().toString();
        }
}
