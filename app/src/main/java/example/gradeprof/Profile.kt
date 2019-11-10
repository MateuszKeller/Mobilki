package example.gradeprof

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        closeButton.setOnClickListener{
            startActivity(Intent( this, MainScreen::class.java))}

        gradeButton.setOnClickListener{
            startActivity(Intent( this, GradeForm::class.java))}
    }
}