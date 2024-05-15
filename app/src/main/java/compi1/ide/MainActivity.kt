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
import compi1.ide.code_analysis.IdentationLexer
import compi1.ide.traductor.Traductor
import compi1.ide.util.FilesUtil
import java.io.StringReader


class MainActivity : AppCompatActivity() {
    var filesUtil:FilesUtil = FilesUtil()
    var traductor:Traductor = Traductor()

    companion object {
        var backupContent: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //change views
        val optionsBtn = findViewById<Button>(R.id.settingsBtn)
        optionsBtn.setOnClickListener{
            keepInputText()
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
        val outputBtn = findViewById<ImageButton>(R.id.outputBtn)
        outputBtn.setOnClickListener{
            keepInputText()
            val intent = Intent(this, OutputActivity::class.java)
            startActivity(intent)
        }

        //custom edit text
        val editText = findViewById<CustomEditText>(R.id.inputCode)
        editText.textView = findViewById<TextView>(R.id.columAndLineDisp)

        //button actions
        val executeBtn = findViewById<Button>(R.id.executeBtn)
        executeBtn.setOnClickListener{
            val output = traductor.analizate(editText.text.toString(), this)
            OutputActivity.display = output
        }

        val clear = findViewById<ImageButton>(R.id.clearBtn)
        clear.setOnClickListener{
            backupContent = ""
            editText.setText("")
        }

        //files
        val bundle = intent.extras
        //abrir el archivo
        val content = bundle?.getString("content")
        if (content != null) {
            editText.setText(content)
            backupContent = content
        }

        //exportation
        val message = bundle?.getString("pdf")
        if(message != null){
            val output = traductor.exportPdf(backupContent, this)
            //val output = traductor.analizate(editText.text.toString(), this)
            OutputActivity.display = output
        }

        val ident = bundle?.getString("ident")
        if(ident != null){
            val reader = StringReader(backupContent)
            val lexer = IdentationLexer(reader)
            while (!lexer.yyatEOF()) {
                lexer.yylex()
            }
            val text = lexer.string.toString()
            backupContent = text
            editText.setText(text)
        }
    }

    override fun onResume(){
        super.onResume()
        //setting current text
        var editText = findViewById<CustomEditText>(R.id.inputCode)
        editText.setText(backupContent)
    }

    override fun onPause(){
        super.onPause()
        keepInputText()
    }

    private fun keepInputText(){
        var editText = findViewById<CustomEditText>(R.id.inputCode)
        backupContent = editText.text.toString()
    }


}