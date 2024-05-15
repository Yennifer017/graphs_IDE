package compi1.ide.traductor

import android.util.Log
import compi1.ide.code_analysis.Lexer
import compi1.ide.code_analysis.parser
import compi1.ide.elements.exceptions.SemanticException
import java.io.StringReader


class Traductor {
    fun analizate(text: String): String{
        val lexer = Lexer(StringReader(text))
        val parser = parser(lexer)
        try {
            parser.parse()
            if (lexer.errors.isEmpty() && parser.syntaxErrors.isEmpty()) {
                val project = parser.project
                try {
                    val code = project.getCode()
                    Log.d("codigo generado", code)
                    return("Exportacion exitosa")
                } catch (e: SemanticException) {
                    return("ERRORES SEMANTICOS\n" + getErrors(project.semanticErrors))
                } catch (e: Exception){
                    return("Error: ${e.message}")
                }
            } else {
                var output: String = "ERRORES LEXICOS\n"
                output += getErrors(lexer.errors)

                output += "\n\nERRORES SINTACTICOS\n"
                output += getErrors(parser.syntaxErrors)

                return output
            }
        } catch (e: Exception) {
            return "Ocurrio un error fatal al tratar de exportar :/\n" + e.toString()
        }
    }

    private fun getErrors(errors: List<String>): String {
        var code = ""
        for (i in errors.indices) {
            code += errors[i] + "\n"
        }
        if(code.isEmpty()){
            code = "Ningun error"
        }
        return code
    }


}