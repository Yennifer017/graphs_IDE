package compi1.ide.elements.statements

import android.util.Log
import compi1.ide.elements.Executable
import compi1.ide.elements.others.MutableValue

class WhileStmt:Statement() {
    var condition: MutableValue? = null
    var executables:List<Executable> = ArrayList()
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        var graphsCode = ""
        try {
            val currentSymbolTable:HashMap<String, Any> = HashMap()
            var result = if(condition != null) condition!!.getConditionData(globalTable, currentSymbolTable, semanticErrors)
                        else false;
            while(result){
                for (i in executables.indices) {
                    executables.get(i).index = this.index
                    graphsCode += executables.get(i).execute(globalTable, internalTable, semanticErrors)
                    //Log.d("respuesta desde un while", executables.get(i).execute(globalTable, internalTable, semanticErrors))
                }
                result = if(condition != null) condition!!.getConditionData(globalTable, currentSymbolTable, semanticErrors)
                        else false;
            }
        } catch (e: Exception){
            semanticErrors.add("No se pudo ejecutar un while")
        }
        return graphsCode
    }

}