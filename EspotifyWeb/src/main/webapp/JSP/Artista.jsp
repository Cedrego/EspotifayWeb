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
            /* Reset and basic styles */
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                background-color: #333; /* Fondo general */
                color: #FFF; /* Color de texto */
                font-family: 'Poppins', sans-serif; /* Fuente */
                display: flex;
                flex-direction: column;
                height: 100vh; /* Altura completa */
            }

            /* Contenedor principal */
            .container {
                display: flex;
                flex-direction: column;
                flex: 1;
            }

            /* Barra superior */
            .top-bar {
                display: flex;
                align-items: center;
                justify-content: space-between;
                background-color: #1a1a1a;
                padding: 15px;
                color: #1db954;
                font-size: 24px;
                font-weight: bold;
            }

            .top-bar .logo-container {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .logo {
                width: 50px;
                height: 50px;
                background-image: url('images/logo.png'); /* Ruta del logo */
                background-size: cover;
                background-position: center;
                border-radius: 5px;
            }

            .client-name {
                color: #FFF; /* Color del nombre del cliente */
                font-size: 20px; /* Tamaño del nombre */
            }

            /* Contenido principal */
            .content {
                display: flex;
                flex: 1;
                background-color: #000; /* Parte central en negro */
                justify-content: space-between; /* Asegúrate de que los paneles se distribuyan */
            }

            /* Columna izquierda para botones */
            .sidebar {
                width: 20%;
                background-color: #1a1a1a; /* Fondo de la barra lateral */
                padding: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 15px; /* Espacio entre botones */
            }

            .sidebar button {
                background-color: #1db954; /* Color de los botones */
                color: white;
                padding: 10px 15px; /* Relleno para botones */
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px; /* Tamaño de fuente */
                transition: background-color 0.3s ease;
                width: 80%; /* Ancho fijo para los botones */
            }

            .sidebar button:hover {
                background-color: #45a049; /* Color al pasar el mouse */
            }

            .sidebar button:active {
                background-color: #3e8e41; /* Color al hacer clic */
                transform: translateY(2px);
            }

            /* Panel derecho para el reproductor de música */
            .right-panel {
                width: 30%; /* Ancho del panel derecho */
                background-color: #1a1a1a; /* Fondo del panel */
                padding: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: flex-start; /* Asegúrate de que los controles estén al inicio */
            }

            /* Espaciado para los controles del reproductor */
            .controls {
                display: flex;
                align-items: center;
                gap: 15px;
                margin-top: 20px;
            }

            .controls button {
                background-color: #FFF;
                border: none;
                border-radius: 50%;
                width: 40px;
                height: 40px;
                cursor: pointer;
                font-size: 18px;
            }

            .slider {
                -webkit-appearance: none;
                width: 100%;
                height: 5px;
                background: #333;
                outline: none;
                opacity: 0.7;
                transition: opacity .2s;
                margin-top: 20px; /* Añadir un margen superior para separar el slider de los botones */
            }
        </style>
    </head>
    <body>
        <div class="container">
            <!-- Top Bar -->
            <div class="top-bar">
                <div class="logo-container">
                    <div class="logo"></div> <!-- Logo aquí -->
                    <div>Espotify</div>
                </div>
                <div class="client-name">
                    <%
                        String nick = (String) session.getAttribute("NickSesion"); // Obtener el nick de la sesión
                        out.print(nick);
                    %>
                </div>
            </div>

            <!-- Main Content -->
            <div class="content">
                <!-- Left Sidebar -->
                <div class="sidebar">
                    <button onclick="window.location.href = '<%= request.getContextPath() %>/SvAltaAlbum'">Alta de Álbum</button>
                    <button onclick="window.location.href = '<%= request.getContextPath() %>/SvConsultarPerfil'">Consulta de Perfil de Usuario</button>
                    <button onclick="window.location.href='<%= request.getContextPath() %>/SvCerrarSesion'">Cerrar Sesión</button>
                </div>

                <!-- Main Content Area -->
                <div class="main-content">
                    <!-- Placeholder para contenido principal -->
                </div>

                <!-- Right Panel with Controls -->
                <div class="right-panel">
                <div class="top-right-image" style="width: 50px; height: 50px; background-color: #FFF; border-radius: 50%; margin-bottom: 20px;"></div>
                <div class="dynamic-image" style="width: 200px; height: 200px; background-color: #FFC107; border-radius: 10px; margin-bottom: 20px;"></div>
                <div class="controls">
                    <button>&#9664;&#9664;</button> <!-- Previous button -->
                    <button>&#9654;</button> <!-- Play button -->
                    <button>&#9654;&#9654;</button> <!-- Next button -->
                </div>
                <input type="range" min="0" max="100" value="50" class="slider">
            </div>
            </div>
        </div>
    </body>
</html>