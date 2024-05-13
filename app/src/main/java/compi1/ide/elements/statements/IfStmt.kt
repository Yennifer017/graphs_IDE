package compi1.ide.elements.statements

import compi1.ide.elements.Executable
import compi1.ide.elements.others.MutableValue

class IfStmt:Statement() {
    var condition: MutableValue? = null;
    var executables:List<Executable> = ArrayList()
    var elseStmt:List<Executable> = ArrayList()

}