package example.gradeprof

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_opinions.*
import kotlinx.android.synthetic.main.activity_opinions.closeButton

class Opinions : AppCompatActivity(){

    lateinit var user: String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opinions)

        val i = intent
        user = i.getStringExtra("index") as String

        closeButton.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            intent.putExtra("index", user)
            startActivity(intent)
        }
        opinion.setOnClickListener{
            val intent = Intent(this, Edit::class.java)
            intent.putExtra("index", user)
            startActivity(intent)
        }

    }
}