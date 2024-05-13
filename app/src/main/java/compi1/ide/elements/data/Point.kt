package compi1.ide.elements.data

import compi1.ide.elements.others.MutableValue
import java_cup.runtime.Symbol

class Point {
    var xData:MutableValue? = null;
    var yData:MutableValue? = null;
    var saved = false;
    var label:Symbol? = null;
}