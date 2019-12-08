package example.gradeprof

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.*
import android.view.View.TEXT_ALIGNMENT_TEXT_END
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

    var pList = ArrayList<Professor>()
    var bList = ArrayList<ProfElement>()
    var dim = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        fillList()
        generatingButtons()
    }

    fun fillList(){

        pList.add(Professor(
            resources.getString(R.string.testProfName),
            "FTIMS",
            resources.getString(R.string.testProfInfo),
            ArrayList<Grade>(),
            "prof1")
        )
        pList.add(Professor(
            resources.getString(R.string.tPN1),
            "FTIMS",
            resources.getString(R.string.TPI1),
            ArrayList<Grade>(),
            "prof2")
        )
        pList.add(Professor(
            resources.getString(R.string.tPN2),
            "FTIMS",
            resources.getString(R.string.TPI2),
            ArrayList<Grade>(),
            "prof3")
        )
    }


    private fun generatingButtons(){

        var id = 0
        for( p in pList){
            id += 10
            bList.add(ProfElement(id, this@MainScreen, p))
        }

        for (b in bList)
            b.create(findViewById(R.id.mainScreenLayout))
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

class ProfElement(id : Int, context: Context, prof: Professor) {

    val professor= prof
    var id= id
    var context= context
    var bg = Button(context)
    var photo = ImageView(context)
    var pname = TextView(context)
    var department = TextView(context)
    var grades = TextView(context)

    val dpFactor= context.resources.displayMetrics.density

    init {
        bg.id = id + 1
        photo.id = id + 2
        pname.id = id + 3
        department.id = id + 4
        grades.id = id + 5

        System.out.println(toString())
    }

    override fun toString(): String{

        val ret = StringBuilder()
        ret.append("ProfElement: i").append(id).append(" b")
           .append(bg.id).append(" p")
           .append(photo.id).append(" n")
           .append(pname.id).append(" d")
           .append(department.id).append(" g")
           .append(grades.id).append("\n")
           .append("Professor: ").append(professor.name)

        return ret.toString()
    }

    fun setData(){

        makePhoto()
        makeName()
        makeDepartment()
        makeGrades()
        makeBG()
    }

    fun constrain(layout: ConstraintLayout){

        val constraint = ConstraintSet()
        constraint.clone(layout)

        val upperButtonID: Int
        var margin: Int
        if(id == 10){
            upperButtonID = R.id.searchBox
            margin = (28 * dpFactor).toInt()
        }
        else{
            upperButtonID = id - 9
            margin = (8 * dpFactor).toInt()
        }

        constraint.connect(bg.id, ConstraintSet.TOP, upperButtonID, ConstraintSet.BOTTOM, margin)
        constraint.connect(bg.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraint.connect(bg.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)

        margin = context.resources.getDimension(R.dimen.fab_margin).toInt()
        constraint.connect(photo.id, ConstraintSet.TOP, bg.id, ConstraintSet.TOP, margin/4)
        constraint.connect(photo.id, ConstraintSet.LEFT, bg.id, ConstraintSet.LEFT, margin/4)

        constraint.connect(pname.id, ConstraintSet.TOP, bg.id, ConstraintSet.TOP, margin/4)
        constraint.connect(pname.id, ConstraintSet.LEFT, photo.id, ConstraintSet.RIGHT, margin)

        constraint.connect(department.id, ConstraintSet.TOP, pname.id, ConstraintSet.BOTTOM, 0)
        constraint.connect(department.id, ConstraintSet.LEFT, photo.id, ConstraintSet.RIGHT, margin)

        constraint.connect(grades.id, ConstraintSet.TOP, department.id, ConstraintSet.BOTTOM, 0)
        constraint.connect(grades.id, ConstraintSet.RIGHT, bg.id, ConstraintSet.RIGHT, margin/4)

        constraint.applyTo(layout)
    }

    fun makeBG(){

        bg.background = context.resources.getDrawable(R.drawable.input, null)
        bg.translationZ = 0.1f

        bg.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.bg_width).toInt(),
            calcHeight())


        bg.setOnClickListener{
            val intent = Intent( context, Profile::class.java)
            intent.putExtra("professor", professor)
            context.startActivity(intent) }
    }

    fun makePhoto(){

        photo.setImageResource(R.drawable.temp_photo)
        photo.translationZ = 6f

        photo.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.photo).toInt(),
            context.resources.getDimension(R.dimen.photo).toInt())
    }

    fun makeName(){

        pname.text = professor.name
        pname.translationZ = 6f

        pname.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.prof_tx_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        pname.setTextColor(context.resources.getColor(R.color.buttonRed))
        pname.textSize = 22f//context.resources.getDimension(R.dimen.font14)
        pname.setTextAppearance(context, R.style.fontFamily)
    }

    fun makeDepartment(){

        department.text = "Wydział: " + professor.department
        department.translationZ = 6f

        department.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.prof_tx_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        department.setTextColor(context.resources.getColor(R.color.darkGrey))
        department.textSize = 20f//context.resources.getDimension(R.dimen.font14)
        department.setTextAppearance(context, R.style.fontFamily)
    }

    fun makeGrades(){

        val g = professor.grades.size
        if(g != 1)
            grades.text = g.toString() + " ocen(y)"
        else
            grades.text = "1 ocena"

        grades.textAlignment = TEXT_ALIGNMENT_TEXT_END
        grades.translationZ = 6f

        grades.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.prof_tx_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        grades.setTextColor(context.resources.getColor(R.color.darkGrey))
        grades.textSize = 20f//context.resources.getDimension(R.dimen.font14)
        grades.setTextAppearance(context, R.style.fontFamily)
    }

    fun calcHeight(): Int{

        pname.measure(0,0)
        department.measure(0,0)
        grades.measure(0,0)
        var lines = (pname.measuredWidth / dpFactor).toInt() / 224
        if((pname.measuredWidth / dpFactor).toInt().rem(224) > -1)
            lines ++

        return pname.measuredHeight * lines + pname.measuredHeight + pname.measuredHeight + (6 * dpFactor).toInt()
    }

    fun create(layout: ConstraintLayout){

        setData()
        layout.addView(bg)
        layout.addView(photo)
        layout.addView(pname)
        layout.addView(department)
        layout.addView(grades)

        constrain(layout)
    }



}