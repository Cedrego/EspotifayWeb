<%@ page import="java.util.List"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
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
       <select id="tipoSeleccion" onchange="actualizarSelect()">
           <option value="album">Álbum</option>
           <option value="listasPorDefecto">Listas por Defecto</option>
           <option value="listasPublicas">Listas Públicas</option>
       </select>

       <label for="opcionesSeleccion">Temas:</label>
       <select id="opcionesSeleccion">
           <!-- Las opciones se cargarán aquí -->
       </select>
    </body>
</html>
