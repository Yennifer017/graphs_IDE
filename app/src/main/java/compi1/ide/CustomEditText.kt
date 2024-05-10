package compi1.ide

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import compi1.ide.util.Positionator

class CustomEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    var textView: TextView? = null;

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        Log.d("Cursor Position", "Posición del cursor cambio")
        if(textView != null){
            val lineNumber = this.layout.getLineForOffset(this.selectionStart)
            val startOfLine = this.layout.getLineStart(lineNumber)
            val columnNumber = this.selectionStart - startOfLine
            val message = "C$columnNumber:L$lineNumber"
            textView?.text = message
        }
    }


}