<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Logica.Factory"%>
<%@page import="Logica.ICtrl"%>
<%@page import="Capa_Presentacion.DataAlbum"%>
<%@page import="Capa_Presentacion.DataTema"%>
<%@page import="Capa_Presentacion.DataParticular"%>
<%@page import="Capa_Presentacion.DataPorDefecto"%>

<%
    String query = request.getParameter("query");
    Factory factory = Factory.getInstance();
    ICtrl ctrl = factory.getICtrl();

    List<String> albums = ctrl.buscadorAlbum(query);
    List<DataTema> temas = ctrl.buscadorTema(query);
    List<String> part = ctrl.buscadorPart(query);
    List<String> pd = ctrl.buscadorPD(query);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Resultados de Búsqueda</title>
    <style>
            /* Fondo y estilos de texto */
            body {
                background-color: #000;
                color: #FFF;
                font-family: 'Poppins', sans-serif;
            }

            h1, label, p {
                color: #FFF;
            }

            /* Estilo de select */
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

            /* Estilo de botones */
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
</head>
<body>
    <h1>Resultados de Búsqueda para "<%= query %>"</h1>

    <h3>Álbumes</h3>
    <% for (String album : albums) { %>
        <p><%= album %></p>
    <% } %>

    <h3>Temas</h3>
    <% for (DataTema tema : temas) { %>
        <p><%=tema.getNombre()%></p>
    <% } %>

    <h3>Listas de Reproduccion</h3>
    <% for (String parti : part) { %>
        <p><%= parti %></p>
    <% } %>
    <% for (String pordef : pd) { %>
        <p><%= pordef %></p>
    <% } %>
</body>
</html>
