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
        <style>
            /* Fondo de la página */
            body {
                background-color: #000; /* Fondo oscuro */
                color: #FFF; /* Texto en blanco */
                font-family: 'Poppins', sans-serif;
            }

            /* Estilo del formulario */
            form {
                display: flex;
                flex-direction: column;
                max-width: 400px;
                margin: 0 auto;
            }

            h1 {
                text-align: center;
                color: #FFF;
            }

            /* Etiquetas y texto */
            p, label, input[type="text"] {
                color: #FFF;
            }

            /* Estilos para los campos de texto */
            input[type="text"] {
                background-color: #1a1a1a;
                color: #FFF;
                border: 2px solid #1db954; /* Borde verde */
                padding: 8px;
                border-radius: 5px;
                transition: box-shadow 0.3s ease; /* Transición de brillo */
            }

            input[type="text"]:focus {
                box-shadow: 0 0 10px #1db954, 0 0 20px #1db954;
                outline: none;
            }

            /* Estilos para el campo de archivo */
            input[type="file"] {
                color: #FFF;
                border: none;
                cursor: pointer;
                background-color: #1a1a1a;
                padding: 8px;
                border-radius: 5px;
                transition: box-shadow 0.3s ease;
            }

            input[type="file"]:hover {
                box-shadow: 0 0 10px #1db954, 0 0 20px #1db954;
            }

            /* Estilos para el botón */
            button[type="submit"] {
                background-color: #1db954; /* Verde */
                color: black;
                font-weight: bold;
                padding: 10px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                transition: box-shadow 0.3s ease;
                margin-top: 10px;
            }

            button[type="submit"]:hover {
                box-shadow: 0 0 10px #1db954, 0 0 20px #1db954, 0 0 30px #1db954;
            }

            /* Mensaje de error */
            .error-message {
                color: red;
                font-weight: bold;
                text-align: center;
                margin-top: 10px;
            }
        </style>
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
