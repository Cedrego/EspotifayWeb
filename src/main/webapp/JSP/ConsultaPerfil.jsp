<%@page import="java.net.URL"%>
<%@page import="java.lang.String"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="Capa_Presentacion.DataClienteMin"%>
<%@page import="Capa_Presentacion.DataPorDefecto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Capa_Presentacion.DataTema"%>
<%@page import="Capa_Presentacion.DataSuscripcion"%>
<%@page import="Capa_Presentacion.DataAlbum"%>
<%@page import="Capa_Presentacion.DataArtistaAlt"%>
<%@page import="Capa_Presentacion.DataParticular"%>
<%@page import="Capa_Presentacion.DataClienteAlt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<%@ page import="java.util.List" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<html>
    <head>
        <title>Consultar Perfil de Usuario</title>
        <style>
        /* Estilo de fondo y texto */
        body {
            background-color: #000;
            color: #FFF;
            font-family: 'Poppins', sans-serif;
        }

        h1,h2,h3,h4, label ,li{
            color: #FFF;
        }
        
        .imagen-cliente {
            max-width: 100px; /* Tamaño máximo de ancho */
            max-height: 100px; /* Tamaño máximo de alto */
            width: auto; /* Mantiene la proporción */
            height: auto; /* Mantiene la proporción */
            border-radius: 50%; /* Hace que la imagen sea circular */
            object-fit: cover; /* Asegura que la imagen cubra el área sin distorsionarse */
        }
        /* Estilo para select */
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

        /* Estilo para botones */
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
            function toggleAlbum(albumId) {
                const panel = document.getElementById(albumId);
                panel.style.display = panel.style.display === "none" ? "block" : "none";
            }
        </script>
    </head>
    <body>
        <script>
                    function toggleTema(temaId) {
                        const selectedPanel = document.getElementById(temaId);
        
                        // Verificar si el panel está actualmente visible
                        if (selectedPanel.style.display === 'block') {
                            selectedPanel.style.display = 'none'; // Ocultar si está visible
                        } else {
                            // Ocultar todos los paneles
                            const allPanels = document.querySelectorAll('.tema-panel');
                            allPanels.forEach(panel => {
                                panel.style.display = 'none'; // Cerrar todos los paneles
                            });
            
                            // Mostrar el panel seleccionado
                            selectedPanel.style.display = 'block'; // Mostrar el panel seleccionado
                        }
                    }

                        function playAudio(idTema, url) {
                            const audioElement = document.getElementById('audio-' + idTema);

                            // Verificar si la URL es de YouTube
                            if (url.startsWith('https://www.youtube.com') || url.startsWith('https://youtu.be')) {
                                // No se necesita ninguna acción, el iframe se muestra siempre
                            } else if (url.toLowerCase().endsWith('.mp3')) {
                                // Si es un MP3, configurar el src y reproducir
                                if (audioElement.src !== url) {
                                    audioElement.src = url; // Cambiar el src solo si es diferente
                                }
                                audioElement.play();
                            } else {
                                // Usar proxy si no es .mp3 y no es de YouTube
                                
                            }
                        }

                        function pauseAudio(idTema) {
                            const audioElement = document.getElementById('audio-' + idTema);
                            audioElement.pause();
                        }
       
        </script>
        <div class="container">
            <%
                String tipo = (String) request.getSession(false).getAttribute("tipo");
                Object usuario = request.getSession(false).getAttribute("usuario");
                request.getSession(false).removeAttribute("tipo");
                request.getSession(false).removeAttribute("usuario");
                
                if (tipo.equals("cliente")) {
                    DataClienteAlt cliente = (DataClienteAlt) usuario;
                    %>
            <div class="perfil">

                <%String pic = cliente.getPicture();%>
                <h3><%= cliente.getNickname()%></h3>
                <%if (pic == null || pic.isBlank()){%>
                    <img src="images/profiles/blank.png" class="imagen-cliente">
                <%}else{%>
                    <img src="<%= pic%>" class="imagen-cliente">
                <%}%>
                <p><strong>Nombre:</strong> <%= cliente.getNombre()%></p>
                <p><strong>Apellido:</strong> <%= cliente.getApellido()%></p>
                <p><strong>Correo</strong> <%= cliente.getCorreo()%></p>
                <p><strong>Fecha de Nacimiento:</strong> <%= cliente.getFecha().getDia() + "/" + cliente.getFecha().getMes() + "/" + cliente.getFecha().getAnio() %></p>
                <%if(!cliente.getDataCliSeguidor().isEmpty()){ %>
                    <ul>           
                    <h2>Seguidores</h2>
                    <%
                        for (String seguidor : cliente.getDataCliSeguidor()) {
                    %>
                    <li><%= seguidor%></li>
                        <% } %>
                </ul>
                <% 
                }
                boolean isSuS = false;
                for(DataSuscripcion sus : cliente.getDataSuscripcion()){
                    if(sus.getEstado().name().equals("Vigente")){
                        isSuS = true;
                    }
                }
                if(!isSuS){
                    %><h3> Suscripcion vigente requerida para mas informacion </h3><%
                }else{
                
                    if(!cliente.getDataCliSeguido().isEmpty()){
                %>
                
                <ul>
                <h2>Usuarios Seguidos</h2>
                    <%
                        for (String seguido : cliente.getDataCliSeguido()) {
                    %>
                    <li><%= "cliente - " + seguido%></li>
                        <% } %>
                </ul>
                <%}%>
                <ul>
                    <%if(!cliente.getDataArtSeguido().isEmpty()){
                        for (String aseguido : cliente.getDataArtSeguido()) {
                    %>
                    <li><%= "artista - " + aseguido%></li>
                        <% } %>
                </ul>
                <%}
                if(!cliente.getDataPart().isEmpty()){
                %>
                <li><h2>Listas de Reproducción</h2></li>
                <!-- Mostrar Listas de Reproducción -->
                <div class="listas-reproduccion">
                    <div style="display: flex; flex-wrap: wrap; margin-bottom: 10px;">
                        <%
                            for (DataParticular lista : cliente.getDataPart()) {
                                String PlayId = "Playlist-" + lista.getNombre().replace(" ", "_");
                        %>
                        <button onclick="toggleAlbum('<%= PlayId%>')" style="margin-right: 10px;"><%= lista.getNombre()%></button>
                        <% } %>
                    </div>
                    <div id="temas-container">
                        <%
                            for (DataParticular lista : cliente.getDataPart()) {
                                String PlayId = "Playlist-" + lista.getNombre().replace(" ", "_");
                        %>
                        <div id="<%= PlayId%>" class="album-panel" style="display: none; margin-top: 10px;">
                            <%if(lista.getDataTemas().isEmpty()){%><h5>La Playlist [<%= lista.getNombre()%>] no contiene temas</h5><%}
                            else{%>
                            <h5>Temas de [<%= lista.getNombre()%>]:</h5>
                            <ul class="temas">
                                <%
                                    for (DataTema tema : lista.getDataTemas()) {
                                %>
                                <li>
                                    <strong>Nombre:</strong> <%= tema.getNombre()%><br>
                                    <strong>Duración:</strong> <%= tema.getDuracion()%><br>
                                    <strong>Orden en el álbum:</strong> <%= tema.getOrdenAlbum()%><br>
                                    <strong>Guardado en:</strong> <%= tema.getDireccion()%><br>
                                    <strong>Géneros:</strong> <%= String.join(", ", tema.getGeneros())%><br>
                                    <%-- Verificar si la URL es de YouTube y crear un iframe --%>
                                    <%
                                        String direccion = tema.getDireccion();
                                        boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");

                                        String videoId = " ";

                                        if (direccion.startsWith("https://youtu.be")) {
                                            // Si es un enlace corto de YouTube, extraemos el ID
                                            videoId = direccion.split("youtu.be/")[1];
                                        } else if (direccion.startsWith("https://www.youtube.com")) {
                                            // Si es un enlace largo de YouTube, extraemos el ID
                                            videoId = direccion.split("v=")[1].split("&")[0];
                                        }
                                    %>

                                    <%-- Mostrar el iframe de YouTube con el ID extraído --%>
                                    <% if (direccion.startsWith("https://youtu.be") || direccion.startsWith("https://www.youtube.com")) {%>
                                    <!-- Embed YouTube Video -->
                                    <iframe width="220" height="135" src="https://www.youtube.com/embed/<%= videoId%>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                        <% } else if (isMp3) {%>
                                    <!-- Reproductor de audio para archivos MP3 con controles completos -->
                                    <audio id="audio-<%= tema.getIdTema()%>" controls>
                                        <source src="<%= direccion%>" type="audio/mpeg">
                                        Tu navegador no soporta el elemento de audio.
                                    </audio>
                                    <% } else {
                                    // Obtener la URL real redirigida
                                        if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
                                                direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
                                        }
                                        String realUrl = null;
                                        try {
                                            // Crear la conexión HTTP con la URL de Bit.ly
                                            HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
                                            connection.setRequestMethod("HEAD"); // Solo necesitamos la cabecera, no el cuerpo
                                            connection.connect();

                                            // Obtener la cabecera "Location" que es la URL redirigida
                                            realUrl = connection.getHeaderField("Location");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
                                        if (realUrl != null) {
                                            // Asegúrate de que la URL real tiene la estructura correcta
                                            String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                                    %>

                                    <!-- Reproducir la pista de SoundCloud en un iframe -->
                                    <iframe width="100%" height="166" scrolling="no" frameborder="no"
                                            src="https://w.soundcloud.com/player/?url=<%= encodedUrl%>&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false">
                                    </iframe>

                                    <%
                                    } else {
                                    %>
                                    <!-- Mensaje en caso de que no se pueda obtener la URL -->
                                    <p>No se pudo redirigir el enlace de Bit.ly.</p>
                                    <%
                                        }
                                    %>
                                    <%}%>
                                    <%if(isSuS){%>
                                    <%-- Botón para Descargar MP3 (Simula "Guardar audio como...") --%>
                                    <% if (isMp3) {%>
                                    <!-- Este enlace tiene el atributo "download" para forzar la descarga del archivo -->
                                    <a href="<%= direccion%>" download>
                                        <button>Descargar MP3</button>
                                    </a>
                                    <% } %>
                                    <%}%>
                                </li>
                                <% } %>
                            </ul>
                            <%}%>
                        </div>
                        <% } %>
                    </div>
                </div>
                    <%}%>
                <%if(!cliente.getDataPartFav().isEmpty() && !cliente.getDataPorDefFav().isEmpty()){%>
                <h2>Playlists Favoritas</h2>
                <!-- Mostrar Listas de Reproducción -->
                <div class="listas-reproduccion">
                    <div style="display: flex; flex-wrap: wrap; margin-bottom: 10px;">
                        <%
                            for (DataParticular lista : cliente.getDataPartFav()) {
                                String PlayId = "Playlist-" + lista.getNombre().replace(" ", "_");
                        %>
                        <button onclick="toggleAlbum('<%= PlayId%>')" style="margin-right: 10px;"><%= lista.getNombre()%></button>
                        <% } %>
                        <%
                            for (DataPorDefecto lista : cliente.getDataPorDefFav()) {
                                String PlayId = "Playlist-" + lista.getNombre().replace(" ", "_");
                        %>
                        <button onclick="toggleAlbum('<%= PlayId%>')" style="margin-right: 10px;"><%= lista.getNombre()%></button>
                        <% } %>
                    </div>
                    <div id="temas-container">
                        <%
                            for (DataParticular lista : cliente.getDataPart()) {
                                String PlayId = "Playlist-" + lista.getNombre().replace(" ", "_");
                        %>
                        <div id="<%= PlayId%>" class="album-panel" style="display: none; margin-top: 10px;">
                            <%if(lista.getDataTemas().isEmpty()){%><h5>La Playlist [<%= lista.getNombre()%>] no contiene temas</h5><%}
                            else{%>
                            <h5>Temas de [<%= lista.getNombre()%>]:</h5>
                            <ul class="temas">
                                <%
                                    for (DataTema tema : lista.getDataTemas()) {
                                %>
                                <li>
                                    <strong>Nombre:</strong> <%= tema.getNombre()%><br>
                                    <strong>Duración:</strong> <%= tema.getDuracion()%><br>
                                    <strong>Orden en el álbum:</strong> <%= tema.getOrdenAlbum()%><br>
                                    <strong>Guardado en:</strong> <%= tema.getDireccion()%><br>
                                    <strong>Géneros:</strong> <%= String.join(", ", tema.getGeneros())%><br>
                                    <%-- Verificar si la URL es de YouTube y crear un iframe --%>
                                    <%
                                        String direccion = tema.getDireccion();
                                        boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");

                                        String videoId = " ";

                                        if (direccion.startsWith("https://youtu.be")) {
                                            // Si es un enlace corto de YouTube, extraemos el ID
                                            videoId = direccion.split("youtu.be/")[1];
                                        } else if (direccion.startsWith("https://www.youtube.com")) {
                                            // Si es un enlace largo de YouTube, extraemos el ID
                                            videoId = direccion.split("v=")[1].split("&")[0];
                                        }
                                    %>

                                    <%-- Mostrar el iframe de YouTube con el ID extraído --%>
                                    <% if (direccion.startsWith("https://youtu.be") || direccion.startsWith("https://www.youtube.com")) {%>
                                    <!-- Embed YouTube Video -->
                                    <iframe width="220" height="135" src="https://www.youtube.com/embed/<%= videoId%>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                        <% } else if (isMp3) {%>
                                    <!-- Reproductor de audio para archivos MP3 con controles completos -->
                                    <audio id="audio-<%= tema.getIdTema()%>" controls>
                                        <source src="<%= direccion%>" type="audio/mpeg">
                                        Tu navegador no soporta el elemento de audio.
                                    </audio>
                                    <% } else {
                                    // Obtener la URL real redirigida
                                        if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
                                                direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
                                            }
                                            String realUrl = null;
                                        try {
                                            // Crear la conexión HTTP con la URL de Bit.ly
                                            HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
                                            connection.setRequestMethod("HEAD"); // Solo necesitamos la cabecera, no el cuerpo
                                            connection.connect();

                                            // Obtener la cabecera "Location" que es la URL redirigida
                                            realUrl = connection.getHeaderField("Location");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
                                        if (realUrl != null) {
                                            // Asegúrate de que la URL real tiene la estructura correcta
                                            String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                                    %>

                                    <!-- Reproducir la pista de SoundCloud en un iframe -->
                                    <iframe width="100%" height="166" scrolling="no" frameborder="no"
                                            src="https://w.soundcloud.com/player/?url=<%= encodedUrl%>&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false">
                                    </iframe>

                                    <%
                                    } else {
                                    %>
                                    <!-- Mensaje en caso de que no se pueda obtener la URL -->
                                    <p>No se pudo redirigir el enlace de Bit.ly.</p>
                                    <%
                                        }
                                    %>
                                    <%}%>
                                    <%if (isSuS) {%>
                                    <%-- Botón para Descargar MP3 (Simula "Guardar audio como...") --%>
                                    <% if (isMp3) {%>
                                    <!-- Este enlace tiene el atributo "download" para forzar la descarga del archivo -->
                                    <a href="<%= direccion%>" download>
                                        <button>Descargar MP3</button>
                                    </a>
                                    <% } %>
                                    <%}%>
                                </li>
                                <% } %>
                            </ul>
                            <%}%>
                        </div>
                        <%
                            }
                        for (DataPorDefecto lista : cliente.getDataPorDefFav()) {
                            String PlayIdpd = "Playlist-" + lista.getNombre().replace(" ", "_");
                        
                        %>
                        <div id="<%= PlayIdpd%>" class="album-panel" style="display: none; margin-top: 10px;">
                            <%if (lista.getDataTemas().isEmpty()) {%><h5>La Playlist [<%= lista.getNombre()%>] no contiene temas</h5><%} else {%>
                            <h5>Temas de [<%= lista.getNombre()%>]:</h5>
                            <ul class="temas">
                                <%
                                    for (DataTema tema : lista.getDataTemas()) {
                                %>
                                <li>
                                    <strong>Nombre:</strong> <%= tema.getNombre()%><br>
                                    <strong>Duración:</strong> <%= tema.getDuracion()%><br>
                                    <strong>Orden en el álbum:</strong> <%= tema.getOrdenAlbum()%><br>
                                    <strong>Guardado en:</strong> <%= tema.getDireccion()%><br>
                                    <strong>Géneros:</strong> <%= String.join(", ", tema.getGeneros())%><br>
                                    <%-- Verificar si la URL es de YouTube y crear un iframe --%>
                                    <%
                                        String direccion = tema.getDireccion();
                                        boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");

                                        String videoId = " ";

                                        if (direccion.startsWith("https://youtu.be")) {
                                            // Si es un enlace corto de YouTube, extraemos el ID
                                            videoId = direccion.split("youtu.be/")[1];
                                        } else if (direccion.startsWith("https://www.youtube.com")) {
                                            // Si es un enlace largo de YouTube, extraemos el ID
                                            videoId = direccion.split("v=")[1].split("&")[0];
                                        }
                                    %>

                                    <%-- Mostrar el iframe de YouTube con el ID extraído --%>
                                    <% if (direccion.startsWith("https://youtu.be") || direccion.startsWith("https://www.youtube.com")) {%>
                                    <!-- Embed YouTube Video -->
                                    <iframe width="220" height="135" src="https://www.youtube.com/embed/<%= videoId%>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                        <% } else if (isMp3) {%>
                                    <!-- Reproductor de audio para archivos MP3 con controles completos -->
                                    <audio id="audio-<%= tema.getIdTema()%>" controls>
                                        <source src="<%= direccion%>" type="audio/mpeg">
                                        Tu navegador no soporta el elemento de audio.
                                    </audio>
                                    <% } else {
                                    // Obtener la URL real redirigida
                                        if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
                                                direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
                                            }
                                            String realUrl = null;
                                        try {
                                            // Crear la conexión HTTP con la URL de Bit.ly
                                            HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
                                            connection.setRequestMethod("HEAD"); // Solo necesitamos la cabecera, no el cuerpo
                                            connection.connect();

                                            // Obtener la cabecera "Location" que es la URL redirigida
                                            realUrl = connection.getHeaderField("Location");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
                                        if (realUrl != null) {
                                            // Asegúrate de que la URL real tiene la estructura correcta
                                            String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                                    %>

                                    <!-- Reproducir la pista de SoundCloud en un iframe -->
                                    <iframe width="100%" height="166" scrolling="no" frameborder="no"
                                            src="https://w.soundcloud.com/player/?url=<%= encodedUrl%>&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false">
                                    </iframe>

                                    <%
                                    } else {
                                    %>
                                    <!-- Mensaje en caso de que no se pueda obtener la URL -->
                                    <p>No se pudo redirigir el enlace de Bit.ly.</p>
                                    <%
                                        }
                                    %>
                                    <%}%>
                                    <%if (isSuS) {%>
                                    <%-- Botón para Descargar MP3 (Simula "Guardar audio como...") --%>
                                    <% if (isMp3) {%>
                                    <!-- Este enlace tiene el atributo "download" para forzar la descarga del archivo -->
                                    <a href="<%= direccion%>" download>
                                        <button>Descargar MP3</button>
                                    </a>
                                    <% } %>
                                    <%}%>
                                </li>
                                <% } %>
                            </ul>
                            <%}%>
                        </div>
                        <%}%>
                    </div>
                    <%}%>
                </div>
                    <%
                    List<String> Gros = (List<String>)request.getSession(false).getAttribute("Generos");
                    
                    if (!cliente.getDataAlmFav().isEmpty()) {%>
                        <h2>Álbumes Favoritos</h2>
                                        <% if (Gros != null && !Gros.isEmpty()) { %>
                            <label for="generoFiltro">Filtrar por género:</label>
                            <select id="generoFiltro" onchange="filtrarAlbumes()">
                                <option value="">Todos los géneros</option>
                                        <% for (String genero : Gros) {%>
                                    <option value="<%= genero%>"><%= genero%></option>
                                        <% } %>
                            </select>
                                        <% } %>

                        <div id="listas-reproduccion" class="listas-reproduccion">
                            <!-- Aquí se llenarán los botones de álbum -->
                                        <% for (DataAlbum alb : cliente.getDataAlmFav()) {%>
                                <div class="album-container" data-generos="<%= String.join(",", alb.getGeneros())%>">
                                    <button onclick="toggleAlbum('<%= alb.getNombre().replace(" ", "_")%>')">
                                        <%= alb.getNombre()%>
                                    </button>
                                    <div id="<%= alb.getNombre().replace(" ", "_")%>" class="album-panel" style="display: none; margin-top: 10px;">
                                        <% if (alb.getDataTemas().isEmpty()) {%>
                                            <h5>La Playlist [<%= alb.getNombre()%>] no contiene temas</h5>
                                        <% } else {%>
                                            <h5>Temas de [<%= alb.getNombre()%>]:</h5>
                                            <ul class="temas">
                                        <% for (DataTema tema : alb.getDataTemas()) {%>
                                                    <li>
                                                        <strong>Nombre:</strong> <%= tema.getNombre()%><br>
                                                        <strong>Duración:</strong> <%= tema.getDuracion()%><br>
                                                        <strong>Orden en el álbum:</strong> <%= tema.getOrdenAlbum()%><br>
                                                        <strong>Guardado en:</strong> <%= tema.getDireccion()%><br>
                                                        <strong>Géneros:</strong> <%= String.join(", ", tema.getGeneros())%><br>
                                                        <%-- Verificar si la URL es de YouTube y crear un iframe --%>
                                                        <%
                                                            String direccion = tema.getDireccion();
                                                            boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");

                                                            String videoId = " ";

                                                            if (direccion.startsWith("https://youtu.be")) {
                                                                // Si es un enlace corto de YouTube, extraemos el ID
                                                                videoId = direccion.split("youtu.be/")[1];
                                                            } else if (direccion.startsWith("https://www.youtube.com")) {
                                                                // Si es un enlace largo de YouTube, extraemos el ID
                                                                videoId = direccion.split("v=")[1].split("&")[0];
                                                            }
                                                        %>

                                                        <%-- Mostrar el iframe de YouTube con el ID extraído --%>
                                                        <% if (direccion.startsWith("https://youtu.be") || direccion.startsWith("https://www.youtube.com")) {%>
                                                        <!-- Embed YouTube Video -->
                                                        <iframe width="220" height="135" src="https://www.youtube.com/embed/<%= videoId%>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                                            <% } else if (isMp3) {%>
                                                        <!-- Reproductor de audio para archivos MP3 con controles completos -->
                                                        <audio id="audio-<%= tema.getIdTema()%>" controls>
                                                            <source src="<%= direccion%>" type="audio/mpeg">
                                                            Tu navegador no soporta el elemento de audio.
                                                        </audio>
                                                        <% } else {
                                                        // Obtener la URL real redirigida
                                                            if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
                                                                    direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
                                                                }
                                                                String realUrl = null;
                                                            try {
                                                                // Crear la conexión HTTP con la URL de Bit.ly
                                                                HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
                                                                connection.setRequestMethod("HEAD"); // Solo necesitamos la cabecera, no el cuerpo
                                                                connection.connect();

                                                                // Obtener la cabecera "Location" que es la URL redirigida
                                                                realUrl = connection.getHeaderField("Location");
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }

                                                            // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
                                                            if (realUrl != null) {
                                                                // Asegúrate de que la URL real tiene la estructura correcta
                                                                String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                                                        %>

                                                        <!-- Reproducir la pista de SoundCloud en un iframe -->
                                                        <iframe width="100%" height="166" scrolling="no" frameborder="no"
                                                                src="https://w.soundcloud.com/player/?url=<%= encodedUrl%>&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false">
                                                        </iframe>

                                                        <%
                                                        } else {
                                                        %>
                                                        <!-- Mensaje en caso de que no se pueda obtener la URL -->
                                                        <p>No se pudo redirigir el enlace de Bit.ly.</p>
                                                        <%
                                                            }
                                                        %>
                                                        <%}%>
                                                        <%if (isSuS) {%>
                                                        <%-- Botón para Descargar MP3 (Simula "Guardar audio como...") --%>
                                                        <% if (isMp3) {%>
                                                        <!-- Este enlace tiene el atributo "download" para forzar la descarga del archivo -->
                                                        <a href="<%= direccion%>" download>
                                                            <button>Descargar MP3</button>
                                                        </a>
                                                        <% } %>
                                                        <%}%>
                                                    </li>
                                        <% } %>
                                            </ul>
                                        <% } %>
                                    </div>
                                </div>
                                        <% } %>
                        </div>

                        <script>
                            function toggleAlbum(playId) {
                                const selectedPanel = document.getElementById(playId);
                                if (selectedPanel.style.display === 'block') {
                                    selectedPanel.style.display = 'none'; // Ocultar si está visible
                                } else {
                                    const allPanels = document.querySelectorAll('.album-panel');
                                    allPanels.forEach(panel => {
                                        panel.style.display = 'none'; // Cerrar todos los paneles
                                    });
                                    selectedPanel.style.display = 'block'; // Mostrar el panel seleccionado
                                }
                            }

                            function filtrarAlbumes() {
                                const selectedGenero = document.getElementById('generoFiltro').value;
                                const albumContainers = document.querySelectorAll('.album-container');

                                albumContainers.forEach(container => {
                                    const generosAlbum = container.getAttribute('data-generos').split(',');
                                    const button = container.querySelector('button');

                                    if (selectedGenero === "" || generosAlbum.includes(selectedGenero)) {
                                        container.style.display = ''; // Mostrar contenedor de álbum
                                    } else {
                                        container.style.display = 'none'; // Ocultar contenedor de álbum
                                    }
                                });
                            }
                        </script>
                                        <% } %>

                <%if(!cliente.getDataTemaFav().isEmpty()){%>
                <h2>Temas Favoritos</h2>
                <!-- Mostrar Temas en dos columnas -->
                <div class="temas-lista">
                    <div style="display: flex; flex-wrap: wrap;">
                        <%
                            int columnCount = 0; // Contador para alternar columnas
                            for (DataTema tema : cliente.getDataTemaFav()) { // Suponiendo que tienes una lista de DataTema
                                String temaId = "Tema-" + tema.getNombre().replace(" ", "_");
                        %>
                        <button onclick="toggleTema('<%= temaId%>')" style="margin: 5px; flex: 1 0 45%;"><%= tema.getNombre()%></button>
                        <%
                                columnCount++;
                                if (columnCount % 2 == 0) { // Salto de línea después de cada dos botones
                                    out.println("</div><div style='display: flex; flex-wrap: wrap; margin-bottom: 10px;'>");
                                }
                            }
                        %>
                    </div>
                </div>
                <div id="tema-details-container">
                    <%
                        for (DataTema tema : cliente.getDataTemaFav()) {
                            String temaId = "Tema-" + tema.getNombre().replace(" ", "_");
                    %>
                    <div id="<%= temaId%>" class="tema-panel" style="display: none; margin-top: 10px;">
                        <h5>Detalles de <%= tema.getNombre()%>:</h5>
                        <strong>Nombre:</strong> <%= tema.getNombre()%><br>
                        <strong>Duración:</strong> <%= tema.getDuracion()%><br>
                        <strong>Orden en el álbum:</strong> <%= tema.getOrdenAlbum()%><br>
                        <strong>Guardado en:</strong> <%= tema.getDireccion()%><br>
                        <strong>Géneros:</strong> <%= String.join(", ", tema.getGeneros())%><br>
                        <%-- Verificar si la URL es de YouTube y crear un iframe --%>
                        <%
                            String direccion = tema.getDireccion();
                            boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");

                            String videoId = " ";

                            if (direccion.startsWith("https://youtu.be")) {
                                // Si es un enlace corto de YouTube, extraemos el ID
                                videoId = direccion.split("youtu.be/")[1];
                            } else if (direccion.startsWith("https://www.youtube.com")) {
                                // Si es un enlace largo de YouTube, extraemos el ID
                                videoId = direccion.split("v=")[1].split("&")[0];
                            }
                        %>

                        <%-- Mostrar el iframe de YouTube con el ID extraído --%>
                        <% if (direccion.startsWith("https://youtu.be") || direccion.startsWith("https://www.youtube.com")) {%>
                        <!-- Embed YouTube Video -->
                        <iframe width="220" height="135" src="https://www.youtube.com/embed/<%= videoId%>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                            <% } else if (isMp3) {%>
                        <!-- Reproductor de audio para archivos MP3 con controles completos -->
                        <audio id="audio-<%= tema.getIdTema()%>" controls>
                            <source src="<%= direccion%>" type="audio/mpeg">
                            Tu navegador no soporta el elemento de audio.
                        </audio>
                        <% } else {
                        // Obtener la URL real redirigida
        if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
            direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
        }

        // Obtener la URL real redirigida
        String realUrl = null;
        try {
            // Crear la conexión HTTP con la URL de Bit.ly
            HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
            connection.setRequestMethod("HEAD"); // Solo necesitamos la cabecera, no el cuerpo

            // Realizamos la conexión
            connection.connect();

            // Obtener el código de respuesta
            int responseCode = connection.getResponseCode();
            String location = connection.getHeaderField("Location");

            // Depuración: Mostrar el código de respuesta y la URL redirigida
            out.println("Código de respuesta: " + responseCode + "<br>");
            out.println("Ubicación redirigida: " + location + "<br>");

            // Si la redirección es válida, obtenemos la URL
            if (location != null) {
                realUrl = location;
            } else {
                out.println("No se encontró una URL de redirección.");
            }

        } catch (Exception e) {
            out.println("Error al conectar con Bit.ly: " + e.getMessage());
            e.printStackTrace();
        }

        // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
        if (realUrl != null) {
            // Asegúrate de que la URL real tiene la estructura correcta
            String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                        %>

        <!-- Reproducir la pista de SoundCloud en un iframe -->
        <iframe width="100%" height="166" scrolling="no" frameborder="no"
        src="https://w.soundcloud.com/player/?url=<%= encodedUrl%>&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false">
        </iframe>

                        <%
                        } else {
                        %>
        <!-- Mensaje en caso de que no se pueda obtener la URL -->
        <p>No se pudo redirigir el enlace de Bit.ly.</p>
                        <%
                            }
                        %>
                        <%}%>
                        <%if (isSuS) {%>
                        <%-- Botón para Descargar MP3 (Simula "Guardar audio como...") --%>
                        <% if (isMp3) {%>
                        <!-- Este enlace tiene el atributo "download" para forzar la descarga del archivo -->
                        <a href="<%= direccion%>" download>
                            <button>Descargar MP3</button>
                        </a>
                        <% } %>
                        <%}%>
                    </div>
                    <% } %>
                </div>
                <%}%>
                
                <%}%>
                </div>
            <%} else if (tipo.equals("artista")) { 
                DataArtistaAlt artista = (DataArtistaAlt) usuario;
            %>
            <div class="perfil">
                <h3><%= artista.getNickname()%></h3>
                <!-- IMAGEN -->

                <%String pic = artista.getPicture();%>
                <%if (pic == null || pic.isBlank()){%>
                    <img src="images/profiles/blank.png" class="imagen-cliente">
                <%}else{%>
                    <img src="<%= pic%>" class="imagen-cliente">
                <%}%>
                <p><strong>Nombre:</strong> <%= artista.getNombre()%></p>
                <p><strong>Apellido:</strong> <%= artista.getApellido()%></p>
                <p><strong>Correo:</strong> <%= artista.getCorreo()%></p>
                <p><strong>Fecha de Nacimiento:</strong> <%= artista.getFecha().getDia() + "/" + artista.getFecha().getMes() + "/" + artista.getFecha().getAnio()%></p>

                <% if (!artista.getDataalbumes().isEmpty()) { %>
                <h2>Álbumes</h2>
                <ul>
                    <%
                        for (DataAlbum album : artista.getDataalbumes()) {
                            String albumId = "album-" + album.getNombre().replace(" ", "_");
                    %>
                    <li>
                        <button onclick="toggleAlbum('<%= albumId%>')"><%= album.getNombre()%></button>
                        <div id="<%= albumId%>" class="album-panel" style="display: none;">
                            <h5>Detalles del Álbum:</h5>
                            <strong>Año de Creación:</strong> <%= album.getCreacion()%><br>
                            <strong>Géneros:</strong> <%= String.join(", ", album.getGeneros())%><br>
                            <!-- Aqui faltan las imagenes al igual que en el resto de clases con imagen faltante :V -->
                            <h5>Temas:</h5>
                            <ul class="temas">
                                <% for (DataTema tema : album.getDataTemas()) {%>
                                <li>
                                    <strong>Nombre:</strong> <%= tema.getNombre()%><br>
                                    <strong>Duración:</strong> <%= tema.getDuracion()%><br>
                                    <strong>Orden en el álbum:</strong> <%= tema.getOrdenAlbum()%><br>
                                    <strong>Guardado en:</strong> <%= tema.getDireccion()%><br>
                                    <strong>Géneros:</strong> <%= String.join(", ", tema.getGeneros())%><br>
                                    <%-- Verificar si la URL es de YouTube y crear un iframe --%>
                                    <%
                                        String direccion = tema.getDireccion();
                                        boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");

                                        String videoId = " ";

                                        if (direccion.startsWith("https://youtu.be")) {
                                            // Si es un enlace corto de YouTube, extraemos el ID
                                            videoId = direccion.split("youtu.be/")[1];
                                        } else if (direccion.startsWith("https://www.youtube.com")) {
                                            // Si es un enlace largo de YouTube, extraemos el ID
                                            videoId = direccion.split("v=")[1].split("&")[0];
                                        }
                                    %>

                                    <%-- Mostrar el iframe de YouTube con el ID extraído --%>
                                    <% if (direccion.startsWith("https://youtu.be") || direccion.startsWith("https://www.youtube.com")) {%>
                                    <!-- Embed YouTube Video -->
                                    <iframe width="220" height="135" src="https://www.youtube.com/embed/<%= videoId%>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                        <% } else if (isMp3) {%>
                                    <!-- Reproductor de audio para archivos MP3 con controles completos -->
                                    <audio id="audio-<%= tema.getIdTema()%>" controls>
                                        <source src="<%= direccion%>" type="audio/mpeg">
                                        Tu navegador no soporta el elemento de audio.
                                    </audio>
                                    <% } else {
                                    // Obtener la URL real redirigida
   // Asegurarse de que la URL tiene el protocolo "https://"
       if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
           direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
       }

       // Obtener la URL real redirigida
       String realUrl = null;
       try {
           // Crear la conexión HTTP con la URL de Bit.ly
           HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
           connection.setRequestMethod("HEAD"); // Solo necesitamos la cabecera, no el cuerpo

           // Realizamos la conexión
           connection.connect();

           // Obtener el código de respuesta
           int responseCode = connection.getResponseCode();
           String location = connection.getHeaderField("Location");

           // Depuración: Mostrar el código de respuesta y la URL redirigida
           out.println("Código de respuesta: " + responseCode + "<br>");
           out.println("Ubicación redirigida: " + location + "<br>");

           // Si la redirección es válida, obtenemos la URL
           if (location != null) {
               realUrl = location;
           } else {
               out.println("No se encontró una URL de redirección.");
           }

       } catch (Exception e) {
           out.println("Error al conectar con Bit.ly: " + e.getMessage());
           e.printStackTrace();
       }

       // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
       if (realUrl != null) {
           // Asegúrate de que la URL real tiene la estructura correcta
           String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                                    %>

        <!-- Reproducir la pista de SoundCloud en un iframe -->
        <iframe width="100%" height="166" scrolling="no" frameborder="no"
        src="https://w.soundcloud.com/player/?url=<%= encodedUrl%>&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false">
        </iframe>

                                    <%
                                    } else {
                                    %>
        <!-- Mensaje en caso de que no se pueda obtener la URL -->
        <p>No se pudo redirigir el enlace de Bit.ly.</p>
                                    <%
                                        }
                                    %>
                                    <%}%>

                                    <%-- Botón para Descargar MP3 (Simula "Guardar audio como...") --%>
                                    <% if (isMp3) {%>
                                    <!-- Este enlace tiene el atributo "download" para forzar la descarga del archivo -->
                                    <a href="<%= direccion%>" download>
                                        <button>Descargar MP3</button>
                                    </a>
                                    <% } %>
                                </li>
                                <% } %>
                            </ul>
                        </div>
                    </li>
                    <% } %>
                </ul>
                <% } else { %>
                <p>No hay álbumes disponibles para este artista.</p>
                <% } %>
            </div>


            <%
            } else { // Es Invitado  
            %>
            <h1><u>Artistas Disponibles</u></h1>
            <div class="container">
                <%int cont = 0;
                    List<DataArtistaAlt> dataArtistas = (List<DataArtistaAlt>) request.getSession(false).getAttribute("dataArtistas");
                    request.getSession(false).removeAttribute("dataArtistas");
                    
                    if (dataArtistas != null && !dataArtistas.isEmpty()) {
                        for (DataArtistaAlt artista : dataArtistas) {
                            String artistaId = "artista-" + artista.getNickname().replace(" ", "_");
                %>
                <div class="perfil">
                    <!-- IMAGEN -->
                    <h3>
                        <a href="javascript:void(0);" onclick="toggleArtistInfo('<%= artistaId%>')" style="display: flex; align-items: center; text-decoration: none;">
                            <% String pic = artista.getPicture(); %>
                            <% if (pic == null || pic.isBlank()) { %>
                                <img src="images/profiles/blank.png" class="imagen-cliente" style="margin-right: 12px;">
                            <% } else {%>
                                <img src="<%= pic%>" class="imagen-cliente" style="margin-right: 12px;">
                            <% }%>
                            <p><strong><%= artista.getNickname()%></strong></p>
                        </a>
                    </h3>
                    <div id="<%= artistaId%>" class="artist-info" style="display: none;">
                        <h2>Información Adicional</h2>
                        <p><strong>Nombre:</strong> <%= artista.getNombre()%></p>
                        <p><strong>Apellido:</strong> <%= artista.getApellido()%></p>
                        <p><strong>Fecha de Nacimiento:</strong> <%= artista.getFecha().getDia() + "/" + artista.getFecha().getMes() + "/" + artista.getFecha().getAnio()%></p>
                        <p><strong>Página Web:</strong> <a href="<%= artista.getSitioWeb()%>" target="_blank"><%= artista.getSitioWeb()%></a></p>
                        <p><strong>Biografía:</strong> <%= artista.getBiografia()%></p>
                        <p><strong>Cantidad de Seguidores:</strong> <%= artista.getDataseguidoPorA().size()%></p>

                        <h2>Álbumes</h2>
                        <ul>
                            <%
                                for (DataAlbum album : artista.getDataalbumes()) {
                                    String albumId = "album-" + album.getNombre().replace(" ", "_");
                            %>
                            <li>
                                <button onclick="toggleAlbumInfo('<%= albumId%>')"><%= album.getNombre()%></button>
                                <div id="<%= albumId%>" class="album-panel" style="display: none;">
                                    <strong>Año de Creación:</strong> <%= album.getCreacion()%><br>
                                    <strong>Géneros:</strong> <%= String.join(", ", album.getGeneros())%><br>
                                    <h5>Temas:</h5>
                                    <ul class="temas">
                                        <%
                                            for (DataTema tema : album.getDataTemas()) {
                                        %>
                                        <li>
                                            <strong>Nombre:</strong> <%= tema.getNombre()%><br>
                                            <strong>Duración:</strong> <%= tema.getDuracion()%><br>
                                            <%-- Verificar si la URL es de YouTube y crear un iframe --%>
                                            <%
                                                String direccion = tema.getDireccion();
                                                boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");

                                                        String videoId = " ";

                                                if (direccion.startsWith("https://youtu.be")) {
                                                    // Si es un enlace corto de YouTube, extraemos el ID
                                                    videoId = direccion.split("youtu.be/")[1];
                                                } else if (direccion.startsWith("https://www.youtube.com")) {
                                                    // Si es un enlace largo de YouTube, extraemos el ID
                                                    videoId = direccion.split("v=")[1].split("&")[0];
                                                }
                                            %>

                                            <%-- Mostrar el iframe de YouTube con el ID extraído --%>
                                            <% if (direccion.startsWith("https://youtu.be") || direccion.startsWith("https://www.youtube.com")) {%>
                                            <!-- Embed YouTube Video -->
                                            <iframe width="220" height="135" src="https://www.youtube.com/embed/<%= videoId%>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                                <% } else if (isMp3) {%>
                                            <!-- Reproductor de audio para archivos MP3 con controles completos -->
                                            <audio id="audio-<%= tema.getIdTema()%>" controls>
                                                <source src="<%= direccion%>" type="audio/mpeg">
                                                Tu navegador no soporta el elemento de audio.
                                            </audio>
                                            <% } else {
                                            // Obtener la URL real redirigida
                                                if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
                                                        direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
                                                    }
                                                    String realUrl = null;
                                                try {
                                                    // Crear la conexión HTTP con la URL de Bit.ly
                                                    HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
                                                    connection.setRequestMethod("HEAD"); // Solo necesitamos la cabecera, no el cuerpo
                                                    connection.connect();

                                                    // Obtener la cabecera "Location" que es la URL redirigida
                                                    realUrl = connection.getHeaderField("Location");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                                // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
                                                if (realUrl != null) {
                                                    // Asegúrate de que la URL real tiene la estructura correcta
                                                    String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                                            %>

                                            <!-- Reproducir la pista de SoundCloud en un iframe -->
                                            <iframe width="100%" height="166" scrolling="no" frameborder="no"
                                                    src="https://w.soundcloud.com/player/?url=<%= encodedUrl%>&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false">
                                            </iframe>

                                            <%
                                            } else {
                                            %>
                                            <!-- Mensaje en caso de que no se pueda obtener la URL -->
                                            <p>No se pudo redirigir el enlace de Bit.ly.</p>
                                            <%
                                                }
                                            %>
                                            <%}%>

                                            <%-- Botón para Descargar MP3 (Simula "Guardar audio como...") --%>
                                            <% if (isMp3) {%>
                                            <!-- Este enlace tiene el atributo "download" para forzar la descarga del archivo -->
                                            <a href="<%= direccion%>" download>
                                                <button>Descargar MP3</button>
                                            </a>
                                            <% } %>
                                        </li>
                                        <% } %>
                                    </ul>
                                </div>
                            </li>
                            <% } %>
                        </ul>
                    </div>
                </div>
                        <h4>=============================================</h4>
                <%
                    }
                } else {
                %>
                <p><label>No hay artistas disponibles.</label></p>
                <%
                    }
                %>
            </div>

            <script>
                function toggleArtistInfo(artistId) {
                    const infoPanel = document.getElementById(artistId);
                    infoPanel.style.display = infoPanel.style.display === 'none' ? 'block' : 'none';
                }

                function toggleAlbumInfo(albumId) {
                    const albumPanel = document.getElementById(albumId);
                    albumPanel.style.display = albumPanel.style.display === 'none' ? 'block' : 'none';
                }
            </script>

            <h1><u>Clientes Disponibles</u></h1>
            <div class="container">
                <%
                    List<DataClienteMin> dataClientes = (List<DataClienteMin>) request.getSession(false).getAttribute("DataClientes");
                    request.getSession(false).removeAttribute("DataClientes");
                    
                    if (!(dataClientes == null)) {
                        for (DataClienteMin client : dataClientes) {
                %>
                <div class="perfil">
                    <div style="display: flex; align-items: center; text-decoration: none;">
                        
                        <%String pic = client.getPicture();%>
                        <%if (pic == null || pic.isBlank()){%>
                            <img src="images/profiles/blank.png" class="imagen-cliente" style="margin-right: 12px">
                        <%}else{%>
                            <img src="<%= pic%>" class="imagen-cliente" style="margin-right: 12px">
                        <%}%>
                        <h3><%= client.getNickname()%></h3>
                    </div>
                    <%if(!client.getDataPart().isEmpty()){%>
                        <h4>Listas de Reproducción</h4>
                        <ul>
                            <%
                                for (DataParticular lista : client.getDataPart()) {
                                    String albumId = "lista-" + lista.getNombre().replace(" ", "_");
                            %>
                            <ul class="temas">
                                <%

                                    for (DataTema tema : lista.getDataTemas()) {
                                        String direccion = tema.getDireccion();

                                        cont++;
                                        boolean isMp3 = direccion.toLowerCase().endsWith(".mp3");
                                %>
                                <li>
                                    <strong>Nombre:</strong> <%= tema.getNombre()%><br>
                                    <strong>Duración:</strong> <%= tema.getDuracion()%><br>

                                    <%-- Verificar si la URL es de YouTube y crear un iframe --%>
                                    <%
                                        String videoId = " ";
                                    
                                        if (direccion.startsWith("https://youtu.be")) {
                                            // Si es un enlace corto de YouTube, extraemos el ID
                                            videoId = direccion.split("youtu.be/")[1];
                                        } else if (direccion.startsWith("https://www.youtube.com")) {
                                            // Si es un enlace largo de YouTube, extraemos el ID
                                            videoId = direccion.split("v=")[1].split("&")[0];
                                        }
                                    %>

                                    <%-- Mostrar el iframe de YouTube con el ID extraído --%>
                                    <% if (direccion.startsWith("https://youtu.be") || direccion.startsWith("https://www.youtube.com")) {%>
                                    <!-- Embed YouTube Video -->
                                    <iframe width="220" height="135" src="https://www.youtube.com/embed/<%= videoId%>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                        <% } else if (isMp3) {%>
                                    <!-- Reproductor de audio para archivos MP3 con controles completos -->
                                    <audio id="audio-<%= tema.getIdTema()%>" controls>
                                        <source src="<%= direccion%>" type="audio/mpeg">
                                        Tu navegador no soporta el elemento de audio.
                                    </audio>
                                    <% }else{
                                            // Asegurarse de que la URL tiene el protocolo "https://"
                                                if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
                                                    direccion = "https://" + direccion; // Agregamos el protocolo HTTPS si no existe
                                                }

                                                // Obtener la URL real redirigida
                                                String realUrl = null;
                                                try {
                                                    // Crear la conexión HTTP con la URL de Bit.ly
                                                    HttpURLConnection connection = (HttpURLConnection) new URL(direccion).openConnection();
                                                     // Solo necesitamos la cabecera, no el cuerpo
                                                    connection.setRequestMethod("GET"); // Usamos GET en lugar de HEAD para obtener la URL de redirección
                                                    connection.setInstanceFollowRedirects(false); // No seguir redirecciones automáticamente, así podemos ver la cabecera Location
                                                    // Realizamos la conexión
                                                    connection.connect();

                                                    // Obtener el código de respuesta
                                                    int responseCode = connection.getResponseCode();
                                                    String location = connection.getHeaderField("Location");

                                                    // Depuración: Mostrar el código de respuesta y la URL redirigida
                                                    out.println("Código de respuesta: " + responseCode + "<br>");
                                                    out.println("Ubicación redirigida: " + location + "<br>");

                                                    // Si la redirección es válida, obtenemos la URL
                                                    if (location != null) {
                                                        realUrl = location;
                                                    } else {
                                                        out.println("No se encontró una URL de redirección.");
                                                    }

                                                } catch (Exception e) {
                                                    out.println("Error al conectar con Bit.ly: " + e.getMessage());
                                                    e.printStackTrace();
                                                }

                                                // Si realUrl es nula, significa que no hubo redirección, así que lo gestionamos
                                                if (realUrl != null) {
                                                    // Asegúrate de que la URL real tiene la estructura correcta
                                                    String encodedUrl = java.net.URLEncoder.encode(realUrl, "UTF-8");
                                    %>

                                    <!-- Reproducir la pista de SoundCloud en un iframe -->
                                    <iframe width="100%" height="166" scrolling="no" frameborder="no"
                                            src="https://w.soundcloud.com/player/?url=<%= encodedUrl%>&auto_play=false&hide_related=false&show_comments=true&show_user=true&show_reposts=false">
                                    </iframe>

                                    <%
                                    } else {
                                    %>
                                    <!-- Mensaje en caso de que no se pueda obtener la URL -->
                                    <p>No se pudo redirigir el enlace de Bit.ly.</p>
                                    <%
                                        }
                                    %>
                                    <%}%>

                                    <%-- Botón para Descargar MP3 (Simula "Guardar audio como...") --%>
                                    <% if (isMp3) {%>
                                    <!-- Este enlace tiene el atributo "download" para forzar la descarga del archivo -->
                                    <a href="<%= direccion%>" download>
                                        <button>Descargar MP3</button>
                                    </a>
                                    <% } %>
                                </li>
                                <% } %>
                            </ul>
                            <% } %>
                        </ul>

                        

                                    <% } %>
                                </ul>
                                <h4>=============================================</h4>
                        <%}%>
                        
                </div>
                    <%}
                }

                    %>
            </div>
             <%%>
        </div>
    </body>
</html>
