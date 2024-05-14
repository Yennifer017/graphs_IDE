package compi1.ide.elements.statements

import compi1.ide.elements.exceptions.UnexpectedValueException
import compi1.ide.elements.others.MutableValue
import java_cup.runtime.Symbol

class Asignation:Statement() {
    var value: MutableValue? = null;
    var variable:Symbol? = null;
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        try {
            if(value == null){ //no deberia ocurrir
                throw UnexpectedValueException("No se especifico el valor para asignar")
            } else {
                val key:String = variable?.value as String
                val valueConvert = value!!.getValueAsignated(key, globalTable, internalTable, semanticErrors)
                if(internalTable == null){
                    //se debe insertar en la tabla global
                    globalTable.put(key, valueConvert)
                } else {
                    if(globalTable.containsKey(key)){
                        globalTable.put(key, valueConvert);
                    } else {
                        internalTable.put(key, valueConvert)
                    }
                }
            }
        } catch (e: Exception) {
            semanticErrors.add("No se pudo guardar una variable: " + e.message);
        }
        return "";
    }
}