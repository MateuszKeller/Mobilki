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

    /// TODO DEL - FOR TESTING
    val logOFF = true
    private val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val m = Manager.getInstance()

        logInButton.setOnClickListener {

            var login = email.text.toString().trim()
            var passw = password.text.toString().trim()
            val regex = Regex ("([0-9]){6}")

            if(logOFF){ login = "217107"; passw = "haslo1" }

            if(regex.matches(login)){

                m.indexNumber = login
                login  = login + "@edu.p.lodz.pl"
            }

            m.registerUser(login)

            firebaseAuth.signInWithEmailAndPassword(login, passw).addOnCompleteListener {
                println("onComplete function started")
                if (it.isSuccessful) {
                    val intent = Intent(this, MainScreen::class.java)
                    intent.putExtra("startup", true)
                    startActivity(intent)

                }
                else
                    Toast.makeText(applicationContext, "Nieprawidłowe dane logowania!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
