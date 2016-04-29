package server;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
public class GrizzlyHttpServer
{

    public static void main(String[] args) throws Exception
    {
        initMongoDB();
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8000).build();
        ResourceConfig config = new ResourceConfig(StudentsDataResource.class, SubjectsDataResource.class);
        config.packages("org.glassfish.jersey.examples.linking", "com.fasterxml.jackson.jaxrs").
                register(DeclarativeLinkingFeature.class);
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);

    }

    private static void initMongoDB(){
        // To directly connect to a single MongoDB server
        // (this will not auto-discover the primary even if it's a member of a replica set)
        MongoClient mongoClient = new MongoClient("localhost", 8004);
        MongoDatabase database = mongoClient.getDatabase("RESTDatabase");
        MongoCollection<Document> studentsCollection = database.getCollection("students");
        MongoCollection<Document> subjectsCollection = database.getCollection("subjects");
    }


    private static void testDummyMongoDB(){
        // To directly connect to a single MongoDB server
        // (this will not auto-discover the primary even if it's a member of a replica set)
        MongoClient mongoClient = new MongoClient("localhost", 8004);
        MongoDatabase database = mongoClient.getDatabase("mydb");
        MongoCollection<Document> collection = database.getCollection("test");
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));
        collection.insertOne(doc);
        System.out.println(collection.count());
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
    }

}
