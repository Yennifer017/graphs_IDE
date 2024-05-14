package compi1.ide

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import compi1.ide.traductor.Traductor
import compi1.ide.util.FilesUtil


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
            val output = traductor.analizate(editText.text.toString())
            OutputActivity.display = output
        }

        //files
        var bundle = intent.extras
        //abrir el archivo
        val path = bundle?.getString("path")
        if (path != null) {
            println("Se ha encontrado un path")
            filesUtil.currentPath = path
            println(path)
            editText.setText(filesUtil.readFromFile(this, path))
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