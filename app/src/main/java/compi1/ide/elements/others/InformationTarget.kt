package compi1.ide.elements.others

import compi1.ide.elements.Executable
import compi1.ide.traductor.DataCollector
import java_cup.runtime.Symbol

class InformationTarget: Executable() {
    var line = 0;
    var column = 0;
    var value:MutableValue? = null;
    var label:Symbol? = null;
    var description:Symbol? = null;
    var icon:Symbol? = null;
    var color:Symbol? = null;
    var link:Symbol? = null;

    val dataCollector = DataCollector()
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        val code = StringBuilder()
        code.append("function d${index.current}() {\n")
        code.append("var data = new google.visualization.DataTable();\n")
        code.append("data.addColumn('string', 'Tarjeta de Informacion');\n")
        code.append("data.addRows([\n")

        if(value == null){
            semanticErrors.add("Le falta el valor a la tarjeta de informacion - linea:" + line + " columna:" + column)
        } else {
            code.append("['VALOR: ${value!!.getNumericOperableData(globalTable, internalTable, semanticErrors)}'],\n")
        }

        if(label == null){
            semanticErrors.add("Le falta el label a la tarjeta de informacion - linea:" + line + " columna:" + column)
        } else {
            code.append("['LABEL: ${dataCollector.getData(label, globalTable, internalTable, semanticErrors)}'],\n")
        }

        if(description == null){
            semanticErrors.add("Le falta la descripcion a la tarjeta de informacion - linea:" + line + " columna:" + column)
        } else {
            code.append("['DESCRIPCION: ${dataCollector.getData(description, globalTable, internalTable, semanticErrors)}']")
        }

        if(icon != null){
            code.append(",['ICONO: ${dataCollector.getData(icon, globalTable, internalTable, semanticErrors)}']\n")
        }
        if(link != null){
            code.append(",['LINK: ${dataCollector.getData(link, globalTable, internalTable, semanticErrors)}']\n")
        }
        code.append("]);\n")
        code.append("var options = {\n" +
                "        showRowNumber: false,\n" +
                "        width: '100%',\n" +
                "        height: '100%',\n" +
                "        sort: 'disable', // Deshabilita la ordenación de la tabla\n" +
                "        cssClassNames: {\n" +
                "          tableCell: 'center-align' // Centra los elementos de la tabla\n" +
                "        }\n" +
                "      };\n")
        code.append("var table = new google.visualization.Table(document.getElementById('div_d${index.current}'));\n")
        code.append("table.draw(data, options);\n")
        code.append("}")
        index.increment()
        return code.toString()
    }
}