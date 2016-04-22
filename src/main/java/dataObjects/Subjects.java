package dataObjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by Paulina Sadowska on 21.04.2016.
 */
@XmlRootElement
public class Subjects
{
    private ArrayList<Subject> subjectsList = new ArrayList<Subject>();

    public ArrayList<Subject> getSubjectsList(){
        return subjectsList;
    }

    public Subjects()
    {
    }

    public void addSubject(Subject subject){
        subjectsList.add(subject);
    }

    public int getAvailableId(){
        int id = subjectsList.get(subjectsList.size()-1).getId();
        return ++id;
    }


    public Subject getSubject(int subjectId){
        for(Subject s: subjectsList){
            if(s.getId() == subjectId){
                return s;
            }
        }
        return null;
    }

    public Grade getStudentGrade(int subjectId, int studentId)
    {
        Subject subject = getSubject(subjectId);
        return subject.getGrade(studentId);
    }


    public Grades getGrades(int subjectId)
    {
        Subject subject = getSubject(subjectId);
        if(subject!=null)
            return subject.getGrades();

        return null;

    }

    public void editSubject(Subject subjectToEdit, Subject subject)
    {
        subjectsList.remove(subjectToEdit);
        subjectsList.add(subject);
    }

    public void deleteSubject(int subjectId)
    {
        subjectsList.remove(getSubject(subjectId));
    }
}
