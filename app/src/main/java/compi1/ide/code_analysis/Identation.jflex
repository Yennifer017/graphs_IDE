/* codigo de usuario */
package compi1.ide.code_analysis;

import java.util.*;

%% //separador de area

/* ------------------------------------------------
        opciones y declaraciones de jflex
---------------------------------------------------*/
%public
%unicode
%intwrap
%class IdentationLexer

/* ------------------------------------------------
            codigo en el constructor
---------------------------------------------------*/
%init{
    string = new StringBuffer();
    identation = "    ";
    currentIdentation = 0;
%init}

/*--------------------------------------------------
                macros o constantes
----------------------------------------------------*/

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* constants */
/* nothing here */

/*---------------------------------------------------
                estados del lexer
-----------------------------------------------------*/

%state STRING


%{ /****************CODIGO DE USUARIO*************/
    StringBuffer string;
    String identation;
    int currentIdentation;

    public StringBuffer getString(){
        return this.string;
    }

    private String identar(int times){
        String code = "";
        for(int index = 0; index < times; index++){
            code += identation;
        }
        return code;
    }
%}

%% // separador de areas


/*----------------------------------------------------
                    reglas lexicas
------------------------------------------------------*/

  /*etiquetas para la identacion*/

    ("{"|"[")         {
                                    string.append(yytext());
                                    string.append("\n");
                                    currentIdentation++;
                                    string.append(this.identar(currentIdentation));
                                    return 1;
                                }
    ","            {
                                    string.append(yytext());
                                    string.append("\n");
                                    string.append(this.identar(currentIdentation));
                                    return 1;
                                 }


    ("]"|"}")     {
                                    string.append("\n");
                                    if(currentIdentation > 0){
                                        currentIdentation--;
                                    }
                                    string.append(this.identar(currentIdentation));
                                    string.append(yytext());
                                    string.append("\n");
                                    return 1;
                                }

    "},"            {
                                     string.append("\n");
                                     if(currentIdentation > 0){
                                        currentIdentation--;
                                     }
                                     string.append(this.identar(currentIdentation));
                                     string.append(yytext());
                                     string.append("\n");
                                     string.append(this.identar(currentIdentation));
                                     return 1;
                                 }

  /* lo demas */
    [^]             { string.append(yytext().replace("\n", "")); }