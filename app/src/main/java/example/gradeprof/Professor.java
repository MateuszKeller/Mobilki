package example.gradeprof;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

    public Professor(String name, String department, String info, List<Grade> grades, String ID) {
        this.name = name;
        this.department = department;
        this.grades = new ArrayList<Grade>();
        this.ID = ID;
        this.info = info;
    }
    public Professor(String ID, String name, String department, String info) {
        this.name = name;
        this.department = department;
        this.grades = new ArrayList<Grade>();
        this.ID = ID;
        this.info = info;
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

    @Override
    public int compareTo(Professor other) {
        Integer thisProf = this.getGrades().size();
        Integer otherProf = other.getGrades().size();
        return thisProf.compareTo(otherProf);
    }


}
