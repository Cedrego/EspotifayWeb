<%-- 
    Document   : PublicarLista
    Created on : 31 oct. 2024, 13:25:43
    Author     : tecnologo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script>
            function CargarListSesion() {
            const ListaPart = document.getElementById("listaPrivada");
            // Limpiar la tercera ComboBox
            ListaPart.innerHTML = "";
            // Realiza una solicitud AJAX al servlet para obtener lo pedido
            const xhr = new XMLHttpRequest();
            const contextPath = "${pageContext.request.contextPath}";
            xhr.open('GET', contextPath + '/SvPublicarLista?_=' + new Date().getTime(), true);

            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Verifica si la respuesta tiene opciones válidas
                    if (xhr.responseText.trim() !== "") {
                        ListaPart.innerHTML = xhr.responseText;
                    } else {
                        ListaPart.innerHTML = '<option value="">No se encontraron resultados</option>';
                    }
                } else {
                    ListaPart.innerHTML = '<option value="">No se encontraron listas</option>';
                }
            };
            xhr.send();
    }
    window.onload = function(){
            CargarListSesion();
        };
        </script>
    </head>
    <body>
        <h1>Publicar Lista</h1>
        <form action="${pageContext.request.contextPath}/SvPublicarLista" method="POST">
            <label for="listaPrivada">Selecciona una lista:</label>
            <select id="listaPrivada" name="listaPrivada">
              <option value="">Seleciona su Lista</option>
          </select>
        <button type="submit">Publicar</button>
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
