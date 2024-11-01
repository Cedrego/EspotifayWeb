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
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<%@ page import="java.util.List" %>
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

        h1, label {
            color: #FFF;
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
        <div class="container">
            <%
                String tipo = (String) request.getSession(false).getAttribute("tipo");
                Object usuario = request.getSession(false).getAttribute("usuario");
                
                
                if (tipo.equals("cliente")) {
                    DataClienteAlt cliente = (DataClienteAlt) usuario;
                    %>
            <div class="perfil">
                <h3><%= cliente.getNickname()%></h3>
                <img src="<%= cliente.getNickname()%>">
                <p><strong>Nombre:</strong> <%= cliente.getNombre()%></p>
                <p><strong>Apellido:</strong> <%= cliente.getApellido()%></p>
                <p><strong>Correo</strong> <%= cliente.getCorreo()%></p>
                <p><strong>Fecha de Nacimiento:</strong> <%= cliente.getFecha().getDia() + "/" + cliente.getFecha().getMes() + "/" + cliente.getFecha().getAnio() %></p>
                <%if(!cliente.getDataCliSeguidor().isEmpty()){ %>
                    <ul>           
                    <h4>Seguidores</h4>
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
                if(isSuS){
                
                    if(!cliente.getDataCliSeguido().isEmpty()){
                %>
                
                <ul>
                <li>Usuarios Seguidos</li>
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
                <%}%>
                <li>Listas de Reproducción</li>
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

                                    <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').play()">Reproducir</button>
                                    <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').pause()">Pausar</button>
                                    <audio id="audio-<%= tema.getIdTema()%>" controls style="display: none;">
                                        <source src="<%= tema.getDireccion()%>" type="audio/mpeg">
                                        Tu navegador no soporta el elemento de audio.
                                    </audio>
                                </li>
                                <% } %>
                            </ul>
                            <%}%>
                        </div>
                        <% } %>
                    </div>
                </div>
                <%if(!cliente.getDataPartFav().isEmpty() && !cliente.getDataPorDefFav().isEmpty()){%>
                <h4>Playlists Favoritas</h4>
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

                                    <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').play()">Reproducir</button>
                                    <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').pause()">Pausar</button>
                                    <audio id="audio-<%= tema.getIdTema()%>" controls style="display: none;">
                                        <source src="<%= tema.getDireccion()%>" type="audio/mpeg">
                                        Tu navegador no soporta el elemento de audio.
                                    </audio>
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

                                    <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').play()">Reproducir</button>
                                    <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').pause()">Pausar</button>
                                    <audio id="audio-<%= tema.getIdTema()%>" controls style="display: none;">
                                        <source src="<%= tema.getDireccion()%>" type="audio/mpeg">
                                        Tu navegador no soporta el elemento de audio.
                                    </audio>
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
                        <h4>Álbumes Favoritos</h4>
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
                                                        <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').play()">Reproducir</button>
                                                        <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').pause()">Pausar</button>
                                                        <audio id="audio-<%= tema.getIdTema()%>" controls style="display: none;">
                                                            <source src="<%= tema.getDireccion()%>" type="audio/mpeg">
                                                            Tu navegador no soporta el elemento de audio.
                                                        </audio>
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
                <h4>Temas Favoritos</h4>
                <!-- Mostrar Temas en dos columnas -->
                <div class="temas-lista">
                    <div style="display: flex; flex-wrap: wrap; margin-bottom: 10px;">
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

                        <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').play()">Reproducir</button>
                        <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').pause()">Pausar</button>
                        <audio id="audio-<%= tema.getIdTema()%>" controls style="display: none;">
                            <source src="<%= tema.getDireccion()%>" type="audio/mpeg">
                            Tu navegador no soporta el elemento de audio.
                        </audio>
                    </div>
                    <% } %>
                </div>
                <%}%>
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
                
                </script>
                <%}%>
                </div>
            <%} else if (tipo.equals("artista")) { DataArtistaAlt artista = (DataArtistaAlt) usuario;%>
            <div class="perfil">
                <h3><%= artista.getNickname()%></h3>
                <!-- IMAGEN -->
                <p><strong>Nombre:</strong> <%= artista.getNombre()%></p>
                <p><strong>Apellido:</strong> <%= artista.getApellido()%></p>
                <p><strong>Correo:</strong> <%= artista.getCorreo()%></p>
                <p><strong>Fecha de Nacimiento:</strong> <%= artista.getFecha().getDia() + "/" + artista.getFecha().getMes() + "/" + artista.getFecha().getAnio()%></p>

                <% if (!artista.getDataalbumes().isEmpty()) { %>
                <h4>Álbumes</h4>
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

                                    <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').play()">Reproducir</button>
                                    <button onclick="document.getElementById('audio-<%= tema.getIdTema()%>').pause()">Pausar</button>
                                    <audio id="audio-<%= tema.getIdTema()%>" controls style="display: none;">
                                        <source src="<%= tema.getDireccion()%>" type="audio/mpeg">
                                        Tu navegador no soporta el elemento de audio.
                                    </audio>
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
            <h2>Artistas Disponibles</h2>
            <div class="container">
                <%
                    List<DataArtistaAlt> dataArtistas = (List<DataArtistaAlt>) request.getSession(false).getAttribute("dataArtistas");
                    
                    if (dataArtistas != null && !dataArtistas.isEmpty()) {
                        for (DataArtistaAlt artista : dataArtistas) {
                            String artistaId = "artista-" + artista.getNickname().replace(" ", "_");
                %>
                <div class="perfil">
                    <h3>
                        <a href="javascript:void(0);" onclick="toggleArtistInfo('<%= artistaId%>')">
                            <%= artista.getNickname()%>
                        </a>
                    </h3>
                    <img src="ruta_por_defecto.jpg" alt="Imagen de <%= artista.getNickname()%>">
                    <p><strong>Página Web:</strong> <a href="<%= artista.getSitioWeb()%>" target="_blank"><%= artista.getSitioWeb()%></a></p>

                    <div id="<%= artistaId%>" class="artist-info" style="display: none;">
                        <h4>Información Adicional</h4>
                        <p><strong>Nombre:</strong> <%= artista.getNombre()%></p>
                        <p><strong>Apellido:</strong> <%= artista.getApellido()%></p>
                        <p><strong>Fecha de Nacimiento:</strong> <%= artista.getFecha().getDia() + "/" + artista.getFecha().getMes() + "/" + artista.getFecha().getAnio()%></p>
                        <p><strong>Biografía:</strong> <%= artista.getBiografia()%></p>
                        <p><strong>Cantidad de Seguidores:</strong> <%= artista.getDataseguidoPorA().size()%></p>

                        <h4>Álbumes</h4>
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
                                        </li>
                                        <% } %>
                                    </ul>
                                </div>
                            </li>
                            <% } %>
                        </ul>
                    </div>
                </div>
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

            <h2>Clientes Disponibles</h2>
            <div class="container">
                <%
                    List<DataClienteMin> dataClientes = (List<DataClienteMin>) request.getSession(false).getAttribute("DataClientes");
                    
                    if (!(dataClientes == null)) {
                        for (DataClienteMin client : dataClientes) {
                %>
                <div class="perfil">
                    <h3><%= client.getNickname()%></h3>
                    <img src="<%= client.getNickname()%>">
                    <h4>Listas de Reproducción</h4>
                    <ul>
                        <%
                            for (DataParticular lista : client.getDataPart()) {
                        %>
                        <li><%= lista.getNombre()%></li>
                            <% } %>
                    </ul>
                </div>
                        <%}
                    }
                        
                        %>
            </div>
             <%}%>
        </div>
    </body>
</html>
