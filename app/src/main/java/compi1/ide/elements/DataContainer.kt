package compi1.ide.elements

import compi1.ide.Executable
import compi1.ide.elements.chart.Leyenda

abstract class DataContainer: Executable() {
    var leyenda: Leyenda? = null;
}