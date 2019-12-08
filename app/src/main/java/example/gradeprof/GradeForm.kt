package example.gradeprof

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_grade_form.*
import kotlinx.android.synthetic.main.activity_profile.closeButton

class GradeForm : AppCompatActivity(){

    lateinit var professor: Professor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_form)

        val i = intent
        professor = i.getSerializableExtra("professor") as Professor

        closeButton.setOnClickListener{
            val intent = Intent( this, Profile::class.java)
            intent.putExtra("professor", professor)
            startActivity(intent)}

        acceptButton.setOnClickListener{
            val intent = Intent( this, UserName::class.java)
            intent.putExtra("professor", professor)
            startActivity(intent)}
    }
}