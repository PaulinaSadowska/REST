package server;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import server.dataObjects.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class DatabaseFactory
{
    public static final String DATABASE_NAME = "RESTDatabase";

    public static void initMongoDB(){
        MongoClient mongoClient = new MongoClient("localhost", 8004);
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(SimpleDate.class);
        morphia.map(Subject.class).map(Grade.class);
        Datastore datastore = morphia.createDatastore(mongoClient, "db");
        datastore.ensureIndexes();

        //resetStudentsRecords(datastore);
        //resetSubjectsRecords(datastore);

        List<Student> students  = datastore.find(Student.class).asList();
        for (Student student : students)
        {
            System.out.println(student.getName() + " " + student.getSurname());
        }
        List<Subject> subjects = datastore.find(Subject.class).asList();
        for (Subject subject : subjects)
        {
            System.out.println(subject.getName() + " " + subject.getTeacher());
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
