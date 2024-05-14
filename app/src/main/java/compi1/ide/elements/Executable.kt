package compi1.ide.elements

abstract class Executable {
    abstract fun execute(globalTable: HashMap<String, Any>,
                         internalTable:HashMap<String, Any>?,
                         semanticErrors: ArrayList<String>):String
}