package example.gradeprof

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_opinions.closeButton
import kotlinx.android.synthetic.main.u_sure_alert.*
import kotlinx.android.synthetic.main.u_sure_alert.view.*

class Edit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        closeButton.setOnClickListener{
        startActivity(Intent( this, Opinions::class.java))}

        editButton.setOnClickListener{
        startActivity(Intent( this, GradeForm::class.java))}

        deleteButton.setOnClickListener{
            val builder = AlertDialog.Builder(this@Edit, R.style.CustomDialog)
            val view = LayoutInflater.from(this).inflate(R.layout.u_sure_alert, null)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()

            view.yesButton.setOnClickListener(){
                dialog.dismiss()
                startActivity(Intent( this, Opinions::class.java))
            }
            view.noButton.setOnClickListener(){
                dialog.dismiss()
            }
        }




    }
}