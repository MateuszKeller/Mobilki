package example.gradeprof

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.activity_opinions.*

class Opinions : AppCompatActivity(){

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opinions)

        opinion.setOnClickListener{
        startActivity(Intent( this, Edit::class.java))}

        closeButton.setOnClickListener{
            startActivity(Intent( this, MainScreen::class.java))}
        }
}