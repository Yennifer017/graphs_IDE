package compi1.ide.elements.others

import java_cup.runtime.Symbol
import compi1.ide.code_analysis.sym

class MutableValue {
    var symbols:List<Symbol> = ArrayList()

    fun getValueAsignated(
        keyVar:String,
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>): Any{
        if(symbols.size == 1){
            val type:Int =  symbols.get(0).sym
            return when (type) {
                sym.PLUS_PLUS -> {
                    operateFastOperator(keyVar, globalTable, internalTable, semanticErrors, 0)
                }
                sym.MINUS_MINUS -> {
                    operateFastOperator(keyVar, globalTable, internalTable, semanticErrors, 1)
                }
                sym.VARIABLE -> {
                    getValueFromVar(symbols.get(0).value.toString(), globalTable, internalTable, semanticErrors)
                }
                else -> { //es un valor normalito
                    symbols.get(0).value;
                }
            }
        } else if(symbols.size == 2){
            val type:Int =  symbols.get(0).sym
            when (type) {
                sym.PLUS_EQUALS -> {
                    return operateEquals(keyVar, globalTable, internalTable, semanticErrors, 0)
                }
                sym.MINUS_EQUALS -> {
                    return operateEquals(keyVar, globalTable, internalTable, semanticErrors, 1)
                }
                sym.TIMES_EQUALS -> {
                    return operateEquals(keyVar, globalTable, internalTable, semanticErrors, 2)
                }
                sym.DIV_EQUALS -> {
                    return operateEquals(keyVar, globalTable, internalTable, semanticErrors, 3)
                }
                else -> {
                    semanticErrors.add("No debio pasar esto, desde getValueAsignated()")
                    return ""
                }
            }
        } else if(symbols.size == 3){
            return getNumericData(globalTable, internalTable, semanticErrors)
        } else {
            println("Esto no deberia pasar")
            return "";
        }
    }

    fun getNumericOperableData(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>): Number{
        if(symbols.size == 1){
            if(symbols.get(0).sym == sym.VARIABLE){
                val recoveredValue = getValueFromVar(symbols.get(0).value.toString(), globalTable, internalTable, semanticErrors)
                if(recoveredValue is Int || recoveredValue is Float){
                    return recoveredValue as Number
                } else {
                    saveError(semanticErrors, symbols.get(0), "La variable no es de tipo numerica")
                    return 0;
                }
            } else {
                return symbols.get(0).value as Number
            }
        } else if(symbols.size == 3){
            return getNumericData(globalTable, internalTable, semanticErrors)
        } else {
            saveError(semanticErrors, symbols.get(0), "Error inesperado desde getNumericOperableData")
            return 0
        }
    }

    fun getConditionData(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>): Boolean{
        val type:Int = symbols.get(1).sym
        val firstValue = if(symbols.get(0).sym == sym.VARIABLE)
            getValueFromVar(symbols.get(0).value.toString(), globalTable, internalTable, semanticErrors)
            else symbols.get(0).value
        val secondValue = if(symbols.get(2).sym == sym.VARIABLE)
            getValueFromVar(symbols.get(2).value.toString(), globalTable, internalTable, semanticErrors)
            else symbols.get(2).value
        when (type) {
            sym.EQUALS -> {
                return firstValue == secondValue
            }
            sym.DIFFERENT -> {
                return firstValue != secondValue
            }
            sym.MAYOR -> {
                return getBooleanData(semanticErrors, 0, firstValue, secondValue)
            }
            sym.MAYOR_EQUALS -> {
                return getBooleanData(semanticErrors, 1, firstValue, secondValue)
            }
            sym.MENOR -> {
                return getBooleanData(semanticErrors, 2, firstValue, secondValue)
            }
            sym.MENOR_EQUALS -> {
                return getBooleanData(semanticErrors, 3, firstValue, secondValue)
            }
            else -> {
                semanticErrors.add("No debio pasar esto, desde getConditionData()")
                return false
            }
        }
    }

    private fun getBooleanData(
        semanticErrors: ArrayList<String>,
        type:Int,
        actualValue: Any,
        addingValue: Any): Boolean{
        if (actualValue is Int && addingValue is Int){
            val actualNumber:Int = actualValue as Int
            val addingNumber:Int = addingValue as Int
            return when (type) {
                0 -> actualNumber > addingNumber
                1 -> actualNumber >= addingNumber
                2-> actualNumber < addingNumber
                else -> actualNumber <= addingNumber
            }
        } else if(actualValue is Int && addingValue is Float){
            val actualNumber:Int = actualValue
            val addingNumber:Float = addingValue
            return when (type) {
                0 -> actualNumber > addingNumber
                1 -> actualNumber >= addingNumber
                2-> actualNumber < addingNumber
                else -> actualNumber <= addingNumber
            }
        } else if(actualValue is Float && addingValue is Float){
            val actualNumber:Float = actualValue
            val addingNumber:Float = addingValue
            return when (type) {
                0 -> actualNumber > addingNumber
                1 -> actualNumber >= addingNumber
                2-> actualNumber < addingNumber
                else -> actualNumber <= addingNumber
            }
        } else if(actualValue is Float && addingValue is Int){
            val actualNumber:Float = actualValue
            var addingNumber:Int = addingValue
            if(addingNumber == 0  && type >= 3 ){
                saveError(semanticErrors, symbols.get(2), "No se puede dividir entre 0")
                addingNumber = 1
            }
            return when (type) {
                0 -> actualNumber > addingNumber
                1 -> actualNumber >= addingNumber
                2-> actualNumber < addingNumber
                else -> actualNumber <= addingNumber
            }
        }
        else {
            saveError(semanticErrors, symbols.get(0), "La variable no es de tipo numero")
            return false
        }
    }

    private fun getNumericData(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>): Number{
        val first:Int =  symbols.get(0).sym
        val second:Int = symbols.get(2).sym
        val firstValue = if(first == sym.VARIABLE)
            getValueFromVar(symbols.get(0).toString(), globalTable, internalTable, semanticErrors)
        else symbols.get(0).value
        val secondValue = if(second == sym.VARIABLE)
            getValueFromVar(symbols.get(1).toString(), globalTable, internalTable, semanticErrors)
        else symbols.get(1).value
        val type = symbols.get(1).sym
        when (type) {
            sym.PLUS -> {
                return operateIntegers(semanticErrors, 0, firstValue, secondValue)
            }
            sym.MINUS -> {
                return operateIntegers(semanticErrors, 2, firstValue, secondValue)
            }
            sym.TIMES -> {
                return operateIntegers(semanticErrors, 3, firstValue, secondValue)
            }
            sym.DIV -> {
                return operateIntegers(semanticErrors, 4, firstValue, secondValue)
            }
            else -> {
                semanticErrors.add("No debio pasar esto, desde getValueAsignated()")
                return 0
            }
        }
    }


    private fun operateEquals(
        keyVar:String,
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>, type:Int ):Any{

        val actualValue =  getValueFromVar(keyVar, globalTable, internalTable, semanticErrors)
        val addingValue = if (symbols.get(1).sym == sym.VARIABLE)
            getValueFromVar(symbols.get(1).value.toString(), globalTable, internalTable, semanticErrors)
        else symbols.get(1).value

        if (actualValue is Int && addingValue is Int){
            val actualNumber:Int = actualValue as Int
            var addingNumber:Int = addingValue as Int
            if(addingNumber == 0  && type >= 3 ){
                saveError(semanticErrors, symbols.get(1), "No se puede dividir entre 0")
                addingNumber = 1
            }
            return when (type) {
                0 -> actualNumber + addingNumber
                1 -> actualNumber - addingNumber
                2-> actualNumber * addingNumber
                else -> actualNumber / addingNumber
            }
        } else if(actualValue is Int && addingValue is Float){
            val actualNumber:Int = actualValue
            var addingNumber:Float = addingValue
            if(addingNumber == 0f  && type >= 3 ){
                saveError(semanticErrors, symbols.get(1), "No se puede dividir entre 0")
                addingNumber = 1f
            }
            return when (type) {
                0 -> actualNumber + addingNumber
                1 -> actualNumber - addingNumber
                2-> actualNumber * addingNumber
                else -> actualNumber / addingNumber
            }
        } else if(actualValue is Float && addingValue is Float){
            val actualNumber:Float = actualValue
            var addingNumber:Float = addingValue
            if(addingNumber == 0f  && type >= 3 ){
                saveError(semanticErrors, symbols.get(1), "No se puede dividir entre 0")
                addingNumber = 1f
            }
            return when (type) {
                0 -> actualNumber + addingNumber
                1 -> actualNumber - addingNumber
                2-> actualNumber * addingNumber
                else -> actualNumber / addingNumber
            }
        } else if(actualValue is Float && addingValue is Int){
            val actualNumber:Float = actualValue
            var addingNumber:Int = addingValue
            if(addingNumber == 0  && type >= 3 ){
                saveError(semanticErrors, symbols.get(1), "No se puede dividir entre 0")
                addingNumber = 1
            }
            return when (type) {
                0 -> actualNumber + addingNumber
                1 -> actualNumber - addingNumber
                2-> actualNumber * addingNumber
                else -> actualNumber / addingNumber
            }
        }
        else {
            saveError(semanticErrors, symbols.get(0), "La variable no es de tipo numero")
            return 0
        }
    }

    private fun operateIntegers(
        semanticErrors: ArrayList<String>,
        type:Int,
        actualValue: Any,
        addingValue: Any): Number{
        if (actualValue is Int && addingValue is Int){
            val actualNumber:Int = actualValue as Int
            var addingNumber:Int = addingValue as Int
            if(addingNumber == 0  && type >= 3 ){
                saveError(semanticErrors, symbols.get(1), "No se puede dividir entre 0")
                addingNumber = 1
            }
            return when (type) {
                0 -> actualNumber + addingNumber
                1 -> actualNumber - addingNumber
                2-> actualNumber * addingNumber
                else -> actualNumber / addingNumber
            }
        } else if(actualValue is Int && addingValue is Float){
            val actualNumber:Int = actualValue
            var addingNumber:Float = addingValue
            if(addingNumber == 0f  && type >= 3 ){
                saveError(semanticErrors, symbols.get(1), "No se puede dividir entre 0")
                addingNumber = 1f
            }
            return when (type) {
                0 -> actualNumber + addingNumber
                1 -> actualNumber - addingNumber
                2-> actualNumber * addingNumber
                else -> actualNumber / addingNumber
            }
        } else if(actualValue is Float && addingValue is Float){
            val actualNumber:Float = actualValue
            var addingNumber:Float = addingValue
            if(addingNumber == 0f  && type >= 3 ){
                saveError(semanticErrors, symbols.get(1), "No se puede dividir entre 0")
                addingNumber = 1f
            }
            return when (type) {
                0 -> actualNumber + addingNumber
                1 -> actualNumber - addingNumber
                2-> actualNumber * addingNumber
                else -> actualNumber / addingNumber
            }
        } else if(actualValue is Float && addingValue is Int){
            val actualNumber:Float = actualValue
            var addingNumber:Int = addingValue
            if(addingNumber == 0  && type >= 3 ){
                saveError(semanticErrors, symbols.get(2), "No se puede dividir entre 0")
                addingNumber = 1
            }
            return when (type) {
                0 -> actualNumber + addingNumber
                1 -> actualNumber - addingNumber
                2-> actualNumber * addingNumber
                else -> actualNumber / addingNumber
            }
        }
        else {
            saveError(semanticErrors, symbols.get(0), "La variable no es de tipo numero")
            return 0
        }
    }
    private fun operateFastOperator(
        keyVar:String,
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>, type:Int ): Any {
        val actualValue =  getValueFromVar(keyVar, globalTable, internalTable, semanticErrors)
        if(actualValue is Int){
            val value:Int = actualValue
            return if(type == 0){
                value + 1
            } else {
                value - 1
            }
        } else if (actualValue is Float){
            val value:Float = actualValue
            return if(type == 0){
                value + 1
            } else {
                value - 1
            }
        } else {
            saveError(semanticErrors, symbols.get(0), "La variable no es de tipo entero")
            return 0
        }
    }
    private fun getValueFromVar(
        keyVar:String,
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String> ):Any{
        var assigned:Any? = globalTable.get(keyVar) //obtener el valor de la variable
        if(assigned == null && internalTable != null){
            assigned = internalTable.get(keyVar)
        }
        if(assigned == null){
            val symbolError = symbols.get(0)
            saveError(semanticErrors, symbolError, "No se encontro la variable ")
            return 0;
        } else {
            return assigned;
        }
    }

    private fun saveError(semanticErrors: ArrayList<String>, symbolError:Symbol, message:String ){
        semanticErrors.add(message
                + symbolError.value + " linea:" + symbolError.left + " columna" + symbolError.right)
    }


}