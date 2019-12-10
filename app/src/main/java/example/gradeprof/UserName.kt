package example.gradeprof

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_grade_form.*
import kotlinx.android.synthetic.main.activity_profile.closeButton
import java.util.*

class UserName : AppCompatActivity() {

    lateinit var professor: Professor
    lateinit var user: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_name)

        val i = intent
        professor = i.getSerializableExtra("professor") as Professor
        user = i.getStringExtra("index") as String

        closeButton.setOnClickListener{
            val intent = Intent( this, GradeForm::class.java)
            intent.putExtra("professor", professor)
            intent.putExtra("index", user)
            startActivity(intent)}

        acceptButton.setOnClickListener{
            val intent = Intent( this, Profile::class.java)
            intent.putExtra("professor", professor)
            intent.putExtra("index", user)
            startActivity(intent)}
    }
}