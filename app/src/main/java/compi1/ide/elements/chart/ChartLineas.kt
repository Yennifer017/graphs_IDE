package compi1.ide.elements.chart

import compi1.ide.elements.data.DataContainer
import compi1.ide.elements.data.DataLineas

class ChartLineas: DataContainer(){
    var dataLineas: List<DataLineas> = ArrayList()
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        var code = "function d${index.current}() {\n"
        code += "var data = new google.visualization.DataTable();\n"
        code += getColumns()
        code += "data.addRows([\n"
        code += getData(globalTable, internalTable, semanticErrors)
        code += "]);\n"
        code += ""
        return "//grafica de lineas aqui"
    }

    private fun getColumns():String{
        var code = "data.addColumn('number', 'X');"
        for (i in dataLineas.indices) {
            code += "data.addColumn('number', '${dataLineas.get(i).name}');\n"
        }
        return code
    }

    private fun getData(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ):String{
        var mostIndex = 0
        var code = "["
        for (i in dataLineas.indices) {
            if(i == 0){
                mostIndex = dataLineas.get(i).points.size
            }
            val dataSet = dataLineas.get(i)
            var j = 0
            while (j < mostIndex){
                if(i == 0){ // es la primera grafica

                }
                j++
            }
        }
        code += "]"
        return ""
    }
}