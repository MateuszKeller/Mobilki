package example.gradeprof;

import java.util.ArrayList;
import java.util.List;

public class Grade implements Comparable <Grade>{

    private float passRate;
    private float availability;
    private float merits;
    private String opinion;
    private int likes;
    private List<String> whoLiked;


    public Grade(float passRate, float availability, float merits) {
        this.passRate = passRate;
        this.availability = availability;
        this.merits = merits;
    }

    public Grade(float passRate, float availability, float merits, String opinion) {
        this.passRate = passRate;
        this.availability = availability;
        this.merits = merits;
        this.opinion = opinion;
        this.likes = 0;
        this.whoLiked = new ArrayList<>();
    }

    public float getPassRate() { return passRate; }
    public void setPassRate(float passRate) { this.passRate = passRate; }
    public float getAvailability() { return availability; }
    public void setAvailability(float availability) { this.availability = availability; }
    public float getMerits() { return merits; }
    public void setMerits(float merits) { this.merits = merits; }
    public String getOpinion() { return opinion; }
    public void setOpinion(String opinion) { this.opinion = opinion; }
    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }
    public void likeTap(String indexNumber){
        if (whoLiked.contains(indexNumber)) {
            whoLiked.remove(indexNumber);
            likes--;
        } else {
            whoLiked.add(indexNumber);
            likes++;
        }
    }

    @Override
    public int compareTo(Grade other) {
        Integer thisGrade = this.getLikes();
        Integer otherGrade = other.getLikes();
        return thisGrade.compareTo(otherGrade);
    }
}
