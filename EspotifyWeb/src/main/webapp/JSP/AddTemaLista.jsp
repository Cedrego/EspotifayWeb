<%@ page import="java.util.List"%>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Tema</title>
        <style>
        /* Estilo de fondo y texto */
        body {
            background-color: #000;
            color: #FFF;
            font-family: 'Poppins', sans-serif;
        }

        h1, label {
            color: #FFF;
        }

        /* Estilo para select */
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

        /* Estilo para botones */
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
        function actualizarListTemas() {
            const listaAgregar = document.getElementById("listaAgregar").value;
            const tipoSeleccionado = document.getElementById("tipoDelObjeto").value;
            const filtroSecundarioSeleccionado = document.getElementById("opcionesSeleccion").value;
            const TemasPos = document.getElementById("TemasPos");
            // Limpiar la tercera ComboBox
            TemasPos.innerHTML = "";
            // Realiza una solicitud AJAX al servlet para obtener lo pedido
            const xhr = new XMLHttpRequest();
            const contextPath = "${pageContext.request.contextPath}";
            xhr.open('GET', contextPath + '/SvActualizarSelectAddTema?tipoDelObjeto=' + tipoSeleccionado+ '&filtroPrincipal='+  filtroSecundarioSeleccionado +'&listaAgregar='+ listaAgregar +'&_=' + new Date().getTime(), true);

            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Verifica si la respuesta tiene opciones válidas
                    if (xhr.responseText.trim() !== "") {
                        TemasPos.innerHTML = xhr.responseText;
                    } else {
                        TemasPos.innerHTML = '<option value="">No se encontraron resultados</option>';
                    }
                } else {
                    TemasPos.innerHTML = '<option value="">Error al cargar los datos</option>';
                }
            };
            xhr.send();
            
        }
        function CargarListSesion() {
            const ListaPart = document.getElementById("listaAgregar");
            // Limpiar la tercera ComboBox
            ListaPart.innerHTML = "";
            // Realiza una solicitud AJAX al servlet para obtener lo pedido
            const xhr = new XMLHttpRequest();
            const contextPath = "${pageContext.request.contextPath}";
            xhr.open('GET', contextPath + '/SvAddTemaLista?_=' + new Date().getTime(), true);

            xhr.onload = function() {
                if (xhr.status === 200) {
                    // Verifica si la respuesta tiene opciones válidas
                    if (xhr.responseText.trim() !== "") {
                        ListaPart.innerHTML = xhr.responseText;
                    } else {
                        ListaPart.innerHTML = '<option value="">No se encontraron resultados</option>';
                    }
                } else {
                    ListaPart.innerHTML = '<option value="">Error al cargar los datos</option>';
                }
            };
            xhr.send();
    }
        function toggleListaPart() {
            const listaAgregar = document.getElementById("listaAgregar").value;
            const tipoSeleccionado = document.getElementById("tipoDelObjeto").value;
            const filtroSecundarioSeleccionado = document.getElementById("opcionesSeleccion").value;
            const listaParticularEspecifica = document.getElementById("TemasPos").value;
            const listaPartSelect = document.getElementById("listaPartSelect");
            const opcionesListaPart = document.getElementById("opcionesListaPart");
            const temasPosLabel = document.getElementById("temasPosLabel");
            if (tipoSeleccionado === "ListaPart") {
                listaPartSelect.style.display = "block"; // Mostrar el select
                temasPosLabel.textContent = "Listas públicas:"; // Cambiar el label
                opcionesListaPart.innerHTML="";
                const xhr = new XMLHttpRequest();
                const contextPath = "${pageContext.request.contextPath}";
                xhr.open('GET', contextPath + '/SvActualizarSelectAddTema?tipoDelObjeto=' + tipoSeleccionado+ '&filtroPrincipal='+  filtroSecundarioSeleccionado + '&listaCliente='+ listaParticularEspecifica +'&listaAgregar='+ listaAgregar + '&_=' + new Date().getTime(), true);

                xhr.onload = function() {
                    if (xhr.status === 200) {
                        // Verifica si la respuesta tiene opciones válidas
                        if (xhr.responseText.trim() !== "") {
                            opcionesListaPart.innerHTML = xhr.responseText;
                        } else {
                            opcionesListaPart.innerHTML = '<option value="">No se encontraron resultados</option>';
                        }
                    } else {
                        opcionesListaPart.innerHTML = '<option value="">Error al cargar los datos</option>';
                    }
                };
                xhr.send();
            } else {
                listaPartSelect.style.display = "none"; // Ocultar el select
                opcionesListaPart.innerHTML = ""; // Limpiar opciones
                temasPosLabel.textContent = "Temas:"; // Cambiar el label
            }
        }
        
        window.onload = function(){
            CargarListSesion();
        };
        </script>
    </head>
    <body>        
        <form action="${pageContext.request.contextPath}/SvActualizarSelectAddTema" method="POST">
            <h1>Agregar Tema a Lista</h1>
            <label for="listaAgregar">Selecciona una lista:</label>
            <select id="listaAgregar" name="listaAgregar">
              <option value="">Seleciona su Lista</option>
          </select>
        <br>
        <br>
      <label for="tipoSeleccion">Filtro:</label>
        <select id="tipoDelObjeto" name="tipoDelObjeto" onchange="toggleListaPart(); actualizarFiltrosSecundarios(); actualizarListTemas();">
            <option value="">Seleccione un filtro</option>
            <option value="Album">Album</option>
            <option value="ListaPorDef">Lista Por Defecto</option>
            <option value="ListaPart">Listas públicas de clientes</option>
        </select>
        <br>
        <br>
        <label for="opcionesSeleccion">Filtro 2:</label>
        <select id="opcionesSeleccion" name="filtroPrincipal" onclick="actualizarListTemas();">
            <option value="">Seleccione un filtro</option>
        </select>      
        <br>
        <br>
         <label for="TemasPos" id="temasPosLabel">Temas:</label>
        <select id="TemasPos" name="Temas" onchange="toggleListaPart();">
            <option value="">Seleccione un Tema</option>
        </select>
        <br>
        <br>
        <div id="listaPartSelect" style="display: none;">
          
            <label for="opcionesListaPart">Seleccione un tema</label>
          <select id="opcionesListaPart" name="opcionesListaPart" >
              <option value="">Seleccione un tema</option>
          </select>
        <br>
        <br>
        </div>
        <button type="submit">Agregar Tema</button>
        <%-- Mostrar mensaje de error si existe --%>
        <%
            String errorMessage = (String) request.getSession().getAttribute("error");
            if (errorMessage != null) {
        %>
        <p><label style="color: white;"> <%= errorMessage%> </label></p>
        <%
                request.getSession().removeAttribute("error"); // Limpiar el mensaje para que no persista
            }
        %>
        </form>
    </body>
</html>
