package eu.tutorials.quiz


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        // if username field is empty, and the button is clicked, than the toast shows up
        btn_start.setOnClickListener {
            if (at_name.text.toString().isEmpty()){

                // Toast - small popup msg
                Toast.makeText(this,"Enter your name",Toast.LENGTH_SHORT).show()
            }
            else
            {
                // Intent is an object that provides runtime binding between separate components,
                // such as two activities. The intent starts another activity.
                val intent = Intent(this, QuizQuestion::class.java)
                // saving username into the intent
                intent.putExtra(Constants.USER_NAME, at_name.text.toString())
                startActivity(intent)
                finish()

            }

        }
    }
}
