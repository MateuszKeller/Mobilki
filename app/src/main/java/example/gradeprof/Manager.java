package example.gradeprof;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {

    private String indexNumber;
    private static Manager manager = new Manager();
    private List<Professor> professorList = new ArrayList<>();
    private FirebaseDatabase base = FirebaseDatabase.getInstance();
    private Map<String, DataStatus> listenersMap = new HashMap<>();
    private String user = "";


    public void addDataStatusListener(String name, DataStatus status){
        listenersMap.put(name, status);
        status.dataChanged(professorList);
    }

    public void registerUser(String login){
        this.user = login;
        int index = login.indexOf('@');
        String number = login.substring(0,index);
        this.indexNumber = number;

    }
    public String getUser(){
        return user;
    }

    public static Manager getInstance(){
        return manager;
    }


    public List<Professor> getProfessorList(){
        return professorList;
    }
    private Manager(){
        System.out.println("Manager.Manager");
        indexNumber = "";
        addListener();
        //read from database/xml here
    }


    public List<Professor> getProfs(){
        System.out.println("profsList size from getProfs(): " + professorList.size());
        return professorList;
    }


    //TODO dodać obsługę wyjątków, żeby nie przewracało apki jak jest błąd w bazie

    private void addListener(){
        DatabaseReference ref = base.getReference();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Professor> listenersProfList = new ArrayList<>();
                for(DataSnapshot childProfList : dataSnapshot.getChildren()) {
                    for(DataSnapshot childProf : childProfList.getChildren()) {

                        String name = childProf.child("name").getValue().toString();
                        String id = childProf.getKey();
                        String department = childProf.child("department").getValue().toString();
                        String info = childProf.child("info").getValue().toString();
                        Professor profTemp = new Professor(id, name, department, info);
                        List<Grade> gradesTempList = new ArrayList<>();
                        DataSnapshot childGradesList = childProf.child("grades");
                        Grade tempGrade;
                        for (DataSnapshot childGrade : childGradesList.getChildren()) {
                            if (childGrade != null) {
                                String gradeId = nullReplacer(childGrade.getKey().toString());
                                String passRate = nullReplacer(childGrade.child("passRate").getValue());
                                String availability = nullReplacer(childGrade.child("availability").getValue());
                                String merits = nullReplacer(childGrade.child("merits").getValue());
                                String opinion = nullReplacer(childGrade.child("opinion").getValue());
                                String author = nullReplacer(childGrade.child("author").getValue());
                                String displayName = nullReplacer(childGrade.child("displayName").getValue());
                                System.out.println("gradeId: " + gradeId +
                                        " passRate: " + passRate + " availability: " + availability +
                                        " merits: " + merits +
                                        " opinion: " + opinion);
                                tempGrade = new Grade(gradeId, Float.valueOf(passRate), Float.valueOf(availability), Float.valueOf(merits), opinion, author);
                                tempGrade.setDisplayName(displayName);
                                List<String> likesTempList = new ArrayList<>();
                                DataSnapshot childLikesList = childGrade.child("likes");
                                for (DataSnapshot childLike : childLikesList.getChildren()) {
                                    String likeTemp = childLike.getKey();
                                    likesTempList.add(likeTemp);
                                }
                                tempGrade.setWhoLiked(likesTempList);
                                gradesTempList.add(tempGrade);
                            }
                        }
                        profTemp.setGrades(gradesTempList);
//                        isNull(profTemp, "profTemp");
                        listenersProfList.add(profTemp);
//                        System.out.println("rozmiar professorList: " + listenersProfList.size());
                    }

                }
                professorList = listenersProfList;
                for(DataStatus listener : listenersMap.values()){
                    listener.dataChanged(professorList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("databaseError.getMessage() = " + databaseError.getMessage());
            }
        };
        ref.addValueEventListener(listener);
    }

    public Map<String, Object> gradeToMap(Grade grade){
        HashMap <String, Object> result = new HashMap<>();
        result.put("author", user);
        result.put("availability", grade.getAvailability());
        result.put("merits", grade.getMerits());
        result.put("passRate", grade.getPassRate());
        result.put("opinion", grade.getOpinion());
        result.put("displayName", grade.getDisplayName());
        return  result;
    }

    public Map<String, Object> profToMap(Professor prof){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name", prof.getName());
        result.put("info", prof.getInfo());
        result.put("department", prof.getDepartment());
        return result;
    }

    public void sendProfToDatabase(Professor prof){
        DatabaseReference ref = base.getReference();
        String key = prof.getID();

        Map<String, Object> profValues = profToMap(prof);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/profList/" + key, profValues);

        ref.updateChildren(childUpdates);
    }

    public void removeGrade(String gradeId){
        String profId = getProfForGrade(gradeId);
        System.out.println("Prof to remove: " + profId);
        System.out.println("grade to remove: " + gradeId);
        DatabaseReference ref = base.getReference();
        ref.child("profList").child(profId).child("grades").child(gradeId).removeValue();

    }

    public String getProfForGrade(String gradeId){
        for (Professor prof : professorList){
            for (Grade grade : prof.getGrades()){
                if(grade.getUID().equals(gradeId)){
                    return prof.getID();
                }
            }
        }
        return null;
    }

    public void sendGrade(String profID, Grade grade){
        DatabaseReference ref = base.getReference();
        String key = grade.getUID();

        Map<String, Object> gradeValues = gradeToMap(grade);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/profList/" + profID + "/grades/" + key, gradeValues);
        ref.updateChildren(childUpdates);
    }

    public void addLike(String profID, String gradeID){
        DatabaseReference ref = base.getReference();
        Map<String, Object> like = new HashMap<>();
        like.put(indexNumber, "1");

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/profList/" + profID + "/grades/" + gradeID + "/likes/" , like);
        ref.updateChildren(childUpdates);
    }

    public void removeLike(String profID, String gradeID, String indexNumber){
        DatabaseReference ref = base.getReference();
        ref.child("profList").child(profID).child("grades").child(gradeID).child("likes").child(indexNumber).removeValue();

    }

    public void isNull(Object o, String name){
        if(o==null){
            System.out.println(name + " is null!!!");
        }
    }

    public void isEmpty(List<Object> list, String name){
        if(list.isEmpty()){
            System.out.println(name + " list is empty!!!");
        }
    }
    private String nullReplacer(Object toCheck){
        if(toCheck != null){
            return toCheck.toString();
        } else return "";
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
            if(temp.getName().toLowerCase().matches(regex.toLowerCase())){
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
                if(temp.getAuthor().equals(user)){
                    myGrades.add(temp);
                }
            }

        }
        System.out.println("MYGRADES: " + myGrades.size());
        return myGrades;
    }

    public Professor getExactProfessor(String id){

            for(Professor ret: professorList)
                if(ret.getID().equals(id))
                    return ret;

            return null;
    }

    public Professor getExactProfessor(Grade grade){

        for(Professor ret: professorList)
            for(Grade g: ret.getGrades())
                if(g.equals(grade))
                return ret;

        return null;
    }

    public Grade getExactGrade(String uuid){

        for(Professor p: professorList)
            for(Grade ret: p.getGrades())
                if(ret.getUID().equals(uuid))
                return ret;

        return null;
    }


}
