package example.gradeprof

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /// TODO usunać
    // for faster testing Toast msg still appearing
    val logOFF = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO rzucać index
        logInButton.setOnClickListener {
            if (logOFF){
                val intent = Intent(this, MainScreen::class.java)
                intent.putExtra("index", "admin")
                startActivity(intent)
            }

            val login = email.text.toString()
            val passw = password.text.toString()
            val m = Manager()

            if(m.logIn(login, passw)){

                val intent = Intent(this, MainScreen::class.java)
                intent.putExtra("index", login)
                startActivity(intent)
            }
            else
                Toast.makeText(applicationContext, "Nieprawidłowe dane logowania!", Toast.LENGTH_SHORT ).show()
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
