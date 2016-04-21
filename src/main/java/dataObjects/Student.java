package dataObjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
//posiada numer indeksu (unikalny identyfikator), imię, nazwisko, dataurodzenia (informacje nie mogą być puste)
@XmlRootElement
public class Student
{
        private int id;
        private String name;
        private String surname;
        private Date birthDate;

        public Student(int id, String name, String surname, Date birthDate){
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

        public Date getBirthDate()
        {
                return birthDate;
        }

        public String toString(){
                return "STUDENT: " + getId() + "\n"
                        + getName() + " " + getSurname() + "\n"
                        + getBirthDate().toString();
        }
}
