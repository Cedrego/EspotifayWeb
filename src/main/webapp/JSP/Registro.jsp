<%-- 
    Document   : Registro
    Created on : 21 oct. 2024, 6:50:44 p. m.
    Author     : camin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Espotify - Crear Cuenta</title>
        <style>
            /* Reset and basic styling */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            body {
                background-color: #000;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                font-family: 'Biski', sans-serif; /* Aplica la fuente Biski */
                font-weight: bold; /* Asegúrate de que sea negrita */
                font-size: 24px;
            }

            /* Main container */
            .register-container {
                width: 300px;
                padding: 40px 20px;
                background-color: #333;
                border-radius: 10px;
                text-align: center;
                color: white;
            }
            /* Title */
            .register-container h2 {
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
            }

            /* Input fields */
            .register-container input[type="text"],
            .register-container input[type="password"],
            .register-container input[type="date"],
            .register-container textarea {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: 2px solid #666;
                border-radius: 20px;
                background-color: #444;
                color: white;
                font-size: 16px;
                text-align: center;
            }

            /* Checkbox and label */
            .register-container input[type="checkbox"] {
                margin-right: 5px;
            }

            /* Buttons */
            .register-container button {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border: none;
                border-radius: 20px;
                font-size: 16px;
                font-weight: bold;
                cursor: pointer;
                background-color: #1db954;
                color: #000;
                transition: box-shadow 0.3s ease; /* Añade una transición para suavizar el efecto */
            }

            .register-container button:hover {
                box-shadow: 0 0 10px #1db954, 0 0 20px #1db954, 0 0 30px #1db954; /* Efecto de brillo */
            }


            /* Extra fields for artist */
            #extraFields {
                display: none;
                margin-top: 10px;
            }

            /* Error message */
            .error-message {
                color: white;
                margin-top: 10px;
            }
            
            #uploadContainer {
                display: none; /* Inicialmente oculto */
                margin-top: 10px;
            }
            
            body {
                background-color: #000;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                font-family: 'Biski', sans-serif; /* Aplica la fuente Biski */
                font-weight: bold; /* Asegúrate de que sea negrita */
                font-size: 24px;
                overflow-y: auto; /* Permite el desplazamiento vertical */
            }

            .register-container {
                width: 300px;
                max-height: 90vh; /* Limita la altura máxima */
                padding: 40px 20px;
                background-color: #333;
                border-radius: 10px;
                text-align: center;
                color: white;
                overflow-y: auto; /* Permite el desplazamiento vertical */
            }
        </style>
        <script>
        function toggleFields() {
            const checkbox = document.getElementById('artista'); // Usamos el ID correcto del checkbox
            const extraFields = document.getElementById('extraFields');

                // Mostrar u ocultar los campos en función del estado del checkbox
                if (checkbox.checked) {
                    extraFields.style.display = 'block'; // Mostrar los campos
                } else {
                    extraFields.style.display = 'none';  // Ocultar los campos
                }
        }
        
        function toggleUploadType(type) {
            document.getElementById('uploadContainer').style.display = 'block'; // Mostrar el contenedor al seleccionar

            if (type === 'file') {
                document.getElementById('fileInput').style.display = 'block';
                document.getElementById('urlInput').style.display = 'none';

                // Vaciar el campo de URL
                document.querySelector('#urlInput input').value = ''; // Vacía el input de URL
            } else {
                document.getElementById('fileInput').style.display = 'none';
                document.getElementById('urlInput').style.display = 'block';

                // Vaciar el campo de archivo
                document.querySelector('#fileInput input').value = ''; // Vacía el input de archivo
            }
        }
        </script>
    </head>
    <body>
        <div class="register-container">
            
            <!-- Title -->
            <h2>Espotify</h2>
            
            <!--Subir imagenes-->
            <label for="uploadType">Selecciona tipo de carga:</label>
            <button onclick="toggleUploadType('file')">Cargar archivo</button>
            <button onclick="toggleUploadType('url')">Usar URL</button>

            <!-- Registration Form -->
            <form action="${pageContext.request.contextPath}/SvRegistro" method="post" enctype="multipart/form-data">
                <input type="text" name="nick" placeholder="Nickname" required>
                <input type="text" name="nom" placeholder="Nombre" required>
                <input type="text" name="ape" placeholder="Apellido" required>
                <input type="text" name="mail" placeholder="Correo" required>
                <input type="password" name="pass" placeholder="Contraseña" required>
                <input type="password" name="pass2" placeholder="Confirmar contraseña" required>
                <input type="date" name="fech" placeholder="Fecha de Nacimiento" required>
                
                <div id="uploadContainer" style="display: none;"> <!-- Mostrarlo inicialmente oculto -->
                    <div id="fileInput" style="display: none;">
                        <input type="file" name="file" accept="image/*">
                    </div>
                    <div id="urlInput" style="display: none;">
                        <input type="text" name="url" placeholder="Ingresa la URL">
                    </div>
                </div>

                <p>
                    <input type="checkbox" id="artista" name="esArtista" onclick="toggleFields()">
                    <label for="artista">Perfil de artista</label>
                </p>

                <div id="extraFields">
                    <input type="text" name="web" placeholder="Página Web">
                    <textarea name="bio" rows="4" placeholder="Biografía"></textarea>
                </div>
                
                <button type="submit">Crear perfil</button>
            </form>

            <!-- Error Message -->
            <%-- Mostrar mensaje de error si existe --%>
            <%
                String errorMessage = (String) request.getSession().getAttribute("error");
                if (errorMessage != null) {
            %>
            <p class="error-message"><%= errorMessage %></p>
            <%
                    request.getSession().removeAttribute("error"); // Limpiar el mensaje para que no persista
                }
            %>
        </div>
    </body>
</html>
