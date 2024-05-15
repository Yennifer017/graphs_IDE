package compi1.ide.elements.statements

import android.util.Log
import compi1.ide.elements.Executable
import compi1.ide.elements.others.MutableValue

class ForStmt:Statement() {
    var firstAsign:Asignation? = null
    var increment:Asignation? = null
    var condition: MutableValue? = null
    var executables:List<Executable> = ArrayList()
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        var graphsCode = "";
        try {
            val currentSymbolTable:HashMap<String, Any> = HashMap()
            var result = if(condition != null) condition!!.getConditionData(globalTable, currentSymbolTable, semanticErrors)
            else false;
            firstAsign?.execute(globalTable, currentSymbolTable, semanticErrors)
            while (result){
                for (i in executables.indices) {
                    executables.get(i).index = this.index
                    graphsCode += executables.get(i).execute(globalTable, internalTable, semanticErrors)
                    //Log.d("respuesta desde un for", executables.get(i).execute(globalTable, internalTable, semanticErrors))
                }
                increment?.execute(globalTable, currentSymbolTable, semanticErrors)
                result = if(condition != null) condition!!.getConditionData(globalTable, currentSymbolTable, semanticErrors)
                        else false;
            }

        } catch (e: Exception){
            semanticErrors.add("No se pudo ejecutar un for")
        }
        return graphsCode
    }
}