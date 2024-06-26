package compi1.ide.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import com.itextpdf.text.Document
import com.itextpdf.text.html.simpleparser.HTMLWorker
import com.itextpdf.text.pdf.PdfWriter
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringReader


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

    fun leerArchivo(uri: Uri, actividad: Context): String {
        try {
            val inputStream = actividad.contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
                stringBuilder.append("\n")
            }
            inputStream?.close()
            return stringBuilder.toString()
        } catch (e: Exception) {
            Toast.makeText(actividad, "Error al leer el archivo", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
        return ""
    }

    fun createFile(fileName:String, texto: String, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val archivo = File(context.getExternalFilesDir(null), fileName)
            try {
                if (!archivo.exists()) {
                    archivo.createNewFile()
                    Toast.makeText(context, "ARCHIVO CREADO", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "El archivo ya existe", Toast.LENGTH_SHORT).show()
                }
                archivo.writeText(texto) // Escribir el texto en el archivo
            } catch (e: IOException) {
                Toast.makeText(context, "Error al exportar", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        } else {
            val path = Environment.getExternalStorageDirectory().absolutePath + "/" + fileName
            val archivo = File(path)
            try {
                if (!archivo.exists()) {
                    archivo.createNewFile()
                    Toast.makeText(context, "ARCHIVO CREADO", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "El archivo ya existe", Toast.LENGTH_SHORT).show()
                }
                archivo.writeText(texto) // Escribir el texto en el archivo
            } catch (e: IOException) {
                Toast.makeText(context, "Error al crear el archivo", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun generatePdf(fileName:String, content:String, context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val archivo = File(context.getExternalFilesDir(null), fileName)
            try {
                if (!archivo.exists()) {
                    val document = Document()
                    PdfWriter.getInstance(document, FileOutputStream(archivo))
                    document.open()
                    val htmlWorker = HTMLWorker(document)
                    htmlWorker.parse(StringReader(content))
                    document.close();
                    Toast.makeText(context, "ARCHIVO CREADO", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "El archivo ya existe", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                Toast.makeText(context, "Error al exportar", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        } else {
            val path = Environment.getExternalStorageDirectory().absolutePath + "/" + fileName
            val archivo = File(path)
            try {
                if (!archivo.exists()) {

                    val document = Document()
                    PdfWriter.getInstance(document, FileOutputStream(archivo))
                    document.open()
                    val htmlWorker = HTMLWorker(document)
                    htmlWorker.parse(StringReader(content))
                    document.close()

                    val htmlFile = File("file:///android_asset/graficas.html")
                    val pdfFile = File(Environment.getExternalStorageDirectory(), "graficas.pdf")


                    Toast.makeText(context, "ARCHIVO CREADO", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "El archivo ya existe", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                Toast.makeText(context, "Error al crear el archivo", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

}