<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Espotify</title>
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
    <c:if test="${not empty errorMessage}">
        <p style="color: white;">${errorMessage}</p>
    </c:if>
    <form id="seguimientoForm" action="${pageContext.request.contextPath}/SvDejarSeguir" method="POST">
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
