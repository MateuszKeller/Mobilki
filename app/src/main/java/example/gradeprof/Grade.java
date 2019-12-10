package example.gradeprof;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Grade implements Comparable <Grade>{

    private float passRate;
    private float availability;
    private float merits;
    private String opinion;
    private List<String> whoLiked;
    private String author;
    private final String uid = UUID.randomUUID().toString();

    public Grade(float passRate, float availability, float merits, String author) {
        this.passRate = passRate;
        this.availability = availability;
        this.merits = merits;
        this.author = author;
    }

    public Grade(float passRate, float availability, float merits, String opinion, String author) {
        this.passRate = passRate;
        this.availability = availability;
        this.merits = merits;
        this.opinion = opinion;
        this.whoLiked = new ArrayList<>();
        this.author = author;
    }

    public String getAuthor(){
        return author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

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
//    public void setLikes(int likes) { this.likes = likes; }
    public void setWhoLiked(List<String> whoLiked){
        this.whoLiked = whoLiked;
    }
    public void likeTap(String indexNumber){
        if (whoLiked.contains(indexNumber)) {
            whoLiked.remove(indexNumber);
        } else {
            whoLiked.add(indexNumber);
        }
    }

    @Override
    public int compareTo(Grade other) {
        Integer thisGrade = this.getLikes();
        Integer otherGrade = other.getLikes();
        return thisGrade.compareTo(otherGrade);
    }
}
