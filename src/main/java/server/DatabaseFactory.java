package server;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import server.dataObjects.*;

import java.util.List;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class DatabaseFactory
{

    private static DatabaseFactory databaseFactory;
    private Datastore datastore;

    public static DatabaseFactory getInstance( ) {
        if(databaseFactory==null)
            databaseFactory = new DatabaseFactory();

        return databaseFactory;
    }

    private static final String DATABASE_NAME = "RESTDatabase";
    private static final String DATABASE_ADDRESS = "localhost";
    private static final int DATABASE_PORT = 8004;

    private DatabaseFactory(){
        initMongoDB();
    }

    public Datastore getDatastore(){
        return datastore;
    }

    private void initMongoDB(){
        MongoClient mongoClient = new MongoClient(DATABASE_ADDRESS, DATABASE_PORT);

        final Morphia morphia = new Morphia();
        // tell Morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("server.dataObjects");

        // create the Datastore connecting to the default port on the local host
        datastore = morphia.createDatastore(mongoClient, DATABASE_NAME);
        datastore.ensureIndexes();

        //resetStudentsRecords(datastore);
        //resetSubjectsRecords(datastore);

        List<Student> students  = datastore.find(Student.class).asList();
        for (Student student : students)
        {
            System.out.println(student.getFirstName() + " " + student.getSurname());
        }
        List<Subject> subjects = datastore.find(Subject.class).asList();
        for (Subject subject : subjects)
        {
            System.out.println(subject.getSubjectName() + " " + subject.getTeacher());
        }
    }

    public static void resetStudentsRecords(Datastore datastore){
        final Query<Student> students = datastore.createQuery(Student.class);
        datastore.delete(students);
        for(Student s : DataProvider.getInstance().getStudentsList().getStudentsList()){
            datastore.save(s);
        }
    }

    public static void resetSubjectsRecords(Datastore datastore){
        final Query<Subject> subjects = datastore.createQuery(Subject.class);
        datastore.delete(subjects);
        for(Subject s : DataProvider.getInstance().getSubjectsList().getSubjectsList()){
            datastore.save(s);
        }
    }

}
