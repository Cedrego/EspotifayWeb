<%@ page import="java.util.List"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Tema</title>
            <script>
            function actualizarSelect() {
                const tipoSeleccion = document.getElementById("tipoSeleccion").value;

                fetch(`SvActualizarSelectAddTema?tipoSeleccion=${tipoSeleccion}`)
                    .then(response => response.json())
                    .then(data => {
                        const newSelect = document.getElementById("opcionesSeleccion");
                        newSelect.innerHTML = ""; // Limpiar opciones anteriores

                        data.forEach(opcion => {
                            const newOption = document.createElement("option");
                            newOption.value = opcion;
                            newOption.textContent = opcion;
                            newSelect.appendChild(newOption);
                        });
                    })
                    .catch(error => console.error('Error:', error));
            }
            function actualizarFiltrosSecundarios() {
            const tipoSeleccionado = document.getElementById("tipoDelObjeto").value;
            const filtroSecundarioSeleccionado = document.getElementById("opcionesSeleccion");
      
            // Limpiar la tercera ComboBox
            filtroSecundarioSeleccionado.innerHTML = "";
            // Realiza una solicitud AJAX al servlet para obtener lo pedido
            const xhr = new XMLHttpRequest();
            const contextPath = "${pageContext.request.contextPath}";
            xhr.open('GET', contextPath + '/SvActualizarSelectAddTema?tipoDelObjeto=' + tipoSeleccionado  + '&_=' + new Date().getTime(), true);

            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Verifica si la respuesta tiene opciones válidas
                    if (xhr.responseText.trim() !== "") {
                        filtroSecundarioSeleccionado.innerHTML = xhr.responseText;
                    } else {
                        filtroSecundarioSeleccionado.innerHTML = '<option value="">No se encontraron resultados</option>';
                    }
                } else {
                    filtroSecundarioSeleccionado.innerHTML = '<option value="">Error al cargar los datos</option>';
                }
            };
            xhr.send();
            
        }
        </script>
    </head>
    <body>
            <h1>Agregar Tema a Lista</h1>
            <label for="lista">Selecciona una lista:</label>
            <select name="lista">
                <%
                    List<String> nombresListas = (List<String>) request.getAttribute("nombresListas");
                    if (nombresListas != null && !nombresListas.isEmpty()) {
                        for (String nombre : nombresListas) {
                %>
                            <option value="<%= nombre %>"><%= nombre %></option>
                <%
                        }
                    } else {
                %>
                        <option value="">No hay listas disponibles1</option>
                <%
                    }
                %></select>
      <label for="tipoSeleccion">Filtro:</label>
        <select id="tipoDelObjeto" name="tipoDelObjeto" onclick="actualizarFiltrosSecundarios();">
            <option value="">Seleccione un filtro</option>
            <option value="Album">Album</option>
            <option value="ListaPorDef">Lista Por Defecto</option>
            <option value="ListaPart">Listas públicas de clientes</option>
        </select>

        <label for="opcionesSeleccion">Filtro 2:</label>
        <select id="opcionesSeleccion" name="filtroPrincipal" required>
            <option value="">Seleccione un filtro</option>
        </select>      
    </body>
</html>
