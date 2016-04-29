package server.dataObjects;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import server.DatabaseFactory;

/**
 * Created by Paulina Sadowska on 29.04.2016.
 */
public class StudentDAO extends BasicDAO<Student, String>
{
    protected StudentDAO(MongoClient mongoClient, Morphia morphia)
    {
        super(mongoClient, morphia, DatabaseFactory.DATABASE_NAME);
    }
}
