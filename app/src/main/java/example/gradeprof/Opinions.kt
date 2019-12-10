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

class Opinions : AppCompatActivity(){

    lateinit var grades: ArrayList<Grade>
    var gList = ArrayList<GradeElement>()
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

        testText.setOnClickListener{
            val intent = Intent(this, Edit::class.java)
            intent.putExtra("index", user)
            startActivity(intent)
        }

    }

    private fun generatingGrades() {

        var id = 0
        for (g in grades) {
            id += 10
            gList.add(GradeElement(id, this@Opinions, g, user))
        }
        for (o in gList)
            o.create(findViewById(R.id.innerScrollLayout))
    }
}

///-------------------------------------------------------------------------------------------------
class GradeElement(id : Int, context: Context, grade: Grade, user: String) {

    var opinion= grade
    var id= id
    var context= context
    val user = user
    var who = TextView(context)
    var date = TextView(context)
    

    val dpFactor= context.resources.displayMetrics.density
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

    fun setData(){

        makeWho()
        makeDate()
    }

    fun constrain(layout: ConstraintLayout){

        val constraint = ConstraintSet()
        constraint.clone(layout)

        var upperOpinionID = ConstraintSet.PARENT_ID
        var margin = 0
        var marginSide = ConstraintSet.TOP
        if(id != 10){
            upperOpinionID = id - 9
            margin = (4 * dpFactor).toInt()
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

    fun makeDate(){

        date.text = opinion.author
        date.translationZ = 6f

        date.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.author_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        date.setTextColor(Color.BLACK)
        date.textSize = 16f
    }

    fun makeWho(){

        val margin = context.resources.getDimension(R.dimen.fab_margin).toInt()
        who.text = opinion.opinion
        who.translationZ = 5f
        who.background = context.resources.getDrawable(R.drawable.input, null)
        who.setPadding(margin/2, (margin * 1.5).toInt(), margin/2, margin/4)

        who.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.bg_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        who.setTextColor(context.resources.getColor(R.color.darkRed))
        who.textSize = 14f//context.resources.getDimension(R.dimen.font14)
        //text.setTextAppearance(context, R.style.fontFamily)
    }

//    fun makeLikes(){
//
//        likes.text = opinion.likes.toString()
//        likes.translationZ = 6f
//
//        likes.layoutParams = ConstraintLayout.LayoutParams(
//            (60*dpFactor).toInt(),
//            ViewGroup.LayoutParams.WRAP_CONTENT)
//
//        likes.textAlignment = View.TEXT_ALIGNMENT_CENTER
//
//        //likes.setTextColor(context.resources.getColor(R.color.basicGrey)) -> in setLikesColors()
//        likes.textSize = 16f//context.resources.getDimension(R.dimen.font14)
//        likes.setTextAppearance(context, R.style.fontFamily)
//    }
//
//    fun makeUpButton(){
//
//        upButton.setBackgroundColor(Color.argb(0,0,0,0))
//        upButton.translationZ = 6f
//
//        setLikesColors(opinion.isLiked(user))
//
//        upButton.layoutParams = ConstraintLayout.LayoutParams(
//            (25*dpFactor).toInt(),
//            (21*dpFactor).toInt())
//
//        upButton.setOnClickListener{
//
//            setLikesColors(opinion.likeTap(user))
//            likes.text = opinion.likes.toString()
//        }
//    }
//
//    fun setLikesColors(function: Boolean){
//
//        if(function){
//
//            upButton.setImageResource(R.drawable.ic_1up_red)
//            likes.setTextColor(context.resources.getColor(R.color.buttonRed))
//        }
//        else{
//            upButton.setImageResource(R.drawable.ic_1up_grey)
//            likes.setTextColor(context.resources.getColor(R.color.redishGrey))
//        }
//    }

    fun create(layout: ConstraintLayout){

        setData()
        layout.addView(who)
        layout.addView(date)

        constrain(layout)
    }
}