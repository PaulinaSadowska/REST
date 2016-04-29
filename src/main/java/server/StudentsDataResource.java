package server;

import server.dataObjects.Student;
import server.dataObjects.Students;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
@Path("students")
public class StudentsDataResource
{
    private Students studentsList;

    @Context
    UriInfo uriInfo;

    public StudentsDataResource()
    {
        studentsList = DataProvider.getInstance().getStudentsList();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllStudents()
    {
        return Response.ok(studentsList).build();
    }


    @GET
    @Path("{studentId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("studentId") int studentId)
    {
        Student result = studentsList.getStudent(studentId);
        if(result!=null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addStudent(@NotNull @Valid Student student) throws URISyntaxException
    {
        if(studentsList.getStudent(student.getStudentId())==null)
        {
            int id = studentsList.getAvailableStudentId();
            student.setStudentId(id);
            studentsList.addStudent(student);
            UriBuilder ub = uriInfo.getAbsolutePathBuilder();
            URI userUri = ub.path(id+"").build();
            return Response.
                    created(userUri).
                    status(Response.Status.CREATED).
                    entity("student added").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.CONFLICT).
                entity("student already exists").
                type("text/plain").
                build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response editStudent(Student student){
        if(studentsList.getStudent(student.getStudentId())!=null)
        {
            studentsList.editStudent(student);
            return Response.status(Response.Status.OK).
                    entity("student data edited successfully").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("student don't exists").
                type("text/plain").
                build();
    }

    @DELETE
    @Path("{studentId}")
    public Response deleteStudent(@PathParam("studentId") int studentId){
        if(studentsList.getStudent(studentId)!=null)
        {
            studentsList.deleteStudent(studentId);
            return Response.status(Response.Status.OK).
                    entity("student " + studentId + " deleted successfully").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("student don't exists").
                type("text/plain").
                build();
    }
}
