package server;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import server.dataObjects.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulina Sadowska on 15.04.2016.
 */
@Path("students")
public class StudentsDataResource
{
    private Datastore datastore;
    private int availableStudentId = 0;

    @Context
    UriInfo uriInfo;

    public StudentsDataResource()
    {
        datastore = DatabaseFactory.getInstance().getDatastore();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllStudents()
    {
        List<Student> studentsList = datastore.find(Student.class).asList();
        return Response.ok(studentsList).build();
    }


    @GET
    @Path("{studentId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("studentId") int studentId)
    {
        List<Student> result = datastore.find(Student.class).field("studentId").equal(studentId).asList();
        if(result!=null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addStudent(@NotNull @Valid Student student) throws URISyntaxException
    {
        List<Student> result = datastore.find(Student.class).field("studentId").equal(student.getStudentId()).asList();
        if (result.size() == 0)
        {
            int id = getAvailableStudentId();
            student.setStudentId(id);
            datastore.save(student);
            UriBuilder ub = uriInfo.getAbsolutePathBuilder();
            URI userUri = ub.path(id + "").build();
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

        List<Student> result = datastore.find(Student.class).field("studentId").equal(student.getStudentId()).asList();
        if(result.size() > 0)
        {
            Student studentToEdit = result.get(0);
            student.setId(studentToEdit.getId());
            datastore.save(student);
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

        List<Student> result = datastore.find(Student.class).field("studentId").equal(studentId).asList();
        deleteStudentGrades(studentId);
        if(result.size() > 0)
        {
            final Query<Student> studentToDeleteQuery = datastore.createQuery(Student.class)
                    .filter("studentId ==", studentId);
            datastore.delete(studentToDeleteQuery);
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

    private void deleteStudentGrades(int studentId)
    {
        List<Subject> result = datastore.find(Subject.class).asList();
        for(Subject s: result){
            s.deleteGrade(studentId);
            datastore.save(s);
        }
    }

    public int getAvailableStudentId()
    {
        List<Student> result = new ArrayList<Student>();
        result.add(new Student());
        while(availableStudentId<1 || result.size()>0)
        {
            availableStudentId++;
            result = datastore.find(Student.class).field("studentId").equal(availableStudentId).asList();
        }
        return availableStudentId;
    }
}
