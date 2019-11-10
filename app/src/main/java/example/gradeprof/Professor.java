package example.gradeprof;

import java.util.ArrayList;
import java.util.List;

public class Professor {
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
}
