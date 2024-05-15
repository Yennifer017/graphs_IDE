package compi1.ide.elements.data

import compi1.ide.elements.Executable
import compi1.ide.elements.others.*;
import compi1.ide.traductor.DataCollector
import compi1.ide.util.Index

abstract class DataContainer:Executable() {
    var leyenda:Leyenda? = null;
    var dataCollector: DataCollector = DataCollector()
}