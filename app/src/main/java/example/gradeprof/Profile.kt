package example.gradeprof

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.activity_profile.*
import java.lang.StringBuilder

class Profile : AppCompatActivity() {

    lateinit var professor: Professor
    lateinit var gradesList: List<Grade>
    var opinionElements = ArrayList<OpinionElement>()
    private val m: Manager = Manager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val professorId =  intent.getStringExtra("professor")

        Manager.getInstance().addDataStatusListener("ProfileListener", {professorList -> refreshElements(professorList, professorId) })


//        gradesList = professor.grades



        closeButton.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }

        gradeButton.setOnClickListener {
            if(m.getExactProfessor(professorId).didUserGrade(m.user)){
                Toast.makeText(applicationContext, "Już oceniłeś tego wykładowcę", Toast.LENGTH_LONG ).show()

            } else {
                val intent = Intent(this, GradeForm::class.java)
                intent.putExtra("professor", professorId)
                startActivity(intent)
            }
        }
    }

    private fun refreshElements(professorList: List<Professor>, professorId : String){
        lateinit var professor : Professor
        for(prof in professorList){
            if(prof.id.equals(professorId)){
                professor = prof
            }
        }
        setData(professor)
        generatingOpinions(professor.grades)
    }

    private fun setData(professor : Professor) {

        findViewById<TextView>(R.id.profName).text = professor.name
        findViewById<TextView>(R.id.informationText).text = professor.info

        findViewById<RatingBarSvg>(R.id.ratingZ).rating = professor.averagePassRate()
        findViewById<RatingBarSvg>(R.id.ratingD).rating = professor.averageAvailability()
        findViewById<RatingBarSvg>(R.id.ratingM).rating = professor.averageMerits()
    }

    private fun generatingOpinions(grades : List<Grade>) {
        opinionElements.clear()
        var id = 0
        for (g in grades) {
            if(g.opinion == "" || g.opinion == null) continue

            id += 10
            opinionElements.add(OpinionElement(id, this@Profile, g, m.indexNumber, intent.getStringExtra("professor")))
        }
        for (o in opinionElements)
            o.create(findViewById(R.id.innerScrollLayout))
    }
}

///-------------------------------------------------------------------------------------------------
class OpinionElement(var id: Int, var context: Context, grade: Grade, val user: String, var profId: String) {

    var gradeId = grade.uid
    var professorId = profId
    var opinion= grade
    var author = TextView(context)
    var text = TextView(context)
    var likes = TextView(context)
    var upButton = ImageButton(context)

    private val dpFactor= context.resources.displayMetrics.density
    init {
        text.id = id + 1
        author.id = id + 2
        likes.id = id + 3
        upButton.id = id + 4

        System.out.println(toString())
    }

    override fun toString(): String{

        val ret = StringBuilder()
        ret.append("OpinionElement: i").append(id).append(" t")
            .append(text.id).append(" u")
            .append(author.id).append(" l")
            .append(likes.id).append(" u")
            .append(upButton.id).append("\n")
            .append("Opinion: ").append(opinion.opinion)

        return ret.toString()
    }

    private fun setData(){

        makeAuthor()
        makeText()
        makeLikes()
        makeUpButton()
    }

    private fun constrain(layout: ConstraintLayout){

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

        constraint.connect(text.id, ConstraintSet.TOP, upperOpinionID, marginSide, margin)
        constraint.connect(text.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 0)
        constraint.connect(text.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)

        margin = context.resources.getDimension(R.dimen.fab_margin).toInt()
        constraint.connect(author.id, ConstraintSet.TOP, text.id, ConstraintSet.TOP, margin/4)
        constraint.connect(author.id, ConstraintSet.LEFT, text.id, ConstraintSet.LEFT, margin/4)

        constraint.connect(likes.id, ConstraintSet.TOP, text.id, ConstraintSet.TOP, margin/4)
        constraint.connect(likes.id, ConstraintSet.RIGHT, text.id, ConstraintSet.RIGHT, margin)

        constraint.connect(upButton.id, ConstraintSet.TOP, text.id, ConstraintSet.TOP, margin/4)
        constraint.connect(upButton.id, ConstraintSet.RIGHT, likes.id, ConstraintSet.LEFT, margin/4)

        constraint.applyTo(layout)
    }

    private fun makeAuthor(){

        author.text = opinion.displayName
        author.translationZ = 6f

        author.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.author_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        author.setTextColor(Color.BLACK)
        author.textSize = 16f
    }

    private fun makeText(){

        val margin = context.resources.getDimension(R.dimen.fab_margin).toInt()
        text.text = opinion.opinion
        text.translationZ = 5f
        text.background = context.resources.getDrawable(R.drawable.input, null)
        text.setPadding(margin/2, (margin * 1.5).toInt(), margin/2, margin/4)

        text.layoutParams = ConstraintLayout.LayoutParams(
            context.resources.getDimension(R.dimen.bg_width).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        text.setTextColor(context.resources.getColor(R.color.darkRed))
        text.textSize = 14f//context.resources.getDimension(R.dimen.font14)
        //text.setTextAppearance(context, R.style.fontFamily)
    }

    private fun makeLikes(){

        likes.text = opinion.likes.toString()
        likes.translationZ = 6f

        likes.layoutParams = ConstraintLayout.LayoutParams(
            (60*dpFactor).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT)

        likes.textAlignment = View.TEXT_ALIGNMENT_CENTER

        //likes.setTextColor(context.resources.getColor(R.color.basicGrey)) -> in setLikesColors()
        likes.textSize = 16f//context.resources.getDimension(R.dimen.font14)
        likes.setTextAppearance(context, R.style.fontFamily)
    }

    private fun makeUpButton(){

        upButton.setBackgroundColor(Color.argb(0,0,0,0))
        upButton.translationZ = 6f

        setLikesColors(opinion.isLiked(Manager.getInstance().indexNumber))

        upButton.layoutParams = ConstraintLayout.LayoutParams(
            (25*dpFactor).toInt(),
            (21*dpFactor).toInt())

        upButton.setOnClickListener{

            setLikesColors(opinion.likeTap(Manager.getInstance().indexNumber, professorId, gradeId))
            likes.text = opinion.likes.toString()
        }
    }

    private fun setLikesColors(function: Boolean){

        if(function){

            upButton.setImageResource(R.drawable.ic_1up_red)
            likes.setTextColor(context.resources.getColor(R.color.buttonRed))
        }
        else{
            upButton.setImageResource(R.drawable.ic_1up_grey)
            likes.setTextColor(context.resources.getColor(R.color.redishGrey))
        }
    }

    fun create(layout: ConstraintLayout){

        setData()
        layout.addView(author)
        layout.addView(text)
        layout.addView(likes)
        layout.addView(upButton)

        constrain(layout)
    }
}
