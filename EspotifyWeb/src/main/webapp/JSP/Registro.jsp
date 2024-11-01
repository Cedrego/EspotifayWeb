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

            /* Logo image */
            .logo {
                display: flex; /* Activar Flexbox */
                justify-content: center; /* Centrar horizontalmente */
                align-items: center; /* Centrar verticalmente */
                width: 100%; /* Asegura que ocupe todo el ancho del contenedor */
                margin: 0 auto 20px; /* Mantener márgenes */
                padding: 10px; /* Agregar espacio interno */
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
        </script>
    </head>
    <body>
        <div class="register-container">
            <!-- Logo Placeholder -->
            <div class="logo">
                <img src="${pageContext.request.contextPath}/images/logo.png" alt="Espotify Logo" style="width: 100px; height: auto; border-radius: 5px;">
            </div>

            <!-- Title -->
            <h2>Espotify</h2>

            <!-- Registration Form -->
            <form action="${pageContext.request.contextPath}/SvRegistro" method="post">
                <input type="text" name="nick" placeholder="Nickname" required>
                <input type="text" name="nom" placeholder="Nombre" required>
                <input type="text" name="ape" placeholder="Apellido" required>
                <input type="text" name="mail" placeholder="Correo" required>
                <input type="password" name="pass" placeholder="Contraseña" required>
                <input type="password" name="pass2" placeholder="Confirmar contraseña" required>
                <input type="date" name="fech" placeholder="Fecha de Nacimiento" required>

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
