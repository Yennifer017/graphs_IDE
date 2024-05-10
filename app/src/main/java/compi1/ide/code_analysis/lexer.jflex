/* codigo de usuario */
package compi1.ide.code_analysis;

import java_cup.runtime.*;
import java.util.*;

%% //separador de area

/* ------------------------------------------------
        opciones y declaraciones de jflex
---------------------------------------------------*/
%public
%unicode
%class Lexer
%cup
%line
%column

/* ------------------------------------------------
            codigo en el constructor
---------------------------------------------------*/
%init{
    errorsList = new ArrayList();
    string = new StringBuffer();
%init}

/*--------------------------------------------------
                macros o constantes
----------------------------------------------------*/

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* constants */
Integer = [0-9]+
Float = {Integer}\.{Integer}
Variable = ([A-Za-z_])*(id|ID|iD|Id)
booleanTrue = true
booleanFalse = false


/*---------------------------------------------------
                estados del lexer
-----------------------------------------------------*/

%state STRING


%{ /****************CODIGO DE USUARIO*************/

    /*--------------------------------------------
                    UTIL
    ---------------------------------------------*/
    private List<String> errorsList;

    public void reset(){
        errorsList.clear();
    }


  /*--------------------------------------------
    CODIGO PARA EL MANEJO DE ERRORES
  ----------------------------------------------*/

    public List<String> getErrors(){
        return this.errorsList;
    }

    /*--------------------------------------------
        CODIGO PARA EL PARSER
    ----------------------------------------------*/
    StringBuffer string;

    private Symbol symbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+1);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline+1, yycolumn+1, value);
    }

    private void error(String message) {
        errorsList.add("Error en la linea: " + (yyline+1) + ", columna: " + (yycolumn+1) + " : "+message);
    }

%}

%% // separador de areas


/*----------------------------------------------------
                    reglas lexicas
------------------------------------------------------*/

    /*PALABRAS RESERVADAS DEL LENGUAJE*/
    <YYINITIAL> "if"    { return symbol(sym.IF);    }
    <YYINITIAL> "else"  { return symbol(sym.ELSE);  }
    <YYINITIAL> "for"   { return symbol(sym.FOR);   }
    <YYINITIAL> "while" { return symbol(sym.WHILE); }
    <YYINITIAL> "do"    { return symbol(sym.DO);    }

    <YYINITIAL> {
        /* SYMBOLS */
        "{"             { return symbol(sym.LLAVE_L); }
        "}"             { return symbol(sym.LLAVE_R); }
        ":"             { return symbol(sym.TWO_DOTS); }
        "["             { return symbol(sym.CORCHETE_R); }
        "]"             { return symbol(sym.CORCHETE_L); }
        ","             { return symbol(sym.COMA); }
        ";"             { return symbol(sym.DOT_COMA); }
        "("             { return symbol(sym.PARENTESIS_R); }
        ")"             { return symbol(sym.PARENTESIS_L); }

        /*OPERADORES*/
        "="             { return symbol(sym.ASIGNATOR); }
        "*"             { return symbol(sym.TIMES); }
        "+"             { return symbol(sym.PLUS); }
        "/"             { return symbol(sym.DIV); }
        "-"             { return symbol(sym.MINUS); }
        "++"            { return symbol(sym.PLUS_PLUS); }
        "--"            { return symbol(sym.MINUS_MINUS); }
        "+="            { return symbol(sym.PLUS_EQUALS); }
        "-="            { return symbol(sym.MINUS_EQUALS); }
        "*="            { return symbol(sym.TIMES_EQUALS); }
        "/="            { return symbol(sym.DIV_EQUALS); }

        /*COMPARATORS*/
        "=="            { return symbol(sym.EQUALS); }
        "!="            { return symbol(sym.DIFFERENT); }
        ">"             { return symbol(sym.MAYOR); }
        ">="            { return symbol(sym.MAYOR_EQUALS); }
        "<"             { return symbol(sym.MENOR); }
        "<="            { return symbol(sym.MENOR_EQUALS); }

        \"              { string.setLength(0); yybegin(STRING); }

        /* IGNORED */
        {WhiteSpace} 	  { /* ignore */ }
    }

    /*tipos de datos*/
    <YYINITIAL> {Integer}           { return symbol(sym.INTEGER, yytext());}
    <YYINITIAL> {Float}             { return symbol(sym.FLOAT, yytext());}
    <YYINITIAL> {Variable}          { return symbol(sym.VARIABLE, yytext());}
    <YYINITIAL> {booleanTrue}       { return symbol(sym.TRUE_B, yytext());}
    <YYINITIAL> {booleanFalse}      { return symbol(sym.FALSE_B, yytext());}


    <STRING> {
        \"  {
                yybegin(YYINITIAL); //volver al estado de jflex

                /*CODIGO DE CARACTERES RESERVADOS*/
                String reading = string.toString(); //ATRIBUTOS GLOBALES
                if(reading.equals("title")){
                    return symbol(sym.TITLE);
                } else if (reading.equals("description")){
                    return symbol(sym.DESCRIPTION);
                } else if (reading.equals("keywords")){
                    return symbol(sym.KEYWORDS);
                } else if (reading.equals("header")){
                    return symbol(sym.HEADER);
                } else if (reading.equals("footer")){
                    return symbol(sym.FOOTER);
                } else if (reading.equals("backgroundColor")){
                    return symbol(sym.BACKGROUND);
                } else if (reading.equals("fontFamily")){
                    return symbol(sym.FONT_FAMILY);
                } else if (reading.equals("fontSize")){
                    return symbol(sym.FONT_SIZE);
                } else if (reading.equals("copyright")){
                    return symbol(sym.COPYRIGHT);
                } else if (reading.equals("data")){ //ATRIBUTOS PARA GRAFICAS
                    return symbol(sym.DATA);
                } else if (reading.equals("category")){
                    return symbol(sym.CATEGORY);
                } else if (reading.equals("value")){
                    return symbol(sym.VALUE);
                } else if (reading.equals("color")){
                    return symbol(sym.COLOR);
                } else if (reading.equals("char")){
                    return symbol(sym.CHART);
                } else if (reading.equals("xAxisLabel")){
                    return symbol(sym.X_AXIS_L);
                } else if (reading.equals("yAxisLabel")){
                    return symbol(sym.Y_AXIS_L);
                } else if (reading.equals("label")){
                    return symbol(sym.LABEL);
                } else if (reading.equals("legendPosition")){
                    return symbol(sym.LEGEND_POS);
                } else if (reading.equals("x")){
                    return symbol(sym.X_DATA);
                } else if (reading.equals("y")){
                    return symbol(sym.Y_DATA);
                } else if (reading.equals("name")){
                    return symbol(sym.NAME);
                } else if (reading.equals("points")){
                    return symbol(sym.POINTS);
                } else if (reading.equals("lineStyle")){
                    return symbol(sym.LINE_STYLE);
                } else if (reading.equals("icon")){
                    return symbol(sym.ICON);
                } else if (reading.equals("link")){
                    return symbol(sym.LINK);
                } else if (reading.equals("size")){
                    return symbol(sym.SIZE);
                } else if (reading.equals("dashed")){ //ESPECIFICACION DE VALORES
                    return symbol(sym.DASHED_LINE, reading);
                } else if (reading.equals("solid")){
                    return symbol(sym.SOLID_LINE, reading);
                } else if (reading.equals("bottom")){
                    return symbol(sym.BOTTOM, reading);
                } else if (reading.equals("top")){
                    return symbol(sym.TOP, reading);
                } else if (reading.equals("right")){
                    return symbol(sym.RIGHT, reading);
                } else if (reading.equals("left")){
                    return symbol(sym.LEFT, reading);
                } else if(reading.matches("#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})")){
                    return symbol(sym.HEX_COLOR, reading);
                } else {
                    return symbol(sym.STRING, reading);
                }
            }

        /*para poder insertar saltos de linea*/
        [^\n\r\"\\]+    { string.append( yytext() ); }
        \\t             { string.append('\t'); }
        \\n             { string.append('\n'); }

        \\r             { string.append('\r'); }
        \\\"            { string.append('\"'); }
        \\              { string.append('\\'); }
    }

  /* error fallback */
    [^]             { error("Simbolo invalido <"+ yytext()+">");}
    <<EOF>>         { return symbol(sym.EOF, false); }
