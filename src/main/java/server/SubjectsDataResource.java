package server;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import server.dataObjects.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 21.04.2016.
 */
@Path("subjects")
public class SubjectsDataResource
{
    private Subjects subjectsList = new Subjects();

    @Context
    UriInfo uriInfo;

    public SubjectsDataResource()
    {
        subjectsList = DataProvider.getInstance().getSubjectsList();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllSubjects()
    {
        return Response.ok(subjectsList).build();
    }

    @GET
    @Path("{subjectId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjects(@PathParam("subjectId") int subjectId)
    {
        Subject result = subjectsList.getSubject(subjectId);
        if (result != null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }

    @GET
    @Path("{subjectId}/grades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getGrades(@PathParam("subjectId") int subjectId)
    {
        Grades result = subjectsList.getGrades(subjectId);
        if (result != null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }

    @GET
    @Path("{subjectId}/grades/{studentId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentGrade(@PathParam("subjectId") int subjectId, @PathParam("studentId") int studentId)
    {
        Grade result = subjectsList.getStudentGrade(subjectId, studentId);
        if (result != null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addSubject(Subject subject)
    {
        if (subjectsList.getSubject(subject.getId()) == null)
        {
            int id = subjectsList.getAvailableId();
            subject.setId(id);
            subjectsList.addSubject(subject);
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
        Subject subject = subjectsList.getSubject(subjectId);
        if (subject != null)
        {
            if (subject.getGrade(grade.getStudentId()) == null)
            {
                UriBuilder ub = uriInfo.getAbsolutePathBuilder();
                URI gradeUri = ub.path(grade.getStudentId()+"").build();
                grade.setSubjectId(subjectId);
                subject.addGrade(grade);
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
        Subject subjectToEdit = subjectsList.getSubject(subject.getId());
        if (subjectToEdit != null)
        {
            subjectsList.editSubject(subjectToEdit, subject);
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
        Subject subjectToEdit = subjectsList.getSubject(subjectId);
        if (subjectToEdit != null)
        {
            grade.setSubjectId(subjectId);
            if (subjectToEdit.editGrade(grade))
            {
                return Response.status(Response.Status.OK).
                        entity("grade edited successfully").
                        type("text/plain").
                        build();
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
        if (subjectsList.getSubject(subjectId) != null)
        {
            subjectsList.deleteSubject(subjectId);
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
        Subject subject = subjectsList.getSubject(subjectId);
        if (subject != null)
        {
            if (subject.deleteGrade(studentId))
            {
                return Response.status(Response.Status.OK).
                        entity("grade of student number " + studentId + " deleted successfully").
                        type("text/plain").
                        build();
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

}
