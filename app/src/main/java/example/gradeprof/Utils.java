package example.gradeprof;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static byte[] hash(String toBeHashed){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] pwHash = digest.digest(toBeHashed.getBytes(StandardCharsets.UTF_8));
            return pwHash;
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
