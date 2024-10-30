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
                background-color: #000; /* Cambiar a negro para eliminar el borde gris */
                color: #FFF;
                font-family: 'Biski', sans-serif; /* fuente Biski */
                font-weight: bold; /* negrita */
                font-size: 24px;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                display: flex;
                flex-direction: column;
                width: 100%; /* Cambiar a 100% para ocupar todo el ancho */
                height: 100vh; /* Mantener el alto completo de la pantalla */
                background-color: #222; /* Mantener el fondo del contenedor */
                border-radius: 0; /* Eliminar el borde redondeado */
                overflow: hidden;
            }

            .content {
                display: flex;
                flex: 1;
                margin: 0; /* Eliminar cualquier margen */
                padding: 0; /* Eliminar cualquier padding */
            }

            .sidebar {
                width: 15%;
                background-color: #1a1a1a;
                padding: 20px; /* Puedes ajustar este padding según sea necesario */
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 15px;
            }

            .right-panel {
                width: 25%;
                background-color: #1a1a1a;
                padding: 20px; /* Puedes ajustar este padding según sea necesario */
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            /* Top bar */
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
                margin: 0 auto 20px;
                background-image: url('images/logo.png');
                background-size: cover;
                background-position: center;
                border-radius: 5px;
            }

            .top-bar-buttons {
                display: flex;
                gap: 10px;
            }

            .top-bar button {
                background-color: #1db954;
                color: #FFF;
                border: none;
                border-radius: 5px;
                padding: 5px 10px;
                cursor: pointer;
                font-weight: bold;
            }

            /* Content container */
            .content {
                display: flex;
                flex: 1;
            }

            /* Oval buttons on the sidebar */
            .sidebar button {
                background-color: #1db954;
                border: none;
                border-radius: 20px;
                width: 40px;
                height: 40px;
                color: #FFF;
                cursor: pointer;
                font-weight: bold;
            }

            /* Main display area */
            .main-content {
                flex: 1;
                background-color: #000;
            }

            /* Top right static image */
            .top-right-image {
                width: 50px;
                height: 50px;
                background-color: #FFF;
                border-radius: 50%;
                margin-bottom: 20px;
            }

            /* Dynamic image area - make it square */
            .dynamic-image {
                width: 80%;
                height: 0;
                padding-bottom: 80%; /* Makes it square based on width */
                background-color: #FFC107;
                border-radius: 10px;
                margin-bottom: 20px;
                background-size: cover;
                background-position: center;
            }

            /* Control buttons and slider */
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
                background: #333; /* Color de fondo del slider */
                outline: none;
                opacity: 1;
                transition: opacity .2s;
                margin-top: 20px; /* Añadir un margen superior para separar el slider de los botones */
            }

            /* Para estilizar la parte izquierda del slider */
            .slider::-webkit-slider-thumb {
                -webkit-appearance: none;
                appearance: none;
                width: 20px; /* Ancho del "thumb" */
                height: 20px; /* Alto del "thumb" */
                background: #FFF; /* Color del "thumb" */
                border-radius: 50%; /* Hacer el "thumb" redondeado */
                cursor: pointer;
            }

            /* Estilo para la parte izquierda verde */
            .slider::-webkit-slider-runnable-track {
                background: linear-gradient(to right, #1db954 0%, #1db954 var(--value), #333 var(--value), #333 100%);
                height: 5px; /* Altura del track */
            }

        </style>
            <script>
            function redirect(option) {
                if (option === "Iniciar Sesion") {
                    window.location.href = "JSP/Usuario.jsp";  // Ruta actualizada
                } else if (option === "Registrarse") {
                    window.location.href = "JSP/Registro.jsp";  // Ruta actualizada
                }
            }
        </script>
    </head>
    <body>
        <div class="container">
            <!-- Top Bar -->
            <div class="top-bar">
                <div class="logo-container">
                    <div class="logo"></div> <!-- Logo aquí -->
                    <div>Espotify</div>
                </div>
                <div class="top-bar-buttons">
                    <button onclick="redirect('Iniciar Sesion')">Iniciar Sesion</button>
                    <button onclick="redirect('Registrarse')">Registrarse</button>
                </div>
            </div>

            <!-- Main Content -->
            <div class="content">
                <!-- Left Sidebar -->
                <div class="sidebar">
                    <button onclick="redirect('Button1')">1</button>
                    <button onclick="redirect('Button2')">2</button>
                    <button onclick="redirect('Button3')">3</button>
                    <button onclick="redirect('Button4')">4</button>
                </div>

                <!-- Main Content Area -->
                <div class="main-content"></div>

                <!-- Right Panel with Controls and Image -->
                <div class="right-panel">
                    <!-- Static top-right image -->
                    <div class="top-right-image"></div>

                    <!-- Dynamic image area (Square) -->
                    <div id="dynamic-image" class="dynamic-image"></div>

                    <!-- Controls -->
                    <div class="controls">
                        <button>&#9664;&#9664;</button> <!-- Previous button -->
                        <button>&#9654;</button> <!-- Play button -->
                        <button>&#9654;&#9654;</button> <!-- Next button -->
                    </div>
                    <input type="range" min="0" max="100" value="50" class="slider" oninput="this.style.setProperty('--value', this.value + '%');">
                </div>
            </div>
        </div>

        <!-- Script to load dynamic image from command -->
        <script>
            function loadDynamicImage(imagePath) {
                document.getElementById('dynamic-image').style.backgroundImage = `url('${imagePath}')`;
            }
            // loadDynamicImage('path/to/your/image.jpg'); // Uncomment and replace with your image path
        </script>
    </body>
</html>
