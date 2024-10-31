<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Espotify</title>
        <script>
            function updateUsers() {
                const tipoUser = document.getElementById("tipoUsuario").value;
                const usuarioSelec = document.getElementById("usuario");

                // Limpiar la tercera ComboBox
                usuarioSelec.innerHTML = "";
                // Realiza una solicitud AJAX al servlet para obtener lo pedido
                const xhr = new XMLHttpRequest();
                const contextPath = "${pageContext.request.contextPath}";
                xhr.open('GET', contextPath + '/SvSeguir?tipoUsuario=' + tipoUser + '&_=' + new Date().getTime(), true);



                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // Verifica si la respuesta tiene opciones válidas
                        if (xhr.responseText.trim() !== "") {
                            usuarioSelec.innerHTML = xhr.responseText;
                        } else {
                            usuarioSelec.innerHTML = '<option value="">No se encontraron resultados</option>';
                        }
                    } else {
                        usuarioSelec.innerHTML = '<option value="">Error al cargar los datos</option>';
                    }
                };
                xhr.send();
            }

            function redirectToCliente() {
                // Redirigir a la página JSP/Cliente
                window.location.href = 'JSP/Cliente.jsp';
            }
        </script>
    </head>
    <body> <!-- Ejecutar updateUsers al cargar la página -->
        <h1>Seguir usuario</h1>

        <form id="seguimientoForm" action="${pageContext.request.contextPath}/SvSeguir" method="POST">
            <button type="button" onclick="redirectToCliente()">Volver</button>
            <br>
            <p><label for="tipoUsuario">Que tipo de usuario desea seguir?:</label>
            <select id="tipoUsuario" name="tipoUsuario" onchange="updateUsers()"  required>
                <option value="">Seleccione un tipo</option>
                <option value="Artista">Aritsta</option>
                <option value="Cliente">Cliente</option>  
            </select><br>   
            
            <p><label for="usuario">Seleccione un usuario</label>
                <select id="usuario" name="usuario" required>
                    <option value="">Seleccione un usuario</option>
                </select><br>

            <button type="submit" name="accion" value="seguir" >Seguir Usuario</button>
        </form>
            
    </body>
</html>
