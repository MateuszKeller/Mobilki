package example.gradeprof;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Manager {

    Manager(){}

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

    //function to log in, will compare given name and passwd with database
    public boolean logIn(String indexNum, String password){


        String name = "admin";
            String passwd= "admin";
        //String passwd = Utils.hash("admin");
            //System.out.println(password + Utils.hash(password)+ "\n p:" + passwd);
        if(indexNum.equals(name) && password.equals(passwd)){
            return true;
        } else {
            return false;
        }
    }

    public void addGrade(Professor professor, Grade grade){
        professor.addGrade(grade);
    }


}
