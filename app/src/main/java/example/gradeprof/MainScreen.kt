package example.gradeprof

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_screen.*

class MainScreen : AppCompatActivity() {

    var dim = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        testProf.setOnClickListener{
        startActivity(Intent( this, Profile::class.java))}
    }



    fun showPopUp(view: View) {
        dimming.visibility = VISIBLE
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_main, popupMenu.menu)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.item1 -> startActivity(Intent( this, Opinions::class.java))
            }
            true
        }
    }

}