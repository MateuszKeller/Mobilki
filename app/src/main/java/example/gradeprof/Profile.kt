package example.gradeprof

import android.os.Bundle
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile.*

class Profile : AppCompatActivity() {

    lateinit var professor: Professor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val i = intent
        professor = i.getSerializableExtra("professor") as Professor

        setData()

        closeButton.setOnClickListener{
            startActivity(Intent( this, MainScreen::class.java))}

        gradeButton.setOnClickListener{
            val intent = Intent( this, GradeForm::class.java)
            intent.putExtra("professor", professor)
            startActivity(intent)}
    }

    fun setData(){

        findViewById<TextView>(R.id.profName).text = professor.name
        findViewById<TextView>(R.id.informationText).text = professor.info

        findViewById<RatingBarSvg>(R.id.ratingZ).rating = professor.averagePassRate()
        findViewById<RatingBarSvg>(R.id.ratingD).rating = professor.averageAvailability()
        findViewById<RatingBarSvg>(R.id.ratingM).rating = professor.averageMerits()
    }
}