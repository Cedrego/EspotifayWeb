<%-- 
    Document   : ConsultarLista
    Created on : 27 oct. 2024, 7:26:50 p. m.
    Author     : Franco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function cargarCliGen() {
                const filtroSeleccionado = document.getElementById("filtroBusqueda").value;
                const cliGen = document.getElementById("CliGen");

                // Limpiar la tercera ComboBox
                cliGen.innerHTML = "";
                // Realiza una solicitud AJAX al servlet para obtener lo pedido
                const xhr = new XMLHttpRequest();
                const contextPath = "${pageContext.request.contextPath}";
                xhr.open('GET', contextPath + '/SvConsultarLista?filtroBusqueda=' + filtroSeleccionado + '&_=' + new Date().getTime(), true);



                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // Verifica si la respuesta tiene opciones válidas
                        if (xhr.responseText.trim() !== "") {
                            cliGen.innerHTML = xhr.responseText;
                        } else {
                            cliGen.innerHTML = '<option value="">No se encontraron resultados</option>';
                        }
                    } else {
                        cliGen.innerHTML = '<option value="">Error al cargar los datos</option>';
                    }
                };
                xhr.send();        
            }
            
            function cargarListas() {
                const filtroSeleccionado = document.getElementById("filtroBusqueda").value;
                const cliGen = document.getElementById("CliGen").value;
                const nomLista = document.getElementById("NombreLista");

                // Limpiar la tercera ComboBox
                nomLista.innerHTML = "";
                // Realiza una solicitud AJAX al servlet para obtener lo pedido
                const xhr = new XMLHttpRequest();
                const contextPath = "${pageContext.request.contextPath}";
                xhr.open('GET', contextPath + '/SvConsultarLista?filtroBusqueda=' + filtroSeleccionado + '&CliGen=' + cliGen + '&_=' + new Date().getTime(), true);



                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // Verifica si la respuesta tiene opciones válidas
                        if (xhr.responseText.trim() !== "") {
                            nomLista.innerHTML = xhr.responseText;
                        } else {
                            nomLista.innerHTML = '<option value="">No se encontraron resultados</option>';
                        }
                    } else {
                        nomLista.innerHTML = '<option value="">Error al cargar los datos</option>';
                    }
                };
                xhr.send();        
            }
            
            function consultarLista() {
                const filtroSeleccionado = document.getElementById("filtroBusqueda").value;
                const cliGen = document.getElementById("CliGen").value;
                const nomLista = document.getElementById("NombreLista").value;
                const listaInfo = document.getElementById("listaInfo");
                
                // Limpiar la tercera ComboBox
                listaInfo.innerHTML = "";
                // Realiza una solicitud AJAX al servlet para obtener lo pedido
                const xhr = new XMLHttpRequest();
                const contextPath = "${pageContext.request.contextPath}";
                xhr.open('GET', contextPath + '/SvConsultarLista?filtroBusqueda=' + filtroSeleccionado + '&CliGen=' + cliGen + '&NombreLista=' + nomLista +'&_=' + new Date().getTime(), true);



                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // Verifica si la respuesta tiene opciones válidas
                        if (xhr.responseText.trim() !== "") {
                            listaInfo.innerHTML = xhr.responseText;
                        } else {
                            listaInfo.innerHTML = '<option value="">No se encontraron resultados</option>';
                        }
                    } else {
                        listaInfo.innerHTML = '<option value="">Error al cargar los datos</option>';
                    }
                };
                xhr.send();        
            }
        </SCRIPT>
    </head>
    <body>
        <h1>Bienvenido a Consulta de Lista!</h1>
        <form action="${pageContext.request.contextPath}/SvConsultarLista" method="GET">
            <p><label for="filtroBusqueda">Seleccione un filtro para la busqueda:</label>
            <select id="filtroBusqueda" name="filtroBusqueda" onchange="cargarCliGen()"required>
                <option value="">Seleccione un filtro</option>
                <option value="Cliente">Cliente</option>
                <option value="Genero">Genero</option>  
            </select><br>
            
            <p><label for="CliGen">Seleccione el Cliente o Genero: </label>
            <select id="CliGen" name="CliGen" onclick="cargarListas()" required>
                <option value="">Seleccione un objeto</option>
            </select><br>
            
            <p><label for="NombreLista">Seleccione la Lista: </label>
            <select id="NombreLista" name="NombreLista" required>
                <option value="">Seleccione un objeto</option>
            </select><br>
            
            <button type="button" onclick="consultarLista()">Consultar Datos</button>
        </form>
            <div id="listaInfo"></div><!-- contenedor para los datos de la lista -->
    </body>
</html>
