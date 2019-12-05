package example.gradeprof

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /// TODO delete later
    // for faster testing Toast msg still appearing
    val OFF = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logInButton.setOnClickListener {
            if (OFF) startActivity(Intent(this, MainScreen::class.java))

            val login = email.text.toString()
            val passw = password.text.toString()
            val m = Manager()


            if(m.logIn(login, passw))
                startActivity(Intent(this, MainScreen::class.java))
            else
                Toast.makeText(applicationContext, "Nieprawidłowe dane logowania!", Toast.LENGTH_SHORT ).show()
        }

        var isVisible = false
        visibilityButton.setOnClickListener {
            if (!isVisible)
            {
                visibilityButton.alpha = 1.0f
                isVisible = true
            } else
            {
                visibilityButton.alpha = 0.5f
                isVisible = false
            }
        }
    }
}
