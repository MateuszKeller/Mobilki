package example.gradeprof;

public class Grade {

    private float z;
    private float d;
    private float m;
    private String opinion;

    public Grade(float z, float d, float m) {
        this.z = z;
        this.d = d;
        this.m = m;
    }

    public Grade(float z, float d, float m, String opinion) {
        this.z = z;
        this.d = d;
        this.m = m;
        this.opinion = opinion;
    }

    public float getZ() { return z; }
    public void setZ(float z) { this.z = z; }
    public float getD() { return d; }
    public void setD(float d) { this.d = d; }
    public float getM() { return m; }
    public void setM(float m) { this.m = m; }
    public String getOpinion() { return opinion; }
    public void setOpinion(String opinion) { this.opinion = opinion; }
}
