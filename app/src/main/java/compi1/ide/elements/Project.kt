package compi1.ide.elements

import android.util.Log
import compi1.ide.elements.exceptions.NoCandidateExecutException
import compi1.ide.elements.exceptions.SemanticException
import compi1.ide.traductor.HtmlGen
import java_cup.runtime.Symbol


class Project {
    //recovery by parser
    var title:Symbol? = null
    var description:Symbol? = null
    var header:Symbol? = null
    var footer:Symbol? = null
    var backgroundColor:Symbol? = null
    var fontFamilyS:Symbol? = null
    var fontSizeS:Symbol? = null

    var keyWords:List<Symbol> = ArrayList()
    var data:List<Executable> = ArrayList()

    //utilities for the analysis
    var globalSymbolTable: HashMap<String, Any> = HashMap()
    var HtmlGen = HtmlGen()
    var semanticErrors:ArrayList<String> = ArrayList()

    fun canGenerate():Boolean{
        return title != null && backgroundColor != null
    }

    fun getCode():String {
        if(canGenerate()){
            var codeFunction = "";
            for (i in data.indices) {
                codeFunction += data.get(i).execute(globalSymbolTable, null, semanticErrors)
            }
            if(semanticErrors.isNotEmpty()){
                throw SemanticException("Se encontraron incongruencias semanticas")
            }
            Log.d("contenido de codeFuncion", codeFunction)
            return codeFunction;
        } else {
            throw NoCandidateExecutException("Le faltan para metros al proyecto, no se pudo exportar")
        }
    }

}