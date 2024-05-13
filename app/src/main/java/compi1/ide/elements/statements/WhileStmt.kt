package compi1.ide.elements.statements

import compi1.ide.elements.Executable
import compi1.ide.elements.others.MutableValue

class WhileStmt:Statement() {
    var condition: MutableValue? = null
    var executables:List<Executable> = ArrayList()

}