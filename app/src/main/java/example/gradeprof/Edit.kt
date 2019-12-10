package example.gradeprof

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_opinions.closeButton
import kotlinx.android.synthetic.main.u_sure_alert.view.*

class Edit : AppCompatActivity() {

    lateinit var user: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val i = intent
        user = i.getStringExtra("index") as String

        closeButton.setOnClickListener{
            val intent = Intent(this, Opinions::class.java)
            intent.putExtra("index", user)
            startActivity(intent)
        }

        editButton.setOnClickListener{
        startActivity(Intent( this, GradeForm::class.java))}

        deleteButton.setOnClickListener{
            areUSureM8()
        }
    }

    fun areUSureM8(){
        val builder = AlertDialog.Builder(this@Edit, R.style.CustomDialog)
        val view = LayoutInflater.from(this).inflate(R.layout.u_sure_alert, null)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        view.yesButton.setOnClickListener{
            dialog.dismiss()
            val intent = Intent(this, Opinions::class.java)
            intent.putExtra("index", user)
            startActivity(intent)
        }
        view.noButton.setOnClickListener{
            dialog.dismiss()
        }
    }
}