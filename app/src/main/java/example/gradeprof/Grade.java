package example.gradeprof;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Grade implements Comparable <Grade>, Serializable {

    private float passRate;
    private float availability;
    private float merits;
    private String opinion;
    private List<String> whoLiked;
    private String author;
    private String displayName;
    private String uid = UUID.randomUUID().toString();
    private int tempLikes = 0;
    private LocalDateTime date;
//    private String nick;

    public Grade(float passRate, float availability, float merits, String author) {
        this.passRate = passRate;
        this.availability = availability;
        this.merits = merits;
        this.author = author;
        this.displayName = author;
        this.date = LocalDateTime.now();
    }

    public Grade(String gradeId, float passRate, float availability, float merits, String opinion, String author) {
        this.uid = gradeId;
        this.passRate = passRate;
        this.availability = availability;
        this.merits = merits;
        this.opinion = opinion;
        this.whoLiked = new ArrayList<>();
        this.author = author;
        this.displayName = author;
        this.date = LocalDateTime.now();
    }
    public Grade(float passRate, float availability, float merits, String opinion, String author) {
        this.passRate = passRate;
        this.availability = availability;
        this.merits = merits;
        this.opinion = opinion;
        this.whoLiked = new ArrayList<>();
        this.author = author;
        this.displayName = author;
        this.date = LocalDateTime.now();
    }

    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getDisplayName(){
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

//    public void setNick(String nick) { this.nick = nick; }
    public String getUID(){
        return uid;
    }
    public float getPassRate() { return passRate; }
    public void setPassRate(float passRate) { this.passRate = passRate; }
    public float getAvailability() { return availability; }
    public void setAvailability(float availability) { this.availability = availability; }
    public float getMerits() { return merits; }
    public void setMerits(float merits) { this.merits = merits; }
    public String getOpinion() { return opinion; }
    public void setOpinion(String opinion) { this.opinion = opinion; }
    public int getLikes() { return whoLiked.size(); }
    public void setWhoLiked(List<String> whoLiked){
        this.whoLiked = whoLiked;
    }
    public LocalDateTime getDate(){return date;}
    public void setDate(LocalDateTime date) { this.date = date;}


    public boolean likeTap(String indexNumber){
        if (isLiked(indexNumber)) {
            whoLiked.remove(indexNumber);
            return false;
        } else {
            whoLiked.add(indexNumber);
            return true;
        }
    }
    public boolean isLiked(String indexNumber){
        return whoLiked.contains(indexNumber);
    }

    public void setTempLikes(int likes) { this.tempLikes = likes; }
    public int getTempLikes() { return tempLikes; }

    @Override
    public int compareTo(Grade other) {
        return this.uid.compareTo(other.uid);
    }

    public String toString(){

        String ret = "Zdaw: " + passRate +
                "; Dost: " + availability +
                "; Meryt: " + merits +
                "; Opinia: " + opinion +
                "; Autor: " + author +
                "; User: " + displayName;
        return ret;
    }

}
