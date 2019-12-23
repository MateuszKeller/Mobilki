package example.gradeprof

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_opinions.closeButton
import kotlinx.android.synthetic.main.u_sure_alert.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Edit : AppCompatActivity() {

    val m = Manager.getInstance()
    lateinit var grade: Grade

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        grade =  m.getExactGrade(intent.getStringExtra("uuid"))
        setData()

//        val prof = m.getExactProfessorName(grade)
//        println("GRADE: " + grade.author + " " + grade.opinion)
//        if(prof!=null) println("PROF: " + prof)

        closeButton.setOnClickListener{
            val intent = Intent(this, Opinions::class.java)
            startActivity(intent)
        }

        editButton.setOnClickListener{
            if(editOpinion())
                startActivity(Intent( this, Opinions::class.java))
        }

        deleteButton.setOnClickListener{
            areUSureM8() }
    }

    fun areUSureM8(){
        val builder = AlertDialog.Builder(this@Edit, R.style.CustomDialog)
        val view = LayoutInflater.from(this).inflate(R.layout.u_sure_alert, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        view.yesButton.setOnClickListener{
            dialog.dismiss()
            m.deleteGrade(grade, m.getExactProfessor(grade))
            val intent = Intent(this, Opinions::class.java)
            startActivity(intent)
        }
        view.noButton.setOnClickListener{
            dialog.dismiss()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(){

        findViewById<TextView>(R.id.profName).text = m.getExactProfessor(grade).name

        findViewById<TextView>(R.id.information).text = "Ocena dodana: " + grade.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm"))

        findViewById<RatingBarSvg>(R.id.ratingZ).rating = grade.passRate
        findViewById<RatingBarSvg>(R.id.ratingD).rating = grade.availability
        findViewById<RatingBarSvg>(R.id.ratingM).rating = grade.merits

        findViewById<EditText>(R.id.opinionText).setText(grade.opinion)
    }

    fun editOpinion(): Boolean {

        var zGrade = findViewById<RatingBarSvg>(R.id.ratingZ).rating
        var dGrade = findViewById<RatingBarSvg>(R.id.ratingD).rating
        var mGrade = findViewById<RatingBarSvg>(R.id.ratingM).rating
        var text = findViewById<EditText>(R.id.opinionText).text.toString()

        if(zGrade < 0.5 || dGrade < 0.5 || mGrade < 0.5){

            Toast.makeText(applicationContext, "Ocena nie może być mniejsza niż 0.5!", Toast.LENGTH_SHORT ).show()
            return false
        }

        grade.opinion = text
        grade.passRate = zGrade
        grade.availability = dGrade
        grade.merits = mGrade
        grade.date = LocalDateTime.now()

        return true
    }
}