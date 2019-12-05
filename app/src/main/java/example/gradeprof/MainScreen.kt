package example.gradeprof

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.*
import android.view.View.VISIBLE
import android.view.Window
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_main_screen.*
import kotlinx.android.synthetic.main.activity_opinions.*

class MainScreen : AppCompatActivity() {

    var dim = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        this.testProf.setOnClickListener{
        startActivity(Intent( this, Profile::class.java))}

        generatingButtons()


    }

    fun generatingButtons(){

        lateinit var layout: ConstraintLayout
        lateinit var cs : ConstraintSet
        lateinit var prof : Button


        layout = findViewById(R.id.mainScreenLayout)
        prof = Button(this@MainScreen)

        val factor = this.resources.displayMetrics.density
        val width =  resources.getDimension(R.dimen.prof_bg_width).toInt() // factor * 328
        val height = resources.getDimension(R.dimen.prof_bg_height)
        val par = ConstraintLayout.LayoutParams(width, height.toInt())
        prof.layoutParams = par

        var i = 0
        prof.id = i
        prof.setText("TEST")

        val x  = resources.getDrawable(R.drawable.input, null)
        prof.setBackground(x)

        layout.addView(prof, par)

        cs = ConstraintSet()
        cs.clone(layout)

        cs.connect(prof.id, ConstraintSet.TOP,testProf.id, ConstraintSet.BOTTOM, (8 * factor).toInt())
        cs.connect(prof.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 16)
        cs.connect(prof.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 16)

        cs.applyTo(layout)
        setContentView(layout)
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