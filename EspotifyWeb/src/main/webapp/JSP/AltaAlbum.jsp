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
        <style>
            /* Fondo y estilos de texto */
            body {
                background-color: #000;
                color: #FFF;
                font-family: 'Poppins', sans-serif;
            }

            h1, label, p {
                color: #FFF;
            }

            /* Estilo de select */
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

            /* Estilo de botones */
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
        <script>
            // array para almacenar los géneros seleccionados
            let generosSeleccionados = [];
            
            function cargarGeneros() {
                const comboGeneros = document.getElementById("generos");
                

                // Limpiar la tercera ComboBox
                comboGeneros.innerHTML = "";
                // Realiza una solicitud AJAX al servlet para obtener lo pedido
                const xhr = new XMLHttpRequest();
                const contextPath = "${pageContext.request.contextPath}";
                xhr.open('GET', contextPath + '/SvAltaAlbum?_=' + new Date().getTime(), true);

                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // Verifica si la respuesta tiene opciones válidas
                        if (xhr.responseText.trim() !== "") {
                            comboGeneros.innerHTML = xhr.responseText;
                        } else {
                            comboGeneros.innerHTML = '<option value="">No se encontraron resultados</option>';
                        }
                    } else {
                        comboGeneros.innerHTML = '<option value="">Error al cargar los datos</option>';
                    }
                };
                xhr.send();

            }
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
            
            window.onload = function() {
                cargarGeneros();
            };
        </script>
    </head>
        
    <body>
        <h1>Bienvenido a Alta de Album</h1>
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