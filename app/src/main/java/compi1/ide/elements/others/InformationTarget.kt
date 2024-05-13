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
}