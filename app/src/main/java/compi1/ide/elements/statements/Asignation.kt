package compi1.ide.elements.statements

import compi1.ide.elements.others.MutableValue
import java_cup.runtime.Symbol

class Asignation:Statement() {
    var value: MutableValue? = null;
    var variable:Symbol? = null;
}