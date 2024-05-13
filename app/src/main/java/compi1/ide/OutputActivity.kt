package compi1.ide

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OutputActivity : AppCompatActivity() {
    companion object {
        var display: String = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_output)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var outputDisplay = findViewById<TextView>(R.id.outputDisplay)

        //navigation
        var closeBtn = findViewById<ImageButton>(R.id.closeBtn)
        closeBtn.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //buttons actions
        var clearConsoleBtn = findViewById<Button>(R.id.clearConsoleBtn)
        clearConsoleBtn.setOnClickListener{
            OutputActivity.display = "";
            outputDisplay.text = OutputActivity.display
        }

        //show message
        outputDisplay.text = OutputActivity.display
    }
}