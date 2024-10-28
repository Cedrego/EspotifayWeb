<%-- 
    Document   : Artista
    Created on : 17 oct. 2024, 6:31:10 p. m.
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Artista</title>
        <style>
          body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                margin: 0;
                padding: 0;
            }

            h1 {
                color: #333;
                margin-top: 20px;
                text-align: center;
            }

            .button-container {
                display: flex;
                flex-direction: column;
                align-items: flex-start; /* Alinea los botones a la izquierda */
                margin: 20px;
            }

            .button-container button {
                background-color: #4CAF50;
                color: white;
                padding: 10px 15px; /* Reducir el padding para botones más pequeños */
                margin: 5px 0; /* Espaciado entre botones */
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 14px; /* Reducir el tamaño de fuente */
                transition: background-color 0.3s ease;
                width: 250px; /* Reducir el ancho de los botones */
            }

            .button-container button:hover {
                background-color: #45a049;
            }

            .button-container button:active {
                background-color: #3e8e41;
                transform: translateY(2px);
            }
        </style>
    </head>
    <body>
        <%
            String nick = (String) session.getAttribute("NickSesion"); // Obtener el nick de la sesión
        %>
        <h1>Bienvenido <%= nick %></h1> <!-- Muestra el nick de la sesión -->
        <div class="button-container">
            <button onclick="window.location.href = '<%= request.getContextPath() %>/SvAltaAlbum'"> Alta de Album</button>
            <button onclick="window.location.href = '<%= request.getContextPath() %>/SvConsultarPerfil'"> Consulta de Perfil de Usuario</button>

            
            <button onclick="window.location.href='<%= request.getContextPath() %>/SvCerrarSesion'">Cerrar Sesión</button>
        </div>
    </body>
</html>
