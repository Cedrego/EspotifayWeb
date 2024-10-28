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
            body {
                background-color: #f0f0f0; /* Fondo gris */
                font-family: Arial, sans-serif;
            }
            .container {
                padding: 20px;
            }
            .perfil {
                background-color: #ffffff; /* Fondo blanco */
                border: 1px solid #ccc;
                border-radius: 5px;
                padding: 20px;
                margin: 10px 0;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            }
            .perfil img {
                max-width: 100px;
                height: auto;
                border-radius: 5px;
            }
            .listas-reproduccion ul, .temas ul {
                list-style-type: none;
                padding: 0;
            }
            .listas-reproduccion li, .temas li {
                margin: 5px 0;
            }
            .album-panel {
                display: none; /* Oculto por defecto */
                background-color: #f9f9f9;
                padding: 10px;
                margin-top: 10px;
                border: 1px solid #ccc;
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
                String tipo = (String) request.getAttribute("tipo");
            if (null == request.getAttribute("usuario")) { %>
                            <h3>El Usuario No Cargó Correctamente</h3>
            <%}
                Object usuario = request.getAttribute("usuario");

                if ("cliente".equals(tipo)) {
                    DataClienteAlt cliente = (DataClienteAlt) usuario;
            %>
            <div class="perfil">
                <h3><%= cliente.getNickname()%></h3>
                <img src="<%= cliente.getNickname()%>">
                <p><strong>Nombre:</strong> <%= cliente.getNombre()%></p>
                <p><strong>Apellido:</strong> <%= cliente.getApellido()%></p>
                <p><strong>Correo:</strong> <%= cliente.getCorreo()%></p>
                <p><strong>Fecha de Nacimiento:</strong> <%= cliente.getFecha().getDia() + "/" + cliente.getFecha().getMes() + "/" + cliente.getFecha().getAnio() %></p>

                <h4>Seguidores</h4>
                <ul>
                    <%
                        for (String seguidor : cliente.getDataCliSeguidor()) {
                    %>
                    <li><%= seguidor%></li>
                        <% } %>
                </ul>
                <%  boolean isSuS = false;
                    for(DataSuscripcion sus : cliente.getDataSuscripcion()){
                        if(sus.getEstado().equals("Vigente")){
                            isSuS = true;
                        }
                    }
                    if(isSuS){
                %>
                <h4>Usuarios Seguidos</h4>
                <ul>
                    <%
                        for (String seguido : cliente.getDataCliSeguido()) {
                    %>
                    <li><%= "cliente - " + seguido%></li>
                        <% } %>
                </ul>
                <ul>
                    <%
                        for (String aseguido : cliente.getDataArtSeguido()) {
                    %>
                    <li><%= "artista - " + aseguido%></li>
                        <% } %>
                </ul>
                <%}%>
                <h4>Listas de Reproducción</h4>
                <div class="listas-reproduccion">
                    <ul>
                        <%
                            for (DataParticular lista : cliente.getDataPart()) {
                        %>
                        <li><%= lista.getNombre()%></li>
                            <% } %>
                    </ul>
                </div>
            </div>

            <%
            } else if ("artista".equals(tipo)) {
                DataArtistaAlt artista = (DataArtistaAlt) usuario;
            %>
            <div class="perfil">
                <h3><%= artista.getNickname()%></h3>
                <img src="<%= artista.getNickname()%>">
                <p><strong>Nombre:</strong> <%= artista.getNombre()%></p>
                <p><strong>Apellido:</strong> <%= artista.getApellido()%></p>
                <p><strong>Correo:</strong> <%= artista.getCorreo()%></p>
                <p><strong>Fecha de Nacimiento:</strong> <%= artista.getFecha().getDia() + "/" + artista.getFecha().getMes() + "/" + artista.getFecha().getAnio() %></p>

                <h4>Álbumes</h4>
                <ul>
                    <%
                        for (DataAlbum album : artista.getDataalbumes()) {
                            String albumId = "album-" + album.getNombre().replace(" ", "_");
                    %>
                    <li>
                        <button onclick="toggleAlbum('<%= albumId %>')"><%= album.getNombre() %></button>
                        <div id="<%= albumId %>" class="album-panel">
                            <h5>Temas:</h5>
                            <ul class="temas">
                                <%
                                    for (DataTema tema : album.getDataTemas()) {
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
                        </div>
                    </li>
                    <% } %>
                </ul>
            </div>

            <%
            } else { // Es Invitado  
            %>
            <h2>Artistas Disponibles</h2>
            <div class="container">
                
                <%
                    List<DataArtistaAlt> dataArtistas = (List<DataArtistaAlt>) request.getAttribute("dataArtistas");
                    if (dataArtistas != null && !dataArtistas.isEmpty()) {
                        for (DataArtistaAlt artista : dataArtistas) {
                %>
                <div class="perfil">
                    <h3><%= artista.getNickname()%></h3>
                    <img src="<%= artista.getNickname()%>">
                    <p><strong>Biografía:</strong> <%= artista.getBiografia()%></p>
                </div>
                <%
                    }
                } else {
                %>
                <p>No hay artistas disponibles.</p>
                <%
                    }
                %>
                    <h4>Álbumes</h4>
                    <ul>
                        <%
                            for (DataAlbum album : artista.getDataalbumes()) {
                                String albumId = album.getNombre().replace(" ", "_");
                        %>
                        <% } %>
                    </ul>
                </div>
            </div>

            <h2>Clientes Disponibles</h2>
            <div class="container">
                <%
                    List<DataClienteAlt> dataClientes = (List<DataClienteAlt>) request.getAttribute("DataClientes");
                    if (dataClientes != null && !dataClientes.isEmpty()) {
                        for (DataClienteAlt cliente : dataClientes) {
                %>
                <div class="perfil">
                    <h3><%= cliente.getNickname()%></h3>
                    <img src="<%= cliente.getNickname()%>">
                    <h4>Listas de Reproducción</h4>
                    <ul>
                        <%
                            for (DataParticular lista : cliente.getDataPart()) {
                        %>
                        <li><%= lista.getNombre()%></li>
                            <% } %>
                    </ul>
                </div>
                <%
                    }
                %>
            </div>
            <%
                }
}
            %>
        </div>

    </body>
</html>
