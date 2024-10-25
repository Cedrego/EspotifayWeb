<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Espotify</title>
        <script>
            function updateUsers() {
                const checkbox = document.getElementById('tipo');
                const clienteComboBox = document.getElementById('cliente');
                const artistaComboBox = document.getElementById('artista');
                const clienteLabel = document.getElementById('label1');
                const artistaLabel = document.getElementById('label2');

                // Mostrar/ocultar combobox y etiquetas según el estado de la checkbox
                if (checkbox.checked) {
                    clienteComboBox.style.display = 'none';
                    clienteLabel.style.display = 'none';
                    artistaComboBox.style.display = 'inline';
                    artistaLabel.style.display = 'inline';
                } else {
                    clienteComboBox.style.display = 'inline';
                    clienteLabel.style.display = 'inline';
                    artistaComboBox.style.display = 'none';
                    artistaLabel.style.display = 'none';
                }
            }

            function redirectToCliente() {
                // Redirigir a la página JSP/Cliente
                window.location.href = 'JSP/Cliente.jsp';
            }
        </script>
    </head>
    <body onload="updateUsers()"> <!-- Ejecutar updateUsers al cargar la página -->
        <h1>Seguir usuario</h1>

        <form id="seguimientoForm" action="${pageContext.request.contextPath}/SvSeguir" method="POST">
            <button type="button" onclick="redirectToCliente()">Volver</button>
            <br>
            <input type="checkbox" id="tipo" name="tipo" onchange="updateUsers()"
                <%= request.getParameter("tipo") != null ? "checked" : "" %>>
            <label for="tipo">Cambiar tipo de usuario</label>
            <br><br>
            
            <!-- ComboBox para clientes -->
            <label id="label1" for="cliente">Selecciona un cliente:</label>
            <select id="cliente" name="cliente"> <!-- Cambié name="comboCliente" a name="cliente" -->
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

            <!-- ComboBox para artistas, inicialmente oculto -->
            <label id="label2" for="artista" style="display: none;">Selecciona un artista:</label>
            <select id="artista" name="artista" style="display: none;">
                <%
                    List<String> listArtistas = (List<String>) request.getAttribute("listArtistas");
                    if (listArtistas != null && !listArtistas.isEmpty()) {
                        for (String artista : listArtistas) {
                        %>
                        <option value="<%= artista%>"><%= artista%></option>
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
