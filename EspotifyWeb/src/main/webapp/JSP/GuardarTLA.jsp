<%-- 
    Document   : GuardarTLA
    Created on : 23 oct. 2024, 8:05:24 p. m.
    Author     : Franco
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <script>
        // función para actualizar la segunda ComboBox según la selección de la primera
        function actualizarFiltros() {
            const tipoSeleccionado = document.getElementById("tipoDelObjeto").value;
            const filtrosComboBox = document.getElementById("filtroPrincipal");
            
            // limpiar la segunda ComboBox
            filtrosComboBox.innerHTML = "";
            
            if(tipoSeleccionado === "Seleccione un filtro"){
                filtrosComboBox.innerHTML =`
                    <option value="">Seleccione un filtro</option>
                `;
            }
            // actualizar opciones de la segunda ComboBox según el tipo seleccionado
            if (tipoSeleccionado === "Tema") {
                filtrosComboBox.innerHTML = `
                    <option value="Album">Album</option>
                    <option value="Genero">Genero</option>
                    <option value="ListaParticular">Lista Particular</option>
                    <option value="ListaPorDefecto">Lista Por Defecto</option>
                `;
            }
            if (tipoSeleccionado === "Lista") {
                filtrosComboBox.innerHTML = `
                    <option value="Particular">Particular</option>
                    <option value="PorDefecto">Por Defecto</option>
                `;
            }
            if (tipoSeleccionado === "Album") {
                filtrosComboBox.innerHTML = `
                    <option value="Artista">Artista</option>
                    <option value="Genero">Genero</option>
                `;
            }
        }
        
        function actualizarFiltrosSecundarios() {
            const tipoSeleccionado = document.getElementById("tipoDelObjeto").value;
            const filtroSeleccionado = document.getElementById("filtroPrincipal").value;
            const filtroSecundarioSeleccionado = document.getElementById("filtroSecundario");
            
            // Limpiar la tercera ComboBox
            filtroSecundarioSeleccionado.innerHTML = "";
            // Realiza una solicitud AJAX al servlet para obtener lo pedido
            const xhr = new XMLHttpRequest();
            const contextPath = "${pageContext.request.contextPath}";
            xhr.open('GET', contextPath + '/SvGuardarTLA?tipoDelObjeto=' + tipoSeleccionado + '&filtroPrincipal=' + filtroSeleccionado + '&_=' + new Date().getTime(), true);



            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Verifica si la respuesta tiene opciones válidas
                    if (xhr.responseText.trim() !== "") {
                        filtroSecundarioSeleccionado.innerHTML = xhr.responseText;
                    } else {
                        filtroSecundarioSeleccionado.innerHTML = '<option value="">No se encontraron resultados</option>';
                    }
                } else {
                    filtroSecundarioSeleccionado.innerHTML = '<option value="">Error al cargar los datos</option>';
                }
            };
            xhr.send();
            
        }
        
        function listarObjetos() {
            const tipoSeleccionado = document.getElementById("tipoDelObjeto").value;
            const filtroSeleccionado = document.getElementById("filtroPrincipal").value;
            const filtroSecundarioSeleccionado = document.getElementById("filtroSecundario").value;
            const nombreDelObjetoSeleccionado = document.getElementById("NombreDelObjeto");
            
            // Limpiar la tercera ComboBox
            nombreDelObjetoSeleccionado.innerHTML = "";
            // Realiza una solicitud AJAX al servlet para obtener lo pedido
            const xhr = new XMLHttpRequest();
            const contextPath = "${pageContext.request.contextPath}";
            xhr.open('GET', contextPath + '/SvGuardarTLA?tipoDelObjeto=' + tipoSeleccionado + '&filtroPrincipal=' + filtroSeleccionado + '&filtroSecundario=' + filtroSecundarioSeleccionado + '&_=' + new Date().getTime(), true);



            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Verifica si la respuesta tiene opciones válidas
                    if (xhr.responseText.trim() !== "") {
                        nombreDelObjetoSeleccionado.innerHTML = xhr.responseText;
                    } else {
                        nombreDelObjetoSeleccionado.innerHTML = '<option value="">No se encontraron resultados</option>';
                    }
                } else {
                    nombreDelObjetoSeleccionado.innerHTML = '<option value="">Error al cargar los datos</option>';
                }
            };
            xhr.send();
            
        }
        
        function redirigirACliente() {
            window.location.href = "${pageContext.request.contextPath}/Cliente.jsp";
        }
        
        function alertaExito() {
            var NombreDelObjeto = document.getElementById('NombreDelObjeto').value;
            if (NombreDelObjeto) {
                alert(NombreDelObjeto + " ha sido agregado a Favoritos!");
            }
        }
        </script>
    </head>
    <body>
        <h1>Bienvenido a Guardar Favoritos!</h1> 
        
        <div>
            <%-- mensaje de error --%>
            <%
            String error = (String) session.getAttribute("error");
            if (error != null) {
            %>
                <div style="color: red;">
                    <strong>Error:</strong> <%= error %>
                </div>
            <%
                // limpio el mensaje de error después de mostrarlo
                session.removeAttribute("error");
            }
            %>
        </div>
        
        <form action="${pageContext.request.contextPath}/SvGuardarTLA" method="POST">
            <p><button type="button" onclick="redirigirACliente()">Volver a Pagina Principal</button></p>
            <p><label for="tipoDelObjeto">Que desea agregar a favoritos?:</label>
            <select id="tipoDelObjeto" name="tipoDelObjeto" onchange="actualizarFiltros(); actualizarFiltrosSecundarios()" required>
                <option value="">Seleccione un filtro</option>
                <option value="Tema">Tema</option>
                <option value="Lista">Lista</option>
                <option value="Album">Album</option>  
            </select><br>   
            
            <p><label for="filtroPrincipal">Seleccione un filtro:</label>
                <select id="filtroPrincipal" name="filtroPrincipal" onclick="actualizarFiltrosSecundarios();" required>
                    <option value="">Seleccione un filtro</option>
                </select><br>
            
            <p><label for="filtroSecundario">Seleccione un segundo filtro:</label>
                <select id="filtroSecundario" name="filtroSecundario" onclick="listarObjetos()" required>
                        <option value="">Seleccione un filtro</option>
               </select><br>
            
            <p><label for="NombreDelObjeto">Seleccione el objeto que quiere a agregar a favoritos: </label>
                <select id="NombreDelObjeto" name="NombreDelObjeto" required>
                    <option value="">Seleccione un objeto</option>
                </select><br>
                
            <p><button type="submit">Agregar objeto a Favoritos</button></p>
            
        </form>
    </body>
</html>
