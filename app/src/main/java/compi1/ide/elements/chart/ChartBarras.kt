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
        return "codigo de una grafica de barras"
    }
}