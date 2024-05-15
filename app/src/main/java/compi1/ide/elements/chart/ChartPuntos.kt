package compi1.ide.elements.chart

import compi1.ide.elements.data.DataPuntos
import compi1.ide.elements.data.DataContainer

class ChartPuntos: DataContainer() {
    var dataPuntos: List<DataPuntos> = ArrayList()
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        var code = "function d${index.current}() {\n"
        code += "var data = new google.visualization.DataTable();\n"
        code += "data.addColumn('number', 'X');\n"
        code += "data.addColumn('number', 'Y');\n"
        code += "data.addColumn({ type: 'string', role: 'style' });\n"
        code += "data.addColumn({ type: 'number', role: 'annotation' });\n"
        code += "data.addRows([\n"
        for (i in dataPuntos.indices) {
            val data = dataPuntos.get(i)
            code += "[" + data.xData?.getNumericOperableData(globalTable, internalTable, semanticErrors)
            code += "," + data.yData?.getNumericOperableData(globalTable, internalTable, semanticErrors)
            code += ", 'color: "
            code += if(data.color == null) "#000000"
                    else dataCollector.getColor(data.color, globalTable, internalTable, semanticErrors)
            code += "' ," + if(data.size == null) "1"
                    else data.size?.getNumericOperableData(globalTable, internalTable, semanticErrors)
            code += "]"
            if(i != dataPuntos.size -1){
                code += ","
            }
            code += "\n"
        }
        code += "]);\n"
        code += "var options = {\n"
        if(leyenda != null){
            code += if(leyenda!!.title != null) "'title':'" + dataCollector.getData(leyenda!!.title, globalTable, internalTable, semanticErrors) + "',"
                    else ""
            code += "'hAxis': { title:"
            code += if(leyenda!!.xAxisLabel != null) "'" + dataCollector.getData(leyenda!!.xAxisLabel, globalTable, internalTable, semanticErrors) + "'"
                    else "'x axis'"
            code += "},\n"

            code += "'vAxis': { title:"
            code += if(leyenda!!.yAxisLabel != null) "'" + dataCollector.getData(leyenda!!.yAxisLabel, globalTable, internalTable, semanticErrors) + "'"
                    else "'y axis'"
            code += "},\n"

            code += "legend: 'none',\n"
            code += "sizeAxis: { minValue: 0, maxSize: 25 }\n"
        } else {
            code += "title: 'Gráfica de Puntos',\n" +
                    "hAxis: { title: 'Eje X' },\n" +
                    "vAxis: { title: 'Eje Y' },\n" +
                    "legend: 'none',\n" +
                    "sizeAxis: { minValue: 0, maxSize: 25 }\n"
        }
        code += "};\n"
        code += "var chart = new google.visualization.ScatterChart(document.getElementById('div_d${index.current}'));\n"
        code += "chart.draw(data, options);\n"
        code += "}\n";
        index.increment()
        return code;
    }
}