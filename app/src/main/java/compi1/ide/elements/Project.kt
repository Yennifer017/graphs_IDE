package compi1.ide.elements

import android.util.Log
import compi1.ide.elements.data.DataContainer
import compi1.ide.elements.exceptions.NoCandidateExecutException
import compi1.ide.elements.exceptions.SemanticException
import compi1.ide.traductor.HtmlGen
import compi1.ide.util.Index
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
    var htmlGen = HtmlGen()
    var semanticErrors:ArrayList<String> = ArrayList()
    var index:Index = Index()

    fun canGenerate():Boolean{
        return title != null && backgroundColor != null && keyWords.isNotEmpty() && header != null
                && footer != null && fontFamilyS != null && fontSizeS !=  null
    }

    fun getCode():String {
        if(canGenerate()){
            var codeFunction = "";
            for (i in data.indices) {
                data.get(i).index = this.index
                codeFunction += data.get(i).execute(globalSymbolTable, null, semanticErrors)
            }
            //generate the output code
            var code:String = htmlGen.getInitialCode(this)
            code += htmlGen.getCallBackFunctions(index.current)
            code += codeFunction
            code += htmlGen.getBodyCode(this)
            code += htmlGen.getDivsCode(index.current)
            code += htmlGen.getFinalCode(this)
            if(semanticErrors.isNotEmpty()){
                throw SemanticException("Se encontraron incongruencias semanticas")
            }
            return code;
        } else {
            throw NoCandidateExecutException("Le faltan para metros al proyecto, no se pudo exportar")
        }
    }

}