package compi1.ide.elements.statements

import android.util.Log
import compi1.ide.elements.Executable
import compi1.ide.elements.others.MutableValue

class DoWhileStmt:Statement() {
    var condition: MutableValue? = null
    var executables:List<Executable> = ArrayList()
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        var codeGraphs = ""
        try {
            val currentSymbolTable:HashMap<String, Any> = HashMap()
            var result = true
            do {
                for (i in executables.indices) {
                    executables.get(i).index = this.index
                    codeGraphs += executables.get(i).execute(globalTable, internalTable, semanticErrors)
                    //Log.d("respuesta desde un while", executables.get(i).execute(globalTable, internalTable, semanticErrors))
                }
                result = if(condition != null) condition!!.getConditionData(globalTable, currentSymbolTable, semanticErrors)
                                    else false;
            }while (result)
        } catch (e: Exception){
            semanticErrors.add("No se pudo ejecutar un while")
        }
        return codeGraphs;
    }

}