package server;

import server.dataObjects.*;

/**
 * Created by Paulina Sadowska on 21.04.2016.
 */
public class DataProvider
{

    private static DataProvider dataProvider;

    /* Static 'instance' method */
    public static DataProvider getInstance( ) {
        if(dataProvider==null)
            dataProvider = new DataProvider();

        return dataProvider;
    }

    private Students studentsList = new Students();
    private Subjects subjectsList = new Subjects();

    private DataProvider(){
        initData();
    }

    public Students getStudentsList(){
        return studentsList;
    }

    public Subjects getSubjectsList(){
        return subjectsList;
    }

    private void initData()
    {
        Student student1 = new Student(1, "Kasia", "Kowalska", new SimpleDate(1990, 2, 1));
        Student student2 = new Student(2, "Pawel", "Kkkk", new SimpleDate(1993, 6, 7));
        Student student3 = new Student(3, "Rafał", "Arasz", new SimpleDate(1991, 12, 16));
        studentsList.addStudent(student1);
        studentsList.addStudent(student2);
        studentsList.addStudent(student3);

        Subject subject1 = new Subject(1, "Czarna magia", "Dumbledore");
        subject1.addGrade(new Grade(2.0, new SimpleDate(2016, 3, 4), 1, student1));
        subject1.addGrade(new Grade(4.0, new SimpleDate(2016, 4, 1), 1, student2));
        subjectsList.addSubject(subject1);

        Subject subject2 = new Subject(2, "Cokolwiek", "Ktokolwiek");
        subject2.addGrade(new Grade(3.0, new SimpleDate(2016, 4, 6), 2, student2));
        subject2.addGrade(new Grade(5.0, new SimpleDate(2016, 4, 6), 2, student3));
        subjectsList.addSubject(subject2);
    }
}
