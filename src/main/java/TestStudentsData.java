import dataObjects.SimpleDate;
import dataObjects.Student;
import dataObjects.Students;
import dataObjects.Subject;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
@Path("students")
public class TestStudentsData
{
    private Students studentsList = new Students();

    public TestStudentsData()
    {
        studentsList.addStudent(new Student(1, "Kasia", "Kowalska", new SimpleDate(1990, 2, 1)));
        studentsList.addStudent(new Student(2, "Pawel", "Kkkk", new SimpleDate(1993, 6, 7)));
    }


    @GET
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Student> getAllStudents()
    {
        return studentsList.getStudentsList();
    }


    @GET
    @Path("{studentId}")
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("studentId") String studentId)
    {
        Student result = studentsList.getStudent(Integer.parseInt(studentId));
        if(result!=null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addStudent(@NotNull @Valid Student student){
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

    @DELETE
    @Path("{studentId}")
    public Response deleteStudent(@PathParam("studentId") String studentId){
        if(studentsList.getStudent(Integer.parseInt(studentId))!=null)
        {
            studentsList.deleteStudent(Integer.parseInt(studentId));
            return Response.status(200).
                    entity("student " + studentId + " deleted successfully").
                    type("text/plain").
                    build();
        }
        return Response.status(409).
                entity("student don't exists").
                type("text/plain").
                build();
    }
}
