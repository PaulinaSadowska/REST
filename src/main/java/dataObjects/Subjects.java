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

    public Subject getSubject(String subjectName){
        for(Subject s: subjectsList){
            if(s.getName().equals(subjectName)){
                return s;
            }
        }
        return null;
    }
}
