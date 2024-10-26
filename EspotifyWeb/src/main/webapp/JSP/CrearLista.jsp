<%-- 
    Document   : CrearLista
    Created on : 22 oct. 2024, 8:53:45 p. m.
    Author     : User
--%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CrearLista</title>
    </head>
    <body>
        <h1>CrearLista</h1>
        <form action="${pageContext.request.contextPath}/SvCrearLista" method="POST" enctype="multipart/form-data">
        <p><lable>Nombre de la lista:</lable><input type="text" name="NomLista"</p>
        <p>Selecciona una imagen: <input type="file" name="imagen" accept="image/*" /></p>
        <br>
        <button type="submit">Crear</button>
        <%-- Mostrar mensaje de error si existe --%>
        <%
            String errorMessage = (String) request.getSession().getAttribute("error");
            if (errorMessage != null) {
        %>
        <p><label style="color: red;"> <%= errorMessage%> </label></p>
        <%
                request.getSession().removeAttribute("error"); // Limpiar el mensaje para que no persista
            }
        %>
    </form>
    </body>
</html>
