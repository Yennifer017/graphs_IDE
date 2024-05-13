package compi1.ide.elements

import java_cup.runtime.Symbol


class Project {
    var title:Symbol? = null
    var description:Symbol? = null
    var header:Symbol? = null
    var footer:Symbol? = null
    var backgroundColor:Symbol? = null
    var fontFamilyS:Symbol? = null
    var fontSizeS:Symbol? = null

    var keyWords:List<Symbol> = ArrayList()
    var data:List<Executable> = ArrayList()
    private val globalSymbolTable: HashMap<String, Any> = HashMap()

}