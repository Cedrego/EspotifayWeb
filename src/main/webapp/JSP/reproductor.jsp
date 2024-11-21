<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reproductor</title>
        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background-color: #333; /* Fondo general */
                color: #FFF; /* Color de texto */
                display: flex;
                flex-direction: column;
                height: 100%; /* Ajustar el 100% */
                margin: 0;
                padding-bottom: 80px; /* Añadir espacio para el footer */
            }

            .container {
                display: flex;
                flex-direction: column;
                flex-grow: 1; /* Para que el contenido se expanda correctamente */
            }

            .footer {
                width: 100%;
                background-color: #1a1a1a;
                padding: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                position: fixed;
                bottom: 0;
                height: 80px; /* Ajusta este valor si es necesario */
                z-index: 10; /* Asegúrate de que se quede encima del contenido */
            }

            .footer-content {
                display: flex;
                align-items: center;
                width: 100%;
                max-width: 1200px;
                justify-content: space-between;
                margin: 0 auto;
            }

            .image-and-label {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            #album-image {
                width: 50px;
                height: 50px;
                border-radius: 5px;
                border: 1px solid #FFF;
            }

            .song-label {
                font-size: 16px;
                font-weight: bold;
            }

            /* Controles de la barra inferior */
            .footer-controls {
                display: flex;
                flex-direction: row; /* Asegura que los botones estén en fila */
                align-items: center;
                justify-content: space-between; /* Distribuye los botones uniformemente */
                width: 40%; /* Define un tamaño razonable para el contenedor de los controles */
            }

            .controls button {
                background-color: #FFF;
                color: #000;
                border: none;
                border-radius: 50%;
                width: 40px;
                height: 40px;
                cursor: pointer;
                font-size: 18px;
                transition: background-color 0.3s ease, box-shadow 0.3s ease;
            }

            .controls button:hover {
                background-color: #1db954;
                color: #FFF;
                box-shadow: 0 0 10px #1db954, 0 0 20px #1db954;
            }

            #progress-bar {
                width: 100%; /* Ajusta el progreso para ocupar todo el espacio disponible */
                height: 5px;
                appearance: none;
                background: #555;
                border-radius: 5px;
                outline: none;
                cursor: pointer;
                transition: background 0.3s;
            }

            #progress-bar::-webkit-slider-thumb {
                appearance: none;
                width: 10px;
                height: 10px;
                border-radius: 50%;
                background: #1db954;
                cursor: pointer;
            }

            #progress-bar:hover {
                background: #777;
            }
        </style>
        <script>
            let isPlaying = false;

            function togglePlayPause() {
                const audioPlayer = document.getElementById('audio-player');
                const playPauseButton = document.getElementById('play-pause');

                if (isPlaying) {
                    audioPlayer.pause();
                    playPauseButton.innerHTML = "&#9654;"; // Play icon
                } else {
                    audioPlayer.play();
                    playPauseButton.innerHTML = "&#10074;&#10074;"; // Pause icon
                }
                isPlaying = !isPlaying;
            }

            function updateProgressBar() {
                const audioPlayer = document.getElementById('audio-player');
                const progressBar = document.getElementById('progress-bar');

                progressBar.value = (audioPlayer.currentTime / audioPlayer.duration) * 100;
            }

            function setAudioPosition() {
                const audioPlayer = document.getElementById('audio-player');
                const progressBar = document.getElementById('progress-bar');

                audioPlayer.currentTime = (progressBar.value / 100) * audioPlayer.duration;
            }
        </script>
    </head>
    <body>
        <div class="footer">
            <div class="footer-content">
                <!-- Reproductor con los controles -->
                <img id="album-image" src="<%= request.getContextPath() %>/images/noImageSong.png">
                <span id="song-name" class="song-label">Selecciona una canción</span>
                    <div class="footer-controls">
                        <button id="play-pause" onclick="togglePlayPause()">&#9654;</button>
                    </div>
                <input id="progress-bar" type="range" min="0" max="100" value="0" oninput="setAudioPosition()" class="slider">
                <audio id="audio-player" src="" ontimeupdate="updateProgressBar()"></audio>
            </div>
        </div>
    </body>
</html>
