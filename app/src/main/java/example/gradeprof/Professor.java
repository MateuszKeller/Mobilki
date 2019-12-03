package example.gradeprof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Professor implements Comparable<Professor>{
    private String name;
    private String department;
    private List<Grade> grades;



    public Professor(String name, String department, List<Grade> grades) {
        this.name = name;
        this.department = department;
        this.grades = new ArrayList<Grade>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public List<Grade> getGrades() { return grades; }
    public void setGrades(List<Grade> grades) { this.grades = grades; }
    public void addGrade( Grade grade) { grades.add(grade); }

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
