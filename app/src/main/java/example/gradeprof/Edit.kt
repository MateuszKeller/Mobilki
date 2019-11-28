package example.gradeprof

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_opinions.*
import kotlinx.android.synthetic.main.activity_opinions.closeButton

class Edit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        closeButton.setOnClickListener{
        startActivity(Intent( this, Opinions::class.java))}

        editButton.setOnClickListener{
        startActivity(Intent( this, GradeForm::class.java))}
    }
}