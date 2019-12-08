package example.gradeprof

import android.content.Context
import android.os.Bundle
import android.content.Intent
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val i = intent
        professor = i.getSerializableExtra("professor") as Professor

        setData()

        closeButton.setOnClickListener{
            startActivity(Intent( this, MainScreen::class.java))}

        gradeButton.setOnClickListener{
            val intent = Intent( this, GradeForm::class.java)
            intent.putExtra("professor", professor)
            startActivity(intent)}
    }

    fun setData(){

        findViewById<TextView>(R.id.profName).text = professor.name
        findViewById<TextView>(R.id.informationText).text = professor.info

        findViewById<RatingBarSvg>(R.id.ratingZ).rating = professor.averagePassRate()
        findViewById<RatingBarSvg>(R.id.ratingD).rating = professor.averageAvailability()
        findViewById<RatingBarSvg>(R.id.ratingM).rating = professor.averageMerits()
    }

    fun likeIt(view: View){ findViewById<TextView>(R.id.likes).text = (findViewById<TextView>(R.id.likes).text.toString().toInt() + 1).toString() }



///-------------------------------------------------------------------------------------------------
    class OpinionElement(id : Int, context: Context, grade: Grade) {

        val opinion= grade
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

            makeBG()
            makeUser()
            makeText()
            makeLikes()
            makeUpButton()
            
        }

        fun constrain(layout: ConstraintLayout){

//            val constraint = ConstraintSet()
//            constraint.clone(layout)
//
//            val upperButtonID: Int
//            var margin: Int
//            if(id == 10){
//                upperButtonID = R.id.searchBox
//                margin = (28 * dpFactor).toInt()
//            }
//            else{
//                upperButtonID = id - 9
//                margin = (8 * dpFactor).toInt()
//            }
//
//            constraint.connect(bg.id, ConstraintSet.TOP, upperButtonID, ConstraintSet.BOTTOM, margin)
//            constraint.connect(bg.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
//            constraint.connect(bg.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
//
//            margin = context.resources.getDimension(R.dimen.fab_margin).toInt()
//            constraint.connect(user.id, ConstraintSet.TOP, bg.id, ConstraintSet.TOP, margin/4)
//            constraint.connect(user.id, ConstraintSet.LEFT, bg.id, ConstraintSet.LEFT, margin/4)
//
//            constraint.connect(pname.id, ConstraintSet.TOP, bg.id, ConstraintSet.TOP, margin/4)
//            constraint.connect(pname.id, ConstraintSet.LEFT, user.id, ConstraintSet.RIGHT, margin)
//
//            constraint.connect(department.id, ConstraintSet.TOP, pname.id, ConstraintSet.BOTTOM, 0)
//            constraint.connect(department.id, ConstraintSet.LEFT, user.id, ConstraintSet.RIGHT, margin)
//
//            constraint.connect(grades.id, ConstraintSet.TOP, department.id, ConstraintSet.BOTTOM, 0)
//            constraint.connect(grades.id, ConstraintSet.RIGHT, bg.id, ConstraintSet.RIGHT, margin/4)
//
//            constraint.applyTo(layout)
        }

        fun makeBG(){

            bg.background = context.resources.getDrawable(R.drawable.input, null)
            bg.translationZ = 0.1f

            bg.layoutParams = ConstraintLayout.LayoutParams(
                context.resources.getDimension(R.dimen.bg_width).toInt(),
                //calcHeight())
                (75*dpFactor).toInt())
        }

        fun makeUser(){

            user.text = opinion.author
            user.translationZ = 6f

            user.layoutParams = ConstraintLayout.LayoutParams(
                context.resources.getDimension(R.dimen.author_width).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT)
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

            bg.setOnClickListener{
                likes.text = (likes.text.toString().toInt() + 1).toString()
                }
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
}