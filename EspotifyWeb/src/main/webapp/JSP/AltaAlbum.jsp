<%-- 
    Document   : AltaAlbum
    Created on : 21 oct. 2024, 7:23:39 p. m.
    Author     : Franco
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
        
    <body>
        <h1>Bienvenido a Alta de Album</h1>
        
        
  
        <form action=${pageContext.request.contextPath}"\SvAltaAlbum.java" method="POST">
            <p><lable>Nickname Artista<br></lable><input type="text" name="nick"</p>
            <p><lable>Nombre del Album<br></lable><input type="text" name="nomAlb"</p>
            <p><label for="anioCreacion">Año de Creación:</label>
            <select id="anioCreacion" name="anioCreacion" required>
                <% for (int anioCreacion = 2024; anioCreacion > 1950; anioCreacion--) {%>
                    <option value="<%= anioCreacion %>"><%= anioCreacion %></option>
                <% }%>
            </select><br>
            
            
            <%List<String> generos = (List<String>) request.getAttribute("generos");%>
            <label for="generos">Géneros:</label>
            <select id="generos" name="generos" required>
                <option value="">Seleccione un género</option>
               
                    <%--Llenar el <select> con los géneros--%>
                     <%if (generos != null) {%>
                         <%for (String genero : generos) {%>
                
                            <option value="<%= genero %>"><%= genero %></option>
                
                         <%}%>
                     <%}%>
                
            </select><br>
            <p><lable>Contraseña<br></lable><input type="password" name="pass"</p>
            <p><lable>Confirmar contraseña<br></lable><input type="password" name="pass2"</p>

            <!-- Checkbox para mostrar u ocultar los campos adicionales -->
            <p>
                <input type="checkbox" id="showFields" onclick="toggleFields()">
                <label for="showFields">Perfil de artista</label>
            </p>

            <!-- Campos adicionales (textField y textArea) ocultos inicialmente -->
            <div id="extraFields" style="display: none;">
                <p><label>Pagina Web<br></label><input type="text" name="web"></p>
                <p><label>Biografía:</label></p>
                <p><textarea name="bio" rows="4" cols="50"></textarea></p> <!-- TextArea aparece debajo -->
            </div>

            <button type="submit">Crear perfil</button>
        </form>
    </body>
</html>
