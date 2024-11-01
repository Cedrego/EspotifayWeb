<%-- 
    Document   : Buscador
    Created on : 31 oct. 2024, 15:08:06
    Author     : tecnologo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function buscar() {
                let query = document.getElementById("query").value;

                if (query.length > 1) { // para que ejecute la búsqueda solo si hay al menos 3 caracteres
                    fetch("<%= request.getContextPath() %>/SvBuscador?query=" + query)
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("resultados").innerHTML = data;
                        })
                        .catch(error => console.error("Error en búsqueda:", error));
                } else {
                    document.getElementById("resultados").innerHTML = ""; //limpio si no hay consulta
                }
            }
        </script>
        <style>
        /* Estilo de fondo y texto */
        body {
            background-color: #000;
            color: #FFF;
            font-family: 'Poppins', sans-serif;
        }

        h1, label {
            color: #FFF;
        }

        /* Estilo para select */
        select {
            background-color: #1a1a1a;
            color: #FFF;
            padding: 5px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            transition: box-shadow 0.3s ease;
        }

        select:hover, select:focus {
            box-shadow: 0 0 10px #1db954, 0 0 20px #1db954, 0 0 30px #1db954;
        }

        /* Estilo para botones */
        button {
            background-color: #1db954;
            color: black;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }

        button:hover {
            box-shadow: 0 0 10px #1db954, 0 0 20px #1db954, 0 0 30px #1db954;
        }
        </style>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <form action="${pageContext.request.contextPath}/JSP/Resultados.jsp" method="GET">
            <input type="text" id="query" name="query" onkeyup="buscar()" placeholder="Buscar álbumes, temas, listas de reproducción">
            <div id="resultados"></div> <!-- div donde se muestran los resultados en tiempo real -->
            <button type="submit">Buscar</button>
        </form>


    </body>
</html>
