package compi1.ide.elements.others

import compi1.ide.elements.Executable
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
    override fun execute(
        globalTable: HashMap<String, Any>,
        internalTable: HashMap<String, Any>?,
        semanticErrors: ArrayList<String>
    ): String {
        return "codigo de una tarjeta de informacion"
    }
}