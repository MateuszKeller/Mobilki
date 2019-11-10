package example.gradeprof

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_grade_form.*
import kotlinx.android.synthetic.main.activity_profile.closeButton

class GradeForm : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_form)


        closeButton.setOnClickListener{
            startActivity(Intent( this, Profile::class.java))}

        acceptButton.setOnClickListener{
            startActivity(Intent( this, UserName::class.java))}
    }
}