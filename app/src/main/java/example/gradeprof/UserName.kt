package example.gradeprof

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_grade_form.*
import kotlinx.android.synthetic.main.activity_profile.closeButton
import java.util.*

class UserName : AppCompatActivity() {

    lateinit var professor: Professor
    val m = Manager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_name)

        professor =  m.getExactProfessor(intent.getStringExtra("professor"))
        var grade = intent.getSerializableExtra("grade") as Grade
        println(grade.toString())


        closeButton.setOnClickListener{
            val intent = Intent( this, GradeForm::class.java)
            intent.putExtra("professor", professor.id)
            startActivity(intent)}

        acceptButton.setOnClickListener{
            val intent = Intent( this, Profile::class.java)
            intent.putExtra("professor", professor.id)

            var user = findViewById<EditText>(R.id.userName).text.toString()
            println("|" + user + "|")
            if(user != "")
                grade.userName = user

            professor.addGrade(grade)
            startActivity(intent)}
    }
}