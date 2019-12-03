package example.gradeprof;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Manager {

    private String indexNumber;
    private List<Professor> professorList;

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    //sorts professors in list according to grades number, descending
    public void sortProfs(){
        Collections.sort(professorList, Collections.<Professor>reverseOrder());
    }

    public void addGrade(Professor professor, Grade grade){
        professor.addGrade(grade);
    }


}
