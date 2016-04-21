import java.util.ArrayList;


/**
 * Created by Paulina Sadowska on 15.04.2016.
 */

public class Students
{

    private ArrayList<Student> studentsList = new ArrayList<Student>();

    public Students()
    {
    }

    public void addStudent(Student student){
        studentsList.add(student);
    }

    public Student getStudent(int studentId){
        return studentsList.get(0);
    }


}
