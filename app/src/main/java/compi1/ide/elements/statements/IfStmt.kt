package compi1.ide.elements.statements

import android.util.Log
import compi1.ide.elements.Executable
import compi1.ide.elements.others.MutableValue

class IfStmt:Statement() {
    var condition: MutableValue? = null;
    var executables:List<Executable> = ArrayList()
    var elseStmt:List<Executable>? = ArrayList()
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        var graphsCode = ""
        try{
            val currentSymbolTable:HashMap<String, Any> = HashMap()
            val result = if(condition != null) condition!!.getConditionData(globalTable, currentSymbolTable, semanticErrors)
            else false
            if(result){
                for (i in executables.indices) {
                    executables.get(i).index = this.index
                    graphsCode += executables.get(i).execute(globalTable, internalTable, semanticErrors)
                    //Log.d("respuesta desde un if", executables.get(i).execute(globalTable, internalTable, semanticErrors))
                }
            } else if(elseStmt != null){
                for (i in executables.indices) {
                    executables.get(i).index = this.index
                    graphsCode += executables.get(i).execute(globalTable, internalTable, semanticErrors)
                    //Log.d("respuesta desde un else", elseStmt!!.get(i).execute(globalTable, internalTable, semanticErrors))
                }
            }
        } catch(e: Exception){
            semanticErrors.add("No se pudo ejecutar un if")
        }
        return graphsCode
    }

}