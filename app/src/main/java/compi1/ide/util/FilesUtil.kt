package compi1.ide.util

import android.content.Context
import android.widget.Toast
import java.io.File
import java.io.Serializable


class FilesUtil{
    var currentPath:String = "";

    fun readFromFile(context: Context, filePath: String): String {
        try {
            val file = File(filePath)
            val stringBuilder = StringBuilder()
            file.forEachLine {
                stringBuilder.appendLine(it)
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            println(e)
            Toast.makeText(context, "Ocurrio un error al leer el archivo", Toast.LENGTH_SHORT).show()
            return ""
        }
    }
}