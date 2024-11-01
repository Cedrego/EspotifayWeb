<%-- 
    Document   : SUS
    Created on : 31 oct. 2024, 11:01:05
    Author     : tecnologo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
    </head>
    <body>
        <h1>Bienvenido a Contratar Suscripción</h1>

        <div>
            <%-- mensaje de error --%>
            <%
                String error = (String) session.getAttribute("error");
                
                if (error != null) {
            %>
            <div style="color: white;">
                <strong>Aviso:</strong> <%= error%>
            </div>
            <%
                    // limpio el mensaje de error después de mostrarlo
                    session.removeAttribute("error");
                }
            %>
        </div>
        <form action="${pageContext.request.contextPath}/SvSUS" method="post">
            <p><label for="filtroBusqueda">Ingrese las especificaciones de su Suscripción</label>
                <select id="TipoSus" name="TipoSus" onchange="cargarMonto();" required>
                    <option value="">Seleccione la Duración</option>
                    <option value="Semanal">Semanal</option>
                    <option value="Mensual">Mensual</option>
                    <option value="Anal">Anual</option> <!-- Corregido de "Anal" a "Anual" -->
                </select>

            <p><label for="montos">Monto de su suscripción:</label>
                <span id="montoLabel">Monto de su suscripción</span>
            </p>
            <button type="submit">Confirmar</button>
        </form>
        <script>
            function cargarMonto() {
                const tipoSus = document.getElementById('TipoSus').value;
                const montoLabel = document.getElementById('montoLabel');
                if (tipoSus === "Semanal") {
                    montoLabel.innerHTML = "4.99 USD";
                } else if (tipoSus === "Mensual") {
                    montoLabel.innerHTML = "9.99 USD";
                } else if (tipoSus === "Anal") {
                    montoLabel.innerHTML = "79.99 USD";
                } else {
                    montoLabel.innerHTML = "Monto de su suscripción";
                }
            }
        </script>

    </body>
</html>
