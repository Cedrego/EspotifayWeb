<%-- 
    Document   : ConsultarAlbum
    Created on : 25 oct. 2024, 9:26:57 p. m.
    Author     : Franco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <script>
            function cargarArtGen() {
            const filtroSeleccionado = document.getElementById("filtroBusqueda").value;
            const artGen = document.getElementById("ArtGen");
            
            // Limpiar la tercera ComboBox
            artGen.innerHTML = "";
            // Realiza una solicitud AJAX al servlet para obtener lo pedido
            const xhr = new XMLHttpRequest();
            const contextPath = "${pageContext.request.contextPath}";
            xhr.open('GET', contextPath + '/SvConsultarAlbum?filtroBusqueda=' + filtroSeleccionado + '&_=' + new Date().getTime(), true);



            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Verifica si la respuesta tiene opciones válidas
                    if (xhr.responseText.trim() !== "") {
                        artGen.innerHTML = xhr.responseText;
                    } else {
                        artGen.innerHTML = '<option value="">No se encontraron resultados</option>';
                    }
                } else {
                    artGen.innerHTML = '<option value="">Error al cargar los datos</option>';
                }
            };
            xhr.send();        
        }
        
        function cargarAlbum() {
            const filtroSeleccionado = document.getElementById("filtroBusqueda").value;
            const artGen = document.getElementById("ArtGen").value;
            const nomAlbum = document.getElementById("NombreAlbum");
            
            // Limpiar la tercera ComboBox
            nomAlbum.innerHTML = "";
            // Realiza una solicitud AJAX al servlet para obtener lo pedido
            const xhr = new XMLHttpRequest();
            const contextPath = "${pageContext.request.contextPath}";
            xhr.open('GET', contextPath + '/SvConsultarAlbum?filtroBusqueda=' + filtroSeleccionado + '&ArtGen=' + artGen + '&_=' + new Date().getTime(), true);



            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Verifica si la respuesta tiene opciones válidas
                    if (xhr.responseText.trim() !== "") {
                        nomAlbum.innerHTML = xhr.responseText;
                    } else {
                        nomAlbum.innerHTML = '<option value="">No se encontraron resultados</option>';
                    }
                } else {
                    nomAlbum.innerHTML = '<option value="">Error al cargar los datos</option>';
                }
            };
            xhr.send();        
        }
        </script>
    </head>
    <body>
        <h1>Bienvendido a Consulta de Album!</h1>
        <form action="${pageContext.request.contextPath}/SvConsultarAlbum" method="GET">
            <p><label for="filtroBusqueda">Seleccione un filtro para la busqueda:</label>
            <select id="filtroBusqueda" name="filtroBusqueda" onchange="cargarArtGen()"required>
                <option value="">Seleccione un filtro</option>
                <option value="Artista">Artista</option>
                <option value="Genero">Genero</option>  
            </select><br>
            
            <p><label for="ArtGen">Seleccione el Artista o Genero: </label>
            <select id="ArtGen" name="ArtGen" onclick="cargarAlbum()" required>
                <option value="">Seleccione un objeto</option>
            </select><br>
            
            <p><label for="NombreAlbum">Seleccione el Album: </label>
            <select id="NombreAlbum" name="NombreAlbum" required>
                <option value="">Seleccione un objeto</option>
            </select><br>
            
            <button type="submit">Consultar Datos</button>
        </form>
    </body>
</html>
