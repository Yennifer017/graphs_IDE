package compi1.ide

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import compi1.ide.util.FilesUtil


class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_options)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /*NAVIGATION*/
        val optionsBtn = findViewById<ImageButton>(R.id.closeOpBtn)
        optionsBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        /*OPEN FILE*/
        var activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) { //acciones que se realizan cuando el usuario selecciona el archivo
                    val selectedFileUri = result.data?.data
                    val selectedFilePath = selectedFileUri?.let { uri ->
                        val contentResolver = this.contentResolver
                        val cursor = contentResolver.query(uri, null, null, null, null)
                        cursor?.let {
                            it.moveToFirst()
                            val columnIndex = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                            val filePath = it.getString(columnIndex)
                            it.close()
                            filePath
                        }
                    }
                    if (selectedFilePath != null) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("path", selectedFilePath)
                        startActivity(intent)
                    }
                }
        }
        val openFileBtn = findViewById<Button>(R.id.openFileBtn)
        openFileBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "text/plain"
            activityResultLauncher.launch(Intent.createChooser(intent, "Selecciona un proyecto"))
        }


    }
}