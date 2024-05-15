package compi1.ide.elements.chart

import compi1.ide.elements.data.DataBarras
import compi1.ide.elements.data.DataContainer


class ChartBarras: DataContainer() {
    var dataBarras: List<DataBarras> = ArrayList()
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        var code = "function d${index.current}() {\n"
        code += "var data = google.visualization.arrayToDataTable([\n"
        code += "['Categoria', 'Valor', { role: 'style' }],\n"
        for (i in dataBarras.indices) {
            val data = dataBarras.get(i)
            code += "[ '" + dataCollector.getData(data.category, globalTable, internalTable, semanticErrors) + "'"
            code += "," + data.value?.getNumericOperableData(globalTable, internalTable, semanticErrors)
            code += ", "
            code += if(data.color == null) "'#000000'"
                    else "'" + dataCollector.getColor(data.color, globalTable, internalTable, semanticErrors) + "'"
            code += "]"
            if(i != dataBarras.size -1){
                code += ","
            }
            code += "\n"
        }
        code += "]);\n"
        code += "var options = {\n"
        if(leyenda != null){
            code += if(leyenda!!.title != null) "'title':'" + dataCollector.getData(leyenda!!.title, globalTable, internalTable, semanticErrors) + "',"
                    else ""
            code += "'legend': { position: 'none' },\n"
            code += "'hAxis': { title:"
            code += if(leyenda!!.xAxisLabel != null) "'" + dataCollector.getData(leyenda!!.xAxisLabel, globalTable, internalTable, semanticErrors) + "'"
                    else "'x axis'"
            code += "},\n"

            code += "'vAxis': { title:"
            code += if(leyenda!!.yAxisLabel != null) "'" + dataCollector.getData(leyenda!!.yAxisLabel, globalTable, internalTable, semanticErrors) + "'"
            else "'y axis'"
            code += "}\n"
        } else { //no hay leyenda
            code += "        'legend': { position: 'none' },\n" +
                    "        'hAxis': { title: 'x axis' },\n" +
                    "        'vAxis': { title: 'y axis' }"
        }
        code += "};\n"

        code += "var chart = new google.visualization.ColumnChart(document.getElementById('div_d${index.current}'));\n"
        code += "chart.draw(data, options);\n}\n"
        index.increment()
        return code
    }
}