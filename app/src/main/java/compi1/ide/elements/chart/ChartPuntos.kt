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
        return "codigo de una grafica de puntos"
    }
}