import dataObjects.*;

/**
 * Created by Paulina Sadowska on 21.04.2016.
 */
public class DataProvider
{
    private Students studentsList = new Students();
    private Subjects subjectsList = new Subjects();

    public void DataProvider(){
        initStudentsData();
        initSubjectsData();
    }

    public Students getStudentsList(){
        return studentsList;
    }

    public Subjects getSubjectsList(){
        return subjectsList;
    }

    private void initSubjectsData()
    {
        Subject subject1 = new Subject("Czarna magia", "Dumbledore");
        subject1.addGrade(new Grade(2.0, new SimpleDate(2016, 3, 4), 1));
        subject1.addGrade(new Grade(4.0, new SimpleDate(2016, 4, 1), 2));
        subjectsList.addSubject(subject1);
    }

    private void initStudentsData(){
        studentsList.addStudent(new Student(1, "Kasia", "Kowalska", new SimpleDate(1990, 2, 1)));
        studentsList.addStudent(new Student(2, "Pawel", "Kkkk", new SimpleDate(1993, 6, 7)));
        studentsList.addStudent(new Student(2, "Rafał", "Arasz", new SimpleDate(1991, 12, 16)));
    }
}
