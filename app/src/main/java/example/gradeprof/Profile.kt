package example.gradeprof

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_profile.*
import java.lang.StringBuilder

class Profile : AppCompatActivity() {

    lateinit var professor: Professor
    lateinit var gList: ArrayList<Grade>
    var oList = ArrayList<OpinionElement>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val i = intent
        professor = i.getSerializableExtra("professor") as Professor
        gList = professor.grades

        setData()
        generatingOpinions()

        closeButton.setOnClickListener {
            startActivity(Intent(this, MainScreen::class.java))
        }

        gradeButton.setOnClickListener {
            val intent = Intent(this, GradeForm::class.java)
            intent.putExtra("professor", professor)
            startActivity(intent)
        }
    }

    fun setData() {

        findViewById<TextView>(R.id.profName).text = professor.name
        findViewById<TextView>(R.id.informationText).text = professor.info

        findViewById<RatingBarSvg>(R.id.ratingZ).rating = professor.averagePassRate()
        findViewById<RatingBarSvg>(R.id.ratingD).rating = professor.averageAvailability()
        findViewById<RatingBarSvg>(R.id.ratingM).rating = professor.averageMerits()
    }

    private fun generatingOpinions() {

        var id = 0
        for (g in gList) {
            id += 10
            oList.add(OpinionElement(id, this@Profile, g))
        }

        for (o in oList)
            o.create(findViewById(R.id.innerScrollLayout))
    }

    //fun likeIt(view: View){ findViewById<TextView>(R.id.likes).text = (findViewById<TextView>(R.id.likes).text.toString().toInt() + 1).toString() }
}


///-------------------------------------------------------------------------------------------------
class OpinionElement(id : Int, context: Context, grade: Grade) {

    var opinion= grade
    var id= id
    var context= context
    var bg = ImageView(context)
    var user = TextView(context)
    var text = TextView(context)
    var likes = TextView(context)
    var upButton = ImageButton(context)

    val dpFactor= context.resources.displayMetrics.density
    init {
        bg.id = id + 1
        user.id = id + 2
        text.id = id + 3
        likes.id = id + 4
        upButton.id = id + 5

        System.out.println(toString())
    }

    override fun toString(): String{

        val ret = StringBuilder()
        ret.append("OpinionElement: i").append(id).append(" b")
            .append(bg.id).append(" p")
            .append(user.id).append(" n")
            .append(text.id).append(" d")
            .append(likes.id).append(" g")
            .append(upButton.id).append("\n")
            .append("Opinion: ").append(opinion.opinion)

        return ret.toString()
    }

    fun setData(){

        makeUser()
        makeText()
        makeLikes()
        makeUpButton()
        makeBG()

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

        constraint.connect(bg.id, ConstraintSet.TOP, upperOpinionID, marginSide, margin)
        constraint.connect(bg.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraint.connect(bg.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)

        margin = context.resources.getDimension(R.dimen.fab_margin).toInt()
        constraint.connect(user.id, ConstraintSet.TOP, bg.id, ConstraintSet.TOP, margin/4)
        constraint.connect(user.id, ConstraintSet.LEFT, bg.id, ConstraintSet.LEFT, margin/4)

        constraint.connect(text.id, ConstraintSet.TOP, user.id, ConstraintSet.BOTTOM, margin/4)
        constraint.connect(text.id, ConstraintSet.LEFT, bg.id, ConstraintSet.LEFT, 0)
        constraint.connect(text.id, ConstraintSet.RIGHT, bg.id, ConstraintSet.RIGHT, 0)

        constraint.connect(likes.id, ConstraintSet.TOP, bg.id, ConstraintSet.TOP, margin/4)
        constraint.connect(likes.id, ConstraintSet.RIGHT, bg.id, ConstraintSet.RIGHT, margin)

        constraint.connect(upButton.id, ConstraintSet.TOP, bg.id, ConstraintSet.TOP, margin/4)
        constraint.connect(upButton.id, ConstraintSet.RIGHT, likes.id, ConstraintSet.LEFT, margin/4)

        constraint.applyTo(layout)
    }

    fun makeBG(){

        bg.background = context.resources.getDrawable(R.drawable.input, null)
        bg.translationZ = 0.1f

        bg.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.bg_width).toInt(),
            (71 * dpFactor).toInt())
            //calcHeight())
    }

    fun makeUser(){

        user.text = opinion.author
        user.translationZ = 6f

        user.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.author_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        user.setTextColor(Color.BLACK)
        user.textSize = 16f
    }

    fun makeText(){

        text.text = opinion.opinion
        text.translationZ = 6f

        text.layoutParams = ConstraintLayout.LayoutParams(
            (312*dpFactor).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        text.setTextColor(context.resources.getColor(R.color.darkRed))
        text.textSize = 14f//context.resources.getDimension(R.dimen.font14)
        //text.setTextAppearance(context, R.style.fontFamily)
    }

    fun makeLikes(){

        likes.text = opinion.likes.toString()
        likes.translationZ = 6f

        likes.layoutParams = ConstraintLayout.LayoutParams(
            (60*dpFactor).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        likes.textAlignment = View.TEXT_ALIGNMENT_CENTER

        likes.setTextColor(context.resources.getColor(R.color.buttonRed))
        likes.textSize = 16f//context.resources.getDimension(R.dimen.font14)
        likes.setTextAppearance(context, R.style.fontFamily)
    }

    fun makeUpButton(){

        upButton.setImageResource(R.drawable.ic_1up)

        upButton.translationZ = 6f

        upButton.layoutParams = ConstraintLayout.LayoutParams(
            (25*dpFactor).toInt(),
            (21*dpFactor).toInt())
        upButton.setBackgroundColor(Color.argb(0,0,0,0))

        //TODO zmienić na likeTap() -> potrzebny index (Login.kt->TODO)
        upButton.setOnClickListener{
            opinion.tempLikes++
            likes.text = opinion.tempLikes.toString()
            }

        upButton.scrollBarStyle
    }

    fun calcHeight(): Int{

        user.measure(0,0)
        text.measure(0,0)
        var uLines = (user.measuredWidth / dpFactor).toInt() / 226
        if((user.measuredWidth / dpFactor).toInt().rem(226) > -1)
            uLines ++
        var tLines = (user.measuredWidth / dpFactor).toInt() / 312
        if((text.measuredWidth / dpFactor).toInt().rem(312) > -1)
            tLines ++


        return user.measuredHeight * uLines + text.measuredHeight * tLines + (16 * dpFactor).toInt()
    }

    fun create(layout: ConstraintLayout){

        setData()
        layout.addView(bg)
        layout.addView(user)
        layout.addView(text)
        layout.addView(likes)
        layout.addView(upButton)

        constrain(layout)
    }
}
