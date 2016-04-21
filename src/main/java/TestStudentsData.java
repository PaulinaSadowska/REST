import dataObjects.Student;
import dataObjects.Students;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
@Path("students")
public class TestStudentsData
{
    private Students studentsList = new Students();

    public TestStudentsData()
    {
        studentsList.addStudent(new Student(1, "Kasia", "Kowalska", "1993-6-7"));
        studentsList.addStudent(new Student(2, "Pawel", "Kkkk", "1993-6-7"));
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Students getAllStudents()
    {
        return studentsList;
    }


    @GET
    @Path("{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("studentId") String studentId)
    {
        return studentsList.getStudent(Integer.parseInt(studentId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addStudent(Student student){
        if(studentsList.getStudent(student.getId())==null)
        {
            studentsList.addStudent(student);
            return Response.status(201).
                    entity("student added").
                    type("text/plain").
                    build();
        }
        return Response.status(409).
                entity("student already exists").
                type("text/plain").
                build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editStudent(Student student){
        if(studentsList.getStudent(student.getId())!=null)
        {
            studentsList.editStudent(student);
            return Response.status(200).
                    entity("student data edited successfully").
                    type("text/plain").
                    build();
        }
        return Response.status(409).
                entity("student don't exists").
                type("text/plain").
                build();
    }

}
