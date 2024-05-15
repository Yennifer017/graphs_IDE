package compi1.ide.traductor

import compi1.ide.code_analysis.sym
import java_cup.runtime.Symbol

class DataCollector {
    fun getData(
        symbol:Symbol?,
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>):String {
        if(symbol == null){
            semanticErrors.add("Simbolo nulo, no se pudo recuperar el dato")
            return "nothing"
        } else {
            if (symbol.sym == sym.VARIABLE) {
                var assigned: Any? =
                    globalTable.get(symbol.value.toString()) //obtener el valor de la variable
                if (assigned == null && internalTable != null) {
                    assigned = internalTable.get(symbol.value.toString())
                }
                if (assigned == null) {
                    saveError(semanticErrors, symbol, "No se encontro la variable ")
                    return "nothing";
                } else {
                    return assigned.toString();
                }
            } else {
                return symbol.value.toString()
            }
        }
    }

    fun getColor(
        symbol:Symbol?,
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>):String {
        if(symbol == null){
            semanticErrors.add("Simbolo nulo, no se pudo recuperar el dato")
            return "#0000000"
        } else {
            val colorRegex:Regex = Regex("#[A-Fa-f0-9]{6}")
            if (symbol.sym == sym.VARIABLE) {
                var assigned: Any? =
                    globalTable.get(symbol.value.toString()) //obtener el valor de la variable
                if (assigned == null && internalTable != null) {
                    assigned = internalTable.get(symbol.value.toString())
                }
                if (assigned == null) {
                    saveError(semanticErrors, symbol, "No se encontro la variable ")
                    return "nothing";
                } else if(assigned.toString().matches(colorRegex)){
                    return assigned.toString();
                } else {
                    saveError(semanticErrors, symbol, "No es un color valido ")
                    return "#000000"
                }
            } else {
                return symbol.value.toString()
            }
        }
    }

    private fun saveError(semanticErrors: ArrayList<String>, symbolError:Symbol, message:String ){
        semanticErrors.add(message
                + symbolError.value + " linea:" + symbolError.left + " columna" + symbolError.right)
    }
}