package dataObjects;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
@XmlRootElement
public class Students
{
    private ArrayList<Student> studentsList = new ArrayList<Student>();

    public ArrayList<Student> getStudentsList(){
        return studentsList;
    }

    public Students()
    {
    }

    public void addStudent(Student student){
        studentsList.add(student);
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
            }
        }
    }
}
