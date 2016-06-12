package server;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import server.dataObjects.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public Response getAllStudents(
            @DefaultValue("0") @QueryParam("studentIdQuery") int studentIdQuery,
            @DefaultValue("") @QueryParam("firstNameQuery") String name,
            @DefaultValue("") @QueryParam("lastNameQuery") String surname,
            @QueryParam("birthDateQuery") Date birthDate)
          //  @DefaultValue("0") @QueryParam("comparatorQuery") int comparator)
    {
        Students result = new Students(datastore.find(Student.class).asList());
        if(result.getStudentsList().size()>0)
            return Response.ok(result.getStudentsList()).build();

        return Response.status(Response.Status.NOT_FOUND).entity("Students list is empty").build();
    }


    @GET
    @Path("{studentId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudent(@PathParam("studentId") int studentId)
    {
        Students result = new Students(datastore.find(Student.class).field("studentId").equal(studentId).asList());
        if(result.getStudentsListSize()>0)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
    }


    @GET
    @Path("getByName")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getByName(
            @DefaultValue("") @QueryParam("firstNameQuery") String name,
        @DefaultValue("") @QueryParam("lastNameQuery") String surname)
    {
        if(surname.equals("") && !name.equals(""))
        {
            List<Student> result = datastore.find(Student.class).field("name").equal(name).asList();
            if (result.size() > 0)
                return Response.ok(result).build();
        }
        else if(!surname.equals("") && name.equals("")){
            List<Student> result = datastore.find(Student.class).field("surname").equal(surname).asList();
            if (result.size() > 0)
                return Response.ok(result).build();
        }
        else if(!surname.equals("") && !name.equals("")){
            List<Student> result = datastore.find(Student.class)
                    .field("surname").equal(surname)
                    .field("name").equal(name).asList();
            if (result.size() > 0)
                return Response.ok(result).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
    }

    @GET
    @Path("getByBirthDate")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getByBirthDate(
            @QueryParam("birthDateQuery") Date date,
            @DefaultValue("0") @QueryParam("comparator") int comparator)
    {
        //todo - date less and more problem
        List<Student> result = new ArrayList<Student>();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        if(comparator==0)
        {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            Date startDate=calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date endDate = calendar.getTime();
            Query<Student> query = datastore.find(Student.class);
            query.and(
                    query.criteria("birthDate").lessThan(endDate),
                    query.criteria("birthDate").greaterThan(startDate));
            result = query.asList();
        }
        else if(comparator<0)
        {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            Date endDate = calendar.getTime();
            result = datastore.find(Student.class).field("birthDate").lessThan(endDate).asList();
        }
        else if(comparator>0)
        {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            Date startDate=calendar.getTime();
            result = datastore.find(Student.class).field("birthDate").greaterThan(startDate).asList();
        }

        if (result.size() > 0)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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
                    entity(student).
                    type("application/json").
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
