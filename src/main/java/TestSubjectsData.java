import dataObjects.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 21.04.2016.
 */
@Path("subjects")
public class TestSubjectsData
{
    private Subjects subjectsList = new Subjects();

    public TestSubjectsData()
    {
        Subject subject1 = new Subject("Czarna magia", "Dumbledore");
        subject1.addGrade(new Grade(2.0, new SimpleDate(2016, 3, 4), 1));
        subject1.addGrade(new Grade(4.0, new SimpleDate(2016, 4, 1), 2));
        subjectsList.addSubject(subject1);

    }


    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Subject> getAllSubjects() {
        return subjectsList.getSubjectsList();
    }

    @GET
    @Path("{subjectName}")
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjects(@PathParam("subjectName") String subjectName)
    {
        Subject result = subjectsList.getSubject(subjectName);
        if(result!=null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }
}
