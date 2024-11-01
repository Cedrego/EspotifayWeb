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
