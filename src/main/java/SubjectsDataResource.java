import dataObjects.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 21.04.2016.
 */
@Path("subjects")
public class SubjectsDataResource
{
    private Subjects subjectsList = new Subjects();

    public SubjectsDataResource()
    {
        subjectsList = new DataProvider().getSubjectsList();
    }


    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Subject> getAllSubjects()
    {
        return subjectsList.getSubjectsList();
    }

    @GET
    @Path("{subjectName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSubjects(@PathParam("subjectName") String subjectName)
    {
        Subject result = subjectsList.getSubject(subjectName);
        if (result != null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }

    @GET
    @Path("{subjectName}/grades")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getGrades(@PathParam("subjectName") String subjectName)
    {
        ArrayList<Grade> result = subjectsList.getGrades(subjectName);
        if (result != null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }

    @GET
    @Path("{subjectName}/grades/{studentId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getStudentGrade(@PathParam("subjectName") String subjectName, @PathParam("studentId") int studentId)
    {
        Grade result = subjectsList.getStudentGrade(subjectName, studentId);
        if (result != null)
            return Response.ok(result).build();

        return Response.status(Response.Status.NOT_FOUND).type("text/plain").entity("Not found").build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSubject(Subject subject)
    {
        if (subjectsList.getSubject(subject.getName()) == null)
        {
            subjectsList.addSubject(subject);
            return Response.status(Response.Status.CREATED).
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
    @Path("{subjectName}/grades")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addGrade(@PathParam("subjectName") String subjectName, @NotNull @Valid Grade grade)
    {
        if (subjectsList.getSubject(subjectName) != null)
        {
            subjectsList.getSubject(subjectName).addGrade(grade);
            return Response.status(Response.Status.CREATED).
                    entity("grade added").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("subject not found").
                type("text/plain").
                build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editSubject(Subject subject)
    {
        Subject subjectToEdit = subjectsList.getSubject(subject.getName());
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
    @Path("{subjectName}/grades")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editGrade(@PathParam("subjectName") String subjectName, Grade grade)
    {
        Subject subjectToEdit = subjectsList.getSubject(subjectName);
        if (subjectToEdit != null)
        {
            if (subjectToEdit.editGrade(grade))
            {
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

    @DELETE
    @Path("{subjectName}")
    public Response deleteSubject(@PathParam("subjectName") String subjectName)
    {
        if (subjectsList.getSubject(subjectName) != null)
        {
            subjectsList.deleteSubject(subjectName);
            return Response.status(Response.Status.OK).
                    entity("subject " + subjectName + " deleted successfully").
                    type("text/plain").
                    build();
        }
        return Response.status(Response.Status.NOT_FOUND).
                entity("subject don't exists").
                type("text/plain").
                build();
    }

    @DELETE
    @Path("{subjectName}/grades/{studentId}")
    public Response deleteGrade(@PathParam("subjectName") String subjectName, @PathParam("studentId") int studentId)
    {
        Subject subject = subjectsList.getSubject(subjectName);
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
