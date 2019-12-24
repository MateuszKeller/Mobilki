package example.gradeprof

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.*
import android.view.View.TEXT_ALIGNMENT_TEXT_END
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_main_screen.*
import java.lang.StringBuilder

//TODO linesCount()

class MainScreen : AppCompatActivity() {

    private var professorElements = ArrayList<ProfElement>()
    private val m: Manager = Manager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        if(intent.getBooleanExtra("startup", false))
        Toast.makeText(applicationContext, "Witaj: " + m.user, Toast.LENGTH_LONG ).show()

        m.addDataStatusListener("MainScreenListener") { refreshElements(it)}
    }

    private fun refreshElements(professorList : List<Professor>){
        print("Refresh buttons")

        var list = professorList
        searchBox.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                list = this@MainScreen.m.searchProfs(s.toString())
            }
        })

        professorElements.clear()
        var id = 0
        for (p in list) {
            id += 10
            println("PROF: " + id + p.id)
            professorElements.add(ProfElement(id, this@MainScreen, p))

            if(intent.getBooleanExtra("startup", false))
            t(p)
        }
        for (b in professorElements) {
            b.create(findViewById(R.id.innerScrollLayout))
        }
    }

    ///TODO DEL - FOR TESTING
    fun t(p: Professor){

        p.addGrade(Grade(
            4.5f,
            3.1f,
            2.2f,
            resources.getString(R.string.OT1),
            "216724"))
        p.addGrade(Grade(
            4.5f,
            3.1f,
            2.2f,
            resources.getString(R.string.OT2),
            m.indexNumber))
    }

    fun showPopupMenu(view: View) {
        dimming.visibility = VISIBLE
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_main, popupMenu.menu)
        popupMenu.show()


        popupMenu.setOnMenuItemClickListener {
            val intent = Intent(this, Opinions::class.java)
            startActivity(intent)
            when(it.itemId) {
                R.id.item1 -> startActivity(intent)
            }
            true
        }
    }
}

class ProfElement(val id: Int, private val context: Context, private val professor: Professor) {

    var bg = Button(context)
    var photo = ImageView(context)
    var pname = TextView(context)
    var department = TextView(context)
    var grades = TextView(context)

    private val dpFactor= context.resources.displayMetrics.density

    init {
        bg.id = id + 1
        photo.id = id + 2
        pname.id = id + 3
        department.id = id + 4
        grades.id = id + 5

        println(toString())
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

    private fun setData(){

        makePhoto()
        makeName()
        makeDepartment()
        makeGrades()
        makeBG()
    }

    private fun constrain(layout: ConstraintLayout){

        val constraint = ConstraintSet()
        constraint.clone(layout)

        val upperButtonID: Int
        var margin = 0
        val marginSide : Int
        if(id == 10) {
            upperButtonID = ConstraintSet.PARENT_ID
            marginSide = ConstraintSet.TOP
        }
        else{
            upperButtonID = id - 9
            margin = (8 * dpFactor).toInt()
            marginSide = ConstraintSet.BOTTOM
        }

        constraint.connect(bg.id, ConstraintSet.TOP, upperButtonID, marginSide, margin)
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

    private fun makeBG(){

        bg.background = context.resources.getDrawable(R.drawable.input, null)
        bg.translationZ = 5f

        bg.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.bg_width).toInt(),
            calcHeight())

        bg.setOnClickListener{
            val intent = Intent( context, Profile::class.java)
            intent.putExtra("professor", professor.id)
            context.startActivity(intent) }
    }

    private fun makePhoto(){

        photo.setImageResource(R.drawable.temp_photo)
        photo.translationZ = 6f

        photo.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.photo).toInt(),
            context.resources.getDimension(R.dimen.photo).toInt())
    }

    private fun makeName(){

        pname.text = professor.name
        pname.translationZ = 6f

        pname.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.prof_tx_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        pname.setTextColor(context.resources.getColor(R.color.buttonRed))
        pname.textSize = 22f//context.resources.getDimension(R.dimen.font14)
        pname.setTextAppearance(context, R.style.fontFamily)
    }

    @SuppressLint("SetTextI18n")
    private fun makeDepartment(){

        val dep = professor.department
        department.text = "Wydział: $dep"
        department.translationZ = 6f

        department.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.prof_tx_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        department.setTextColor(context.resources.getColor(R.color.darkGrey))
        department.textSize = 20f//context.resources.getDimension(R.dimen.font14)
        department.setTextAppearance(context, R.style.fontFamily)
    }

    @SuppressLint("SetTextI18n")
    private fun makeGrades(){

        val g = professor.grades.size
        if(g != 1)
            grades.text = "$g ocen(y)"
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

    private fun calcHeight(): Int{

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