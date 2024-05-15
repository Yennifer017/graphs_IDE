package compi1.ide.elements

import compi1.ide.util.Index

abstract class Executable {
    abstract fun execute(globalTable: HashMap<String, Any>,
                         internalTable:HashMap<String, Any>?,
                         semanticErrors: ArrayList<String>):String

    var index: Index = Index()
}