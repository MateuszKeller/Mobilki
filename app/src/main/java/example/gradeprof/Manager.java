package example.gradeprof;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Manager {

    private static Manager manager = null;
    private String indexNumber;
    private List<Professor> professorList;

    public static void initialize(){

    }

    public static Manager getInstance(){
        return manager;
    }


    public Manager(){
        indexNumber = "";
        professorList = new ArrayList<>();
        //read from database/xml here
    }

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


        BigInteger givenPassword = new BigInteger(Utils.hash(password));
        String name = "admin";

        BigInteger passwd = new BigInteger(Utils.hash("admin"));
//            System.out.println(password + Utils.hash(password)+ "\n p:" + passwd);
        if(indexNum.equals(name) && givenPassword.equals(passwd)){
            setIndexNumber(indexNum);
            return true;
        } else {
            return false;
        }
    }

    //function to earch for professors
    public List<Professor> searchProfs(String toSearch){
        List<Professor> list = new ArrayList<>();
        String regex = "(.*)" + toSearch + "(.*)";
        for(Professor temp: professorList){
            if(temp.getName().matches(toSearch)){
                list.add(temp);
            }
        }
        return list;
    }

    //edit opinion should replace old opinion with new one (remove old from list)

    //if clicking button "Zatwierdź" returns Grade object
    public void addGrade(Professor professor, Grade grade){
        for(Professor prof: professorList){
            if(prof.getID().equals(professor.getID())){
                professor.addGrade(grade);
            }
        }
    }

    //deleting grade
    public void deleteGrade(Grade toDelete, Professor professor){
        for (Professor prof: professorList){
            if(prof.getID().equals(professor.getID())){
                professor.removeGrade(toDelete);
            }
        }
    }




    public List<Grade> getMyGrades(){
        List<Grade> myGrades = new ArrayList<Grade>();
        for(Professor prof: professorList){
            List<Grade> tempList = prof.getGrades();
            for(Grade temp: tempList){
                if(temp.getAuthor().equals(indexNumber)){
                    myGrades.add(temp);
                }
            }

        }
        return myGrades;
    }


}
