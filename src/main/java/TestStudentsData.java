import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
@Path("students")
public class TestStudentsData
{
    private Students studentsList = new Students();

    public TestStudentsData(){
        studentsList.addStudent(new Student(1, "Kasia", "Kowalska", new Date(1993, 6, 2)));
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStudent()
    {
        return studentsList.getStudent(1).toString();
    }
}
