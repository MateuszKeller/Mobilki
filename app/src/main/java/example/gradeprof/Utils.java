package example.gradeprof;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String hash(String toBeHashed){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] pwHash = digest.digest(toBeHashed.getBytes());
            String ret = pwHash.toString();
            return ret;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

//    public static ConstraintLayout.LayoutParams but(){
//        ConstraintLayout.LayoutParams x = new ConstraintLayout.LayoutParams(
//                ConstraintLayout.LayoutParams.MATCH_PARENT,
//                ConstraintLayout.LayoutParams.MATCH_PARENT
//        );
//
//        return  x;
//    }
}
