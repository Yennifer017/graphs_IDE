package compi1.ide

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import compi1.ide.util.FilesUtil


class MainActivity : AppCompatActivity() {
    var filesUtil:FilesUtil = FilesUtil()
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
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
        val outputBtn = findViewById<ImageButton>(R.id.outputBtn)
        outputBtn.setOnClickListener{
            val intent = Intent(this, OutputActivity::class.java)
            startActivity(intent)
        }

        //custoum edit text
        val editText = findViewById<CustomEditText>(R.id.inputCode)
        editText.textView = findViewById<TextView>(R.id.columAndLineDisp)

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


}