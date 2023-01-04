package eu.tutorials.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_quiz_question.*
import kotlin.collections.ArrayList

class QuizQuestion : AppCompatActivity(),View.OnClickListener {

    private var currentQuestionNumber: Int = 1    // the question user is looking at
    private var selectedAnswer: Int = 0    // number of the selected answer
    private var questionList: ArrayList<Question>? = null   // list of all questions
    private var correctAnswers = 0
    // ? allows the variable to have null value
    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        userName = intent.getStringExtra(Constants.USER_NAME)

        questionList = Constants.getQuestions()

        setQuestions()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        submit.setOnClickListener(this)

    }

    /*
    function gets the current question from the list
    sets the button according to wether the question is the last or not
    refreshes the progressbar
    assign the labels of the answer options
     */
    private fun setQuestions(){

        val question: Question? = questionList!![currentQuestionNumber-1]

        // at the beggining we need to make all answers look default
        setDefault()

        // Change label of the button according to the question
        if (currentQuestionNumber == questionList!!.size)
            submit.text = "Finish"
        else
            submit.text = "Submit"

        // progressbar showin the number of the question
        progressBar.progress = currentQuestionNumber

        //concating progressbar msg 1/10
        tv_progress.text = "$currentQuestionNumber" + "/" + progressBar.max

        tv_question.text = question!!.question
        iv_image.setImageResource(question.image)
        tv_option_one.text = question.one
        tv_option_two.text = question.two
        tv_option_three.text = question.three
        tv_option_four.text = question.four
    }

    /*
    function makes the look of all answers to default
     */
    private fun setDefault() {

        // storing all annswers into a list
        val options = ArrayList<TextView>()
        options.add(0,tv_option_one)
        options.add(1,tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        // loop sets the default view of the answers
        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,R.drawable.default_option_border_bg
            )

        }
    }

    /*
    custom onClick listener checks which answer or the
    submit button was chosen
     */
    override fun onClick(p0: View?) {
        /*
        according to option ID the answer will be marked
         */
        when(p0?.id){

            R.id.tv_option_one ->{
                selectQuestion(tv_option_one,1)
            }

            R.id.tv_option_two ->{
                selectQuestion(tv_option_two,2)
            }

            R.id.tv_option_three ->{
                selectQuestion(tv_option_three,3)
            }

            R.id.tv_option_four ->{
                selectQuestion(tv_option_four,4)
            }

            R.id.submit ->{
                /* if user doesent mark any answer, than skips to answer, next
                question is loaded */
                if (selectedAnswer == 0) {
                    currentQuestionNumber++

                    when{
                        currentQuestionNumber <= questionList!!.size ->{
                            setQuestions()
                        }

                        else ->{
                            val intent = Intent(this, EndingActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS,correctAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, questionList!!.size)
                            startActivity(intent)
                        }

                    }

                }

                /* if answer is submitted app checks wether it is correct
                * or not. If it is, than the correct answer xml is loaded
                * and the option becomes green, if its incorrect, than becomes red .
                * Correct answer is always highlited . */

                else{
                    val question = questionList!!.get(currentQuestionNumber-1)

                    // if selected question is not correct
                    if (question!!.correct != selectedAnswer){
                        markAnswer(selectedAnswer, R.drawable.wrong_option_border_bg)
                    }
                    // if the answer is correct increment correctAnswers
                    else
                        correctAnswers++

                    markAnswer(question.correct, R.drawable.correct_option_border_bg)

                    /*
                    in case user is at the last question, buttons label changes to finish
                     */
                    if(currentQuestionNumber == questionList!!.size)
                        submit.setText("Finish")
                    else
                        submit.setText("Continue")

                    selectedAnswer = 0
                }
            }

        }

    }

    private fun markAnswer(answer: Int, drawableView: Int){
        when(answer) {
            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }


    /*
    function gets a textview which represents the answer
    and its rownumber, than sets all questions to default
    and marks the selected one as the chosen one
     */
    private fun selectQuestion(tv: TextView, selectedOptionnum: Int){

        setDefault()
        selectedAnswer= selectedOptionnum


        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
                this,R.drawable.default_option_border_bg
            )



    }
}
