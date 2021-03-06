package example.gradeprof;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Professor implements Comparable<Professor>, Serializable {
    private String ID;
    private String name;
    private String department;
    private String info;
    private List<Grade> grades;

    public float averagePassRate(){
        float res = 0;
        for (Grade temp: grades){
            res += temp.getPassRate();
        }
        res = res/grades.size();
        return res;
    }

    public float averageAvailability(){
        float res = 0;
        for (Grade temp: grades){
            res += temp.getAvailability();
        }
        res = res/grades.size();
        return res;
    }

    public float averageMerits(){
        float res = 0;
        for (Grade temp: grades){
            res += temp.getMerits();
        }
        res = res/grades.size();
        return res;
    }

    public Professor(String name, String department, String info, ArrayList<Grade> grades, String ID) {
        this.name = name;
        this.department = department;
        if(grades != null)
            this.grades = grades;
        else
        this.grades = new ArrayList<>();

        this.ID = ID;
        this.info = info;
    }
    public Professor(String ID, String name, String department, String info) {
        this.name = name;
        this.department = department;
        this.grades = new ArrayList<>();
        this.ID = ID;
        this.info = info;
    }

    public boolean didUserGrade(String user){
        for(Grade grade : grades){
            if(grade.getAuthor().equals(user))
                return true;
        }
        return false;
    }

    public String getID(){
        return ID;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public List<Grade> getGrades() { return grades; }
    public void setGrades(List<Grade> grades) { this.grades = grades; }
    public void addGrade( Grade grade) { grades.add(grade); }
    public void removeGrade(Grade grade){
                grades.remove(grade);
    }
    public String getInfo() { return info; }
    public void setInfo(String info) { this.info = info; }

    //sorting grades according to likes number descending
    public void sortGrades(){
        Collections.sort(grades, Collections.<Grade>reverseOrder());
    }

    public String toString(){

        String ret = "";
        ret+= this.ID + "/ "
            + this.name + "/ "
            + this.department + "/ "
            + this.info + "/ "
            + this.grades.size();
        return ret;
    }

    @Override
    public int compareTo(Professor other) {
        String thisProf = this.ID;
        String otherProf = other.getID();
        return thisProf.compareTo(otherProf);
    }
}
