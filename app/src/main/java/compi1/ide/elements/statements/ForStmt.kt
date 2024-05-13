package compi1.ide.elements.statements

import compi1.ide.elements.Executable
import compi1.ide.elements.others.MutableValue

class ForStmt:Statement() {
    var firstAsign:Asignation? = null
    var increment:Asignation? = null
    var condition: MutableValue? = null
    var executables:List<Executable> = ArrayList()
}