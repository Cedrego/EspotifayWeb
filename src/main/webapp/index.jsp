<%-- 
    Document   : index
    Created on : 15 oct. 2024, 7:50:40 p. m.
    Author     : cedre
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Espotify</title>
        <title>Espotify</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
            .top-bar-buttons button {
                background-color: #1db954; /* Verde */
                color: black; /* Texto negro */
                font-weight: bold; /* Texto en negrita */
                padding: 10px 15px; /* Espaciado */
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: box-shadow 0.3s ease, background-color 0.3s ease;
            }

            /* Efecto de brillo al pasar el mouse */
            .top-bar-buttons button:hover {
                box-shadow: 0 0 10px #1db954, 0 0 20px #1db954, 0 0 30px #1db954;
            }
            
            .top-bar .logo-container {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .logo {
                width: 50px;
                height: 50px;
                margin: 0 auto 20px;
                background-image: url('<%= request.getContextPath() %>/images/logo.png');
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
                color: black;
                padding: 10px 15px; /* Relleno para botones */
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px; /* Tamaño de fuente */
                transition: background-color 0.3s ease;
                width: 80%; /* Ancho fijo para los botones */
            }

            .sidebar button:hover {
                box-shadow: 0 0 10px #1db954, 0 0 20px #1db954, 0 0 30px #1db954;
            }

            /* Footer Styles */
            .footer {
                width: 100%;
                background-color: #1a1a1a;
                padding: 10px 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                position: fixed;
                bottom: 0;
                left: 0;
                height: 80px;
            }

            .footer-content {
                display: flex;
                align-items: center;
                width: 100%;
                max-width: 1200px;
                justify-content: space-between;
            }

            /* Imagen y Etiqueta */
            .image-and-label {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .dynamic-image {
                width: 50px;
                height: 50px;
                background-image: url('<%= request.getContextPath()%>/images/noImageSong.png');
                background-size: cover;
                background-position: center;
                border-radius: 5px;
            }

            .song-label {
                color: #FFF;
                font-size: 16px;
                font-weight: bold;
            }

            /* Controles y Barra de Progreso */
            .footer-controls {
                display: flex;
                flex-direction: column;
                align-items: center;
                flex: 1;
            }

            .controls {
                display: flex;
                align-items: center;
                gap: 15px;
                margin-bottom: 5px;
            }

            .controls button {
                background-color: #FFF;
                color: #000;
                border: none;
                border-radius: 50%;
                width: 30px;
                height: 30px;
                cursor: pointer;
                font-size: 16px;
                transition: box-shadow 0.3s ease;
            }

            .slider {
                -webkit-appearance: none;
                width: 80%;
                height: 5px;
                background: #333;
                outline: none;
                opacity: 0.7;
                transition: opacity .2s;
            }
        </style>
        <script>
            function loadContent(url) {
                document.getElementById('dynamic-content').src = url;
            }
            
            function buscarResultados() {
                const query = document.getElementById('query').value;
                if (query.length > 1) { // Ejecuta la búsqueda solo si hay al menos 2 caracteres
                    const url = "<%= request.getContextPath()%>/SvBuscador?query=" + encodeURIComponent(query);
                    loadContent(url); // Utiliza loadContent para cargar el URL en el iframe
                } else {
                    // Si la consulta es corta, limpia el iframe
                    loadContent("");
                }
            }
            
            function redirect(option) {
                if (option === "Iniciar Sesion") {
                    window.location.href = "JSP/Usuario.jsp";  // Ruta actualizada
                } else if (option === "Registrarse") {
                    window.location.href = "JSP/Registro.jsp";  // Ruta actualizada
                }
            }
            
            function loadDynamicImage(imagePath) {
                document.getElementById('dynamic-image').style.backgroundImage = `url('${imagePath}')`;
            }
            // loadDynamicImage('path/to/your/image.jpg'); // Uncomment and replace with your image path
        </script>
    </head>
    <body>
        <!-- Top Bar -->
        <div class="top-bar">
                <div class="logo-container">
                    <div class="logo"></div> <!-- Logo aquí -->
                    <div>Espotify</div>
                </div>
            <form onsubmit="buscarResultados(); return false;" style="display: flex; align-items: center; gap: 10px;">
                <input type="text" id="query" name="query" placeholder="Buscar álbumes, temas, listas de reproducción" 
                       style="width: 300px; padding: 10px; border-radius: 5px; border: none; font-size: 16px; color: #000; background-color: #FFF;">
                <button type="submit" style="background-color: #1db954; color: black; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; font-weight: bold; transition: box-shadow 0.3s ease;">
                    Buscar
                </button>
            </form>
                <div class="top-bar-buttons">
                    <button onclick="redirect('Iniciar Sesion')">Iniciar Sesion</button>
                    <button onclick="redirect('Registrarse')">Registrarse</button>
                </div>
        </div>

        <!-- Main Content -->
        <div class="content">
            <!-- Left Sidebar -->
            <div class="sidebar">
                <button onclick="loadContent('<%= request.getContextPath()%>/JSP/LoadingScreen.jsp')">Consulta de Perfiles</button>
                <button onclick="loadContent('<%= request.getContextPath()%>/JSP/ConsultarAlbum.jsp')">Consulta de Albumes</button>
                <button onclick="loadContent('<%= request.getContextPath()%>/JSP/ConsultarLista.jsp')">Consulta de Listas</button>
            </div>
            
            <div style="flex: 1; background-color: #000; color: #FFF; padding-bottom: 40px;">
                <iframe id="dynamic-content" style="width: 100%; height: calc(100% - 40px); border: none;" src=""></iframe>
            </div>
            
            <!-- Footer with Controls, Image, and Text -->
            <div class="footer">
                <div class="footer-content">
                    <div class="image-and-label">
                        <div class="dynamic-image"></div> <!-- Imagen de la canción -->
                        <span class="song-label">Nombre de la canción</span> <!-- Etiqueta de texto -->
                    </div>
                    <div class="footer-controls">
                        <div class="controls">
                            <button>&#9664;&#9664;</button> <!-- Previous button -->
                            <button>&#9654;</button> <!-- Play button -->
                            <button>&#9654;&#9654;</button> <!-- Next button -->
                        </div>
                        <input type="range" min="0" max="100" value="0" class="slider"> <!-- Barra de progreso -->
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
