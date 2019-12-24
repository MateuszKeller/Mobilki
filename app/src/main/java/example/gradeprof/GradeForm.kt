package example.gradeprof

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_grade_form.*
import kotlinx.android.synthetic.main.activity_profile.closeButton

class GradeForm : AppCompatActivity(){

    lateinit var professor: Professor
    val m = Manager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_form)

        professor =  m.getExactProfessor(intent.getStringExtra("professor"))

        closeButton.setOnClickListener{
            val intent = Intent( this, Profile::class.java)
            intent.putExtra("professor", professor.id)
            startActivity(intent)}

        acceptButton.setOnClickListener{
            val intent = Intent( this, UserName::class.java)
            intent.putExtra("professor", professor.id)

            val grade = addOpinion()
            if(grade != null){
                intent.putExtra("grade", grade)
                startActivity(intent)
            }
        }// setOnClickListener
    }

    fun addOpinion(): Grade? {

        val zGrade = findViewById<RatingBarSvg>(R.id.ratingZ).rating
        val dGrade = findViewById<RatingBarSvg>(R.id.ratingD).rating
        val mGrade = findViewById<RatingBarSvg>(R.id.ratingM).rating
        val text = findViewById<EditText>(R.id.opinionText).text.toString()

        if(zGrade < 0.5 || dGrade < 0.5 || mGrade < 0.5){

            Toast.makeText(applicationContext, "Ocena nie może być mniejsza niż 0.5!", Toast.LENGTH_SHORT ).show()
            return null
        }

        if(text!= "")
            return Grade(zGrade, dGrade, mGrade, text, m.indexNumber)

        return Grade(zGrade, dGrade, mGrade, m.indexNumber)
    }
}