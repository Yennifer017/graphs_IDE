package compi1.ide.elements.chart

import compi1.ide.elements.data.DataContainer
import compi1.ide.elements.data.DataPastel

class ChartPastel: DataContainer() {
    var dataPastel: List<DataPastel> = ArrayList()
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        var colorCodes = "";

        var code = "function d${index.current}() {\n"
        code += "var data = new google.visualization.DataTable();\n"
        code += "data.addColumn('string', 'Topping');\n"
        code += "data.addColumn('number', 'Slices');\n"
        code += "data.addRows([\n"
        for (i in dataPastel.indices) {
            val data = dataPastel.get(i)
            code += "['" + dataCollector.getData(data.label, globalTable, internalTable, semanticErrors) + "'"
            code += "," + data.value?.getNumericOperableData(globalTable, internalTable, semanticErrors)
            code += "]"
            if(i != dataPastel.size -1){
                code += ","
            }
            code += "\n"
            //colors
            colorCodes += if(data.color == null) "'#000000'";
                else "'" + dataCollector.getColor(data.color, globalTable, internalTable, semanticErrors) + "'"
            colorCodes += if(i != dataPastel.size -1 ) "," else ""
        }
        code += "]);\n"
        code += "var options = {"
        if(leyenda != null && leyenda?.title != null){
            code += "'title': '" + dataCollector.getData(leyenda!!.title, globalTable, internalTable, semanticErrors) + "',"
        }
        code += "'colors':[$colorCodes]\n"
        code += "};\n"
        code += "var chart = new google.visualization.PieChart(document.getElementById('div_d${index.current}'));\n"
        code += "chart.draw(data, options);\n}\n"
        index.increment()
        return code
    }
}