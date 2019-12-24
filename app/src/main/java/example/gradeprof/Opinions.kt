package example.gradeprof

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_opinions.*
import kotlinx.android.synthetic.main.activity_opinions.closeButton
import java.lang.StringBuilder
import java.time.format.DateTimeFormatter

class Opinions : AppCompatActivity(){

    lateinit var gradesList: List<Grade>
    var gList = ArrayList<GradeElement>()
    private val m: Manager = Manager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opinions)

        gradesList = m.myGrades
        generatingGrades()

        closeButton.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }
    }

    private fun generatingGrades() {

        var id = 0
        for (g in gradesList) {
            id += 10
            gList.add(GradeElement(id, this@Opinions, g))
        }
        for (o in gList)
            o.create(findViewById(R.id.innerScrollLayout))
    }
}

///-------------------------------------------------------------------------------------------------
class GradeElement(val id: Int, val context: Context, grade: Grade) {

    var opinion= grade
    var who = TextView(context)
    var date = TextView(context)


    private val dpFactor= context.resources.displayMetrics.density
    init {
        who.id = id + 1
        date.id = id + 2

        System.out.println(toString())
    }

    override fun toString(): String{

        val ret = StringBuilder()
        ret.append("GradeElement: i").append(id).append(" who")
            .append(who.id).append(" when")
            .append(date.id).append(" \n")
            .append("Opinion: ").append(opinion.opinion)

        return ret.toString()
    }

    private fun setData(){

        makeWho()
        makeDate()
    }

    private fun constrain(layout: ConstraintLayout){

        val constraint = ConstraintSet()
        constraint.clone(layout)

        var upperOpinionID = ConstraintSet.PARENT_ID
        var margin = 0
        var marginSide = ConstraintSet.TOP
        if(id != 10){
            upperOpinionID = id - 9
            margin = (8 * dpFactor).toInt()
            marginSide = ConstraintSet.BOTTOM
        }

        constraint.connect(who.id, ConstraintSet.TOP, upperOpinionID, marginSide, margin)
        constraint.connect(who.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraint.connect(who.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)

        margin = context.resources.getDimension(R.dimen.fab_margin).toInt()
        constraint.connect(date.id, ConstraintSet.TOP, who.id, ConstraintSet.TOP, margin/4)
        constraint.connect(date.id, ConstraintSet.LEFT, who.id, ConstraintSet.LEFT, margin/4)

        constraint.applyTo(layout)
    }

    private fun makeDate(){

        date.text = opinion.date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm"))
        date.translationZ = 6f

        date.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        date.setTextColor(context.resources.getColor(R.color.darkRed))
        date.textSize = 20f
    }

    private fun makeWho(){

        val padding = context.resources.getDimension(R.dimen.fab_margin).toInt()

        who.text = Manager.getInstance().getExactProfessor(opinion).name
        who.translationZ = 5f
        who.background = context.resources.getDrawable(R.drawable.input, null)
        who.setPadding(padding/2, (padding * 1.5).toInt(), padding/2, padding/4)

        who.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.bg_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        who.setTextColor(context.resources.getColor(R.color.buttonRed))
        who.setTextAppearance(context, R.style.fontFamily)
        who.textSize = 24f
        who.setOnClickListener{
            val intent = Intent(context, Edit::class.java)
            intent.putExtra("uuid", opinion.uid)
            context.startActivity(intent) }
    }

    fun create(layout: ConstraintLayout){

        setData()
        layout.addView(who)
        layout.addView(date)

        constrain(layout)
    }
}