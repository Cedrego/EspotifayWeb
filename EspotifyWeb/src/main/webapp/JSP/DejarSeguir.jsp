<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Espotify</title>
        <script>
            function redirectToCliente() {
                // Redirigir a la página JSP/Cliente
                window.location.href = 'JSP/Cliente.jsp';
            }
        </script>
    </head>
    <body onload="updateUsers()"> <!-- Ejecutar updateUsers al cargar la página -->
        <h1>Dejar de Seguir usuario</h1>

        <form id="seguimientoForm" action="${pageContext.request.contextPath}/SvDejarSeguir" method="POST">
            <button type="button" onclick="redirectToCliente()">Volver</button>
            <br>
            
            <label id="label1" for="perfil">Selecciona un perfil:</label>
            <select id="perfil" name="perfil"> <!-- Cambié name="comboCliente" a name="cliente" -->
                <!-- Insertar los clientes dinámicamente desde el servidor -->
                <%
                    List<String> listClientes = (List<String>) request.getAttribute("listClientes");
                    if (listClientes != null && !listClientes.isEmpty()) {
                        for (String cliente : listClientes) {
                        %>
                        <option value="<%= cliente%>"><%= cliente%></option>
                        <%
                        }
                    }
                %>
            </select>
            <br><br>
            <button type="submit" name="accion" value="seguir">Seguir</button>
        </form>
    </body>
</html>
