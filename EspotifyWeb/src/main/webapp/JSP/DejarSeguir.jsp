<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Espotify</title>
    <script>
        function redirectToCliente() {
            window.location.href = '${pageContext.request.contextPath}/JSP/Cliente.jsp';
        }

        function actualizarLista() {
            const clienteeleccionado = document.getElementById("cliente");
            
            clienteeleccionado.innerHTML = "";

            const xhr = new XMLHttpRequest();
            const contextPath = "${pageContext.request.contextPath}";
            xhr.open('GET', contextPath + '/SvDejarSeguir?_=' + new Date().getTime(), true);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    if (xhr.responseText.trim() !== "") {
                        clienteeleccionado.innerHTML = xhr.responseText;
                    } else {
                        clienteeleccionado.innerHTML = '<option value="">No se encontraron resultados</option>';
                    }
                } else {
                    clienteeleccionado.innerHTML = '<option value="">Error al cargar los datos</option>';
                }
            };
            xhr.onerror = function () {
                clienteeleccionado.innerHTML = '<option value="">Error de red</option>';
            };

            xhr.send();
        }

        window.onload = actualizarLista;
    </script>
</head>
<body onload="actualizarLista()">
    <h1>Dejar de Seguir usuario</h1>

    <form id="seguimientoForm" action="${pageContext.request.contextPath}/SvDejarSeguir" method="POST">
        <button type="button" onclick="redirectToCliente()">Volver</button>
        <br>
        <p><button type="button" onclick="actualizarLista()">Recargar seguidos</button></p>
        
        <label for="cliente">Perfiles que sigues:</label>
        <select id="cliente" name="cliente"required>
            <option value="">Cargando cliente...</option>
        </select>
        <br><br>

        <button type="submit" name="accion" value="dejarSeguir">Dejar de seguir</button>
    </form>
</body>
</html>
