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
Variable = ([A-Za-z_0-9])*(id|ID|iD|Id)
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
    <YYINITIAL> "if"    { return symbol(sym.IF, yytext());    }
    <YYINITIAL> "else"  { return symbol(sym.ELSE, yytext());  }
    <YYINITIAL> "for"   { return symbol(sym.FOR, yytext());   }
    <YYINITIAL> "while" { return symbol(sym.WHILE, yytext()); }
    <YYINITIAL> "do"    { return symbol(sym.DO, yytext());    }

    <YYINITIAL> {
        /* SYMBOLS */
        "{"             { return symbol(sym.LLAVE_L, yytext()); }
        "}"             { return symbol(sym.LLAVE_R, yytext()); }
        ":"             { return symbol(sym.TWO_DOTS, yytext()); }
        "["             { return symbol(sym.CORCHETE_L, yytext()); }
        "]"             { return symbol(sym.CORCHETE_R, yytext()); }
        ","             { return symbol(sym.COMA, yytext()); }
        ";"             { return symbol(sym.DOT_COMA, yytext()); }
        "("             { return symbol(sym.PARENTESIS_L, yytext()); }
        ")"             { return symbol(sym.PARENTESIS_R, yytext()); }

        /*OPERADORES*/
        "="             { return symbol(sym.ASIGNATOR, yytext()); }
        "*"             { return symbol(sym.TIMES, yytext()); }
        "+"             { return symbol(sym.PLUS, yytext()); }
        "/"             { return symbol(sym.DIV, yytext()); }
        "-"             { return symbol(sym.MINUS, yytext()); }
        "++"            { return symbol(sym.PLUS_PLUS, yytext()); }
        "--"            { return symbol(sym.MINUS_MINUS, yytext()); }
        "+="            { return symbol(sym.PLUS_EQUALS, yytext()); }
        "-="            { return symbol(sym.MINUS_EQUALS, yytext()); }
        "*="            { return symbol(sym.TIMES_EQUALS, yytext()); }
        "/="            { return symbol(sym.DIV_EQUALS, yytext()); }

        /*COMPARATORS*/
        "=="            { return symbol(sym.EQUALS, yytext()); }
        "!="            { return symbol(sym.DIFFERENT, yytext()); }
        ">"             { return symbol(sym.MAYOR, yytext()); }
        ">="            { return symbol(sym.MAYOR_EQUALS, yytext()); }
        "<"             { return symbol(sym.MENOR, yytext()); }
        "<="            { return symbol(sym.MENOR_EQUALS, yytext()); }

        \"              { string.setLength(0); yybegin(STRING); }

        /* IGNORED */
        {WhiteSpace} 	  { /* ignore */ }
    }

    /*tipos de datos*/
    <YYINITIAL> {Integer}           { return symbol(sym.INTEGER, Integer.parseInt(yytext()));}
    <YYINITIAL> {Float}             { return symbol(sym.FLOAT, Float.parseFloat(yytext()));}
    <YYINITIAL> {Variable}          { return symbol(sym.VARIABLE, yytext());}
    <YYINITIAL> {booleanTrue}       { return symbol(sym.TRUE_B, Boolean.parseBoolean(yytext()));}
    <YYINITIAL> {booleanFalse}      { return symbol(sym.FALSE_B, Boolean.parseBoolean(yytext()) );}


    <STRING> {
        \"  {
                yybegin(YYINITIAL); //volver al estado de jflex

                /*CODIGO DE CARACTERES RESERVADOS*/
                String reading = string.toString(); //ATRIBUTOS GLOBALES
                if(reading.equals("title")){
                    return symbol(sym.TITLE, reading);
                } else if (reading.equals("description")){
                    return symbol(sym.DESCRIPTION, reading);
                } else if (reading.equals("keywords")){
                    return symbol(sym.KEYWORDS, reading);
                } else if (reading.equals("header")){
                    return symbol(sym.HEADER, reading);
                } else if (reading.equals("footer")){
                    return symbol(sym.FOOTER, reading);
                } else if (reading.equals("backgroundColor")){
                    return symbol(sym.BACKGROUND, reading);
                } else if (reading.equals("fontFamily")){
                    return symbol(sym.FONT_FAMILY, reading);
                } else if (reading.equals("fontSize")){
                    return symbol(sym.FONT_SIZE, reading);
                } else if (reading.equals("copyright")){
                    return symbol(sym.COPYRIGHT, reading);
                } else if (reading.equals("data")){ //ATRIBUTOS PARA GRAFICAS
                    return symbol(sym.DATA, reading);
                } else if (reading.equals("category")){
                    return symbol(sym.CATEGORY, reading);
                } else if (reading.equals("value")){
                    return symbol(sym.VALUE, reading);
                } else if (reading.equals("color")){
                    return symbol(sym.COLOR, reading);
                } else if (reading.equals("chart")){
                    return symbol(sym.CHART, reading);
                } else if (reading.equals("xAxisLabel")){
                    return symbol(sym.X_AXIS_L, reading);
                } else if (reading.equals("yAxisLabel")){
                    return symbol(sym.Y_AXIS_L, reading);
                } else if (reading.equals("label")){
                    return symbol(sym.LABEL, reading);
                } else if (reading.equals("legendPosition")){
                    return symbol(sym.LEGEND_POS, reading);
                } else if (reading.equals("x")){
                    return symbol(sym.X_DATA, reading);
                } else if (reading.equals("y")){
                    return symbol(sym.Y_DATA, reading);
                } else if (reading.equals("name")){
                    return symbol(sym.NAME, reading);
                } else if (reading.equals("points")){
                    return symbol(sym.POINTS, reading);
                } else if (reading.equals("lineStyle")){
                    return symbol(sym.LINE_STYLE, reading);
                } else if (reading.equals("icon")){
                    return symbol(sym.ICON, reading);
                } else if (reading.equals("link")){
                    return symbol(sym.LINK, reading);
                } else if (reading.equals("size")){
                    return symbol(sym.SIZE, reading);
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
                } else if(reading.matches("#[A-Fa-f0-9]{6}")){
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
