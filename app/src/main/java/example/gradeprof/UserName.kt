package example.gradeprof

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_grade_form.*
import kotlinx.android.synthetic.main.activity_profile.closeButton

class UserName : AppCompatActivity() {

    private val m: Manager = Manager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_name)

        var professorId =  intent.getStringExtra("professor")
        var grade = intent.getSerializableExtra("grade") as Grade
        println(grade.toString())


        closeButton.setOnClickListener{
            val intent = Intent( this, GradeForm::class.java)
            intent.putExtra("professor", professorId)
            startActivity(intent)}

        acceptButton.setOnClickListener{
            val intent = Intent( this, Profile::class.java)
            intent.putExtra("professor", professorId)

            val user = findViewById<EditText>(R.id.displayName).text.toString()
            if(user != "")
                grade.displayName = user

//            professor.addGrade(grade)
            m.sendGrade(professorId, grade)
            startActivity(intent)}
    }
}