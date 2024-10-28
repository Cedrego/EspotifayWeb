<%-- 
    Document   : AltaAlbum
    Created on : 21 oct. 2024, 7:23:39 p. m.
    Author     : Franco
--%>

<%@page import="java.util.List"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <script>
            // array para almacenar los géneros seleccionados
            let generosSeleccionados = [];

            // función para agregar género seleccionado al array y mostrarlo
            function agregarGenero() {
                const selectGeneros = document.getElementById("generos");
                const generoSeleccionado = selectGeneros.value;

                if (generoSeleccionado !== "") {
                    // agregar género al array
                    generosSeleccionados.push(generoSeleccionado);
                    
                    // mostrar los géneros seleccionados en la página
                    const listaGeneros = document.getElementById("generos-seleccionados");
                    listaGeneros.innerHTML += "<li>" + generoSeleccionado + "</li>";
                    
                    // actualizar el campo oculto para enviar los géneros al servidor
                    document.getElementById("generosInput").value = generosSeleccionados.join(",");
                } else {
                    alert("Seleccione un género antes de agregar.");
                }
            }
            
            
            // Función para agregar tema
            function agregarTema() {
                const temasContainer = document.getElementById("temasContainer");
                const temaDiv = document.createElement("div");

                // Crear campos para nombre, duración y posición
                temaDiv.innerHTML = `
                    <p>
                    <span>
                        <label>Nombre Tema</label>
                        <input type="text" name="nombresTemas[]">
                    </span>
                    <span>
                        <label>Duración</label>
                        <input type="text" name="duraciones[]">
                    </span>
                    <span>
                        <label>Posición</label>
                        <input type="text" name="posiciones[]">
                    </span>
                    <span>
                        <button type="button" onclick="agregarDireccion(this)">Agregar Dirección</button>
                    </span>
                    </p>
                `;
                temasContainer.appendChild(temaDiv);
            }
            
            // Función para abrir el pop-up y elegir entre URL o Archivo
            function agregarDireccion(button) {
                const seleccion = prompt("Seleccione 'URL' o 'Archivo'");

                if (seleccion.toLowerCase() === 'url') {
                    const url = prompt("Ingrese la URL:");
                    const input = document.createElement("input");
                    input.type = "hidden";
                    input.name = "direcciones[]";
                    input.value = url;
                    button.parentElement.appendChild(input);
                } else if (seleccion.toLowerCase() === 'archivo') {
                    const inputFile = document.createElement("input");
                    inputFile.type = "file";
                    inputFile.name = "direcciones[]";
                    button.parentElement.appendChild(inputFile);
                } else {
                    alert("Selección inválida. Elija 'URL' o 'Archivo'.");
                }
            }
        </script>
    </head>
        
    <body>
        <h1>Bienvenido a Alta de Album</h1>
        <form action="${pageContext.request.contextPath}/SvAltaAlbum" method="POST">
            
            <p><label>Nombre del Album<br></lable><input type="text" name="nombreAlbum" required></p>
            <p><label for="anioCreacion">Año de Creación:</label>
            <select id="anioCreacion" name="anio" required>
                <% for (int anioCreacion = 2024; anioCreacion > 1950; anioCreacion--) {%>
                    <option value="<%= anioCreacion %>"><%= anioCreacion %></option>
                <% }%>
            </select><br>
            
            
            
            <div class="input-container">
                <label for="generos">Géneros:</label>
                <select id="generos" name="generos">
                <option value="">Seleccione un género</option>
                    <%List<String> generos = (List<String>) request.getAttribute("generos");%>
                        <%if (generos != null && !generos.isEmpty()) {%>
                            <%for (String genero : generos) {%>
                    
                                <option value="<%= genero %>"><%= genero %></option>
                    <%}%>
                        <%} else { %>
                            <option value="">No hay géneros disponibles</option>
                    <%}%>
                </select>

                <button type="button" onclick="agregarGenero()">Agregar Género</button>
            </div>

            <!-- Lista de géneros seleccionados -->
            <ul id="generos-seleccionados"></ul>

            <!-- Campo oculto para enviar los géneros seleccionados -->
            <input type="hidden" name="generosSeleccionados" id="generosInput">
      
            
            <style>
            .input-group {
              display: inline-block;
              margin-right: 10px;
            }
            </style>
            <p>
                <div id="temasContainer">
                    <span class="input-group">
                        <button type="button" onclick="agregarTema()">Agregar Tema</button><br><br>
                    </span>
                </div>
            </p>                                     
            

            <button type="submit">Dar Album de Alta</button>
        </form>
    </body>
</html>