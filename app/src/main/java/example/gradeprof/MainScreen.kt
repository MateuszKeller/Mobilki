package example.gradeprof

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.*
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.lang.StringBuilder

//TODO linesCount()

class MainScreen : AppCompatActivity() {

    var dim = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)



        // id: 2131230943
        this.testProf.setOnClickListener{
        startActivity(Intent( this, Profile::class.java))}

        val x = ProfElement(10, this@MainScreen)
        System.out.println(x.toString())
        //^ spr ProfElement init()

        sizes()

        generatingButtons()
    }

    fun sizes(){

        val layout: ConstraintLayout = findViewById(R.id.mainScreenLayout)
        layout.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        val n = findViewById<TextView>(R.id.testProfName)
        n.measure(0,0)
        val d = findViewById<TextView>(R.id.testProfDepartment)
        d.measure(0,0)
        val g = findViewById<TextView>(R.id.testProfGrades)
        g.measure(0,0)
        val p = findViewById<ImageView>(R.id.testProfPhoto)
        p.measure(0,0)

        val toDP = this.resources.displayMetrics.density
        val th = findViewById<Button>(R.id.testProf).layoutParams.height // / toDP).toInt()
        val tw = findViewById<Button>(R.id.testProf).layoutParams.width // / toDP).toInt()

        var lines = (n.measuredWidth / toDP).toInt() / 224

        if((n.measuredWidth / toDP).toInt().rem(224) > -1)
            lines ++

        var H : Int = n.measuredHeight * lines  + d.measuredHeight + g.measuredHeight // in px
        var W : Int = n.measuredWidth + p.measuredWidth // in px

        val button = findViewById<Button>(R.id.testProf)
        val bh = H  + (8 * toDP).toInt()
        button.layoutParams.height = bh



        //^ obliczenie wysokosc w dp

        // if dl < 224dp linie ++
        // ''= 1
        // i = 5
        // M = 19
        // a = 11 18x a = 212

        System.out.println("H: " + (H / toDP).toInt()
                + "/"
                + (th / toDP).toInt()
                + "/" + ( bh / toDP ).toInt()
                + " lines: " + lines )

        System.out.println("W: " + (W / toDP).toInt()
                + "/"
                + (tw / toDP).toInt() )
    }

    private fun generatingButtons(){

        val layout: ConstraintLayout = findViewById(R.id.mainScreenLayout)
        val cs = ConstraintSet()
        val prof = Button(this@MainScreen)

        ///px(Int) = dp * factor

        val factor = this.resources.displayMetrics.density
        val width =  resources.getDimension(R.dimen.prof_bg_width).toInt() // factor * 328
        val height = resources.getDimension(R.dimen.prof_bg_height)
        val par = ConstraintLayout.LayoutParams(width, height.toInt())
        prof.layoutParams = par
        var i = 0
        prof.id = i
        prof.text = "TEST"
        val x  = resources.getDrawable(R.drawable.input, null)
        prof.background = x

        layout.addView(prof, par)

        cs.clone(layout)

        cs.connect(prof.id, ConstraintSet.TOP,testProf.id, ConstraintSet.BOTTOM, (8 * factor).toInt())
        cs.connect(prof.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 16)
        cs.connect(prof.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 16)

        cs.applyTo(layout)
        setContentView(layout)
    }

    fun showPopupMenu(view: View) {
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

class ProfElement(id : Int, context: Context)
{

    var id : Int = id
    var bg = Button(context)
    var photo = ImageView(context)
    var pname = TextView(context)
    var department = TextView(context)
    var grades = TextView(context)

    init {

        bg.id = id + 1
        photo.id = id + 2
        pname.id = id + 3
        department.id = id + 4
        grades.id = id + 5

    }

    override fun toString(): String{

        val ret = StringBuilder()
        ret.append("ProfElement: ").append(id).append(" ")
           .append(bg.id).append(" ")
           .append(photo.id).append(" ")
           .append(pname.id).append(" ")
           .append(department.id).append(" ")
           .append(grades.id)

        return ret.toString()

    }

    fun constrain(layout: ConstraintLayout, constraint: ConstraintSet){

    }




}