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
    </head>
    <body>
        
        <button onclick="window.location.href = '<%= request.getContextPath() %>/JSP/Cliente.jsp'">Home</button>
        <h1>Bienvenido a Contratar Suscripción</h1>

        <div>
            <%-- mensaje de error --%>
            <%
                String error = (String) session.getAttribute("error");
                if (error != null) {
            %>
            <div style="color: red;">
                <strong>Error:</strong> <%= error%>
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
            <button onclick="window.location.href = '<%= request.getContextPath() %>/JSP/Cliente.jsp'">Cancelar</button>
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
