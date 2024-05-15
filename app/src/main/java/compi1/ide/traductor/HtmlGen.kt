package compi1.ide.traductor

import compi1.ide.elements.Project

class HtmlGen {
    val dataCollector:DataCollector = DataCollector()
    fun getInitialCode(project:Project):String{
        return "<html>\n" +
                "<head>\n" +
                "  <!--Styles-->\n" +
                "  <style>\n" +
                "    * {\n" +
                "      box-sizing: border-box;\n" +
                "    }\n" +
                "    body {\n" +
                "      margin: 0;\n" +
                "      font-size:  ${dataCollector.getColor(project.fontSizeS, project.globalSymbolTable, null, project.semanticErrors)};\n" +
                "      background-color: ${dataCollector.getColor(project.backgroundColor, project.globalSymbolTable, null, project.semanticErrors)};\n" +
                "      font-family: ${dataCollector.getData(project.fontFamilyS, project.globalSymbolTable, null, project.semanticErrors)}, sans-serif;\n" +
                "      padding-bottom: 30px;\n" +
                "    }\n" +
                "\n" +
                "    .chart {\n" +
                "      width: 90%;\n" +
                "      height: 60%;\n" +
                "      padding: 8px;\n" +
                "      margin: 1em 5% 1em 5%;\n" +
                "    }\n" +
                "\n" +
                "    header {\n" +
                "      background-color: #ffffff;\n" +
                "      width: 100%;\n" +
                "      padding: 1em 2em;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    footer {\n" +
                "      background-color: #959595;\n" +
                "      position: fixed;\n" +
                "      bottom: 0;\n" +
                "      width: 100%;\n" +
                "      text-align: center;\n" +
                "      padding: 0.5em 0;\n" +
                "      font-size: 0.5em;\n" +
                "    }\n" +
                "  </style>\n" +
                "\n" +
                "  <title>${dataCollector.getData(project.header, project.globalSymbolTable, null, project.semanticErrors)}</title>\n" +
                "\n" +
                "  <!--Load the AJAX API-->\n" +
                "  <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                "  <script type=\"text/javascript\">\n" +
                "\n" +
                "    // Load the Visualization API and the corechart package.\n" +
                "    google.charts.load('current', { 'packages': ['corechart'] });\n"
    }

    fun getCallBackFunctions(number:Int):String{
        var code = ""
        var index = 0;
        while (index < number){
            code += "google.charts.setOnLoadCallback(d${index});\n"
            index++
        }
        return code
    }

    fun getBodyCode(project:Project):String{
        var code = "  </script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "  <header>\n" +
                "    <h1>${dataCollector.getData(project.title, project.globalSymbolTable, null, project.semanticErrors)}</h1>\n"
        code += if(project.description != null) dataCollector.getData(project.description, project.globalSymbolTable, null, project.semanticErrors)
                else ""
        code += "<p>KeyWords: "
        for (i in 0 until project.keyWords.size) {
            code += dataCollector.getData(project.keyWords.get(i), project.globalSymbolTable, null, project.semanticErrors)
            if(i != project.keyWords.size - 1){
                code += " / "
            }
        }
        code += "</p>\n</header>"
        return code
    }

    fun getDivsCode(number:Int):String{
        var code = ""
        var index = 0;
        while (index< number){
            code += "<div class=\"chart\" id=\"div_d${index}\"></div>\n"
            index++;
        }
        return code
    }

    fun getFinalCode(project:Project):String{
        return "  <footer>\n" +
                "    <p>${dataCollector.getData(project.footer, project.globalSymbolTable, null, project.semanticErrors)}</p>\n" +
                "  </footer>\n" +
                "</body>\n" +
                "\n" +
                "</html>"
    }
}