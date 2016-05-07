package server;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import server.dataObjects.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulina Sadowska on 21.04.2016.
 */
@Path("subjects")
public class SubjectsDataResource
{
    private Datastore datastore;
    private int availableSubjectId = 0;

    @Context
    UriInfo uriInfo;

    public SubjectsDataResource()
    {
        datastore = DatabaseFactory.getInstance().getDatastore();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllSubjects()
    {
        Subjects subjectsList = new Subjects(datastore.find(Subject.class).asList());
        if(subjectsList.getSubjectsListSize()>0)
            return Response.ok(subjectsList).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Subjects list is empty").build();
    }

    @GET
    @Path("{subjectId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjects(@PathParam("subjectId") int subjectId)
    {
        List<Subject> result = datastore.find(Subject.class).field("subjectId").equal(subjectId).asList();
        if(result.size()>0)
            return Response.ok(result.get(0)).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }

    @GET
    @Path("{subjectId}/grades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getGrades(@PathParam("subjectId") int subjectId)
    {
        Subjects result = new Subjects(datastore.find(Subject.class).field("subjectId").equal(subjectId).asList());
        if(result.getSubjectsListSize()>0)
            return Response.ok(result.getGrades(subjectId)).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }

    @GET
    @Path("{subjectId}/grades/{studentId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentGrade(@PathParam("subjectId") int subjectId, @PathParam("studentId") int studentId)
    {
        List<Subject> result = datastore.find(Subject.class).field("subjectId").equal(subjectId).asList();
        if(result!=null)
            return Response.ok(result.get(0).getGrade(studentId)).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }


    @GET
    @Path("getSubjectsByTeacher")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjectByTeacher( @DefaultValue("") @QueryParam("teacher") String teacherName)
    {
        List<Subject> result = datastore.find(Subject.class).field("teacher").equal(teacherName).asList();
        if(result!=null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }

    @GET
    @Path("getGrades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getGradesByValue( @DefaultValue("2.0") @QueryParam("grade") double grade,
                                         @DefaultValue("0") @QueryParam("compare") int compare)
    {
        ArrayList<Grade> result = new ArrayList<Grade>();
        for (Subject subject : datastore.find(Subject.class).asList())
        {
            for (Grade g : subject.getGrades())
            {
                if(compareGrade(g.getGrade(), grade, compare)){
                    result.add(g);
                }
            }
        }

        if(result!=null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addSubject(Subject subject)
    {
        List<Subject> result = datastore.find(Subject.class).field("subjectId").equal(subject.getSubjectId()).asList();
        if (result.size() == 0)
        {
            int id = getAvailableSubjectId();
            subject.setSubjectId(id);
            datastore.save(subject);
            UriBuilder ub = uriInfo.getAbsolutePathBuilder();
            URI subjectUri = ub.path(id+"").build();
            return Response.
                    created(subjectUri).
                    status(Response.Status.CREATED).
                    entity("subject added").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.CONFLICT).
                entity("subject already exists").
                type("text/plain").
                build();
    }

    @POST
    @Path("{subjectId}/grades")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addGrade(@PathParam("subjectId") int subjectId, @NotNull @Valid Grade grade)
    {
        List<Subject> result = datastore.find(Subject.class).field("subjectId").equal(subjectId).asList();
        if (result.size() > 0)
        {
            Subject subject = result.get(0);
            if (subject.getGrade(grade.getStudentId()) == null)
            {
                UriBuilder ub = uriInfo.getAbsolutePathBuilder();
                URI gradeUri = ub.path(grade.getStudentId()+"").build();
                grade.setSubjectId(subjectId);
                Student student = datastore.find(Student.class).field("studentId").equal(grade.getStudentId()).asList().get(0);
                grade.setStudent(student);
                subject.addGrade(grade);
                datastore.save(subject);
                return Response.
                        created(gradeUri).
                        status(Response.Status.CREATED).
                        entity("grade added").
                        type("text/plain").
                        build();
            }
            return Response.status(Response.Status.CONFLICT).
                    entity("grade already exists").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("subject not found").
                type("text/plain").
                build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response editSubject(Subject subject)
    {
        List<Subject> result = datastore.find(Subject.class).field("subjectId").equal(subject.getSubjectId()).asList();
        if (result.size() > 0)
        {
            Subject subjectToEdit = result.get(0);
            subject.setId(subjectToEdit.getId());
            datastore.save(subject);
            return Response.status(Response.Status.OK).
                    entity("subject edited successfully").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("subject not found").
                type("text/plain").
                build();
    }


    @PUT
    @Path("{subjectId}/grades")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response editGrade(@PathParam("subjectId") int subjectId, Grade grade)
    {
        List<Subject> result = datastore.find(Subject.class).field("subjectId").equal(subjectId).asList();
        if (result.size() > 0)
        {
            Subject subject = result.get(0);
            if(subject.getGrades()!=null)
            {
                grade.setSubjectId(subjectId);
                Student student = datastore.find(Student.class).field("studentId").equal(grade.getStudentId()).asList().get(0);
                grade.setStudent(student);
                if (subject.editGrade(grade))
                {
                    datastore.save(subject);
                    return Response.status(Response.Status.OK).
                            entity("grade edited successfully").
                            type("text/plain").
                            build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND).
                    entity("grade don't exist").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("subject don't exist").
                type("text/plain").
                build();
    }

    @DELETE
    @Path("{subjectId}")
    public Response deleteSubject(@PathParam("subjectId") int subjectId)
    {
        List<Subject> result = datastore.find(Subject.class).field("subjectId").equal(subjectId).asList();
        if (result.size() > 0)
        {
            final Query<Subject> subjectToDeleteQuery = datastore.createQuery(Subject.class)
                    .filter("subjectId ==", subjectId);
            datastore.delete(subjectToDeleteQuery);
            return Response.status(Response.Status.OK).
                    entity("subject " + subjectId + " deleted successfully").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("subject don't exists").
                type("text/plain").
                build();
    }

    @DELETE
    @Path("{subjectId}/grades/{studentId}")
    public Response deleteGrade(@PathParam("subjectId") int subjectId, @PathParam("studentId") int studentId)
    {
        List<Subject> result = datastore.find(Subject.class).field("subjectId").equal(subjectId).asList();
        if (result.size() > 0)
        {
            Subject subject = result.get(0);
            if(subject.getGrades()!=null)
            {
                if (subject.deleteGrade(studentId))
                {
                    datastore.save(subject);
                    return Response.status(Response.Status.OK).
                            entity("grade of student number " + studentId + " deleted successfully").
                            type("text/plain").
                            build();
                }
            }
            return Response.status(Response.Status.NOT_FOUND).
                    entity("grade don't exists").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("subject don't exists").
                type("text/plain").
                build();
    }


    public int getAvailableSubjectId()
    {
        List<Subject> result = new ArrayList<Subject>();
        result.add(new Subject());
        while(availableSubjectId<1 || result.size()>0)
        {
            availableSubjectId++;
            result = datastore.find(Subject.class).field("subjectId").equal(availableSubjectId).asList();
        }
        return availableSubjectId;
    }

    private boolean compareGrade(double grade, double searchedGrade, int compare)
    {
        if(compare==0)
        {
            if (grade == searchedGrade)
            {
                return true;
            }
        }
        else if(compare<0)
        {
            if (grade < searchedGrade)
            {
                return true;
            }
        }
        else if(compare>0)
        {
            if (grade > searchedGrade)
            {
                return true;
            }
        }
        return false;
    }
}
