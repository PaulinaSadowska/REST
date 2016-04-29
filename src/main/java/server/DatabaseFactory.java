package server;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import server.dataObjects.*;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class DatabaseFactory
{
    public static final String DATABASE_NAME = "RESTDatabase";

    public static void initMongoDB(){
        // To directly connect to a single MongoDB server
        // (this will not auto-discover the primary even if it's a member of a replica set)
        MongoClient mongoClient = new MongoClient("localhost", 8004);
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        Student student = new Student(1, "Kasia", "Kowalska", new SimpleDate(1990, 2, 1));

        MongoCollection<Document> studentsCollection = database.getCollection("students");
        MongoCollection<Document> subjectsCollection = database.getCollection("subjects");
        Morphia morphia = new Morphia();
        morphia.map(Student.class).map(SimpleDate.class);
        morphia.map(Subject.class).map(Grade.class);
        Datastore datastore = morphia.createDatastore(mongoClient, "db");
        datastore.save();
    }

}
