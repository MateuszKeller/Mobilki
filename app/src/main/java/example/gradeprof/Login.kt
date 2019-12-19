package example.gradeprof

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    /// TODO usunać
    // for faster testing Toast msg still appearing
//    val logOFF = false
    val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val m = Manager.getInstance()

        //TODO rzucać index
        logInButton.setOnClickListener {

            var login = email.text.toString().trim()
            val passw = password.text.toString().trim()
            var regex = Regex ("([0-9]){6}")
            if(regex.matches(login)){
                login  = login + "@edu.p.lodz.pl"
            }
            m.registerUser(login)


            firebaseAuth.signInWithEmailAndPassword(login, passw).addOnCompleteListener(
                OnCompleteListener { println("onComplete function started")
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainScreen::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(applicationContext, "Nieprawidłowe dane logowania!", Toast.LENGTH_SHORT)
                            .show()
                    } })
        }




//        var isVisible = false
//        visibilityButton.setOnClickListener {
//            if (!isVisible)
//            {
//                visibilityButton.alpha = 1.0f
//                isVisible = true
//            } else
//            {
//                visibilityButton.alpha = 0.5f
//                isVisible = false
//            }
//        }
    }
}
