<%-- 
    Document   : Registro
    Created on : 21 oct. 2024, 6:50:44 p. m.
    Author     : camin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
        function toggleFields() {
            const checkbox = document.getElementById('showFields');
            const extraFields = document.getElementById('extraFields');
            
            // Mostrar u ocultar los campos en función del estado del checkbox
            if (checkbox.checked) {
                    extraFields.style.display = 'block'; // Mostrar los campos
                } else {
                    extraFields.style.display = 'none';  // Ocultar los campos
                }
            }
        </script>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro</title>
    </head>
    <body>
        <h1>Crear Cuenta</h1>
        <form action="${pageContext.request.contextPath}/SvRegistro" method="POST">
            <p><label>Nickname<br></label><input type="text" name="nick"></p>
            <p><label>Nombre<br></label><input type="text" name="nom"></p>
            <p><label>Apellido<br></label><input type="text" name="ape"></p>
            <p><label>Correo<br></label><input type="text" name="mail"></p>
            <p><label>Contraseña<br></label><input type="password" name="pass"></p>
            <p><label>Confirmar contraseña<br></label><input type="password" name="pass2"></p>
            <p><label>Fecha Nacimiento<br></label><input type="date" name="fech"></p>
            
            <p>
                <input type="checkbox" id="artista" name="esArtista" onclick="toggleFields()">
                <label for="artista">Perfil de artista</label>
            </p>
            
            <div id="extraFields" style="display: none;">
                <p><label>Página Web<br></label><input type="text" name="web"></p>
                <p><label>Biografía:</label></p>
                <p><textarea name="bio" rows="4" cols="50"></textarea></p>
            </div>
            <button type="submit">Crear perfil</button>
        </form>
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

        
</body>
</html>
