package server;

import server.dataObjects.*;
import server.utils.DateUtils;

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

        Student student1 = new Student(1, "Kasia", "Kowalska", DateUtils.getDate(1993, 3, 1));
        Student student2 = new Student(2, "Pawel", "Kkkk", DateUtils.getDate(1991, 8, 2));
        Student student3 = new Student(3, "Rafał", "Arasz", DateUtils.getDate(1992, 12, 16));
        Student student4 = new Student(4, "Łukasz", "Nowak", DateUtils.getDate(2016, 6, 6));
        studentsList.addStudent(student1);
        studentsList.addStudent(student2);
        studentsList.addStudent(student3);
        studentsList.addStudent(student4);

        Subject subject1 = new Subject(1, "Czarna magia", "Dumbledore");
        subject1.addGrade(new Grade(2.0, DateUtils.getDate(2002, 6, 11), 1, student1));
        subject1.addGrade(new Grade(4.0, DateUtils.getDate(2016, 6, 21), 1, student2));
        subjectsList.addSubject(subject1);

        Subject subject2 = new Subject(2, "Cokolwiek", "Ktokolwiek");
        subject2.addGrade(new Grade(3.0, DateUtils.getDate(2016, 6, 1), 2, student2));
        subject2.addGrade(new Grade(5.0, DateUtils.getDate(2016, 6, 4), 2, student3));
        subjectsList.addSubject(subject2);
    }
}
