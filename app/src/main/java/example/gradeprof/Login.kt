package example.gradeprof

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logInButton.setOnClickListener {
            startActivity(Intent(this, MainScreen::class.java))
        }

        var isVisible = false
        visibilityButton.setOnClickListener {
            if (!isVisible) {
                visibilityButton.alpha = 1.0f
                isVisible = true
            } else {
                visibilityButton.alpha = 0.5f
                isVisible = false
            }
        }
    }
}
