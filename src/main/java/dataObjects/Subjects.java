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

    public Subject getSubject(String subjectName){
        for(Subject s: subjectsList){
            if(s.getName().equals(subjectName)){
                return s;
            }
        }
        return null;
    }

    public Grade getStudentGrade(String subjectName, int studentId)
    {
        Subject subject = getSubject(subjectName);
        return subject.getGrade(studentId);
    }

    public ArrayList<Grade> getGrades(String subjectName)
    {
        Subject subject = getSubject(subjectName);
        if(subject!=null)
            return subject.getGrades();

        return null;

    }

    public void editSubject(Subject subjectToEdit, Subject subject)
    {
        subjectsList.remove(subjectToEdit);
        subjectsList.add(subject);
    }

    public void deleteSubject(String subjectName)
    {
        subjectsList.remove(getSubject(subjectName));
    }
}
