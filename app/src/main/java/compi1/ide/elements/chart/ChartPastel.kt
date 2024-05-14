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
        return "codigo de una grafica pastel"
    }
}